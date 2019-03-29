<template>
    <div style="background: #f1f1f1; height:100vh">
        <header style="width: 100%">
            <div class="header">
                <div class="header-middle">
                    <div class="header-l">
                        <h1>云论坛</h1>
                    </div>
                    <div class="header-r">
                        <!-- <el-button>登录</el-button> -->
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
                <div style="padding:20px;">{{data.topicContents}}</div>

                <div class="pinglun">
                    评论
                </div>
                <div>
                    <div style="width:90%; background-color: #fafbfc; padding:20px; margin:0 auto">
                        <el-input placeholder="输入评论"></el-input>
                        <div style="margin:20px 0"><el-button type="primary">评论</el-button></div>

                        <div>
                            <div style="color: #8a9aa9" >{{data.name}}</div>
                            <div style="margin-left:20px" >{{data.comments}}</div>
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
                id:this.$route.query.id,
                data:{
                    topic:'题目',
                    topicTime:'2019.1.14',
                    createUser:'小明',
                    topicContents:".....",
                    comments:"我说了好多内容",
                    name:"谁谁谁"
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
                    "http://localhost:8081/detail",{
                        params: {
                        id: this.id
                        }
                    }
                ).then(res =>{
                    this.data=res.data
                }).catch(error =>{
                    this.console.log(error)
                });
            },
            postForum(){
                this.$router.push('/postForum')
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
        text-align: center;
    }
</style>