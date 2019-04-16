<template>
  <div id="app" style="background: #f1f1f1; height:100vh">
    <BlogHeader/>
    <router-view/>
  </div>
</template>

<script>
import BlogHeader from './views/blog-header.vue'

export default {
  name: 'app',
  components:{
    BlogHeader
  },

}
</script>