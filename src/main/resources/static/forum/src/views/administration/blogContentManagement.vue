
<template>
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
                    <div v-for="comment in data.comments_list" :key="comment">
                        <div style="color: #8a9aa9" >{{comment.commentTime}}</div>
                        <div style="margin-left:20px" >{{comment.commentContents}}</div>
                        <div>
                            <button @click="deleteComment(comment.id)">删除</button>
                        </div>
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
                            id:1,
                            commentContents:null,
                            commentTime:null
                        }
                    ],
                    input:"",
                    user_data:localStorage.getItem("id")
                }
            }
        },
        mounted(){
            this.getBlog()
        },
        methods:{
            getDetail(){
                this.$axios.post(
                    "http://localhost:8081/detail",
                    {id: this.data.id}
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
        }
    }
</script>

<style scoped>
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