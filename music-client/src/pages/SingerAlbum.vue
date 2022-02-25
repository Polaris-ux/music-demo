<template>
  <div class="singer-album">
    <div class="album-slide">
      <div class="singer-img">
        <img :src="getUrl(singer.pic)"/>
      </div>
      <ul class="info">
        <li v-if="singer.sex<=1">{{getSex(singer.sex)}}</li>
        <li >生日：{{transferDate(singer.birth)}}</li>
        <li>故乡：{{singer.location}}</li>
      </ul>
    </div>
    <div class="album-content">
      <div class="intro">
        <h2>{{singer.name}}</h2>
        <span>{{singer.introduction}}</span>
      </div>
      <div class="content">
        <album-content :songList="songList"></album-content>
      </div>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins/tools';
import {mapGetters} from 'vuex';
import {getAllSongBySinger} from '../api/index';
import AlbumContent from '../components/AlbumContent.vue';

export default {
    name:'singer-album',
    components:{
      AlbumContent,
    },
    mixins: [mixin],
    data(){
      return {
        singerId:'',
        singer:{}
      }
    },
    created(){
      this.singerId=this.$route.params.id;
      this.singer=this.songListInfo;
      this.awareSongs();
    },
    computed:{
      ...mapGetters([
        'songList',
        'userInfo',
        'loginIn',
        'songListInfo',// 这里是指的歌手对象，不是歌单
      ])
    },
    methods:{
      awareSongs(){
        let params=new URLSearchParams();
        params.append("singerId",this.singerId);
        getAllSongBySinger(params)
        .then(res=>{
          if(res.data.code=='50200'){
            this.$store.commit('setSongList',res.data.result.songs);
          }else{
            this.$message.error("获取歌曲失败");
          }
        })
        .catch(err=>{
          consoler.log(err);
        })
      },
      getSex(sex){
        if(sex==0){
          return '女';
        }else if(sex==1){
          return '男';
        }
        return '';
      }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer-album.scss';
</style>