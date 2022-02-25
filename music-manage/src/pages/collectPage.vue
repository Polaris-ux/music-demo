<template>
  <div class='table'>
    <div class='container'>
      <div class="crumbs">
        <i class="el-icon-tickets"></i>歌单歌曲信息
      </div>  
      <div class="handle-box">
        <el-button type="primary" size='mini' @click="mulDelIds.length==0?batchDelVisible=false:batchDelVisible=true">批量删除</el-button>
        <div class="search-box">
          <el-button type="primary" size='mini' @click="searchSong()">搜索</el-button>
          <el-input v-model="searchKey" size='mini' class="handle-search" placeholder="请输入歌名"></el-input>
        </div>
      </div>
    </div>
    <el-table
      :data="songList"
      style="width: 100%"
      border
      @selection-change="handleCollectSelectChange">
      <el-table-column type='selection' width='40'></el-table-column>
      <el-table-column
        prop="pic"
        label="歌曲图片"
        width="110"
        align='center'>
        <template slot-scope='scope'>
          <div class="song-img">
            <img :src="getUrl(scope.row.pic)">
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="歌名"
        width="110"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="专辑"
        style="width:100%;overflow:scroll"
        align='center'>
      </el-table-column>
      <el-table-column
        label="操作"
        width="150"
        align='center'>
        <template slot-scope="scope">
          <div class="btn-del">
            <el-button type="danger" size='mini' @click="handleRemoveCollect(scope.row.id)">删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="删除收藏歌曲" :visible.sync="delVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除!</div>  
      <span slot="footer">
        <el-button size='mini' @click='editVisible=false' >取消</el-button>
        <el-button size='mini' @click="delCollect" type='danger'>确认</el-button>
      </span>
    </el-dialog>
    <el-dialog title="批量删除收藏歌曲" :visible.sync="batchDelVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除{{mulDelIds.length}}首收藏歌曲！</div>  
      <span slot="footer">
        <el-button size='mini' @click='batchDelVisible=false' >取消</el-button>
        <el-button size='mini' @click="batchDelListSong" type='danger'>确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mixin} from '../mixins/tips';
import {getCollects,batchGetSongs,delByUserAndSong,batchDelByUserAndSong} from '../api/index';

export default {
  mixins:[mixin],
  data(){
    return {
      delVisible: false,
      batchDelVisible:false,
      userId:-1,
      songList:[],
      searchKey:'',
      tempList: [],
      mulDelIds:[],// 批量删除的id集合
      songIds:[],
      songId:-1,
    }
  },
  computed:{
  },
  watch:{
    searchKey:function(){
      if(this.searchKey==''){
        this.songList=this.tempList;
        this.getData();
      }else{
        this.songList=[];
        for(let i=0;i<this.tempList.length;i++){
          if(this.tempList[i].name.includes(this.searchKey)){
            this.songList.push(this.tempList[i]);
          }
        }
      }
    }
  },
  created(){
    this.userId=this.$route.query.userId;
    this.getSongIds();
  },
  methods:{
    // 获取所有歌曲信息
    getData(){
      let params=new URLSearchParams();
      params.append("songIds",this.songIds);
      console.log(this.songIds);
      batchGetSongs(params)
      .then(res=>{
          if(res.data.code == '50200'){
              this.songList=[];
              this.tempList=[];
              this.songList=res.data.result.songList;
              this.tempList=res.data.result.songList
          }else{
              this.notify(res.data.message,'error');
          }
      })
      .catch(err=>{
          console.log(err);
      })
    },
    // 获取歌曲id
    getSongIds(){
       let params=new URLSearchParams();
       params.append("userId",this.userId);
       getCollects(params)
       .then(res=>{
          if(res.data.code=='50200'){
              let collects=res.data.result.collects;
              this.songIds=[];
              for(let collect of collects){
                  if(collect.type==0){
                      this.songIds.push(collect.songId);
                  }
              }
              this.getData();
          }else{
              this.notify("获取歌曲信息失败",'error');
          }
       })
       .catch(err=>{
          console.log(err);
       })
    },
    // 搜索歌曲
    searchSong(){
      if(this.searchKey==''||this.searchKey==null){
        this.$message.error("搜索框内容不能为空");
        return;
      }
    },
    // 删除歌曲映射
    delCollect(){
      let params=new URLSearchParams();
      params.append('songId',this.songId);
      params.append("userId",this.userId);
      delByUserAndSong(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify('删除成功','success');
          this.getSongIds();
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      this.delVisible=false;
    },
    handleRemoveCollect(id){
      this.songId=id;
      this.delVisible=true;
    },
    handleCollectSelectChange(songList){
        this.mulDelIds=[];
        for(let item of songList){
            this.mulDelIds.push(item.id);
        }
    },
    // 批量删除歌曲
    batchDelListSong(){
      let params=new URLSearchParams();
      params.append("songIds",this.mulDelIds);
      params.append("userId",this.userId);
      batchDelByUserAndSong(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.notify("删除成功",'success');
          this.getSongIds();
        }else{
          this.notify(res.data.message,'error');
        }
      }).catch(err=>{
        console.log(err);
      })

      this.batchDelVisible=false;
    }
  }

}
</script>

<style scoped>
.handle-box{
  margin-bottom:20px;
}

.song-img{
  width:100%;
  height:80px;
  margin-bottom:5px;
  /* 超出的部分隐藏 */
  overflow: hidden;
}
.song-img img{
  object-fit: cover;
  width:80px;
  height:80px;
  border-radius:5px;
}

.search-box{
  width:470px;
  display: block;
  float: right;

}
.handle-search{
  width:400px;
}
.search-box button{
  float: right;
}

.pagination{
  display: flex;
  justify-content:center;
}

.btn-edit button{
  margin-bottom:4px;
  width:80px;
}
.btn-del button{
  margin-bottom:4px;
  width:80px;
}

.play{
  /* 绝对定位 */
  position: absolute;
  /* 在最上方 */
  z-index: 100;
  width: 80px;
  height: 80px;
  /* 自由布局 */
  display: flex;
  align-items: center;
  justify-content:center;
  cursor: pointer;
  top:15px;
  left:15px;
}

.icon{
  /* em:附控件的大小 */
  width: 2em;
  height:2em;
  color:aliceblue;
  fill:currentColor;
  overflow: hidden;

}

</style>