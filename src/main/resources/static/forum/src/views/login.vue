<template>
  <div style="width:100vw; height:100vh; background-color:#f1f1f1">
    <header style="width: 100%">
      <div class="header">
        <div class="header-middle">
          <div class="header-l">
            <h1>云论坛</h1>
          </div>
          <div class="header-r">
            <el-button @click="handleJumpIndex">首页</el-button>
            <!-- <el-button @click="postForum">发帖子</el-button> -->
          </div>
        </div>
      </div>
    </header>
    <h1 style="color:#333; text-align:center; margin-top:30px">欢迎来到云论坛</h1>
    <div style="margin:70px auto 0; width:40%;">
      <el-card>
        <el-form>
          <el-form-item label="账号">
            <el-input placeholder="请输入账号" v-model="user_data.username"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input
              @keydown.native.enter="login"
              placeholder="请输入密码"
              type="password"
              v-model="user_data.password"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div style="width:60%; margin: 0 auto">
              <el-button style="width:100%;" type="primary" @click="login">登录</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
export default {
    name:'login',
    data() {
        return {
          user_data: {
            username: "",
            password: ""
          },
        console:console
        };
    },
    methods: {
        login() {
        // this.console.log("执行登录操作");
        if (!this.user_data.username.trim() || !this.user_data.password.trim())
            return this.$message({
            message: "请输入用户名或密码!",
            type: "warning"
            });

        return (
          this.$axios.post("http://localhost:8081/user/login",this.user_data)
                  .then(result => {
                      localStorage.setItem("token", result.data.data.token);
                      // 将Token存储到localStorage
                      this.$message({
                        message:result.data.msg
                      });
                      // 登录成功跳转到首页
                      this.$router.push("/");
                  })
                  .catch(error => {
                    if(error.response){
                      this.$message({
                        message:error.response.data.msg,
                        type:"warning"
                      });
                    }
                  })
        );
        },
        handleJumpIndex(){
            this.$router.push('/')
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
  width: 90px;
  height: 50px;
}
.header-l h1 {
  color: #fff;
  font-weight: 400;
  font-size: 30px;
  margin-top: 5px;
}
.header-r {
  width: 164px;
  height: 50px;
  margin-top: 5px;
}
</style>