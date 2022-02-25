import axios from 'axios';
// 超时时间为5秒
axios.defaults.timeout=5000; 
// 允许跨域
axios.defaults.withCredentials=true;
// 响应头 Content-Type
axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=UTF-8';
// 基础url
axios.defaults.baseURL="http://localhost:9001";

// 响应拦截器
axios.interceptors.response.use(
    response=>{
        // 如果response里面的status是200，说明访问到接口了，否则错误
        if (response.status==200){
            // Promise是异步的
            return Promise.resolve(response);
        }else{
            return Promise.reject(response);
        }
    },
    error=>{
        if(error.response.status){
            switch(error.response.status){
                case 401:
                    router.replace({
                        path:'/',
                        // 存储当前的地址
                        query:{
                            redirect: router.currentRoute.fullPath
                        }
                    });
                case 404:
                    break;
            }
            return Promise.reject(error.response);
        }
    }
)

/**
 * 封装一个get方法
 */
 export function get(url,params={}){
    return new Promise((resolve,reject)=>{
        axios.get(url,{params:params})
                .then(response=>{
                    resolve(response);
                })
                .catch(err=>{
                    reject(err);
                })
    });
}

/**
 * 封装一个post方法
 */
 export function post(url,data={},config={}){
    return new Promise((resolve,reject)=>{
        axios.post(url,data,config)
                .then(response=>{
                    resolve(response);
                })
                .catch(err=>{
                    reject(err);
                })
    });
}