<template>
  <div class="song-audio">
      <audio
      id="audio"
      :src="url"
      controls="controls"
      preload="true"
      @canplay="startPlay"
      @ended="ended"
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
            'isPlay'
        ])
    },
    watch: {
        // 监听暂停还是播放
        isPlay:function(){
            this.togglePlay();
        }
    },
    methods:{
        startPlay(){
            let player=document.querySelector('#audio');
            player.play();    
        },
        // 播放完成后触发
        ended(){
            this.isPlay = false;
        },
        // 开始、暂停
        togglePlay(){
            let player=document.querySelector('#audio');
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
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