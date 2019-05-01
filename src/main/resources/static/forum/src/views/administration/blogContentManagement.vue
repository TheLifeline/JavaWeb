
<template>
    <div class="content">
        <el-card style="width:80%; margin:40px auto 0">
            <div class="content-header">
                <h1>{{data.topic}}</h1>
            </div>
            
            <div>
                <el-row type="flex" class="row-bg">
                    <el-col :offset="16" :span="8"><div class="grid-content bg-purple">作者：{{data.createUser}}</div></el-col>
                </el-row>
                <el-row type="flex" class="row-bg">
                    <el-col :offset="16" :span="8"><div class="grid-content bg-purple">时间：{{data.topicTime | timeHandler}}</div></el-col>
                </el-row>
            </div>
            <!--<div style="padding:20px;">{{data.topicContents}}</div>-->
            <mavon-editor v-model="data.topicContents" :subfield="false" :defaultOpen="'preview'" :toolbarsFlag	="false" :editable="false"/>
            <div class="pinglun">
                评论
            </div>
            <div>
                <div style="width:97%; background-color: #fafbfc; padding:20px; margin:0 auto">
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
        data(){
            return{
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
                            commentTime:"",
                            likeNum:0,
                        }
                    ],
                    input:""
                }
            }
        },
        mounted(){
            this.getDetail()
        },
        methods:{
            getDetail(){
                this.$axios.get(
                    "http://localhost:8081/admin/detail",
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
            deleteComment(id){
                // delete comment
                this.$axios.get(
                    "http://localhost:8081/admin/deleteComment",
                    {params: {id: id}}
                ).then(res =>{
                    // ----------------todo
                    this.$message({
                            message:res.data.msg
                        });
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