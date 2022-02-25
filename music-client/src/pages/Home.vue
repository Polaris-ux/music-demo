<template>
  <div class="home">
      <swiper/>
      <div class="section" v-for="(item,index) in songsList" :key="index">
          <div class="section-title">{{item.name}}</div>
          <content-list :contentList="item.list"></content-list>
      </div>
  </div>
</template>

<script>
import Swiper from "../components/Swiper";
import {mixin} from "../mixins/tools";
import {getSingersByPage,getSongListByKey} from '../api/index';
import ContentList from '../components/ContentList.vue';
export default {
    name: "home",
    components: {
        // 轮播图
        Swiper,
        ContentList
    },
    mixins:[mixin],
    data(){
        return{
            songsList:[
                {name:'歌手',list:[]},
                {name:'歌单',list:[]}
            ],
            currentPage:1,
            pageSize:10
        }
    },
    created(){
        this.getSingers();
        this.getSongLists();
    },
    methods:{
        getSingers(){
            let params=new URLSearchParams();
            params.append('pageNum',this.currentPage);
            params.append('pageSize',this.pageSize);
            getSingersByPage(params)
            .then(res=>{
                console.log(res);
                if(res.data.code=="50200"){
                    this.songsList[0].list=res.data.result.pageInfo.list;
                }else{
                    this.$message.error("加载数据失败");
                }
            })
            .catch(err=>{
                console.log(err);
            })
        },
        getSongLists(){
            let params=new URLSearchParams();
            params.append('pageNum',this.currentPage);
            params.append('pageSize',this.pageSize);
            getSongListByKey(params)
            .then(res=>{
                console.log(res);
                if(res.data.code=="50200"){
                    this.songsList[1].list=res.data.result.songListPageInfo.list;
                }else{
                    this.$message.error("加载数据失败");
                }
            })
            .catch(err=>{
                console.log(err);
            })
        }
    },

}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>