<template>
  <div class='table'>
    <div class='container'>
      <div class="handle-box">
        <el-button type="primary" size='mini' @click="handleAddUser">添加用户</el-button>
        <el-button type="primary" size='mini' @click="mulDelIds.length==0?batchDelVisible=false:batchDelVisible=true">批量删除</el-button>
        <div class="search-box">
          <el-button type="primary" size='mini' @click="searchConsumer()">搜索</el-button>
          <el-input v-model="searchKey" size='mini' class="handle-search" placeholder="请输入用户名"></el-input>
        </div>
      </div>
    </div>
    <el-table
      :data="consumerList"
      style="width: 100%"
      border
      @selection-change="handleSelectChange">
      <el-table-column type='selection' width='40'></el-table-column>
      <el-table-column
        prop="avatar"
        label="用户头像"
        width="110"
        align='center'>
        <template slot-scope='scope'>
          <div class="user-img">
            <img :src="getUrl(scope.row.avator)">
          </div>
          <el-upload
            ref="uploadUserAvator"
            :action="uploadUrl(scope.row.id,scope.row.avator)"
            :on-success="handleConsumerAvator"
            :before-upload="beforeAvatorUpload"
            :show-file-list="false"
            >
            <el-button size="mini" type="primary">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="110"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="introduction"
        label="昵称"
        width="110"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="password"
        label="密码"
        width="140"
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
        prop="phoneNum"
        label="电话号码"
        width="150"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱"
        width="150"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="birth"
        label="出生日期"
        width="130"
        align='center'>
        <template slot-scope="scope">
          {{awareBirth(scope.row.birth)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="location"
        label="祖籍"
        width="100"
        align='center'>
      </el-table-column>
      <el-table-column
        label="操作"
        style="width:100%;overflow:scroll"
        align='center'>
        <template slot-scope="scope">
          <div class="btn-edit">
            <el-button type="primary" size='mini' @click="handleEdit(scope.row)">编辑</el-button>
          </div>
          <div class="btn-del">
            <el-button type="danger" size='mini' @click="handleRemoveConsumer(scope.row)">删除</el-button>
          </div>
          <div class="btn-manage">
            <el-button type="info" size='mini' @click="turnToCollectPage(scope.row.id)">收藏歌曲</el-button>
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
    <el-dialog title="添加用户" :visible.sync="dialogVisible" width='450px' center>
      <el-form :model="registerForm" ref='registerForm' label-width='100px' :rules="rules">
        <el-form-item prop='username' label='用户名：' size='mini'>
            <el-input clearable v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop='password' label='密码：' size='mini'>
            <el-input :type="passw" v-model="registerForm.password" placeholder="密码">
              <!-- input中加图标必须要有slot="suffix"属性，不然无法显示图标 -->
              <i slot="suffix" :class="icon" @click="showPass"></i>
            </el-input>
        </el-form-item>
        <el-form-item prop='introduction' label='昵称：' size='mini'>
            <el-input v-model="registerForm.introduction" placeholder="昵称"></el-input>
        </el-form-item>
        <el-form-item prop='sex' label='性别：' size='mini'>
            <el-radio-group v-model="registerForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop='birth' label="生日：" size='mini'>
          <el-date-picker type='date' v-model="registerForm.birth" placeholder="选择日期" style='width:100%'></el-date-picker>
        </el-form-item>
        <el-form-item prop='location' label='地区：' size='mini'>
            <el-input v-model="registerForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop='phoneNum' label='手机号码：' size='mini'>
            <el-input v-model="registerForm.phoneNum" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop='email' label='邮箱：' size='mini'>
            <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='dialogVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="addConsumer" type='success'>确定 </el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改用户信息" :visible.sync="editVisible" width='400px' center>
      <el-form :model="editForm" ref='editForm' label-width='60px'>
        <el-form-item prop='username' label='用户名：' size='mini'>
            <el-input v-model="editForm.username" placeholder="用户名："></el-input>
        </el-form-item>
        <el-form-item prop='introduction' label='昵称：' size='mini'>
            <el-input v-model="editForm.introduction" placeholder="昵称："></el-input>
        </el-form-item>
        <el-form-item prop='password' label='密码：' size='mini'>
            <el-input v-model="editForm.password" placeholder="密码："></el-input>
        </el-form-item>
        <el-form-item prop='sex' label='性别：' size='mini'>
            <el-radio-group v-model="editForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item prop='birth' label="生日：" size='mini'>
          <el-date-picker type='date' v-model="editForm.birth" placeholder="选择日期" style='width:100%'></el-date-picker>
        </el-form-item>
        <el-form-item prop='location' label='地区：' size='mini'>
            <el-input v-model="editForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop='phoneNum' label='手机号码：' size='mini'>
            <el-input v-model="editForm.phoneNum" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop='email' label='邮箱：' size='mini'>
            <el-input v-model="editForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size='mini' @click='editVisible=false' type="danger">取消</el-button>
        <el-button size='mini' @click="editSave" type='success'>保存 </el-button>
      </span>
    </el-dialog>
    <el-dialog title="删除用户" :visible.sync="delVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除!</div>  
      <span slot="footer">
        <el-button size='mini' @click='delVisible=false' >取消</el-button>
        <el-button size='mini' @click="delConsumer" type='danger'>确认</el-button>
      </span>
    </el-dialog>
    <el-dialog title="批量删除歌手" :visible.sync="batchDelVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除{{mulDelIds.length}}位用户！</div>  
      <span slot="footer">
        <el-button size='mini' @click='batchDelVisible=false' >取消</el-button>
        <el-button size='mini' @click="batchDelConsumer" type='danger'>确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {getConsumersBykey,addUser,deleteConsumer,batchRemoveConsumer,editConsumer} from '../api/index';
import {mixin} from '../mixins/tips';
export default {
  mixins:[mixin],
  data(){
    return {
      dialogVisible: false,
      editVisible: false,
      delVisible: false,
      batchDelVisible:false,
      icon:"el-input__icon el-icon-view",
      passw:"password",
      registerForm: {
        username: '',
        password:'',
        sex: '',
        birth: '',
        location: '',
        introduction: '',
        phoneNum:'',
        email: ''
      },
      editForm:{
        id:'',
        username: '',
        password:'',
        sex: '',
        birth: '',
        location: '',
        introduction: '',
        phoneNum:'',
        email: ''
      },
      delForm:{
        id:-1,
        pic:""
      },
      consumerList:[],
      searchKey:'',
      tempList: [],
      currentPage:1,
      pageSize:7,
      total:0,
      idx:-1,// 用户的id
      mulDelIds:[],// 批量删除的id集合
      paths:[],
      rules:{
        username:[
          {required:true,message:"请输入用户名",trigger:"blur"}
        ],
        password:[
          {required:true,message:"请输入密码",trigger:"blur"},
        ],
        phoneNum:[
          {required:true,message:"请输入手机号",trigger:"blur"}
        ],
        email:[
          {required:true,message:"请输入邮箱",trigger:"blur"},
          {type:'email',message:"请输入正确的邮箱格式",trigger:['blur','change']}
        ]
      }
    }
  },
  watch:{
    searchKey:function(){
      if(this.searchKey==''){
        this.consumerList=this.tempList;
        this.getConsumers(null);
      }else{
        this.consumerList=[];
        for(let i=0;i<this.tempList.length;i++){
          if(this.tempList[i].username.includes(this.searchKey)){
            this.consumerList.push(this.tempList[i]);
          }
        }
      }
    }
  },
  created(){
    this.getConsumers(null);
  },
  methods:{
    // 获取所有歌手
    getConsumers(key){
      let params=new URLSearchParams();
      params.append('pageNum',this.currentPage);
      params.append('pageSize',this.pageSize);
      console.log(params.toString());
      if(key!=''&&key!=null){
        params.append('key',key);
      }
      getConsumersBykey(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.consumerList=[];
          this.tempList=[];
          this.total=res.data.result.pageInfo.total;
          this.consumerList=res.data.result.pageInfo.list;
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
    addConsumer(){
      this.$refs['registerForm'].validate(valid=>{
        if(valid){
          let params=new URLSearchParams();
          params.append('username',this.registerForm.username);
          params.append('password',this.registerForm.password);
          params.append('sex',this.registerForm.sex);
          params.append('birth',this.registerForm.birth);
          params.append('avator','/img/consumerPic/default.jpg');
          params.append('location',this.registerForm.location);
          params.append('introduction',this.registerForm.introduction);
          params.append('phoneNum',this.registerForm.phoneNum);
          params.append('email',this.registerForm.email);
          addUser(params)
          .then(res=>{
            if(res.data.code=="50200"){
              this.notify("添加成功","success");

              this.getConsumers(null);
            }else{
              this.notify('添加失败','danger');
            }
          })
          .catch(err=>{
            console.log(err);
          })
          this.registerForm={};
          this.dialogVisible=false;
        }
      })
      
    },
    uploadUrl(id,pic){
      return `${this.$store.state.HOST}/consumer/updateAvatar?id=${id}&pic=${pic}`;
    },
    // 获取当前页
    handleCurrentChange(val){
      this.currentPage=val;
      this.getConsumers(null);
    },
    // 搜索歌手
    searchConsumer(){
      if(this.searchKey==''||this.searchKey==null){
        this.$message.error("搜索框内容不能为空");
        return;
      }
      this.getConsumers(this.searchKey);
    },
    // 修改歌手信息
    handleEdit(consumer){
      this.editForm={
        id:consumer.id,
        username:consumer.username,
        password:consumer.password,
        sex:consumer.sex,
        phoneNum:consumer.phoneNum,
        email:consumer.email,
        introduction:consumer.introduction,
        location:consumer.location,
        birth:new Date(consumer.birth)
      }
      console.log(consumer.birth);
      this.editVisible=true;
    },
    // 保存修改的信息到数据库
    editSave(){
      let params =new URLSearchParams();
      params.append('id',this.editForm.id);
      params.append('username',this.editForm.username);
      params.append('password',this.editForm.sex);
      params.append('birth',this.editForm.birth);
      params.append('location',this.editForm.location);
      params.append('introduction',this.editForm.introduction);
      params.append('sex',this.editForm.sex);
      params.append('phoneNum',this.editForm.phoneNum);
      params.append('email',this.editForm.email);
      editConsumer(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify("修改成功",'success');
          this.getConsumers(null);
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
    delConsumer(){
      let params=new URLSearchParams();
      params.append('id',this.delForm.id);
      params.append('pic',this.delForm.pic);
      deleteConsumer(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify('删除成功','success');
          this.getConsumers(null);
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
    batchDelConsumer(){
      let params=new URLSearchParams();
      params.append("ids",this.mulDelIds);
      params.append("paths",this.paths);
      batchRemoveConsumer(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify("删除成功",'success');
          this.getConsumers(null);
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
    },
    handleRemoveConsumer(consumer){
      this.delForm={
        id:consumer.id,
        pic:consumer.avatar
      }
      this.delVisible=true;
    },
    handleConsumerAvator(res){
      if(res.code=='50200'){
        this.notify('上传图片成功','success');
        this.getConsumers(null);
        this.$refs.uploadUserAvator.clearFiles();
        
      }else{
        this.notify(res.message,'error');
      }
    },
    handleAddUser(){
      this.dialogVisible=true;
      this.registerForm={};
    },
    turnToCollectPage(userId){
      console.log(userId);
      // 跳转到收藏歌曲页面
      this.$router.push({path:'/collect',query:{userId:userId}});
    }
    
  },
  

}
</script>

<style scoped>
.handle-box{
  margin-bottom:20px;
}

.user-img{
  width:100%;
  height:80px;
  margin-bottom:5px;
  /* 超出的部分隐藏 */
  overflow: hidden;
}
.user-img img{
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