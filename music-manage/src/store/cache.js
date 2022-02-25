import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store=new Vuex.Store({
    state:{
        HOST: 'http://127.0.0.1:9001',
        isPlay:false, // 歌曲是否播放中
        url:'',// 歌曲地址
        id:'',// 歌曲id
    },
    // 获取参数
    getters:{
        isPlay: state=>state.isPlay,
        url: state=>state.url,
        id: state=>state.id
    },
    // 设置参数
    mutations:{
        setIsPlay: (state,isPlay)=>{state.isPlay=isPlay},
        setUrl: (state,url)=>{state.url=url},
        setId: (state,id)=>{state.id=id}
    }
})

export default store;