import Vue from 'vue'
import Router from 'vue-router'
import index from '../views/index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/postForum',
      meta:{ 
        requiresAuth: true 
      },
      name: 'postForum',
      component: () => import('../views/postForum')
    },
    {
      path: '/detail',
      meta:{ 
        requiresAuth: true 
      },
      name: 'detail',
      component: () => import('../views/detail')
    },
    {
      path:'/login',
      name: 'login',
      component: () => import('../views/login')
    },
    {
      path: '/information',
      meta:{
        requiresAuth: true
      },
      name: 'information',
      component: () => import('../views/information')
    }
  ]
})
