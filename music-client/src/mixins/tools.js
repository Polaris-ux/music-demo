import {isLikeSong} from '../api/index';
import { mapGetters } from 'vuex';
export const mixin={
    computed:{
        ...mapGetters([
          'id',
          'loginIn',
          'userInfo',
        ])
    },
    methods: {
        attachImageUrl(src){
            return src?`${this.$store.state.configure.HOST}${src}`:'../assets/img/user.jpg';
        },
        getName(index,str){
            let arr=str.split('-');
            return arr[index];
        },
        getUrl(url){
            return `${this.$store.state.configure.HOST}${url}`;
        },
        // 音乐播放
        toPlay(id,url,pic,index,name,lyric){
            this.$store.commit('setId',id);
            this.$store.commit('setUrl',`${this.$store.state.configure.HOST}${url}`);
            this.$store.commit('setIsPlay',true);
            this.$store.commit('setPicUrl',pic);
            this.$store.commit('setListIndex',index);
            this.$store.commit('setTitle',this.getName(1,name));
            this.$store.commit('setArtist',this.getName(0,name));
            this.$store.commit('setLyric',this.parseLyric(lyric));
            this.$store.commit('setIsLike',false);
            if(this.loginIn){
                let params=new URLSearchParams();
                params.append('userId',this.userInfo.id);
                params.append('type',0);
                params.append('songId',id);
                isLikeSong(params)
                .then(res=>{
                    if(res.data.result.isLike){
                        this.$store.commit('setIsLike',true);
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
            }
        },
        // 解析歌词
        parseLyric(text){
            let lines=text.split("\n");
            let pattern=/\[\d{2}:\d{2}.(\d{2}|\d{3})\]/g; // 匹配事件的正则表达式
            let res=[];
            if(!(/\[.+\]/).test(text)){
                return [[0,text]];
            }

            // 去掉格式不正确的行
            while(!pattern.test(lines[0])){
                lines=lines.slice(1);
            }

            for(let item of lines){
                let time=item.match(pattern);
                let value=item.replace(pattern,'');
                for(let item1 of time){
                    let arr=item1.slice(1,-1).split(':');
                    let seconds=parseInt(arr[0],10)*60+parseFloat(arr[1]); // 以十进制转换，得到时间
                    if(value!=null&&value!=""){
                        res.push([seconds,value]);
                    }
                }
            }

            res.sort((a,b)=>{
                return a[0]-b[0];
            });

            return res;
        },
        transferDate(date){
            return date.substring(0,10);
        }
    }
}