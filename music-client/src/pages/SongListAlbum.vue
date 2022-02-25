<template>
  <div class="song-list-album">
      <div class="album-slide">
          <div class="album-img">
              <img :src="getUrl(songListInfo.pic)"/>
          </div>
          <div class="album-info">
              <h2>简介：</h2>
              <span>
                  {{songListInfo.introduction}}
              </span>
          </div>
      </div>
      <div class="album-content">
          <div class="album-title">
              <p>{{songListInfo.title}}</p>
          </div>
          <div class="album-score">
              <div>
                  <h3>歌单评分：</h3>
                  <div>
                    <el-rate v-model="avarage" disabled></el-rate>
                  </div>
              </div>
              <span>{{avarage*2}}</span>
              <div>
                  <h3>评价：</h3>
                  <div @click="setRank">
                    <el-rate v-model="rank" allow-half show-text ></el-rate>
                  </div>
              </div>
          </div>
          <div class="songs-body">
            <album-content :songList="songList"></album-content>
            <comment :playId="songListId" :type="1"></comment>
          </div>
      </div>
  </div>
</template>

<script>
import {mixin} from '../mixins/tools';
import {mapGetters} from 'vuex';
import {getSongIdsBySongList,getSongsByIds,getRanksInfo,addRank} from '../api/index';
import AlbumContent from '../components/AlbumContent.vue';
import Comment from '../components/Comment.vue';
export default {
    components: { AlbumContent, Comment },
    name:'songList-album',
    mixins:[mixin],
    data(){
        return {
            songsList:[], // 当前歌曲播放列表
            songListId:-1, // 传过来的歌单id 
            avarage:0, // 评分平均值
            rank:0,
        }
    },
    created(){
        this.songListId=this.$route.params.id;
        this.getSongIds();
        this.getCurRank();
    },
    computed:{
        ...mapGetters([
            'songList',
            'songListInfo', // 当前歌单对象
            'userInfo',
            'loginIn'
        ])
    },
    methods:{
        getSongsList(ids){
            let params=new URLSearchParams();
            params.append("songIds",ids);
            getSongsByIds(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.songsList=res.data.result.songList;
                    this.$store.commit('setSongList',this.songsList);
                }else{
                    this.$message.error(res.data.message);
                }
            })
        },
        getSongIds(){
            console.log(this.songListInfo);
            console.log(this.songListInfo==null||this.songListInfo==undefined);
            getSongIdsBySongList(this.songListId)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.getSongsList(res.data.result.songIds);
                }else{
                    this.$message.error(res.data.message);
                }
            })
            .catch(err=>{
                console.log(err);
            })
        },
        getCurRank(){
            let params=new URLSearchParams();
            params.append('songListId',this.songListId);
            getRanksInfo(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.avarage=res.data.result.rank/2;
                    console.log(res.data.result);
                }else{
                    this.$message.error(res.data.message);
                }
            })
            .catch(err=>{
                console.log(err);
            })
        },
        setRank(){
            if(!this.loginIn){
                this.$message({
                    type:'warning',
                    message:'请先登录',
                    duration: 1000
                });
                return;
            }
            let params=new URLSearchParams();
            params.append('songListId',this.songListId);
            params.append('consumerId',this.userInfo.id);
            params.append('score',this.rank*2);
            addRank(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.getCurRank();
                    this.$message({
                        type:'success',
                        message:'评价成功',
                        duration:1000
                    });
                }else{
                    this.$message.error("您已经评价过了");
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
@import '../assets/css/song-list-album.scss';
</style>