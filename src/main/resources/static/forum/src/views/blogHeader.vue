<template>
    <header style="width: 100%">
        <div class="header">
            <div class="header-middle">
                <div class="header-l" >
                    <h1 @click="index">北航在线编程社区</h1>
                </div>
                <div class="header-r">
                    <template v-if="status===null">
                        <el-button @click="handleLogin">登录</el-button>
                        <el-button @click="postForum">发帖子</el-button>
                    </template> 
                    <template v-else-if="status==='administrator'">
                        <el-button @click="LoginOut">登出</el-button>
                        <el-button @click="administartion">管理页面</el-button>
                        <el-button @click="information">个人信息</el-button>
                    </template> 
                    <template v-else>
                        <el-button @click="LoginOut">登出</el-button>
                        <el-button @click="postForum">发帖子</el-button>
                        <el-button @click="information">个人信息</el-button>
                    </template>
                </div>
            </div>
        </div>
    </header>
</template>

<script>
export default {
    name:'BlogHeader',
    mounted(){
        this.identifyAuth()
    },
    computed:{
        status() {
            return this.$store.state.status
        }
    },
    methods:{
        identifyAuth: function(){
            this.$axios.get(
                "http://localhost:8081/islogin"
            ).then(res => {
                if(res.data.data){
                    //this.$store.commit('id', res.data.id)
                    this.$store.commit("id",res.data.id);
                    this.$store.commit("status", "administrator");
                } else {
                this.$message({
                    message:res.data.msg,
                    type:"warning"
                });
                }
            }).catch(error => {
                if(error.response){
                this.$message({
                    message:error.response.data.msg,
                    type:"warning"
                });
                }
            });
        },
        postForum(){
            this.$router.push('/postForum')
        },
        handleTitle(id){
            this.$router.push({ path: '/detail', query: { id: id }});
        },
        handleLogin(){
            this.$router.push('/login')
        },
        information(){
            this.$router.push('/information')
        },
        LoginOut(){
            this.$message({
                message:"success!"
            });
            this.$store.commit('status', null);
            this.$store.commit('id', 0)
            localStorage.clear()
        },
        administartion(){
            this.$router.push('/administrator')
        },
        index(){
            this.$router.push('/')
        }
    }
}
</script>

<style scoped>
    .header{
        width: 100%;
        height: 50px;
        background-color: #409eff;
    }
    .header-middle{
        margin: 0 auto;
        width: 1170px;
        display: flex;
        justify-content: space-between;
    }
    .header-l{
        width: 250px;
        height: 50px;
    }
    .header-l h1{
        color: #fff;
        font-weight: 400;
        font-size: 30px;
        margin-top: 5px;
    }
    .header-r{
        width: 400px;
        height: 50px;
        margin-top: 5px;
    }
</style>

