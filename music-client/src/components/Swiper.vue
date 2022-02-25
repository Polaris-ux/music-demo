<template>
  <div class="swiper">
      <!-- 轮播图：interval:每隔多少毫秒轮换一次 -->
      <el-carousel :interval="4000" type="card" height="280px">
          <el-carousel-item v-for="(item,index) in swiperList" :key="index">
              <img :src="item.picImag"/>
          </el-carousel-item>
      </el-carousel>
  </div>
</template>

<script>
import {swiperList} from '../assets/data/swiper';
export default {
    name:"swiper",
    data(){
        return{
            swiperList:[]
        }
    },
    created(){
        console.log(swiperList);
        this.swiperList=swiperList;
        console.log(this.swiperList[0].picImag);
    }
}
</script>

<style scoped>
@import '../assets/css/swiper.scss';

</style>