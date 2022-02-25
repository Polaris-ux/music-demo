<template>
  <div class="play-bar" :class="{show:!toggle}">
      <div @click="toggle=!toggle" class="item-up">
        <svg class="icon" v-if="toggle">
            <use xlink:href="#icon-jiantou-xia-cuxiantiao"></use>
        </svg>
        <svg class="icon" v-else>
            <use xlink:href="#icon-jiantou-shang-cuxiantiao"></use>
        </svg>
      </div>
      <div class="kongjian">
          <!-- 歌曲图片 -->
          <div class="item-img" @click="toLyric">
              <img :src="getUrl(picUrl)"/>
          </div>
          <div class="item-content">
              <div class="title">{{getTitle()}}</div>
          </div>
          <!-- 上一首 -->
          <div class="item" @click="prev">
              <svg class="icon">
                  <use xlink:href="#icon-ziyuanldpi"></use>
              </svg>
          </div>
          <!-- 播放 -->
          <div class="item" @click="togglePlay()">
              <svg class="icon">
                  <use :xlink:href="playButtonUrl"></use>
              </svg>
          </div>
          <!-- 下一首 -->
          <div class="item" @click="next">
              <svg class="icon">
                  <use xlink:href="#icon-ziyuanldpi1"></use>
              </svg>
          </div>
          
          <!-- 播放进度 -->
          <div class="playing-speed">
              <!-- 播放时间 -->
              <div class="current-time">{{nowTime}}</div>
              <!-- 进度条 -->
              <div class="progress-box">
                  <div ref="progress" class="progress" @mousemove="mousemove" >
                      <div ref="bg" class="bg" @click="updateLength">
                          <div ref="curProgress" class="cur-progress" :style="{width:nowLength+'%'}"></div>
                      </div>
                      <!-- 拖动的点点 -->
                      <div ref="idot" class="idot" :style="{left:nowLength+'%'}" @mousedown="mousedown" @mouseup="mouseup" ></div>
                  </div>
              </div>
              <!-- 播放结束时间 -->
              <div class="left-time">{{endTime}}</div>
          </div>
          <!-- 音量 -->
          <div class="item item-volume" >
              <svg v-if="volumeVal==0" class="icon">
                  <use xlink:href="#icon-yinliangjingyinheix"></use>
              </svg>
              <svg v-else class="icon">
                  <use xlink:href="#icon-yinliang1"></use>
              </svg>
              <el-slider class="volume" v-model="volumeVal" :vertical="true"></el-slider>
          </div>
          <!-- 喜欢 收藏 -->
          <div class="item" @click="collect">
              <svg :class="{active:this.isLike}" class="icon">
                  <use xlink:href="#icon-xihuan-shi"></use>
              </svg>
          </div>
          <!-- 下载 -->
          <div class="item" @click="down">
              <svg class="icon">
                  <use xlink:href="#icon-xiazai"></use>
              </svg>
          </div>
          <!-- 播放列表 -->
          <div class="item" @click="showSongList">
              <svg class="icon">
                  <use xlink:href="#icon-liebiao"></use>
              </svg>
          </div>
      </div>
  </div>
</template>

