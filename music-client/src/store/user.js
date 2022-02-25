const user = {
    state:{
        userId: '',                 //用户id
        username: '',               //用户账号
        avator: '',               //用户头像
        userInfo:{},
    },
    getters: {
        userId: state => {
            let userId = JSON.parse(window.sessionStorage.getItem('userId'))
            if(!userId){
                userId = state.userId 
            }
            return userId
        },
        username: state => {
            let username = JSON.parse(window.sessionStorage.getItem('username'));
            if(!username){
                username = state.username;
            }
            return username
        },
        avator: state => {
            let avator = JSON.parse(window.sessionStorage.getItem('avator'))
            if(!avator){
                avator = state.avator;
            }
            return avator;
        },
        userInfo: state => {
            let userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'))
            if(userInfo){
                state.userInfo = userInfo; 
            }
            return state.userInfo;
        }

        
    },
    mutations: {
        setUserId: (state,userId) => {
            state.userId = userId
            window.sessionStorage.setItem('userId',JSON.stringify(userId))
        },
        setUsername: (state,username) => {
            state.username = username
            window.sessionStorage.setItem('username',JSON.stringify(username))
        },
        setAvator: (state,avator) => {
            state.avator = avator
            window.sessionStorage.setItem('avator',JSON.stringify(avator))
        },
        setUserInfo: (state,userInfo) => {
            state.userInfo = userInfo
            window.sessionStorage.setItem('userInfo',JSON.stringify(userInfo))
        }
    }
}

export default user