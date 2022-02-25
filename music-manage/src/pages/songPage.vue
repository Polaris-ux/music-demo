<template>
  <div class='table'>
    <div class='container'>
      <div class="crumbs">
        <i class="el-icon-tickets"></i>歌曲信息
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
      @selection-change="handleSelectChange">
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
          <div class="play" @click="setSongUrl(scope.row.url,scope.row.name)">
            <div v-if="toggle==scope.row.name">
              <svg class="icon">
                <use xlink:href="#icon-zanting"></use>
              </svg>
            </div>
            <div v-if="toggle!=scope.row.name">
              <svg class="icon">
                <use xlink:href="#icon-bofanganniu"></use>
              </svg>
            </div>

          </div>
          <el-upload
            ref="uploadAvator"
            :action="uploadUrl(scope.row)"
            :on-success="handleAvatorSuccess"
            :before-upload="beforeAvatorUpload">
            <el-button size="mini" type="primary">上传图片</el-button>
          </el-upload>
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
        prop="lyric"
        label="歌词"
        width="700"
        align='center'>
        <template slot-scope="scope">
          <ul style="height:100px;overflow:scroll">
            <li v-for="(item,index) in transferLyric(scope.row.lyric)" :key="index">
              {{item}}
            </li>
          </ul>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="150"
        align='center'>
        <template slot-scope="scope">
          <div class="btn-edit">
            <el-button type="primary" size='mini' @click="handleEdit(scope.row)">编辑</el-button>
          </div>
          <div class="btn-del">
            <el-button type="danger" size='mini' @click="handleRemoveSong(scope.row)">删除</el-button>
          </div>
          <div class="btn-manage">
            <el-upload
              ref="uploadSongSource"
              :action="uploadSongUrl(scope.row)"
              :on-success="handleMusicUploadSuccess"
              :before-upload="handleMusicUpload">
              <el-button size="mini" type="primary">修改音频</el-button>
            </el-upload>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination background 
      :total="total" 
      :current-page="currentPage" 
      :page-size="pageSize" 
      @current-change="handleCurrentChange" 
      layout="total,prev,pager,next">

      </el-pagination>
    </div>
    <el-dialog title="添加歌曲" :visible.sync="dialogVisible" width='400px' center>
      <el-form :model="songInfo" ref='songInfo' label-width='60px' id="add-song">
        <el-form-item prop='name' label='歌名：' size='mini'>
            <el-input v-model="songInfo.name" placeholder="歌名"></el-input>
        </el-form-item>
        <el-form-item prop='introduction' label='专辑：' size='mini'>
            <el-input v-model="songInfo.introduction" placeholder="介绍"></el-input>
        </el-form-item>
        <el-form-item prop='lyric' label='歌词：' size='mini'>
            <el-input type='textarea' v-model="songInfo.lyric" placeholder="歌词"></el-input>
        </el-form-item>
        <el-form-item prop='file' label='歌曲：' size='mini'>
            <el-upload 
            action="#"
            ref="upload"
            :before-upload="handleMusicUpload"
            :on-change="uploadMusicChange"
            :http-request="httpRequest"
            multiple
            :limit="1"
            :file-list="fileList"
            :on-exceed="handleExceed" 
            >
              <el-button type="primary" size="mini">点我上传</el-button>
            </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='dialogVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="addSong" type='success'>确定 </el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改歌曲信息" :visible.sync="editVisible" width='400px' center>
      <el-form :model="editForm" ref='editForm' label-width='60px'>
        <el-form-item prop='name' label='歌名：' size='mini'>
            <el-input v-model="editForm.name" placeholder="歌名"></el-input>
        </el-form-item>
        <el-form-item prop='introduction' label='专辑：' size='mini'>
            <el-input v-model="editForm.introduction" placeholder="专辑名"></el-input>
        </el-form-item>
        <el-form-item prop='lyric' label='歌词：' size='mini'>
            <el-input type='textarea' v-model="editForm.lyric" placeholder="歌词"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='editVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="editSave" type='success'>保存 </el-button>
      </span>
    </el-dialog>
    <el-dialog title="删除歌曲" :visible.sync="delVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除!</div>  
      <span slot="footer">
        <el-button size='mini' @click='editVisible=false' >取消</el-button>
        <el-button size='mini' @click="delSong" type='danger'>确认</el-button>
      </span>
    </el-dialog>
    <el-dialog title="批量删除歌手" :visible.sync="batchDelVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除{{mulDelIds.length}}首歌曲！</div>  
      <span slot="footer">
        <el-button size='mini' @click='batchDelVisible=false' >取消</el-button>
        <el-button size='mini' @click="batchDelSong" type='danger'>确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mixin} from '../mixins/tips';
import {mapGetters} from 'vuex';
import '@/assets/js/iconfont.js';
import {getSongByPage,addSongReq,editSongInfo,removeSongById,batchRemoveSongs} from '../api/index';

