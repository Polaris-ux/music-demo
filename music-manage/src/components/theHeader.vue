<template>
  <div class='header'>
    <div class='collapse-btn'>
      <i class='el-icon-menu' @click="changeCollapse"></i>
    </div>
    <div class='logo'>music 后台管理</div>
    <div class="header-right">
      <div class='btn-fullscreen' @click='handlerFullScreen'>
        <el-tooltip :content="fullscreen?'取消全屏':'全屏'" placement="bottom">
          <i class='el-icon-rank'></i>
        </el-tooltip>
      </div>
      <div class='user-avator'>
        <img src='../assets/img/user.jpg'/>
      </div>
      <el-dropdown class='user-name' trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{userName}}
          <i class="el-icon-caret-bottom"></i>
        </span>
        
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import bus from '../assets/js/bus';
export default {
  data(){
    return {
      collapse:false,
      fullscreen:false
    }
  },
  computed: {
    userName(){
      return localStorage.getItem('userName');
    }
  },
  methods:{
    // 侧边栏折叠
    changeCollapse(){
      this.collapse = !this.collapse;
      bus.$emit('collapse',this.collapse );
    },
    // 全屏事件
    handlerFullScreen(){
      if(this.fullscreen){
        if(document.exitFullscreen){
          document.exitFullscreen();
        }else if(document.webkitCancelFullScreen){ // sofari、Chrome
          document.webkitCancelFullScreen();
        }else if(document.mozCancelFullScreen){ // firefox
          document.mozCancelFullScreen();
        }else if(document.msExitFullScreen){ // ie
          document.msExitFullScreen();
        }
      }else{
        let element=document.documentElement;
        if(element.requestFullscreen){
          element.requestFullscreen();
        }else if(element.webkitRequestFullScreen){
          element.webkitRequestFullScreen();
        }else if(element.mozRequestFullScreen){
          element.mozRequestFullScreen();
        }else if(element.msRequestFullScreen){
          element.msRequestFullScreen();
        }
      }
      this.fullscreen=!this.fullscreen;
    },
    // 检查是否是全屏
    escExit(){
      this.fullscreen=false;
    },
    handleCommand(command){
      if(command=='logout'){
        localStorage.removeItem('userName');
        this.$router.push('/');
      }
    }
  },

  mounted() {
    //监听键盘按键事件
    let self = this;
    this.$nextTick(function () {
	  document.addEventListener('keyup', function (e) {
	    //此处填写你的业务逻辑即可
      if(e.keyCode==27){
        self.escExit();
      }
	  })
  })
}

}
</script>

<style scoped>
.header{
  position: relative;
  background-color:rgb(105, 143, 192);
  width:100%;
  height:70px;
  color:aliceblue;
  /*  立体感*/
  box-sizing:border-box;
  font-size:22px;
}
.collapse-btn{
  float:left;
  padding:0px 20px;
  /* 鼠标移动到该位置，变为手的模样 */
  cursor: pointer;
  line-height:70px;
}

.header .logo{
  position:relative;
  float:left;
  line-height:70px;
}

.header-right{
  float:right;
  padding-right:50px;
  /* 自由布局 */
  display:flex;
  height:70px;
  align-items:center;
}

.btn-fullscreen{
  /* 旋转45度 */
  transform: rotate(45deg);
  margin-right:5px;
  font-size:24px;
}

.user-avator{
  margin-left:10px;
}

.user-avator img{
    display:block;
    width:40px;
    height:40px;
    border-radius:50%;
}

.user-name{
  margin-left:10px;
  font-size:18px;
}

.el-dropdown-link{
  cursor: pointer;
  color:aliceblue;
}
</style>