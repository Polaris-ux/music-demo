<template>
  <div class="search-songs">
      <album-content :songList="songList"></album-content>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import {getSongByKey} from '../../api/index';
import AlbumContent from '../AlbumContent.vue';
export default {
    name:'search-songs',
    components:{
        AlbumContent,
    },
    data(){
        return {

        }
    },
    computed: {
        ...mapGetters([
            'songList',
        ])
    },
    // 页面加载时执行方法
    mounted() {
        this.searchSongs();
    },
    methods:{
        searchSongs(){
            let searchKey=this.$route.query.searchKey;
            let params=new URLSearchParams();
            params.append("key",searchKey);
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
    }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-songs.scss';
</style>