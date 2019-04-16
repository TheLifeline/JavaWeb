
<template>
    <div class="content">
        <el-card style="width:90%; margin:40px auto 0">
            <div class="content-header">
                <h1>{{blog.topic}}</h1>
            </div>
            <div style="margin-left:20px">
                <h4>{{blog.createUser}}</h4>
                <div>{{blog.topicTime}}</div>
            </div>
            <el-container v-for="comment in comments" v-bind:key="comment.id">
                <el-header>
                    <div>{{comment.user_name}}发表于{{comment.comment_time}}</div>
                </el-header>
                <el-main>
                    <div>
                        {{comment.comment_content}}
                    </div>
                </el-main>
                <el-footer>
                    <el-row>
                        <el-col :span="4" :offset="20" left><el-button type="warning" @click="deleteComment(comment.id)" plain>删除</el-button></el-col>
                    </el-row>
                </el-footer>
            </el-container>
        </el-card>
        
    </div>
</template>

<script>
    export default {
        data(){
            return{
                blog:{

                },
                comments:[{
                    id:1,
                    user_name:'小明',
                    comment_time:'2019.1.14',
                    comment_content:"babbbababa",
                },{
                    id:1,
                    user_name:'小明',
                    comment_time:'2019.1.14',
                    comment_content:"babbbababa",
                }]
            }
        },
        mounted(){
            var id = this.$route.query.id
            if(id==null){
                id = 0
            }
            this.getBlog(id)
            this.getComment(id)
        },
        methods:{
            getBlog(id) {
                this.$axios.get(
                    "http://localhost:8081/detail",
                    {params: {id: id}}
                ).then(res =>{
                    this.blog=res.data.data
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
            getComment(id) {  // 
                this.$axios.get(
                    "http://localhost:8081/comment",
                     {params: {id: id}}
                ).then(res =>{
                    this.items = [{
                            id:1,
                            user_name:'小明',
                            comment_time:'2019.1.14',
                            comment_content:"babbbababa",
                        }]
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
            }
        },
        filters:{
            stateFilter(state) {
                if(state){
                    return "已审核";
                }else{
                    return "未审核";
                }
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