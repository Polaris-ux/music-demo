<template>
  <div class='table'>
    <div class='container'>
      <div class="crumbs">
        <i class="el-icon-tickets"></i>歌单歌曲信息
      </div>  
      <div class="handle-box">
        <el-button type="primary" size='mini' @click="dialogVisible=true">添加歌曲</el-button>
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
      @selection-change="handleListSongSelectChange">
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
            <el-button type="danger" size='mini' @click="handleRemoveListSong(scope.row.id)">删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="添加歌曲" :visible.sync="dialogVisible" width='400px' center>
      <el-form :model="registerForm" ref='registerForm' label-width='80px' id="add-song">
        <el-form-item prop='singerName' label='歌手名：' size='mini'>
            <el-input v-model="registerForm.singerName" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop='songName' label='歌名：' size='mini'>
            <el-input v-model="registerForm.songName" placeholder="歌名"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='dialogVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="addListSong" type='success'>确定 </el-button>
      </span>
    </el-dialog>
    <el-dialog title="删除歌曲" :visible.sync="delVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除!</div>  
      <span slot="footer">
        <el-button size='mini' @click='editVisible=false' >取消</el-button>
        <el-button size='mini' @click="delListSong" type='danger'>确认</el-button>
      </span>
    </el-dialog>
    <el-dialog title="批量删除歌曲" :visible.sync="batchDelVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除{{mulDelIds.length}}首歌曲！</div>  
      <span slot="footer">
        <el-button size='mini' @click='batchDelVisible=false' >取消</el-button>
        <el-button size='mini' @click="batchDelListSong" type='danger'>确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mixin} from '../mixins/tips';
import {getSongIdsBySongList,batchGetSongs,getSongIdByName,addListSongReq,removeListSong,batchRemoveListSong} from '../api/index';

export default {
  mixins:[mixin],
  data(){
    return {
      dialogVisible: false,
      delVisible: false,
      batchDelVisible:false,
      registerForm: {
        singerName: '',
        songName:''
      },
      songListId:-1,
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
    this.songListId=this.$route.query.songListId;
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
       getSongIdsBySongList(this.songListId)
       .then(res=>{
          if(res.data.code=='50200'){
              this.songIds=res.data.result.songIds;
              this.getData();
          }else{
              this.notify("获取歌曲信息失败",'error');
          }
       })
       .catch(err=>{
          console.log(err);
       })
    },
    // 添加歌曲
    addListSong(){
      this.awareSongIdByName(this.registerForm.singerName+"-"+this.registerForm.songName);
      this.dialogVisible=false;
    },
    // 
    addTemp(songId){
        let params=new URLSearchParams();
        if(songId!=null){
            params.append("songListId",this.songListId);
            params.append("songId",songId);
            addListSongReq(params)
            .then(res=>{
              if(res.data.code=='50200'){
                  this.notify("添加成功",'success');
                  this.getSongIds();
              }else{
                  this.notify(res.data.message,'error');
              }
            })
            .catch(err=>{
              console.log(err);
            })
            this.registerForm={};
        }else{
            this.notify("添加失败",'error');
        }
    },
    // 判断该歌曲是否已经存在
    isExist(name){
        for(let song of this.songList){
            if(song.name==name){
                return true;
            }
        }
        return false;
    },
    // 通过歌名获区歌曲Id
    awareSongIdByName(name){
        if(this.isExist(name)){
            this.notify("该歌曲已经存在","success");
            this.registerForm={};
            return;
        }
        getSongIdByName(name)
        .then(res=>{
            if(res.data.code=='50200'){
                this.addTemp(res.data.result.songId);
            }else{
                this.notify(res.data.message,'error');
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
    delListSong(){
      let params=new URLSearchParams();
      params.append('songId',this.songId);
      params.append("songListId",this.songListId);
      removeListSong(params)
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
    handleRemoveListSong(id){
      this.songId=id;
      this.delVisible=true;
    },
    handleListSongSelectChange(songList){
        this.mulDelIds=[];
        for(let item of songList){
            this.mulDelIds.push(item.id);
        }
    },
    // 批量删除歌曲
    batchDelListSong(){
      let params=new URLSearchParams();
      params.append("songIds",this.mulDelIds);
      params.append("songListId",this.songListId);
      batchRemoveListSong(params)
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