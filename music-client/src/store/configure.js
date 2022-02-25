const configure ={
    state:{
        HOST:"http://127.0.0.1:9001",
        activeName:'', // 当前选中的菜单名
        showAside:false,
        loginIn:false,
        isLike:false, // 是否喜欢收藏歌曲
    },
    getters:{
        activeName:(state)=>{
            let activeName=state.activeName;
            if(!activeName){
                activeName=JSON.parse(window.sessionStorage.getItem('activeName'));
            }
            return activeName;
        },
        showAside:(state)=>{
            let showAside=state.showAside;
            if(showAside==null||showAside==NaN){
                showAside=JSON.parse(window.sessionStorage.getItem('showAside'));
            }
            return showAside;
        },
        loginIn:(state)=>{
            let temp=JSON.parse(window.sessionStorage.getItem('loginIn'));
            if(temp){
                state.loginIn=temp;
            }
            return state.loginIn;
        },
        isLike:(state)=>{
            let temp=JSON.parse(window.sessionStorage.getItem('isLike'));
            if(temp){
                state.isLike=temp;
            }
            return state.isLike;
        }
    },
    mutations:{
        setActiveName:(state,activeName)=>{
            state.activeName=activeName;
            // 放入缓存中
            window.sessionStorage.setItem('activeName',JSON.stringify(activeName));
        },
        setShowAside:(state,showAside)=>{
            state.showAside=showAside;
            // 放入缓存中
            window.sessionStorage.setItem('showAside',JSON.stringify(showAside));
        },
        setLoginIn:(state,loginIn)=>{
            state.loginIn=loginIn;
            // 放入缓存中
            window.sessionStorage.setItem('loginIn',JSON.stringify(loginIn));
        },
        setIsLike:(state,isLike)=>{
            state.isLike=isLike;
            // 放入缓存中
            window.sessionStorage.setItem('isLike',JSON.stringify(isLike));
        },
    }
}

export default configure