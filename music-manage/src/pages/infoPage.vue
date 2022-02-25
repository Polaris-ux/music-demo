<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <!-- span：总数为24，为6即占1/4 -->
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{consumerNum}}</div>
              <div>用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{songNum}}</div>
              <div>歌曲总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{singerNum}}</div>
              <div>歌手总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="grid-num">{{songListNum}}</div>
              <div>歌单总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mgb20">
      <!-- span：总数为24，为6即占1/4 -->
      <el-col :span="12">
        <el-card>
          <h3>用户性别比例图</h3>
          <div class="bin-grap">
            <ve-pie :data="consumerSex"></ve-pie>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {mixin} from '../mixins/tips';
import {awareConsumerOfMan,awareConsumerNum,awareSongNum,awareSongListNum,awareSingerNum} from '../api/index';
export default {
  mixins: [mixin],
  data(){
    return{
      consumerNum:0,
      songNum:0,
      singerNum:0,
      songListNum:0,
      consumerSex:{
        columns:['性别',"总数"],
        rows:[
          {"性别":"男","总数":0},
          {"性别":"女","总数":0}
        ]
      }
    }
  },
  // 页面加载时执行
  created() {

  },
  // 页面元素加载完后执行
  mounted(){
    this.allSongNum();
    this.allSingerNum();
    this.allSongListNum();
    this.allConsumers();
  },
  methods:{
    allConsumers(){
      awareConsumerNum()
      .then(res=>{
        if(res.data.code=='50200'){
          this.consumerNum=res.data.result.consumerNum;
          this.consumerSexGrap();
        }else{
          this.notify("获取数据失败",'error');
        }
      })
    },
    allSongNum(){
      awareSongNum()
      .then(res=>{
        if(res.data.code=='50200'){
          this.songNum=res.data.result.songNum;
        }else{
          this.notify("获取数据失败",'error');
        }
      })
    },
    allSingerNum(){
      awareSingerNum()
      .then(res=>{
        if(res.data.code=='50200'){
          this.singerNum=res.data.result.singerNum;
        }else{
          this.notify("获取数据失败",'error');
        }
      })
    },
    allSongListNum(){
      awareSongListNum()
      .then(res=>{
        if(res.data.code=='50200'){
          this.songListNum=res.data.result.songListNum;
        }else{
          this.notify("获取数据失败",'error');
        }
      })
    },
    consumerSexGrap(){
      awareConsumerOfMan()
      .then(res=>{
        if(res.data.code=='50200'){
          let manNum=res.data.result.consumerOfMan;
          let ladyNum=this.consumerNum-manNum;
          this.consumerSex.rows[0]['总数']=manNum;
          this.consumerSex.rows[1]['总数']=ladyNum;
        }
      })
    }
  }
}
</script>

<style>
.grid-content{
  /* 自由布局 */
  display: flex;
  align-items: center;
  height:60px;
}

.grid-cont-center{
  font-size: 20px;
  color:darkgray;
  text-align: center;
  flex: 1;
}

.grid-num{
  font-size: 30px;
  /* 加粗 */
  font-weight: bold;
}

.bin-grap{
  
}
</style>