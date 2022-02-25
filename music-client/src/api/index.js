import Axios from 'axios';
import {get,post} from './http';

// 获取所有歌手
export const getAllSingerByType=(params)=>get(`singer/getAllSingerByType`,params);
export const getSingersByPage=(params)=>get(`singer/getSingersByKey`,params);


// 歌曲
export const getSongBySinger=(params)=>get(`song/getSongsBySinger`,params);
export const getSongPage=(params)=>get(`song/getSongsPage`,params);
export const getSongByKey=(params)=>get(`song/getSongsByKey`,params);
export const getSongsByIds=(params)=>get(`song/getSongByIds`,params);
export const getAllSongBySinger=(params)=>get(`song/getAllSongBySinger`,params);

// 歌单
export const getSongListByKey=(params)=>get(`songList/getSongListByKey`,params);
export const getAllSongListByKey=(params)=>get(`songList/getAllSongListByKey`,params);

// 歌单对应的歌曲
export const getSongIdsBySongList=(songListId)=>get(`listSong/getSongIds?songListId=${songListId}`);

// 用户
export const getConsumersBykey=(params)=>get(`consumer/getConsumersByKey`,params);
export const addConsumer=(params)=>post(`consumer/addConsumer`,params);
export const loginReq=(params)=>post('consumer/login',params);
export const getUserByIds=(params)=>get(`consumer/getUserByIds`,params);
export const updateConsumer=(params)=>post(`consumer/updateConsumer`,params);
export const getUserById=(params)=>get(`consumer/getUserById`,params);
// 下载
export const download=(url)=>new Axios({
    url:url,
    type:'get',
    responseType:'blob'
});

// 评价
export const addRank=(params)=>post(`ranks/addRank`,params);
export const getRanks=(params)=>get(`ranks/getAllRank`,params);
export const getRanksInfo=(params)=>get(`ranks/getRankInfo`,params);

// 评论
export const addComment=(params)=>post(`comment/addComment`,params);
export const getSongListComment=(params)=>get(`comment/getSongListComment`,params);
export const getSongComment=(params)=>get(`comment/getSongComment`,params);
export const incrLike=(params)=>post(`comment/incrUp`,params);

// 收藏
export const addCollect=(params)=>post(`collect/add`,params);
export const delCollect=(params)=>post(`collect/delCollect`,params);
export const getAllCollectByUserId=(params)=>get(`collect/getAllCollectByUserId`,params);
export const isLikeSong=(params)=>get(`collect/isLike`,params);