<template>
  <div class="singer">
    <ul class="singer-header">
      <li :class="{active:activeName==item.name}" v-for="(item,index) in singerStyle" :key="index" @click="getSongWithType(item)">
        {{item.name}}
      </li>
    </ul>
    <div>
      <content-list :contentList="singerList"></content-list>
    </div>
    
  </div>
</template>

<script>
import {getAllSingerByType} from '../api/index';
import ContentList from '../components/ContentList.vue';
import {singerStyle} from '../assets/data/singer.js';
export default {
    name:'singer',
    components:{
      ContentList
    },
    data(){
      return {
        singerList:[],
        singerStyle:[],
        activeName:"全部歌手",
      }
    },
    created(){
      this.singerStyle=singerStyle;
      this.allSingers(null);
    },
    methods:{
      allSingers(params){
        getAllSingerByType(params)
        .then(res=>{
          if(res.data.code=='50200'){
            this.singerList=res.data.result.allSingers;
          }else{
            this.$message.error("加载歌单数据失败");
          }
        })
        .catch(err=>{
          console.log(err);
        })
      },
      getSongWithType(item){
        if(item.type==-1){
          this.allSingers(null);
        }else{
          let params=new URLSearchParams();
          params.append("type",item.type);
          this.allSingers(params);
        }
        this.activeName=item.name;
      }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer.scss';
</style>