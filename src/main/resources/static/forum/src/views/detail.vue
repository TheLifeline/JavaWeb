<template>
    <div class="content">
        <el-card style="width:80%; margin:40px auto 0">
            <div class="content-header">
                <h1>{{data.topic}}</h1>
            </div>
            <div style="margin-left:20px">
                <h4>{{data.createUser}}</h4>
                <div>{{data.topicTime | timeHandler}}</div>
            </div>
            <!--<div style="padding:20px;">{{data.topicContents}}</div>-->
            <mavon-editor v-model="data.topicContents" :subfield="false" :defaultOpen="'preview'" :toolbarsFlag	="false"/>
            <div class="pinglun">
                评论
            </div>
            <div>
                <div style="width:97%; background-color: #fafbfc; padding:20px; margin:0 auto">
                    <el-input placeholder="输入评论" v-model="data.input"></el-input>
                    <div style="margin:20px 0"><el-button type="primary" @click="postComment">评论</el-button></div>
                    <div v-for="comment in data.comments_list" :key="comment.id">
                        <el-row :align="'middle'">
                            <el-col :span="6">
                                <div class="comment-left">
                                    <el-row>
                                        <el-col :span="24"><div class="comment-title">用户名：{{comment.userName}}</div></el-col>
                                    </el-row>
                                    <el-row>
                                        <el-col :span="24"><div class="comment-like">点赞数：{{comment.likeNum}}</div></el-col>
                                    </el-row>
                                </div>
                            </el-col>
                            <el-col :span="18">
                                <el-row>
                                    <el-col :span="24"><div class="comment-content">{{comment.commentContent}}</div></el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="24"><div class="comment-time">发表于{{comment.commentTime | timeHandler}}</div></el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                    </div>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script>
    export default {
        name:'detail',
        data(){
            return {
                value: '',
                state:"preview",
                data:{
                    id:this.$route.query.id,
                    topic:'题目',
                    topicTime:'2019.1.14',
                    createUser:'小明',
                    topicContents:".....",
                    comments_list:[
                        {
                            id:0,
                            userID:1,
                            userName:'wkz',
                            commentContents:"",
                            commentTime:""
                        }
                    ],
                    input:"",
                    user_data:localStorage.getItem("id")
                },
                console:console
            }
        },
        mounted (){
            this.getDetail()
        },
        methods:{
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
                    this.data.comments_list.unshift(res.data.data)
                }).catch(error => {
                        if(error.response){
                            this.$message({
                                message:error.response.data.msg,
                                type:"warning"
                            });
                        }
                    });
            }
        },
        filters: {
            timeHandler(t) {
                var d = new Date(t);
                return d.getFullYear()+"年"+(d.getMonth()+1)+"月"+d.getDate()+"日"+d.getHours()+"点"+d.getMinutes()+"分";
            }
        }
    }
</script>

<style scoped>
    .comment-left {
        margin: auto auto;
        height: 120px
    }
    .comment-title {
        font-family:微软雅黑;
        font-size: 18px;
        text-align: center;
        line-height: 93px;
        height: 60px;
    }
    .comment-like {
        font-family:微软雅黑;
        font-size: 12px;
        text-align: center;
        height: 60px;
    }
    .comment-content {
        font-family:微软雅黑; 
        font-size: 16px;
        min-height: 100px;
        text-indent:32px;
    }
    .comment-time {
        text-align: right;
        margin-right: 5%;
        font-size: 12px;
        min-height: 20px;
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
