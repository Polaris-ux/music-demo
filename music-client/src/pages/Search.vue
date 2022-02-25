<template>
  <div class="search" >
      <nav class="searchList-nav" ref="change">
          <span :class="{isActive:toggle=='SearchSongs'}" @click="checkView('SearchSongs')">歌曲</span>
          <span :class="{isActive:toggle=='SearchSongList'}" @click="checkView('SearchSongList')">歌单</span>
      </nav>
      <component :is="currentView"></component>
  </div>
</template>

<script>
import SearchSongs from '../components/search/SearchSongs.vue';
import SearchSongList from '../components/search/SearchSongList.vue';
export default {
    name:'search',
    components:{
        SearchSongs,
        SearchSongList
    },
    data(){
        return {
            toggle:'SearchSongs',
            currentView:'SearchSongs',// 默认的页面
        }
    },
    methods:{
        checkView(view){
            this.currentView=view;
            this.toggle=view;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/search.scss';
</style>