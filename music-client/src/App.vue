<template>
  <div id="app">
    <the-header />
    <router-view v-if="isRouterActive" class="music-content"/>
    <scroll-top/>
    <song-audio/>
    <the-aside/>
    <play-bar/>
    <the-footer/>
  </div>
</template>

<script>
import SongAudio from './components/SongAudio';
import ScrollTop from './components/ScrollTop';
import TheFooter from './components/TheFooter';
import TheHeader from './components/TheHeader';
import PlayBar from './components/PlayBar.vue';
import TheAside from './components/TheAside';
export default {
  name: 'App',
  components: {
    TheHeader,
    ScrollTop,
    TheFooter,
    SongAudio,
    PlayBar,
    TheAside,
  },
  provide(){
    return {
      reload:this.reload
    }
  },
  data () {
    return {
      isRouterActive:true,
    }
  },
  methods:{
    reload(){
     this.isRouterAlive = false
     this.$nextTick(function(){
       this.isRouterAlive = true
     })
    }
  }

}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>
