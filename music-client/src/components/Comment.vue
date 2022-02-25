<template>
  <div>
    <div class="comment">
      <h2>评论</h2>
      <div class="comment-msg">
          <div class="avatar-img">
              <img :src="getUrl(userInfo.avator)"/>
          </div>
          <el-input class="comment-input" type="textarea" v-model="text" placeholder="发表评论："></el-input>
      </div>
      <el-button @click="submitText" type="primary" class="sub-btn">发表</el-button>
    </div>
    <p>精彩评论：共{{this.commentList.length}}条评论</p>
    <ul class="popular" v-for="(item,index) in commentList" :key="index">
        <li>
            <div class="popular-img">
                <img :src="getUrl(getConsumerById(item.userId).avator)"/>
            </div>
            <div class="popular-msg">
                <ul>
                    <li class="name">{{getConsumerById(item.userId).username}}</li>
                    <li class="time">{{item.createTime}}</li>
                    <li class="content">{{item.content}}</li>
                </ul>
            </div>
            <div class="up" ref="up" @click="postUp(item.id,index)">
                <svg class="icon">
                    <use xlink:href="#icon-zan"></use>
                </svg>
                {{item.up}}
            </div>
        </li>
    </ul>
  </div>
</template>

<script>
import {mixin} from '../mixins/tools';
import {mapGetters} from 'vuex';
import {addComment,getSongListComment,getSongComment,incrLike,getUserByIds} from '../api/index';
export default {
    name:'comment',
    props:[
        'playId',
        'type', // 歌单评论还是歌曲评论
    ],
    mixins:[mixin],
    computed:{
        ...mapGetters([
            'userInfo',
            'loginIn',
        ])    
    },
    data(){
        return {
            text:'',
            commentList:[],
            consumerList:[],// 评论用户的集合
        }
    },
    created() {
        
    },
    mounted() {
        this.comments();
    },
    methods:{
        submitText(){
            if(!this.loginIn){
                this.$message({
                    type:'warning',
                    message:'请先登录',
                    duration: 1000
                });
                return;
            }
            if(this.text!=null&&this.text!=""){
                let params=new URLSearchParams();
                params.append('content',this.text);
                params.append('userId',this.userInfo.id);
                params.append('songListId',this.playId);
                params.append('type',this.type);
                params.append('up',0);
                addComment(params)
                .then(res=>{
                    if(res.data.code == '50200'){
                        // 刷新评论列表
                        this.comments();
                        this.text='';
                        this.$message.success('评论成功');
                    }else{
                        this.$message.error(res.data.message);
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
            }
        },
        comments(){
            let params = new URLSearchParams();
            
            if(this.type==1){
                params.append('songListId',this.playId);
                getSongListComment(params)
                .then(res=>{
                    if(res.data.code=='50200'){
                        this.commentList=res.data.result.comments;
                        this.getConsumer();
                    }else{
                        this.$message.error(res.data.message);
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
            }else{
                params.append('songId',this.playId);
                getSongComment(params)
                .then(res=>{
                    if(res.data.code=='50200'){
                        this.commentList=res.data.result.comments;
                    }else{
                        this.$message.error(res.data.message);
                    }
                })
                .catch(err=>{
                    console.log(err);
                })
            }
        },
        getConsumer(){
            let userIds=new Set();
            let ids=[];
            for(let comment of this.commentList){
                userIds.add(comment.userId);
            }
            for(let id of userIds.values()){
                ids.push(id);
            }
            let params=new URLSearchParams();
            params.append('ids',ids);
            getUserByIds(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.consumerList=res.data.result.consumerList;
                    console.log(this.consumerList);
                }else{
                    this.$message.error("获取评论用户信息失败");
                }
            })
            .catch(err=>{
                console.log(err);
            })
        },
        getConsumerById(id){
            for(let consumer of this.consumerList){
                if(id==consumer.id){
                    return consumer;
                }
            }
            return null;
        },
        postUp(id,index){
            let params =new URLSearchParams();
            params.append('id',id);
            incrLike(params)
            .then(res=>{
                if(res.data.code=='50200'){
                    this.commentList[index].up=this.commentList[index].up+1;
                    this.$refs.up[index].children[0].style.color='#2796cd';
                }else{
                    this.$message.error('点赞失败');
                }
            })
            .catch(err=>{
                console.log(err);
            })
        }
    }

}
</script>

<style lang="scss" scoped>
@import '../assets/css/comment.scss';
</style>