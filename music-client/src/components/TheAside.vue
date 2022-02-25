<template>
    <transition name="slide-fade">
        <div class="the-aside" v-if="showAside">
            <h2 style="color:orange;text-align:center;font-family:STKaiti;">播放列表</h2>
            <ul class="menus">
                <li v-for="(item,index) in songList" :key="index" :class="{'is-play':id==item.id}"
                @click="toPlay(item.id,item.url,item.pic,index,item.name,item.lyric)">
                    {{item.name}}
                </li>
            </ul>
        </div>
    </transition>
</template>

<script>
import {mapGetters} from 'vuex';
import {mixin} from '../mixins/tools';
export default {
    name:'the-aside',
    mixins: [mixin],
    computed:{
        ...mapGetters([
            'showAside',// 是否显示播放中的歌曲列表
            'songList', // 歌曲列表
            'id' // 歌曲id
        ])
    },
    mounted(){
        let _this=this;
        document.addEventListener('click',function(){
            _this.$store.commit('setShowAside',false);
        },true);
    },
    methods:{
        
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-aside.scss';
</style>