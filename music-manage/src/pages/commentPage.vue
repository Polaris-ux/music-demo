<template>
  <div class='table'>
    <div class='container'>
      <div class="crumbs">
        <i class="el-icon-tickets"></i>歌单评论
      </div>  
      <div class="handle-box">
        <el-button type="primary" size='mini' @click="mulDelIds.length==0?batchDelVisible=false:batchDelVisible=true">批量删除</el-button>
      </div>
    </div>
    <el-table
      :data="comments"
      style="width: 100%"
      border
      @selection-change="commentSelectChange">
      <el-table-column type='selection' width='40'></el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="110"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        width="80"
        align='center'>
        <template slot-scope="scope">
          {{changeSex(scope.row.sex)}}
        </template>
      </el-table-column>
      <el-table-column
        prop="up"
        label="点赞数"
        width="110"
        align='center'>
      </el-table-column>
      <el-table-column
        prop="content"
        label="评论内容"
        style="width:100%;overflow:scroll"
        align='center'>
      </el-table-column>
      <el-table-column
        label="操作"
        width="150"
        align='center'>
        <template slot-scope="scope">
          <div class="btn-del">
            <el-button type="danger" size='mini' @click="handleRemoveSong(scope.row)">删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination background 
      :total="total" 
      :current-page="currentPage" 
      :page-size="pageSize" 
      @current-change="handleCurrentChange" 
      layout="total,prev,pager,next">

      </el-pagination>
    </div>
    <el-dialog title="删除评论" :visible.sync="delVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除!</div>  
      <span slot="footer">
        <el-button size='mini' @click='editVisible=false' >取消</el-button>
        <el-button size='mini' @click="delComment" type='danger'>确认</el-button>
      </span>
    </el-dialog>
    <el-dialog title="批量删除评论" :visible.sync="batchDelVisible" width='400px' center>
      <div align='center'>删除后不可恢复，请确认是否删除{{mulDelIds.length}}条评论！</div>  
      <span slot="footer">
        <el-button size='mini' @click='batchDelVisible=false' >取消</el-button>
        <el-button size='mini' @click="batchDelComment" type='danger'>确认</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mixin} from '../mixins/tips';
import '@/assets/js/iconfont.js';
import {batchRemove,removeById,getCommentVoPage} from '../api/index';

export default {
  mixins:[mixin],
  data(){
    return {
      delVisible: false,
      batchDelVisible:false,
      comments:[],
      currentPage:1,
      pageSize:7,
      total:0,
      mulDelIds:[],// 批量删除的id集合
      songListId:-1,
      commentId:-1,
    }
  },
  watch:{
  },
  created(){
    this.songListId=this.$route.query.songListId;
    this.getAllComments();
  },
  methods:{
    // 获取所有评论
    getAllComments(){
      let params=new URLSearchParams();
      params.append('pageNum',this.currentPage);
      params.append('pageSize',this.pageSize);
      params.append('songListId',this.songListId);
      getCommentVoPage(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.comments=[];
          this.total=res.data.result.pageInfo.total;
          this.comments=res.data.result.pageInfo.list;
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 获取当前页
    handleCurrentChange(val){
      this.currentPage=val;
      this.getAllComments(null);
    },
    // 删除评论
    delComment(){
      let params=new URLSearchParams();
      params.append("id",this.commentId);
      removeById(params)
      .then(res=>{
        if(res.data.code=='50200'){
          this.notify('删除评论成功','success');
          this.getAllComments();
        }else{
          this.notify(res.data.message,'error');
        }
      })
      .catch(err=>{
        console.log(err);
      })
      this.delVisible=false;
    },
      // 批量删除歌曲
    batchDelComment(){
      let params=new URLSearchParams();
      params.append("ids",this.mulDelIds);
      batchRemove(params)
      .then(res=>{
        console.log(res);
        if(res.data.code=='50200'){
          this.notify("批量删除评论成功",'success');
          this.getAllComments();
        }else{
          this.notify(res.data.message,'error');
        }
      }).catch(err=>{
        console.log(err);
      })

      this.batchDelVisible=false;
    },
    handleRemoveSong(item){
      this.commentId=item.id;
      this.delVisible=true;
    },
    commentSelectChange(item){
      this.mulDelIds=[];
      for(let comment of item){
        this.mulDelIds.push(comment.id);
      }
    }
    
  }

}
</script>

<style scoped>
.handle-box{
  margin-bottom:20px;
}

.song-img{
  width:100%;
  height:80px;
  margin-bottom:5px;
  /* 超出的部分隐藏 */
  overflow: hidden;
}
.song-img img{
  object-fit: cover;
  width:80px;
  height:80px;
  border-radius:5px;
}

.search-box{
  width:470px;
  display: block;
  float: right;

}
.handle-search{
  width:400px;
}
.search-box button{
  float: right;
}

.pagination{
  display: flex;
  justify-content:center;
}

.btn-edit button{
  margin-bottom:4px;
  width:80px;
}
.btn-del button{
  margin-bottom:4px;
  width:80px;
}

.play{
  /* 绝对定位 */
  position: absolute;
  /* 在最上方 */
  z-index: 100;
  width: 80px;
  height: 80px;
  /* 自由布局 */
  display: flex;
  align-items: center;
  justify-content:center;
  cursor: pointer;
  top:15px;
  left:15px;
}

.icon{
  /* em:附控件的大小 */
  width: 2em;
  height:2em;
  color:aliceblue;
  fill:currentColor;
  overflow: hidden;

}

</style>