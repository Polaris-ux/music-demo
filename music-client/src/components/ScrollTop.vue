<template>
  <div class="scroll-top" @click="returnToTop">
      <div class="box-in"></div>
  </div>
</template>

<script>
export default {
    name:'scroll-top',
    methods:{
        returnToTop(){
            // 页面被卷去的长度为0
            document.documentElement.scrollTop=document.body.scrollTop=0;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/scroll-top.scss';
</style>