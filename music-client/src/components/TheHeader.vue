<template>
  <div class="the-header">
      <div class="header-logo" @click="goHome">
          <svg class="icon">
              <use xlink:href="#icon-erji"></use>
          </svg>
          music
      </div>
      <ul class="navbar">
          <li :class="{active:item.name==activeName}" v-for="item in navMsg" :key="item.path" @click="goPage(item.path,item.name)">
              {{item.name}}
          </li>
          <li>
              <div class="header-search">
                    <input id="search" type="text" @keyup.enter="goSearch()" v-model="searchKey" placeholder="搜索音乐"/>
                    <div class="search-btn" @click="goSearch()">
                        <svg class="icon">
                            <use xlink:href="#icon-sousuo"></use>
                        </svg>
                    </div>
              </div>

          </li>
          <li v-show="!loginIn" :class="{active:item.name==activeName}" v-for="item in loginMsg" :key="item.path" @click="goPage(item.path,item.name)">
              {{item.name}}
          </li>
      </ul>
      <div class="header-right" v-show="loginIn">
          <div id="user">
              <img :src="getUrl(userInfo.avator)"/>
          </div>
          <ul class="menu">
              <li v-for="(item,index) in menuMsg" :key="index" @click="toSetting(item.path)">
                  {{item.name}}
              </li>
          </ul>
      </div>

  </div>
</template>

<script>
import {getSongByKey} from '../api/index';
import {navMsg,loginMsg,menuMsg} from '../assets/data/header';
import {mapGetters} from 'vuex';
import {mixin} from '../mixins/tools';
export default {
    name:"the-header",
    inject:['reload'], // 用来刷新页面
    mixins:[mixin],
    data(){
        return{
            navMsg:[], // 导航栏
            searchKey:'',
            loginMsg:[],// 右侧导航栏
            menuMsg:[], // 下拉菜单
        }
    },
    created(){
        this.navMsg=navMsg;
        this.loginMsg=loginMsg;
        this.menuMsg=menuMsg;
        console.log(this.loginIn);
        // console.log("***");
        // console.log(this.userInfo);
    },
    mounted() {
        document.querySelector("#user").addEventListener('click',function(e){
            document.querySelector(".menu").classList.add("show");
            e.stopPropagation(); //阻止冒泡
        },false);
        // 防止点击菜单导致菜单关闭
        document.querySelector('.menu').addEventListener('click',function(e){
            e.stopPropagation();
        },false);
        document.documentElement.addEventListener('click',function(e){
            document.querySelector(".menu").classList.remove("show");
            e.stopPropagation();
        })
    },
    computed:{
        ...mapGetters([
            'activeName',
            'loginIn',
            'userInfo',
        ])
    },
    methods:{
        goHome(){
            this.goPage("/","首页");
        },
        goPage(path,name){
            if(name=="我的音乐"&&!this.loginIn){
                this.$message({
                    type:'warning',
                    message:"请先登录",
                    duration:2000
                });
                return;
            }
            this.$store.commit('setActiveName',name);
            let nowPath=this.$route.path;
            if(path!=nowPath){
                this.$router.push({path:path});
            }
        },
        goSearch(){
            let nowPath=this.$route.path;
            // 跳转到搜索页面
            if(this.searchKey){
                if(nowPath!="/search"){
                    this.$router.push({path:'/search',query:{searchKey:this.searchKey}});
                }else{
                    let params=new URLSearchParams();
                    params.append('key',this.searchKey);
                    getSongByKey(params)
                    .then(res=>{
                        if(res.data.code=='50200'){
                            if(res.data.result.searchSongs.length!=0){
                                this.$store.commit("setSongList",res.data.result.searchSongs);
                                console.log(this.songList);
                            }else{
                                this.$message({
                                    type:'success',
                                    message:"未查询到相关歌曲",
                                    duration:500
                                });
                            }
                            
                        }else{
                            this.$message.error("搜索失败！！");
                        }
                    })
                    .catch(err=>{
                        console.log(err);
                    })
                }
            }else{
                this.$store.commit('setSongList',[]);
                this.$message({
                    type:'error',
                    message:"搜索内容为空",
                    duration:500
                });
            }
        },
        toSetting(path){
            if(path==0){
                this.$store.commit('setLoginIn',false);
                this.$store.commit('setUserInfo',{});
                this.$store.commit('setIsLike',false);
                this.$router.go(0);
            }else{
                document.querySelector(".menu").classList.remove("show");
                this.$router.push({path:path});
            }
        }

    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-header.scss';
</style>