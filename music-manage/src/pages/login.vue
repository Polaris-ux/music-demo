<template>
  <div class="login-wrap">
      <div class="ms-title" >倾 听 后台管理系统</div>
      <div class="ms-login">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm">'
              <el-form-item prop="username">
                  <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                  <el-input type='password' v-model="ruleForm.password" placeholder="密码"></el-input>
              </el-form-item>
              <div class="login-btn">
                  <el-button type="primary" @click="submitForm">登录</el-button>
              </div>
          </el-form>
      </div>
  </div>
</template>

<script>
import {getLoginInfo} from '../api/index';
import {mixin} from '../mixins/tips';
export default {
    mixins:[mixin],
    data:function(){
        return {
            ruleForm:{
                username:"admin",
                password:"1343"
            },
            rules:{
                username:[{required: true,message:"请输入用户名",trigger:"blur"}],
                password:[
                    {required: true,message:"请输入密码",trigger:"blur"}
                ]
            }
        }
    },
    methods:{
        submitForm:function(){
            let params=new URLSearchParams();
            params.append("username",this.ruleForm.username);
            params.append("password",this.ruleForm.password);
            getLoginInfo(params)
                .then(response=>{

                    localStorage.setItem('userName',this.ruleForm.username);
                    this.notify("登录成功",'success');
                    // 页面跳转
                    this.$router.push('/info');
                    
                })
        }
    }
}
</script>

<style>
.login-wrap {
    position: relative;
    background:url("../assets/img/background1.jpg");
    background-attachment: fixed;
    background-position:center;
    background-size:cover;
    width:100%;
    height:100%;
}

.ms-title {
    position:absolute;
    top:50%;
    width:100%;
    margin-top:-240px;
    text-align:center;
    font-size:30px;
    font-weight:600;
    color:white;
}

.ms-login {
    position:absolute;
    left:50%;
    top:50%;
    margin-left:-150px;
    margin-top:-120px;
    width:300px;
    height:180px;
    background-color:rgba(255,255,255,0.2);
    border-radius:5px;
    padding:10px;
}
.login-btn {
    text-align:center;
}
.login-btn button {
    width:100%;
    height:36px;
}
</style>