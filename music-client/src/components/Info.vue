<template>
  <div>
      <div class="info">
          <div class="title">
              <span>编辑个人资料</span>
          </div>
          <hr/>
          <div class="personal">
              <el-form :model="registerForm" ref="registerForm" label-width="80px" class="demo-ruleForm" :rules="rules">
                <el-form-item prop="username" label="用户名">
                    <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
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
                    <el-date-picker :editable="false" v-model="registerForm.birth" placeholder="选择日期" style="width:100%"></el-date-picker>
                </el-form-item>
                <el-form-item prop="introduction" label="昵称">
                    <el-input v-model="registerForm.introduction" placeholder="昵称"></el-input>
                </el-form-item>
                <el-form-item prop="location" label="地区">
                    <el-select v-model="registerForm.location" placeholder="地区" style="width:100%">
                        <el-option v-for="item in cities" :label="item.label" :value="item.value" :key="item.label"></el-option>
                    </el-select>
                </el-form-item>
                <div class="btn">
                    <el-button type="primary" @click="saveUserInfo">保存</el-button>
                    <el-button @click="goback()">取消</el-button>
                </div>
            </el-form>

          </div>
          
      </div>
  </div>
</template>

<script>
import {rules,cities} from '../assets/data/form';
import {updateConsumer} from '../api/index';
import {mapGetters} from 'vuex';
export default {
    name:'info',
    computed: {
        ...mapGetters([
            'userInfo',
        ])
    },
    data(){
        return {
            registerForm:{
                username:'',
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
        this.registerForm=this.userInfo;
        console.log(this.registerForm);
    },
    methods:{
        goback(){
            this.$store.commit('setActiveName',"首页");
            this.$router.push({path:"/"});
        },
        // 注册
        saveUserInfo(){
            let _this=this;
            let params=new URLSearchParams();
            params.append("id",this.registerForm.id);
            params.append("username",this.registerForm.username);
            params.append("sex",this.registerForm.sex);
            params.append("phoneNum",this.registerForm.phoneNum);
            params.append("email",this.registerForm.email);
            params.append("birth",new Date(this.registerForm.birth));
            params.append("location",this.registerForm.location);
            params.append("introduction",this.registerForm.introduction);
            updateConsumer(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.$message({
                        type:'success',
                        message:"修改成功",
                        duration: 1000
                    });
                    this.$store.commit("setUserInfo",this.registerForm);
                }else{
                    this.$message.error("修改失败");
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
@import '../assets/css/info.scss';
</style>