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
      // redirect: {
      //   name: 'personMessage'
      // },
      name: 'information',
      component: () => import('../views/information'),
      children:[
        {
          path:'personMessage',
          name:'personMessage',
          component:() => import('../views/informations/personMessage'),
        },
        {
          path:'editMessage',
          component:() => import('../views/informations/editMessage'),
        },
        {
          path:'editPassword',
          component:() => import('../views/informations/editPassword'),
        },
      ]
    },
    {
      path: '/administrator',
      meta:{
        requiresAuth: true
      },
      // redirect: {
      //   name: 'personMessage'
      // },
      name: 'administrator',
      component: () => import('../views/administrator'),
      children:[
        {
          path:'blogManagement',
          name:'blogManagement',
          component:() => import('../views/administration/blogManagement'),
        },
        {
          path:'commentManagement',
          name:'commentManagement',
          component:() => import('../views/informations/editMessage'),
        },
        {
          path:'userManagement',
          name:'userManagement',
          component:() => import('../views/informations/editPassword'),
        },
      ]
    }
  ]
})