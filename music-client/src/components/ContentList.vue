<template>
    <div class="content-list">
        <ul class="section-content">
            <li class="content-item" v-for="(item,index) in contentList" :key="index" @click="goAlbum(item,item.name)">
                <div class="kuo">
                    <img class="item-img" :src="attachImageUrl(item.pic)"/>
                    <div class="mask">
                        <svg class="icon">
                            <use xlink:href="#icon-bofang"></use>
                        </svg>
                    </div>
                </div>
                <p class="item-name">{{item.name?item.name:item.title}}</p>
            </li>
        </ul>
    </div>
</template>

<script>
import {mixin} from "../mixins/tools";
export default {
    name:'content-list',
    props:['contentList'],
    mixins:[mixin],
    methods:{
        goAlbum(item,type){
            this.$store.commit('setSongListInfo',item);
            console.log(item)
            if(type){
                this.$router.push({path:`singer-album/${item.id}`});
            }else{
                this.$router.push({path:`songList-album/${item.id}`});
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
</style>