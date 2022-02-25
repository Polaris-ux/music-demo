<template>
  <div>
      <login-logo/>
      <div class="signUp">
          <div class="signUp-head">
              <span>用户注册</span>
          </div>
          <el-form :model="registerForm" ref="registerForm" label-width="80px" class="demo-ruleForm" :rules="rules">
            <el-form-item prop="username" label="用户名">
                <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="sex" label="性别">
                <el-radio-group v-model="registerForm.sex">
                  <el-radio :label="0">女</el-radio>
                  <el-radio :label="1">男</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item prop="phoneNum" label="手机号">
                <el-input v-model="registerForm.phoneNum" placeholder="手机号码"></el-input>
            </el-form-item>
            <el-form-item prop="email" label="邮箱">
                <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="birth" label="生日">
                <el-date-picker v-model="registerForm.birth" placeholder="选择日期" style="width:100%"></el-date-picker>
            </el-form-item>
            <el-form-item prop="introduction" label="昵称">
                <el-input v-model="registerForm.introduction" placeholder="昵称"></el-input>
            </el-form-item>
            <el-form-item prop="location" label="地区">
                <el-select v-model="registerForm.location" placeholder="地区" style="width:100%">
                    <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.label"></el-option>
                </el-select>
            </el-form-item>
            <div class="login-btn">
                <el-button @click="goback()">取消</el-button>
                <el-button type="primary" @click="registFunc()">注册</el-button>
            </div>
          </el-form>
      </div>
  </div>
</template>

<script>
import LoginLogo from '../components/LoginLogo.vue';
import {rules,cities} from '../assets/data/form';
import {addConsumer} from '../api/index';
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
                sex:'',
                phoneNum:'',
                email:'',
                birth:'',
                location:'',
                introduction:'',
            },
            cities:[], // 城市
            rules:{ }
        }
    },
    created(){
        this.rules=rules;
        this.cities=cities;
    },
    methods:{
        goback(){
            this.$store.commit('setActiveName',"首页");
            this.$router.push({path:"/"});
        },
        // 注册
        registFunc(){
            let _this=this;
            let params=new URLSearchParams();
            params.append("username",this.registerForm.username);
            params.append("password",this.registerForm.password);
            params.append("sex",this.registerForm.sex);
            params.append("phoneNum",this.registerForm.phoneNum);
            params.append("email",this.registerForm.email);
            params.append("birth",new Date(this.registerForm.birth));
            params.append("location",this.registerForm.location);
            params.append("introduction",this.registerForm.introduction);
            params.append('avator','/img/consumerPic/default.jpg');
            addConsumer(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.$message({
                        type:'success',
                        message:"注册成功",
                        duration: 1000
                    });
                    setTimeout(function(){
                        _this.$router.push({path:'/'});
                        _this.$store.commit('setActiveName',"首页");
                    },1000);
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
@import '../assets/css/sign-up.scss';
</style>