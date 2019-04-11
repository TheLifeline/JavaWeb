<template>
    <div style="background: #f1f1f1; height:100vh">
        <header style="width: 100%">
            <div class="header">
                <div class="header-middle">
                    <div class="header-l" >
                        <h1>北航在线编程社区</h1>
                    </div>
                    <div class="header-r" v-if="!isLogin">
                        <el-button @click="handleLogin">登录</el-button>
                        <el-button @click="postForum">发帖子</el-button>
                    </div>
                    <div class="header-r" v-else>
                        <el-button @click="LoginOut">登出</el-button>
                        <el-button @click="postForum">发帖子</el-button>
                    </div>
                </div>
            </div>
        </header>
        <div class="content">
            <el-card style="width:80%; margin:40px auto 0">
                <div class="content-header">
                    <h1>{{data.topic}}</h1>
                </div>
                <div style="margin-left:20px">
                    <h4>{{data.createUser}}</h4>
                    <div>{{data.topicTime}}</div>
                </div>
                <!--<div style="padding:20px;">{{data.topicContents}}</div>-->
                <mavon-editor v-model="data.topicContents" :subfield="false" :defaultOpen="state" :toolbarsFlag	="false"/>
                <div class="pinglun">
                    评论
                </div>
                <div>
                    <div style="width:97%; background-color: #fafbfc; padding:20px; margin:0 auto">
                        <el-input placeholder="输入评论" v-model="data.input"></el-input>
                        <div style="margin:20px 0"><el-button type="primary" @click="postComment">评论</el-button></div>

                        <div v-for="comment in data.comments_list" :key="comment">
                            <div style="color: #8a9aa9" >{{comment.commentTime}}</div>
                            <div style="margin-left:20px" >{{comment.commentContents}}</div>
                        </div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name:'detail',
        data(){
            return {
                value: '',
                state:"preview",
                isLogin:false,
                data:{
                    id:this.$route.query.id,
                    topic:'题目',
                    topicTime:'2019.1.14',
                    createUser:'小明',
                    topicContents:".....",
                    comments_list:[
                        {
                            commentContents:null,
                            commentTime:null
                        }
                    ],
                    input:"",
                    user_data:localStorage.getItem("id")
                },
                console:console
            }
        },
        mounted (){
            this.isLogined(),
            this.getDetail()
        },
        methods:{
            handleLogin(){
                this.$router.push('/login')
            },
            getDetail(){
                this.$axios.get(
                    "http://localhost:8081/detail",
                    {params: {id: this.data.id}}
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
            },
            postForum(){
                this.$router.push('/postForum')
            },
            postComment(){
                this.$axios.post(
                    "http://localhost:8081/comment",{
                        "id":""+this.$route.query.id,
                        "user_data":localStorage.getItem("id"),
                        "input":this.data.input
                    }
                ).then(res =>{
                    this.$message({
                        message:res.data.msg
                    });
                    window.reload()
                }).catch(error => {
                        if(error.response){
                            this.$message({
                                message:error.response.data.msg,
                                type:"warning"
                            });
                        }
                    });
            },
            isLogined(){
                this.$axios.get(
                    "http://localhost:8081/islogin",{
                        headers: {
                            'Authorization': localStorage.getItem('token')
                        }
                    }
                ).then(res =>{
                    if(res.data.data){
                        this.isLogin=res.data.data
                    }else {
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
            LoginOut(){
                this.$message({
                    message:"success!"
                });
                this.isLogin=false
                localStorage.clear()
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
        font-weight:400;
        font-size: 30px;
        margin-top: 5px;
    }
    .header-r{
        width: 164px;
        height: 50px;
        margin-top: 5px;
    }

    .content{
        background-color: #f1f1f1;
        padding: 20px 0;
    }
    .content-header{
        text-align: center;
    }
    .pinglun{
        margin:20px 0;
        color: #8a9aa9;
        font-size: 16px;
        font-weight: 400;
    }
</style>