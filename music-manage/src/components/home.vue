<template>
  <div>
      <the-header></the-header>
      <the-aside></the-aside>
      <div class="content-box" :class="{'content-collapse':collapse}">
          <router-view></router-view>
      </div>
  </div>
</template>

<script>
import theAside from './theAside';
import theHeader from './theHeader';
import bus from '../assets/js/bus';
export default {
    components: {
        theAside,
        theHeader
    },
    data() {
        return {
            collapse:false
        }
    },
    created() {
        bus.$on('collapse',msg=>{
            this.collapse=msg;
        })
    }
}
</script>

<style>

</style>