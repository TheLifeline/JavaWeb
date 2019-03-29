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


router.beforeEach((to, from, next) => {
  let token = localStorage.getItem('token')
  if (to.path == '/'){
    return next()
  }
  if (!token && to.path !== '/login') {
    return next('/login')
  }
  next()
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
