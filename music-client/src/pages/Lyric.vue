<template>
  <div class="song-lyric">
      <h1 class="lyric-title">歌词</h1>
      <!-- 有歌词 -->
      <ul class="has-lyric" v-if="lyr.length">
          <li v-for="(item,index) in lyr" :key="index">
              {{item[1]}}
          </li>
      </ul>
      <div v-if="lyr.length==0" class="no-lyric">
          <span>暂无歌词</span>
      </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
export default {
    name:"lyric",
    data(){
        return {
            lyr:[], // 歌词
        }
    },
    created(){
        this.lyr=this.lyric;
        console.log(this.lyr);
    },
    computed: {
        ...mapGetters([
            'curTime',
            'id',
            'lyric',
            'curTime',
        ])
    },
    watch: {
        id(){
            this.lyr=this.lyric;
        },
        curTime() {
            if(this.lyr.length>0){
                for(let i=0;i<this.lyr.length;i++){
                    if(this.curTime>=this.lyr[i][0]){
                        for(let j=0;j<this.lyr.length;j++){
                            document.querySelectorAll(".has-lyric li")[j].style.color='#000';
                            document.querySelectorAll('.has-lyric li')[j].style.fontSize='15px';
                        }

                        document.querySelectorAll('.has-lyric li')[i].style.color="#95d2f6";
                        document.querySelectorAll('.has-lyric li')[i].style.fontSize="25px";
                    }
                }
                if(this.curTime>this.lyr[this.lyr.length-1][0]+3){
                    document.querySelectorAll('.has-lyric li')[this.lyr.length-1].style.color="#000";
                    document.querySelectorAll('.has-lyric li')[this.lyr.length-1].style.fontSize="15px";
                }
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/lyric.scss';
</style>