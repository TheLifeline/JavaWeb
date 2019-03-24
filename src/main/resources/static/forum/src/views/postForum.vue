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
                        <el-button>发帖子</el-button>
                    </div>
                </div>
            </div>
        </header>
        <div style="width:1170px;margin: 0 auto;">
            <div class="write-container">
                <div>
                    <div class="title">
                        <span>标题</span>
                    </div>
                    <el-input v-model="title" class="write-input" type="text" placeholder="请输入标题"></el-input>
                </div>
                <div>
                    <div class="title">内容</div>
                    <div class="rich-text mt10">
                        <div id="main">
                            <mavon-editor v-model="value" :ishljs = "true"/>
                        </div>
                    </div>
                    <div style="text-align: left; margin-bottom:10px;">
                        <el-button style="width:98px;height:40px;margin-top: 20px;" type="primary">发布帖子</el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name:'postForum',
        data() {
            return {
                title:'',
                value:''
            }
        },
        methods:{
            handleLogin(){
                this.$router.push('/login')
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

    .write-container{
        width: 1170px;
        margin: 30px auto;
        box-sizing: border-box;
        padding: 8px 24px;
        background: #fff;
        border-radius: 6px;
    }
    .write-input{
        width: 1122px;
        height: 42px;
        margin-top: 10px;
    }
    .title{
        margin-top: 6px;
        color: #333;
        font-size: 20px;
        font-weight: 700;
        line-height: 1.5;
        text-align: left
    }
    .mt10{
        margin-top: 10px;
    }
    .editer{
        height: 275px;
    }
    .rich-text{
        height: 340px;
    }
    .label{
        text-align: left;
    }
    .label-title{
        font-size: 14px;
        font-weight: 700;
        color: #409eff;
    }
</style>