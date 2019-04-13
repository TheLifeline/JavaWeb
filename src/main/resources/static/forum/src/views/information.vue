<template>
  <div style="background: #f1f1f1; height:100vh">
    <header style="width: 100%">
      <div class="header">
        <div class="header-middle">
          <div class="header-l">
            <h1>北航在线编程社区</h1>
          </div>
          <div class="header-r" v-if="!isLogin">
            <el-button @click="handleLogin">登录</el-button>
            <el-button @click="postForum">发帖子</el-button>
          </div>
          <div class="header-r" v-else>
            <el-button @click="LoginOut">登出</el-button>
            <el-button @click="postForum">发帖子</el-button>
            <el-button @click="index">返回主页</el-button>
          </div>
        </div>
      </div>
    </header>
    <el-container width="200px">
      <el-aside>
        <el-menu :router="true">
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>用户管理</span>
                </template>
                <el-menu-item-group>
                    <template slot="title">用户管理</template>
                    <el-menu-item index="/information/personMessage">个人信息</el-menu-item>
                    <el-menu-item index="/information/editMessage">修改信息</el-menu-item>
                    <el-menu-item index="/information/editPassword">修改密码</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
      </el-aside>
      <el-mian style="width:100%">
        <router-view></router-view>
      </el-mian>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "formation",
  data() {
    return {
      isLogin: false
    };
  },
  mounted() {
    this.isLogined();
  },
  methods: {
    postForum() {
      this.$router.push("/postForum");
    },
    handleTitle(id) {
      this.$router.push({ path: "/detail", query: { id: id } });
    },
    handleLogin() {
      this.$router.push("/login");
    },
    information() {
      this.$router.push("/information");
    },
    index(){
      this.$router.push('/')
    },
    isLogined: function() {
      this.$axios
        .get("http://localhost:8081/islogin")
        .then(res => {
          if (res.data.data) {
            this.isLogin = res.data.data;
          } else {
            this.$message({
              message: res.data.msg,
              type: "warning"
            });
          }
        })
        .catch(error => {
          if (error.response) {
            this.$message({
              message: error.response.data.msg,
              type: "warning"
            });
          }
        });
    },
    LoginOut() {
      this.$message({
        message: "success!"
      });
      this.isLogin = false;
      localStorage.clear();
    }
  }
};
</script>

<style scoped>
.header {
  width: 100%;
  height: 50px;
  background-color: #409eff;
}
.header-middle {
  margin: 0 auto;
  width: 1170px;
  display: flex;
  justify-content: space-between;
}
.header-l {
  width: 250px;
  height: 50px;
}
.header-l h1 {
  color: #fff;
  font-weight: 400;
  font-size: 30px;
  margin-top: 5px;
}
.header-r {
  width: 400px;
  height: 50px;
  margin-top: 5px;
}
</style>