import Vue from 'vue'
import Vuex from 'vuex'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/router.js'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import App from './App.vue'
import axios from 'axios'
import './components/_globals'

Vue.use(Vuex)
Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.config.productionTip = false
Object.defineProperty(Vue.prototype, '$axios', { value: axios });

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
  let token = localStorage.getItem('token')
  if (to.path == '/'){
    return next()
  }
  if (!token && to.path !== '/login') {
    return next('/login')
  }
  next()
});

const store = new Vuex.Store({
  state: {
    id: 0,
    status: null,
  },
  mutations: {
    status(state, status) {
      state.status = status
    },
    id(state, id){
      state.id = id
    }
  }
})

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
