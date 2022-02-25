<template>
  <div class="my-music">
      <div class="album-slide">
        <div class="album-img">
          <img :src="getUrl(userInfo.avator)"/>
        </div>
        <ul class="album-info">
          <li>昵称:{{userInfo.username}}</li>
          <li v-if="userInfo.sex<=1">性别:{{getSex(userInfo.sex)}}</li>
          <li>生日:{{transferDate(userInfo.birth)}}</li>
          <li>家乡:{{userInfo.location}}</li>
        </ul>
      </div>
      <div class="album-content">
        <div class="album-title">
          个性签名：{{userInfo.introduction}}
        </div>
        <div class="songs-body">
          <album-content :songList="songs">
            <template slot="title">我的收藏</template>
          </album-content>
        </div>
      </div>
  </div>
</template>

<script>
import {mixin} from '../mixins/tools';
import { mapGetters} from 'vuex';
import {getAllCollectByUserId,getSongsByIds} from '../api/index';
import AlbumContent from '../components/AlbumContent.vue';
export default {
    name:'my-music',
    mixins:[mixin],
    components:{
      AlbumContent
    },
    data(){
      return {
        collectList:[],// 收藏的歌曲列表
        songs:[], // 收藏的歌曲列表（带歌曲详情）
      }
    },
    created(){
      this.getCollectIds();  
    },
    computed:{
      ...mapGetters([
        'loginIn',
        'userInfo'
      ])
    },
    methods:{
      getSex(sex){
        if(sex==0){
          return '女';
        }else if(sex==1){
          return '男';
        }
        return '';
      },
      getCollectSongs(params){
        getSongsByIds(params)
        .then(res=>{
          if(res.data.code=='50200'){
            this.songs=res.data.result.songList;
            this.$store.commit("setSongList",this.songs);
          }else{
            this.$message.error(res.data.message);
          }
        })
        .catch(err=>{
          console.log(err);
        })
      },
      getCollectIds(){
        let params=new URLSearchParams();
        params.append("userId",this.userInfo.id);
        getAllCollectByUserId(params)
        .then(res=>{
          if(res.data.code=='50200'){
            this.collectList=res.data.result.collects;
            let params1=new URLSearchParams();
            let ids=[];
            for(let item of this.collectList){
              if(item.type==0){
                ids.push(item.songId);
              }
            }
            params1.append("songIds",ids);
            this.getCollectSongs(params1);
          }
        })
      }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>