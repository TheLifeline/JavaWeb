<template>
    <div style="background: #f1f1f1; height:100vh">
        <header style="width: 100%">
            <div class="header">
                <div class="header-middle">
                    <div class="header-l">
                        <h1>云论坛</h1>
                    </div>
                    <div class="header-r">
                        <el-button @click="handleLogin">登录</el-button>
                        <el-button @click="postForum">发帖子</el-button>
                    </div>
                </div>
            </div>
        </header>
        <el-card style="width:81%; margin:40px auto 0">
            <el-table width="80%" :data="data" border>
                <el-table-column type='index' width="50" align="center"></el-table-column>
                <el-table-column label="标题" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleTitle(scope.row.id)">{{scope.row.topic}}</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="likeNums" label="点赞数" align="center"></el-table-column>
                <el-table-column prop="topicTime" label="发表时间" align="center"></el-table-column>
                <el-table-column prop="createUser" label="发表人" align="center"></el-table-column>
                <el-table-column prop="topicReplyCount" label="评论数" align="center"></el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
    export default {
        name:'index',
        data(){
            return {
                data:[{
                    id:1,
                    topic:'题目',
                    likeNums:102,
                    topicTime:'2019.1.14',
                    createUser:'小明',
                    topicReplyCount:20
                }],
                console:console
            }
        },
        mounted (){
            this.getAllTopic()
        },
        methods:{
            postForum(){
                this.$router.push('/postForum')
            },
            handleTitle(id){
               this.$router.push({ path: '/detail', query: { id: id }});
            },
            handleLogin(){
                this.$router.push('/login')
            },
            getAllTopic(){
                this.$axios.get(
                    "http://localhost:8081/topic",{
                        headers: {
                        'Authorization': localStorage.getItem('token')
                        }
                    }
                ).then(res =>{
                    this.data=res.data.data
                })
                .catch(error => {
                    if(error.response){
                        this.$message({
                            message:error.response.data.msg,
                            type:"warning"
                        });
                    }
                });
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
    width: 90px;
    height: 50px;
}
.header-l h1{
    color: #fff;
    font-weight: 400;
    font-size: 30px;
    margin-top: 5px;
}
.header-r{
    width: 164px;
    height: 50px;
    margin-top: 5px;
}
</style>