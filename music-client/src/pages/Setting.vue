<template>
  <div class="setting">
      <div class="leftCol">
          <div class="settingsMainHeader">
              设置
              <ul class="setting-aside">
                  <li v-for="(item,index) in settingList" :key="index" :class="{activeColor:activeName==item.name}" @click="handleChange(item)">
                      {{item.name}}
                  </li>
              </ul>
          </div>
      </div>
      <div class="contentCol">
          <component :is="componentDefault"></component>
      </div>
  </div>
</template>

<script>
import {mixin} from '../mixins/tools';
import { mapGetters} from 'vuex';
import Info from '../components/Info';
import Upload from '../components/Upload';
export default {
    name:'setting',
    computed:{
        ...mapGetters([
            'userInfo'
        ])
    },
    components:{
        Info,
        Upload
    },
    data(){
        return {
            settingList:[
                {name:"个人信息",path:"Info"},
                {name:"修改头像",path:"Upload"},
            ],
            activeName:"个人信息",
            componentDefault:"Info",
        }
    },
    methods:{
        handleChange(item){
            this.activeName=item.name;
            this.componentDefault=item.path;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/setting.scss';
</style>