export default {
  mixins:[mixin],
  data(){
    return {
      dialogVisible: false,
      editVisible: false,
      delVisible: false,
      batchDelVisible:false,
      songInfo: {
        name: '',
        lyric: '',
        introduction: '',
        pic: '/img/songPic/boy.jpg',
      },
      editForm:{
        name: '',
        introduction: '',
        lyric: ''
      },
      delForm:{

      },
      singerId:-1,
      singerName:'',
      songList:[],
      searchKey:'',
      tempList: [],
      currentPage:1,
      pageSize:7,
      total:0,
      mulDelIds:[],// 批量删除的id集合
      paths:[],// 批量删除的path集合
      fileList:[], // 上传歌曲文件的集合,
      toggle:false,// 播放按钮状态
    }
  },
  computed:{
    ...mapGetters([
      'isPlay'
    ])
  },
  watch:{
    searchKey:function(){
      if(this.searchKey==''){
        this.songList=this.tempList;
        this.getSongs(null);
      }else{
        this.songList=[];
        for(let i=0;i<this.tempList.length;i++){
          if(this.tempList[i].name.includes(this.searchKey)){
            this.songList.push(this.tempList[i]);
          }
        }
      }
    },
    isPlay:function(){
      if(!this.isPlay){
        this.toggle=false;
      }
    }
  },
  created(){
    this.singerId=this.$route.query.id;
    this.singerName=this.$route.query.name;
    this.getSongs(null);
  },
  destroyed() {
    // 跳转页面时，暂停播放
    this.$store.commit('setIsPlay',false);
  },
  methods:{
    // 获取所有歌手
    getSongs(key){
      let params=new URLSearchParams();
      params.append('pageNum',this.currentPage);
      params.append('pageSize',this.pageSize);
      params.append('singerId',this.singerId);
      console.log(params.toString());
      if(key!=''&&key!=null){
        params.append('key',key);
      }
      getSongByPage(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.songList=[];
          this.tempList=[];
          this.total=res.data.result.songPageInfo.total;
          this.songList=res.data.result.songPageInfo.list;
          this.tempList=res.data.result.songPageInfo.list;
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 添加歌手
    addSong(){
      let dataForm=new FormData();
      dataForm.append('name',this.singerName+"-"+this.songInfo.name);
      dataForm.append('lyric',this.songInfo.lyric);
      dataForm.append('singerId',this.singerId);
      dataForm.append('introduction',this.songInfo.introduction);
      dataForm.append('pic','/img/songPic/boy.jpg');
      if(this.fileList.length>0){
        dataForm.append('file',this.fileList[0].file);
      }else{
        this.$message.error("还未上传文件");
        return;
      }
      let config={
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
      addSongReq(dataForm,config)
      .then(res=>{
        if(res.data.code == '50200'){
          this.notify('添加歌曲成功','success');
          this.getSongs(null);
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      // 清除表单信息
      this.$refs.songInfo.resetFields();
      // 清空上传列表
      this.$refs.upload.clearFiles();
      this.fileList=[];
      this.dialogVisible=false;
    },
    // 获取当前页
    handleCurrentChange(val){
      this.currentPage=val;
      this.getSongs(null);
    },
    // 搜索歌曲
    searchSong(){
      if(this.searchKey==''||this.searchKey==null){
        this.$message.error("搜索框内容不能为空");
        return;
      }
      this.getSongs(this.searchKey);
    },
    // 修改歌曲信息
    handleEdit(song){
      this.editForm.id=song.id;
      this.editForm.singerId=song.singerId;
      this.editForm.name=song.name;
      this.editForm.introduction=song.introduction;
      this.editForm.pic=song.pic;
      this.editForm.url=song.url;
      this.editForm.lyric=song.lyric;
      this.editVisible=true;
    },
    // 保存修改的信息到数据库
    editSave(){
      let params =new URLSearchParams();
      params.append('name',this.editForm.name);
      params.append('introduction',this.editForm.introduction);
      params.append('lyric',this.editForm.lyric);
      params.append('id',this.editForm.id);
      // 是否包含歌词修改
      params.append('withBlobs',"true");
      editSongInfo(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify('修改成功','success');
          this.getSongs(null);
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      this.$refs.editForm.resetFields();
      this.editVisible=false;
    },
    // 删除歌歌曲
    delSong(){
      let params=new URLSearchParams();
      params.append('id',this.delForm.id);
      params.append('pic',this.delForm.pic);
      params.append('url',this.delForm.url);
      removeSongById(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify('删除成功','success');
          this.getSongs(null);
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      this.delVisible=false;
    },
      // 批量删除歌曲
    batchDelSong(){
      let params=new URLSearchParams();
      params.append("ids",this.mulDelIds);
      params.append("paths",this.paths);
      batchRemoveSongs(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.notify("删除成功",'success');
          this.getSongs(null);
        }else{
          this.notify(res.data.message,'error');
        }
      }).catch(err=>{
        console.log(err);
      })

      this.batchDelVisible=false;
    },
    transferLyric(text){
      let lines=text.split("\n");
      let result=[];
      let pattern=/\[\d{2}:\d{2}.(\d{2}|\d{3})\]/g;
      for(let item of lines){
        let words=item.replace(pattern,'');
        result.push(words);
      }
      return result;
    },
    setSongUrl(url,name){
      this.toggle=name;
      this.$store.commit('setUrl',this.$store.state.HOST+url);
      if(this.isPlay){
        this.$store.commit('setIsPlay',false);
      }else{
        this.$store.commit('setIsPlay',true);
      }
    }
    ,
    // 上传歌曲时需要用到的方法
    uploadMusicChange(fileList){
        this.songInfo.file=fileList[0];
    },
    uploadUrl(song){
      return `${this.$store.state.HOST}/song/uploadStaticResource?id=${song.id}&path=${song.pic}`;
    },

    uploadSongUrl(song){
      return `${this.$store.state.HOST}/song/uploadStaticResource?id=${song.id}&path=${song.url}`;
    },
    
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