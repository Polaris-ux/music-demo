<template>
  <div>
      <login-logo/>
      <div class="login">
          <div class="login-head">
              <span>用户登录</span>
          </div>
          <el-form :model="registerForm" ref="registerForm" label-width="80px" class="demo-ruleForm" :rules="rules">
            <el-form-item prop="username" label="用户名">
                <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
            </el-form-item>
            <div class="login-btn">
                <el-button @click="toRegistPage()">注册</el-button>
                <el-button type="primary" @click="loginFunc()">登录</el-button>
            </div>
          </el-form>
      </div>
  </div>
</template>

<script>
import LoginLogo from '../components/LoginLogo.vue';
import {rules,cities} from '../assets/data/form';
import {loginReq} from '../api/index';
export default {
    name:'sign-up',
    components: { 
        LoginLogo 
    },
    data(){
        return {
            registerForm:{
                username:'',
                password:'',
            },
            rules:{ }
        }
    },
    created(){
        this.rules=rules;
    },
    methods:{
        toRegistPage(){
            this.$store.commit('setActiveName',"注册");
            this.$router.push({path:'/sign-up'});
        },
        loginFunc(){
            let _this=this;
            let params=new URLSearchParams();
            params.append('username',this.registerForm.username);
            params.append('password',this.registerForm.password);
            loginReq(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.$store.commit('setLoginIn',true);
                    this.$store.commit('setUserInfo',res.data.result.consumerInfo);
                    this.$message({
                        type:'success',
                        message:'登录成功',
                        duration:1000
                    });
                    setTimeout(function(){
                        _this.$store.commit('setActiveName',"首页");
                        _this.$router.push({path:'/'})
                    })
                }else{
                    this.$message.error(res.data.message);
                }
            })
            .catch(err=>{
                console.log(err);
            })
            
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/login-in.scss';
</style>