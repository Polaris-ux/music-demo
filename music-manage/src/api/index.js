import {get,post} from './http';

// 判断登录是否成功
export const getLoginInfo=(params)=>post(`admin/login`,params);
// 获取所有歌手
export const getAllSingers=()=>get(`singer/getAllSinger`);
export const getSingersByPage=(params)=>get(`singer/getSingersByKey`,params);
export const insertSinger=(params)=>post(`singer/addSinger`,params);
export const awareSingerNum=()=>get(`singer/getNum`);
export const editSingerInfo=(params)=>post(`singer/editSinger`,params);
export const removeSinger=(id)=>get(`singer/removeSinger?id=${id}`);
export const batchRemoveSingers=(params)=>post(`singer/batchRemoveSinger`,params);

// 歌曲
export const addSongReq=(params,config)=>post(`song/addSong`,params,config);
export const getSongByPage=(params)=>get(`song/getSongsBySinger`,params);
export const editSongInfo=(params)=>post('song/updateSongInfo',params);
export const removeSongById=(params)=>get(`song/removeSongById`,params);
export const batchRemoveSongs=(params)=>get(`song/batchRemoveSongById`,params);
export const batchGetSongs=(params)=>get(`song/getSongByIds`,params);
export const getSongIdByName=(name)=>get(`song/getIdByName?name=${name}`);
export const awareSongNum=()=>get(`song/getNum`);

// 歌单
export const addSongListReq=(params)=>post(`songList/addSongList`,params);
export const getSongListByKey=(params)=>get(`songList/getSongListByKey`,params);
export const editSongListInfo=(params)=>post(`songList/updateSongList`,params);
export const removeSongListById=(params)=>get(`songList/delSongListById`,params);
export const batchRemoveSongLists=(params)=>get(`songList/batchRemoveSongListByIds`,params);
export const awareSongListNum=()=>get(`songList/getNum`);

// 歌单对应的歌曲
export const getSongIdsBySongList=(songListId)=>get(`listSong/getSongIds?songListId=${songListId}`);
export const addListSongReq=(params)=>post(`listSong/addListSong`,params);
export const removeListSong=(params)=>get(`listSong/delListSong`,params);
export const batchRemoveListSong=(params)=>get(`listSong/batchRemoveListSong`,params);

// 用户
export const getConsumersBykey=(params)=>get(`consumer/getConsumersByKey`,params);
export const addUser=(params)=>post(`consumer/addConsumer`,params);
export const deleteConsumer=(params)=>post(`consumer/delConsumer`,params);
export const batchRemoveConsumer=(params)=>post(`consumer/batchDeleteConsumer`,params);
export const editConsumer=(params)=>post(`consumer/updateConsumer`,params);
export const awareConsumerNum=()=>get(`consumer/getNum`);
export const awareConsumerOfMan=()=>get(`consumer/getManNum`);

// 收藏歌曲
export const getCollects=(params)=>get(`collect/getAllCollectByUserId`,params);
export const delByUserAndSong=(params)=>post(`collect/delByUserAndSong`,params);
export const batchDelByUserAndSong=(params)=>post(`collect/batchDelByUserAndSong`,params);

// 评论
export const batchRemove=(params)=>post(`comment/batchRemove`,params);
export const removeById=(params)=>post(`comment/removeById`,params);
export const getCommentVoPage=(params)=>get(`comment/getCommentVoPage`,params);