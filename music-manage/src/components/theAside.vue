<template>
  <div class="sidebar">
    <el-menu 
      class='sidebar-el-menu'
      :default-active="onRoutes"
      :collapse='collapse'
      text-color='white'
      active-text-color='green'
      background-color='#334256'
      router
    >
      <template v-for='item in items'>
        <template>
          <el-menu-item :index="item.index" :key="item.index">
            <i :class="item.icon"></i>
            <span>{{item.title}}</span>
          </el-menu-item>
        </template>
      </template>
      
    </el-menu>
  </div>
</template>

<script>
import bus from '../assets/js/bus';
export default {
  data(){
    return {
      //是否折叠
      collapse: false,
      items:[
        {
          icon:'el-icon-document',
          index: 'info',
          title: '系统首页'
        },
        {
          icon:'el-icon-document',
          index: 'consumer',
          title: '用户管理'
        },
        {
          icon:'el-icon-document',
          index: 'singer',
          title: '歌手管理'
        },
        {
          icon:'el-icon-document',
          index: 'songList',
          title: '歌单管理'
        }
      ]
    }
  },
  computed: {
    onRoutes(){
      return this.$route.path.replace('/','');
    }
  },
  // 创建页面时调用
  created(){
    //通过bus进行组件间的通信，来折叠侧边栏
    // 接收
    bus.$on('collapse',msg=>{
      this.collapse = msg;
    })
  }
}
</script>

<style scoped>
.sidebar {
  display:block;
  position: absolute;
  top:70px;
  left:0px;
  bottom:0px;
  overflow-y: scroll;
  background-color:#334256;
}

.sidebar::-webkit-scrollbar {
  width:0px;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width:160px;
}

.sidebar >ul{
  height:100%;
}
</style>