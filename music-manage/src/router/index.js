import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: resolve => require(['../pages/login.vue'], resolve)
    },
    {
      path: '/home',
      component: resolve =>require(['../components/home.vue'],resolve),
      children: [
        {
          path:'/info',
          component: resolve =>require(['../pages/infoPage.vue'],resolve)
        },
        {
          path:'/consumer',
          component: resolve =>require(['../pages/consumerPage.vue'],resolve)
        },
        {
          path:'/singer',
          component: resolve =>require(['../pages/singerPage.vue'],resolve)
        },
        {
          path:'/songList',
          component: resolve =>require(['../pages/songList.vue'],resolve)
        },
        {
          path:'/song',
          component: resolve =>require(['../pages/songPage.vue'],resolve)
        },
        {
          path:'/listSong',
          component: resolve =>require(['../pages/listSongPage.vue'],resolve)
        },
        {
          path:'/consumer',
          component: resolve =>require(['../pages/consumerPage.vue'],resolve)
        },
        {
          path:'/collect',
          component: resolve =>require(['../pages/collectPage.vue'],resolve)
        },
        ,
        {
          path:'/comment',
          component: resolve =>require(['../pages/commentPage.vue'],resolve)
        }
      ]
    }
  ]
})
