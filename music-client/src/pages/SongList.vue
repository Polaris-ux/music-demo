<template>
  <div class="song-list">
    <ul class="song-list-header">
      <li :class="{active:activeName==item.name}" v-for="(item,index) in songStyle" :key="index" @click="getSongListWithStyle(item.name)">
        {{item.name}}
      </li>
    </ul>
    <div>
      <content-list :contentList="songLists"></content-list>
    </div>
    
  </div>
</template>

<script>
import {getAllSongListByKey} from '../api/index';
import ContentList from '../components/ContentList.vue';
import {songStyle} from '../assets/data/songList.js';
export default {
    name:'song-list',
    components:{
      ContentList
    },
    data(){
      return {
        songLists:[],
        songStyle:[],
        activeName:"全部歌单",
      }
    },
    created(){
      this.songStyle=songStyle;
      this.allSongLists(null);
    },
    methods:{
      allSongLists(params){
        getAllSongListByKey(params)
        .then(res=>{
          if(res.data.code=='50200'){
            this.songLists=res.data.result.songLists;
          }else{
            this.$message.error("加载歌单数据失败");
          }
        })
        .catch(err=>{
          console.log(err);
        })
      },
      getSongListWithStyle(name){
        if(name=="全部歌单"){
          this.allSongLists(null);
        }else{
          let params=new URLSearchParams();
          params.append("key",name);
          this.allSongLists(params);
        }
        this.activeName=name;
      }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
</style>