<template>
  <div class='table'>
    <div class='container'>
      <div class="handle-box">
        <el-button type="primary" size='mini' @click="dialogVisible=true">添加歌单</el-button>
        <el-button type="primary" size='mini' @click="mulDelIds.length==0?batchDelVisible=false:batchDelVisible=true">批量删除</el-button>
        <div class="search-box">
          <el-button type="primary" size='mini' @click="searchSongList()">搜索</el-button>
          <el-input clearable v-model="searchKey" size='mini' class="handle-search" placeholder="请输入歌单名或类型"></el-input>
        </div>
      </div>
    </div>
    <el-table
      :data="songLists"
      style="width: 100%"
      border
      @selection-change="handleSelectChange">
      <el-table-column type='selection' width='40'></el-table-column>
      <el-table-column
        prop="pic"
        label="歌单图片"
        width="110"
        align='center'>
        <template slot-scope='scope'>
          <div class="songList-img">
            <img :src="getUrl(scope.row.pic)">
          </div>
          <el-upload
            ref="uploadSongList"
            :action="uploadUrl(scope.row.id,scope.row.pic)"
            :on-success="handleSongListSuccess"
            :before-upload="beforeAvatorUpload"
            :show-file-list="false">
            <el-button size="mini" type="primary">上传图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column
        prop="title"
        label="歌单名称"
        width="150"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="style"
        label="风格"
        width="70"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="简介"
        align='center'>
        <template slot-scope="scope">
          <div style="height:100px;overflow:scroll">
            {{scope.row.introduction}}
          </div>
        </template>
      </el-table-column>
      <el-table-column
        label="评论"
        width="150"
        align='center'>
        <template slot-scope="scope">
          <div class="btn-edit">
            <el-button type="primary" size='mini' @click="handleComment(scope.row)">评论管理</el-button>
          </div>
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
            <el-button type="danger" size='mini' @click="handleRemoveSongList(scope.row.id,scope.row.pic)">删除</el-button>
          </div>
          <div class="btn-manage">
            <el-button type="info" size='mini' @click="handleSongManage(scope.row.id)">歌曲管理</el-button>
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
    <el-dialog title="添加歌单" :visible.sync="dialogVisible" width='400px' center>
      <el-form :model="registerForm" ref='registerForm' label-width='90px'>
        <el-form-item prop='title' label='歌单名称：' size='mini'>
            <el-input v-model="registerForm.title" placeholder="歌单名"></el-input>
        </el-form-item>
        <el-form-item prop='style' label='风格：' size='mini'>
            <el-input v-model="registerForm.style" placeholder="类型"></el-input>
        </el-form-item>
        <el-form-item prop='introduction' label='简介：' size='mini'>
            <el-input type='textarea' v-model="registerForm.introduction" placeholder="介绍"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='dialogVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="addSongList" type='success'>确定 </el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改歌单信息" :visible.sync="editVisible" width='400px' center>
      <el-form :model="editForm" ref='editForm' label-width='60px'>
        <el-form-item prop='title' label='歌单名称：' size='mini'>
            <el-input v-model="editForm.title" placeholder="歌单名称"></el-input>
        </el-form-item>
        <el-form-item prop='style' label="风格：" size='mini'>
            <el-input v-model="editForm.style" placeholder="类型"></el-input>
        </el-form-item>
        <el-form-item prop='introduction' label='简介：' size='mini'>
            <el-input type='textarea' v-model="editForm.introduction" placeholder="介绍"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='editVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="editSave" type='success'>保存 </el-button>
      </span>
    </el-dialog>
    <el-dialog title="删除歌单" :visible.sync="delVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除!</div>  
      <span slot="footer">
        <el-button size='mini' @click='delVisible=false' >取消</el-button>
        <el-button size='mini' @click="delSongList" type='danger'>确认</el-button>
      </span>
    </el-dialog>
    <el-dialog title="批量删除歌单" :visible.sync="batchDelVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除{{mulDelIds.length}}位歌单！</div>  
      <span slot="footer">
        <el-button size='mini' @click='batchDelVisible=false' >取消</el-button>
        <el-button size='mini' @click="batchDelSongLists" type='danger'>确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {addSongListReq,getSongListByKey,editSongListInfo,removeSongListById,batchRemoveSongLists} from '../api/index';
