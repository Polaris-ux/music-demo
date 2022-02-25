<template>
  <div class='table'>
    <div class='container'>
      <div class="handle-box">
        <el-button type="primary" size='mini' @click="dialogVisible=true">添加歌手</el-button>
        <el-button type="primary" size='mini' @click="mulDelIds.length==0?batchDelVisible=false:batchDelVisible=true">批量删除</el-button>
        <div class="search-box">
          <el-button type="primary" size='mini' @click="searchSinger()">搜索</el-button>
          <el-input v-model="searchKey" size='mini' class="handle-search" placeholder="请输入歌手名"></el-input>
        </div>
      </div>
    </div>
    <el-table
      :data="singerList"
      style="width: 100%"
      border
      @selection-change="handleSelectChange">
      <el-table-column type='selection' width='40'></el-table-column>
      <el-table-column
        prop="pic"
        label="歌手图片"
        width="110"
        align='center'>
        <template slot-scope='scope'>
          <div class="singer-img">
            <img :src="getUrl(scope.row.pic)">
          </div>
          <el-upload
            ref="uploadSingerAvator"
            :action="uploadUrl(scope.row.id)"
            :on-success="handleAvatorSuccess"
            :before-upload="beforeAvatorUpload">
            <el-button size="mini" type="primary">上传图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="歌手姓名"
        width="110"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        width="60"
        align='center'>
        <template slot-scope="scope">
          {{changeSex(scope.row.sex)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="birth"
        label="出生日期"
        width="110"
        align='center'>
        <template slot-scope="scope">
          {{awareBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="location"
        label="祖籍"
        width="70"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="简介"
        style="width:100%;overflow:scroll"
        align='center'>
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
            <el-button type="danger" size='mini' @click="handleRemove(scope.row.id)">删除</el-button>
          </div>
          <div class="btn-manage">
            <el-button type="info" size='mini' @click="handleSongManage(scope.row.id,scope.row.name)">歌曲管理</el-button>
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
    <el-dialog title="添加歌手" :visible.sync="dialogVisible" width='400px' center>
      <el-form :model="registerForm" ref='registerForm' label-width='60px'>
        <el-form-item prop='name' label='性名：' size='mini'>
            <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop='sex' label='性别：' size='mini'>
            <el-radio-group v-model="registerForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
              <el-radio :label="2">组合</el-radio>
              <el-radio :label="3">不明</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop='birth' label="生日：" size='mini'>
          <el-date-picker type='date' v-model="registerForm.birth" placeholder="选择日期" style='width:100%'></el-date-picker>
        </el-form-item>
        <el-form-item prop='location' label='地区：' size='mini'>
            <el-input v-model="registerForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop='introduction' label='简介：' size='mini'>
            <el-input type='textarea' v-model="registerForm.introduction" placeholder="介绍"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='dialogVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="addSinger" type='success'>确定 </el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改歌手信息" :visible.sync="editVisible" width='400px' center>
      <el-form :model="editForm" ref='editForm' label-width='60px'>
        <el-form-item prop='name' label='性名：' size='mini'>
            <el-input v-model="editForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop='sex' label='性别：' size='mini'>
            <el-radio-group v-model="editForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
              <el-radio :label="2">组合</el-radio>
              <el-radio :label="3">不明</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop='birth' label="生日：" size='mini'>
          <el-date-picker type='date' v-model="editForm.birth" placeholder="选择日期" style='width:100%'></el-date-picker>
        </el-form-item>
        <el-form-item prop='location' label='地区：' size='mini'>
            <el-input v-model="editForm.location" placeholder="地区"></el-input>
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
    <el-dialog title="删除歌手" :visible.sync="delVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除!</div>  
      <span slot="footer">
        <el-button size='mini' @click='delVisible=false' >取消</el-button>
        <el-button size='mini' @click="delSinger" type='danger'>确认</el-button>
      </span>
    </el-dialog>
    <el-dialog title="批量删除歌手" :visible.sync="batchDelVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除{{mulDelIds.length}}位歌手！</div>  
      <span slot="footer">
        <el-button size='mini' @click='batchDelVisible=false' >取消</el-button>
        <el-button size='mini' @click="batchDelSinger" type='danger'>确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {insertSinger,editSingerInfo,getSingersByPage,removeSinger,batchRemoveSingers} from '../api/index';
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
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: '',
      },
      editForm:{
        id:'',
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: '',
      },
      singerList:[],
      searchKey:'',
      tempList: [],
      currentPage:1,
      pageSize:7,
      total:0,
      idx:-1,// 歌手的id
      mulDelIds:[],// 批量删除的id集合
    }
  },
  watch:{
    searchKey:function(){
      if(this.searchKey==''){
        this.singerList=this.tempList;
        this.getSingers(null);
      }else{
        this.singerList=[];
        for(let i=0;i<this.tempList.length;i++){
          if(this.tempList[i].name.includes(this.searchKey)){
            this.singerList.push(this.tempList[i]);
          }
        }
      }
    }
  },
  created(){
    this.getSingers(null);
  },
  methods:{
    // 获取所有歌手
    getSingers(key){
      let params=new URLSearchParams();
      params.append('pageNum',this.currentPage);
      params.append('pageSize',this.pageSize);
      console.log(params.toString());
      if(key!=''&&key!=null){
        params.append('key',key);
      }
      getSingersByPage(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.singerList=[];
          this.tempList=[];
          this.total=res.data.result.pageInfo.total;
          this.singerList=res.data.result.pageInfo.list;
          this.tempList=res.data.result.pageInfo.list;
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 添加歌手
    addSinger(){
      let params=new URLSearchParams();
      params.append('name',this.registerForm.name);
      params.append('sex',this.registerForm.sex);
      params.append('birth',this.registerForm.birth);
      params.append('pic','/img/singerPic/default.jpg');
      params.append('location',this.registerForm.location);
      params.append('introduction',this.registerForm.introduction);
      insertSinger(params)
      .then(res=>{
        if(res.data.code=="50200"){
          this.notify("添加成功","success");

          this.getSingers(null);
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
    uploadUrl(id){
      return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`;
    },
    // 获取当前页
    handleCurrentChange(val){
      this.currentPage=val;
      this.getSingers(null);
    },
    // 搜索歌手
    searchSinger(){
      if(this.searchKey==''||this.searchKey==null){
        this.$message.error("搜索框内容不能为空");
        return;
      }
      this.getSingers(this.searchKey);
    },
    // 修改歌手信息
    handleEdit(singer){
      this.editForm.id=singer.id;
      this.editForm.name=singer.name;
      this.editForm.sex=singer.sex;
      this.editForm.pic=singer.pic;
      this.editForm.birth=new Date(singer.birth);
      this.editForm.location=singer.location;
      this.editForm.introduction=singer.introduction;
      this.editVisible=true;
    },
    // 保存修改的信息到数据库
    editSave(){
      let params =new URLSearchParams();
      params.append('id',this.editForm.id);
      params.append('name',this.editForm.name);
      params.append('sex',this.editForm.sex);
      params.append('birth',this.editForm.birth);
      params.append('location',this.editForm.location);
      params.append('introduction',this.editForm.introduction);
      editSingerInfo(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify("修改成功",'success');
          this.getSingers(null);
        }else{
          this.notify(res.data.message,'error');
        }
        this.editVisible=false;
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 删除歌手
    delSinger(){
      removeSinger(this.idx)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify('删除成功','success');
          this.getSingers(null);
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      this.delVisible=false;
    },
      // 批量删除歌手
    batchDelSinger(){
      let params=new URLSearchParams();
      params.append("ids",this.mulDelIds);
      batchRemoveSingers(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify("删除成功",'success');
          this.getSingers(null);
        }else{
          this.notify(res.data.message,'error');
        }
      }).catch(err=>{
        console.log(err);
      })

      this.batchDelVisible=false;
    },
    // 跳转到歌曲管理
    handleSongManage(id,name){
      this.$router.push({path:`/song`,query:{id,name}});
    }
    
  },

}
</script>

<style scoped>
.handle-box{
  margin-bottom:20px;
}

.singer-img{
  width:100%;
  height:80px;
  margin-bottom:5px;
  /* 超出的部分隐藏 */
  overflow: hidden;
}
.singer-img img{
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