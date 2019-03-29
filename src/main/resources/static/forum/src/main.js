import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/router.js'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import App from './App.vue'
import axios from 'axios'

Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.config.productionTip = false
Object.defineProperty(Vue.prototype, '$axios', { value: axios });

// 添加请求拦截器

axios.interceptors.request.use(function (config) {
  let token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config;
}, function (error) {
  return Promise.reject(error);
});


router.beforeEach((to, from, next) => {
  // 在此处就需要判断, 是否能进入一些禁地(需要登录的页面)
  // console.log(to, from)
  // 如果添加了导航守卫的回调函数
  // 必须调用next函数  将其引导到某个页面, 如果不传参数就是不干预路由跳转
  // let token = localStorage.getItem('token')
  if (to.path == '/'){
    return next()
  }
  // if (!token && to.path !== '/login') {
  //   // console.log('我在疯狂的进login')
  //   return next('/login')
  // }
  next()
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