<script>
import {download,addCollect,delCollect,isLikeSong} from '../api/index';
import {mapGetters} from 'vuex';
import {mixin} from '../mixins/tools';
export default {
    name:'play-bar',
    mixins:[mixin],
    data(){
        return{
            nowTime:'00:00',
            endTime:'00:00', // 歌曲总时间
            nowLength:0, //进度条的位置
            progressLength:0, // 进度条的总长度
            mouseStartX:0, // 鼠标
            tag:false, // 标志着是否在进行拖拽
            volumeVal:50, // 默认为一半的音量,
            toggle:true
        }
    },
    mounted(){
        this.progressLength=this.$refs.progress.getBoundingClientRect().width;
        // 使音量进度条显示出来
        document.querySelector(".item-volume").addEventListener('click',function(e){
            document.querySelector('.volume').classList.add('show-volume');
            // 停止父控件的操作
            e.stopPropagation();
        },false);
        document.querySelector('.volume').addEventListener('click',function(e){
            e.stopPropagation();
        },false);
        // 任意点击其他位置，使音量进度条消失
        document.addEventListener('click',function(){
            document.querySelector('.volume').classList.remove('show-volume');
        },false);
    },
    computed:{
        ...mapGetters([
            'id',
            'url',
            'isPlay',
            'playButtonUrl',
            'picUrl',
            'artist',
            'title',
            'curTime',
            'duration',
            'volume',
            "listIndex",
            'songList',
            'autoNext',
            'loginIn',
            'userInfo',
            'isLike',
        ])
    },
    watch:{
       // 切换播放状态的图标
       isPlay(){
           if(this.isPlay){
               this.$store.commit('setPlayButtonUrl',"#icon-zanting");
           }else{
               this.$store.commit('setPlayButtonUrl',"#icon-bofang");
           }
       },
       curTime(){
           this.nowTime=this.formatSeconds(this.curTime);
           this.endTime=this.formatSeconds(this.duration);
           this.nowLength=(this.curTime/this.duration)*100;
       },
       volumeVal(){
           this.$store.commit('setVolume',this.volumeVal/100);
       },
       autoNext(){
           this.next();
       }

    },
    methods:{
        // 控制播放或暂停
        togglePlay(){
            if(this.isPlay){
                this.$store.commit('setIsPlay',false);
            }else{
                this.$store.commit('setIsPlay',true);
            }
        },
        // 获取歌手-歌名
        getTitle(){
            return this.artist+'-'+this.title;
        },
        // 
        formatSeconds(value){
            let res="";
            let total=parseInt(value);
            let hour=parseInt(total/3600);
            let minute=parseInt((total%3600)/60);
            let seconds=parseInt(((total%3600)%60));
            if(hour>0){
                if(hour<10){
                    res+="0"+hour+":";
                }else{
                    res+=hour+":";
                }
            }
            if(minute>0){
                if(minute<10){
                    res+="0"+minute+":"
                }else{
                    res+=minute+":";
                }
            }else{
                res+="00:"
            }
            if(seconds>0){
                if(seconds<10){
                    res+="0"+seconds+""
                }else{
                    res+=seconds+"";
                }
            }else{
                res+="00"
            }

            return res;

        },
        // 鼠标拖拽开始
        mousedown(e){
            this.mouseStartX=e.clientX;
            this.tag=true;
        },
        // 鼠标拖拽结束
        mouseup(e){
            this.tag=false;
            this.changeTime(this.nowLength);
        },
        // 鼠标移动
        mousemove(e){
            if(!this.duration){
                return false;
            }
            if(this.tag){
                console.log("**")
                let moveDist=0;
                let curLen=this.$refs.curProgress.getBoundingClientRect().width;
                let tempLen=0;
                if(e.clientX<this.mouseStartX){
                    moveDist=this.mouseStartX-e.clientX;
                    tempLen=((curLen-moveDist)/this.progressLength)*100;
                    if(tempLen<0){
                        tempLen=0;
                    }
                }else{
                    moveDist=e.clientX-this.mouseStartX;
                    tempLen=((moveDist+curLen)/this.progressLength)*100;
                    if(tempLen>100){
                        tempLen=100;
                    }
                }
                this.mouseStartX=e.clientX;
                this.nowLength=tempLen;
            }
        },
        // 更改歌曲进度
        changeTime(percent){
            let newCurTime=percent*0.01*this.duration;
            this.$store.commit('setChangeTime',newCurTime);
        },
        // 点击改变进度条
        updateLength(e){
            if(!this.tag){
                // 进度条的左侧坐标
                let leftX=this.$refs.bg.offsetLeft;
                let curLen=e.clientX-leftX;
                let percent=(curLen/this.progressLength)*100;
                if(percent>100){
                    percent=100;
                }
                if(percent<0){
                    percent=0;
                }
                this.nowLength=percent;
                this.changeTime(percent);
            }
            
        },
        // 显示歌曲列表
        showSongList(){
            this.$store.commit('setShowAside',true);
        },
        // 上一首
        prev(){
            if(this.listIndex!=-1&&this.songList.length>=1){
                let item=null;
                let idx=0;
                if(this.listIndex>0){
                    item=this.songList[this.listIndex-1];
                    idx=this.listIndex-1;
                }else if(this.listIndex==0){
                    item=this.songList[this.songList.length-1];
                    idx=this.songList.length-1;
                }
                if(item.url!=null){
                    this.toPlay(item.id,item.url,item.pic,idx,item.name,item.lyric);
                }
            }
        },
        // 下一首
        next(){
            if(this.listIndex!=-1&&this.songList.length>=1){
                let item=null;
                let idx=0;
                if(this.listIndex>=0&&this.listIndex<this.songList.length-1){
                    idx=this.listIndex+1;
                    item=this.songList[idx];
                }else if(this.listIndex==this.songList.length-1){
                    idx=0;
                    item=this.songList[idx];
                }
                if(item!=null&&item.url!=null){
                    this.toPlay(item.id,item.url,item.pic,idx,item.name,item.lyric);
                }
            }
        },
        // 跳转到歌词页面
        toLyric(){
            this.$router.push({path:'/lyric'});
        },
        // 下载
        down(){
            download(this.url)
            .then(res=>{
                let content=res.data;
                let eleLink=document.createElement('a');
                eleLink.download=`${this.artist}`+'-'+`${this.title}`+".mp3";
                eleLink.style.display='none'; // 将下载的a标签隐藏
                // 把字符内容转化为blob地址
                let blob=new Blob([content]);
                eleLink.href=URL.createObjectURL(blob);
                // 把链接地址加到document里
                document.body.appendChild(eleLink);
                // 触发点击
                eleLink.click();
                // 移除该元素
                document.body.removeChild(eleLink);
            })
        },
        // 收藏
        collect(){
            if(this.loginIn){
                let params=new URLSearchParams();
                params.append('userId',this.userInfo.id);
                params.append('type',0);
                params.append("songId",this.id);
                addCollect(params)
                .then(res=>{
                    if(res.data.code=='50200'){
                        this.$store.commit("setIsLike",true);
                        this.$message.success("收藏成功");
                    }else{
                        this.$message({
                            type:'error',
                            message:res.data.message,
                            duration:2000
                        });
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
            }else{
                this.$message.warning("请先登录");
            }
        }
        
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/play-bar.scss';
</style>