import {mixin} from '../mixins/tips';
export default {
  mixins:[mixin],
  data(){
    return {
      dialogVisible: false,
      editVisible: false,
      delVisible: false,
      batchDelVisible:false,
      registerForm: {
        title: '',
        style: '',
        introduction: '',
      },
      editForm:{
        title: '',
        style: '',
        introduction: '',
      },
      delForm:{
        id:'',
        pic:''
      },
      songLists:[],
      searchKey:'',
      tempList: [],
      currentPage:1,
      pageSize:7,
      total:0,
      idx:-1,// 歌单的id
      mulDelIds:[],// 批量删除的id集合
      paths:[],// 批量删除时，歌单的图片路径
    }
  },
  watch:{
    searchKey:function(){
      if(this.searchKey==''){
        this.songLists=this.tempList;
        this.getSongLists(null);
      }else{
        this.songLists=[];
        for(let i=0;i<this.tempList.length;i++){
          if(this.tempList[i].title.includes(this.searchKey)||this.tempList[i].style.includes(this.searchKey)){
            this.songLists.push(this.tempList[i]);
          }
        }
      }
    }
  },
  created(){
    this.getSongLists(null);
  },
  methods:{
    // 获取所有歌单
    getSongLists(key){
      let params=new URLSearchParams();
      params.append('pageNum',this.currentPage);
      params.append('pageSize',this.pageSize);
      console.log(params.toString());
      if(key!=''&&key!=null){
        params.append('key',key);
      }
      getSongListByKey(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.songLists=[];
          this.tempList=[];
          this.total=res.data.result.songListPageInfo.total;
          this.songLists=res.data.result.songListPageInfo.list;
          this.tempList=res.data.result.songListPageInfo.list;
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 添加歌单
    addSongList(){
      let params=new URLSearchParams();
      params.append('title',this.registerForm.title);
      params.append('style',this.registerForm.style);
      params.append('pic','/img/songListPic/default.jpg');
      params.append('introduction',this.registerForm.introduction);
      addSongListReq(params)
      .then(res=>{
        if(res.data.code=="50200"){
          this.notify("添加成功","success");

          this.getSongLists(null);
        }else{
          this.notify('添加失败','danger');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      this.registerForm={};
      this.dialogVisible=false;
    },
    uploadUrl(id,pic){
      return `${this.$store.state.HOST}/songList/uploadAvatar?id=${id}&path=${pic}`;
    },
    // 获取当前页
    handleCurrentChange(val){
      this.currentPage=val;
      this.getSongLists(null);
    },
    // 搜索歌手
    searchSongList(){
      if(this.searchKey==''||this.searchKey==null){
        this.$message.error("搜索框内容不能为空");
        return;
      }
      this.getSongLists(this.searchKey);
    },
    // 修改歌手信息
    handleEdit(songList){
      this.editForm={
        id:songList.id,
        title:songList.title,
        style:songList.style,
        introduction:songList.introduction
      };
      this.editVisible=true;
    },
    // 保存修改的信息到数据库
    editSave(){
      let params =new URLSearchParams();
      params.append('id',this.editForm.id);
      params.append('title',this.editForm.title);
      params.append('style',this.editForm.style);
      params.append('introduction',this.editForm.introduction);
      editSongListInfo(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify("修改成功",'success');
          this.getSongLists(null);
        }else{
          this.notify(res.data.message,'error');
        }
        this.editVisible=false;
      })
      .catch(err=>{
        console.log(err);
      })
      
    },
    // 删除歌单
    delSongList(){
      let params=new URLSearchParams();
      params.append('id',this.delForm.id);
      params.append('pic',this.delForm.pic);
      removeSongListById(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify('删除成功','success');
          this.getSongLists(null);
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      this.delVisible=false;
    },
    // 批量删除歌单
    batchDelSongLists(){
      let params=new URLSearchParams();
      params.append("ids",this.mulDelIds);
      params.append("paths",this.paths);
      batchRemoveSongLists(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify("删除成功",'success');
          this.getSongLists(null);
        }else{
          this.notify(res.data.message,'error');
        }
      }).catch(err=>{
        console.log(err);
      })

      this.batchDelVisible=false;
    },
    // 歌单图片上传成功后
    handleSongListSuccess(res){
        console.log(res);
        if(res.code=='50200'){
            this.notify("上传图片成功",'success');
            this.getSongLists(null);
            console.log(this.$refs);
            this.$refs.uploadSongList.clearFiles();
        }else{
            this.notify(res.message,'error');
        }
    },
    // 跳转到歌曲管理
    handleSongManage(id,name){
      this.$router.push({path:`/listSong`,query:{songListId:id}});
    },
    // 跳转到歌单评论页面
    handleComment(item){
      this.$router.push({path:'/comment',query:{songListId:item.id}});
    }
    
  },

}
</script>

<style scoped>
.handle-box{
  margin-bottom:20px;
}

.songList-img{
  width:100%;
  height:80px;
  margin-bottom:5px;
  /* 超出的部分隐藏 */
  overflow: hidden;
}
.songList-img img{
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

</style>