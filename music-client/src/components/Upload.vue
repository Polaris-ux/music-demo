<template>
  <div class="upload">
      <p class="title">修改头像</p>
      <hr/>
      <div class="section">
          <el-upload :action="uploadUrl(userInfo.id,userInfo.avator)"
          ref="avatarUpload"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :show-file-list="false"
          drag 
          >
            <i class="el-icon-upload"></i>
            <div>
                将文件拖动此处,或<span style="color:blue">修改头像</span>
            </div>
            <div slot="tip">只能上传jpg/png文件</div>
          </el-upload>
      </div>
  </div>
</template>

<script>
import {getUserById} from '../api/index';
import {mapGetters} from 'vuex';
export default {
    name:'upload',
    computed:{
        ...mapGetters([
            'userInfo',
        ])
    },
    methods:{
        uploadUrl(userId,avatar){
            return `${this.$store.state.configure.HOST}/consumer/updateAvatar?id=${userId}&pic=${avatar}`;
        },
        handleAvatarSuccess(res){
            if(res.code=='50200'){
                let params=new URLSearchParams();
                params.append("userId",this.userInfo.id);
                this.$message.success("修改成功");
                getUserById(params)
                .then(response=>{
                    if(response.data.code=='50200'){
                        console.log(res);
                        this.$store.commit('setUserInfo',response.data.result.userInfo);
                    }else{
                        this.$message.error("刷新用户信息失败");
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
                this.$refs.avatarUpload.clearFiles();
            }
        },
        beforeAvatarUpload(file){
            let isJPG=(file.type==='image/jpeg')||(file.type==='image/png');
            if(!isJPG){
                this.$message.error("上传图片格式必须为jpg或png");
                return false;
            }
            let sizeLt10M=file.size/(1024*1024)<10;
            if(!sizeLt10M){
                this.$message.error("上传图片大小不能大于10M");
                return false;
            }
            return true;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/upload.scss';
</style>