<template>
  <div class="song-audio">
      <audio
      ref='audio'
      :src="url"
      controls="controls"
      preload="true"
      @canplay="togglePlay"
      @ended="ended"
      @timeupdate="updateTime"
      ></audio>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
export default {
    name:'song-audio',
    computed:{
        ...mapGetters([
            'id',
            'url',
            'isPlay',
            'playButtonUrl',
            'duration',//音乐时长
            'curTime',// 当前音乐的播放位置
            'changeTime',// 指定播放位置
            'autoNext',// 用于自动触发播放下一首
            'songList',// 歌单
            'volume',// 音量
        ])
    },
    watch: {
        // 监听暂停还是播放
        isPlay:function(){
            this.togglePlay();
        },
        // 跳转到指定时间
        changeTime(){
            this.$refs.audio.currentTime = this.changeTime;
        },
        // 改变音量
        volume(){
            this.$refs.audio.volume = this.volume;
        }
    },
    methods:{
        startPlay(){
            
            let player=this.$refs.audio;
            console.log(player.duration);
            this.$store.commit('setDuration',player.duration);
            player.play();    
            this.$store.commit('setIsPlay',true);
        },
        // 播放完成后触发
        ended(){
            this.$store.commit('setIsPlay',false);
            this.$store.commit('setCurTime',0);
            this.$store.commit('setAutoNext',!this.autoNext);
        },
        // 开始、暂停
        togglePlay(){
            let player=this.$refs.audio;
            this.$store.commit('setDuration',player.duration);
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        }, 
        // 记录音乐播放的位置
        updateTime(){
            this.$store.commit('setCurTime',this.$refs.audio.currentTime);
        }
    }
}
</script>

<style>
.song-audio{
    /* 隐藏播放器 */
    display:none;
}
</style>