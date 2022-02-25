const song = {
    state:{
        songList:[],
        isPlay:false,
        url:'',
        id:'',
        playButtonUrl:'#icon-bofang',
        duration:0, //音乐时长
        curTime:0, // 当前音乐的播放位置
        changeTime:0, // 指定播放位置
        title:'', // 歌名
        artist:'', // 歌手名
        picUrl:'', // 歌曲图片名
        autoNext:true, // 用于自动触发播放下一首
        lyric:[], // 未处理的歌词数据
        songListInfo:null, // 单个歌单信息,
        listIndex:-1, // 当前歌曲在歌单中的位置
        volume: 50, // 音量

    },
    getters:{
        songList:(state)=>{
            let songList=state.songList;
            if(songList==null||songList==undefined){
                songList=JSON.parse(window.sessionStorage.getItem('songList'));
            }
            return songList;
        },
        isPlay:(state)=>{
            let play=JSON.parse(window.sessionStorage.getItem('isPlay'));
            if(play){
                state.isPlay=play;
            }
            return state.isPlay;
        },
        url:(state)=>{
            let url=state.url;
            if(url==null||url==undefined){
                url=JSON.parse(window.sessionStorage.getItem('url'));
            }
            return url;
        },
        id:(state)=>{
            let id=state.id;
            if(id==null||id==undefined){
                id=JSON.parse(window.sessionStorage.getItem('id'));
            }
            return id;
        },
        playButtonUrl:state=>{
            let playButtonUrl=state.playButtonUrl;
            if(playButtonUrl==null||playButtonUrl==undefined){
                playButtonUrl=JSON.parse(window.sessionStorage.getItem('playButtonUrl'));
            }
            return playButtonUrl;
        },
        duration:state=>{
            let duration=state.duration;
            if(duration==null||duration==undefined){
                duration=JSON.parse(window.sessionStorage.getItem('duration'));
            }
            return duration;
        },
        curTime:state=>{
            let curTime=state.curTime;
            if(curTime==null||curTime==undefined){
                curTime=JSON.parse(window.sessionStorage.getItem('curTime'));
            }
            return curTime;
        },
        changeTime:state=>{
            let changeTime=state.changeTime;
            if(changeTime==null||changeTime==undefined){
                changeTime=JSON.parse(window.sessionStorage.getItem('changeTime'));
            }
            return changeTime;
        },
        title:state=>{
            let title=state.title;
            if(title==null||title==undefined){
                title=JSON.parse(window.sessionStorage.getItem('title'));;
            }
            return title;
        },
        artist:state=>{
            let artist=state.artist;
            if(artist==null||artist==undefined){
                artist=JSON.parse(window.sessionStorage.getItem('artist'));
            }
            return artist;
        },
        picUrl:state=>{
            let picUrl=state.picUrl;
            if(picUrl==null||picUrl==undefined){
                picUrl=JSON.parse(window.sessionStorage.getItem('picUrl'));
            }
            return picUrl;
        },
        autoNext:state=>{
            let autoNext=state.autoNext;
            if(autoNext==null||autoNext==undefined){
                autoNext=JSON.parse(window.sessionStorage.getItem('autoNext'));
            }
            return autoNext;
        },
        lyric:state=>{
            let lyric=state.lyric;
            if(lyric==null||lyric==undefined){
                lyric=JSON.parse(window.sessionStorage.getItem('lyric'));
            }
            return lyric;
        },
        songListInfo:state=>{
            let temp=JSON.parse(window.sessionStorage.getItem('songListInfo'));
            if(temp){
                state.songListInfo=temp;
            }
            
            return state.songListInfo;
        },
        listIndex:state=>{
            let listIndex=state.listIndex;
            if(listIndex==null||listIndex==undefined){
                listIndex=JSON.parse(window.sessionStorage.getItem('listIndex'));
            }
            return listIndex;
        },
        volume:state=>{
            let volume=state.volume;
            if(volume==null||volume==undefined){
                volume=JSON.parse(window.sessionStorage.getItem('volume'));
            }
            return volume;
        },
    },
    mutations:{
        setSongList:(state,songList)=>{
            state.songList=songList;
            window.sessionStorage.setItem('songList',JSON.stringify(songList));
        },
        setIsPlay:(state,isPlay)=>{
            state.isPlay=isPlay;
            window.sessionStorage.setItem('isPlay',JSON.stringify(isPlay));

        },
        setUrl:(state,url)=>{
            state.url=url;
            window.sessionStorage.setItem('url',JSON.stringify(url));
        },
        setId:(state,id)=>{
            state.id=id;
        },
        setPlayButtonUrl:(state,btnUrl)=>{
            state.playButtonUrl=btnUrl;
            window.sessionStorage.setItem('playButtonUrl',JSON.stringify(btnUrl));
        },
        setDuration:(state,duration)=>{
            state.duration=duration;
            window.sessionStorage.setItem('duration',JSON.stringify(duration));
        },
        setCurTime:(state,curTime)=>{
            state.curTime=curTime;
            window.sessionStorage.setItem('curTime',JSON.stringify(curTime));
        },
        setChangeTime:(state,changeTime)=>{
            state.changeTime=changeTime;
            window.sessionStorage.setItem('changeTime',JSON.stringify(changeTime));
        },
        setTitle:(state,title)=>{
            state.title=title;
            window.sessionStorage.setItem('title',JSON.stringify(title));
        },
        setArtist:(state,artist)=>{
            state.artist=artist;
            window.sessionStorage.setItem('artist',JSON.stringify(artist));
        },
        setPicUrl:(state,picUrl)=>{
            state.picUrl=picUrl;
            window.sessionStorage.setItem('picUrl',JSON.stringify(picUrl));
        },
        setAutoNext:(state,autoNext)=>{
            state.autoNext=autoNext;
            window.sessionStorage.setItem('autoNext',JSON.stringify(autoNext));
        },
        setLyric:(state,lyric)=>{
            state.lyric=lyric;
            window.sessionStorage.setItem('lyric',JSON.stringify(lyric));
        },
        setListIndex:(state,listIndex)=>{
            state.listIndex=listIndex;
            window.sessionStorage.setItem('listIndex',JSON.stringify(listIndex));
        },
        setVolume:(state,volume)=>{
            state.volume=volume;
            window.sessionStorage.setItem('volume',JSON.stringify(volume));
        },
        setSongListInfo:(state,songListInfo)=>{
            state.songListInfo=songListInfo;
            window.sessionStorage.setItem('songListInfo',JSON.stringify(songListInfo));
        },

    }
}

export default song