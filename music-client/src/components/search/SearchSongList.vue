<template>
  <div class="search-song-Lists">
      <content-list :contentList="songLists"></content-list>
  </div>
</template>

<script>
import {getAllSongListByKey} from '../../api/index';
import ContentList from '../ContentList.vue';
export default {
    name:'search-songLists',
    components:{
      ContentList
    },
    data() {
      return {
        songLists:[],
      }
    },
    created() {
      this.searchSongList();
    },
    methods:{
      searchSongList(){
        let key=this.$route.query.searchKey;
        let params=new URLSearchParams();
        params.append('key',key);
        getAllSongListByKey(params)
        .then(res=>{
          if(res.data.code=='50200'){
            this.songLists=res.data.result.songLists;
          }else{
            this.$message.error("搜索失败");
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
@import '../../assets/css/search-song-Lists.scss';
</style>