<template>
    <div>
        <el-card>
            <el-form>
                <el-form-item label="旧密码:">
                    <el-input v-model="password.oldPassword"></el-input>
                </el-form-item>
                <el-form-item label="新密码:">
                    <el-input type="password" v-model="password.newPassword"></el-input>
                </el-form-item>
                <el-form-item label="再次输入新密码:">
                    <el-input type="password" v-model="password.againPassword"></el-input>
                </el-form-item>
            </el-form>
            <el-button type="primary" @click="postPassword">确认修改</el-button>
        </el-card>
    </div>
</template>

<script>
    export default {
        data(){
            return{
                password:{
                    oldPassword:'',
                    newPassword:'',
                    againPassword:'',
                    id:localStorage.getItem("id")
                },
            }
        },
        methods:{
            postPassword(){
                this.$axios.post(
                    "http://localhost:8081/user/password",this.password
                ).then(res =>{
                    this.$message({
                        message:res.data.msg
                    });
                }).catch(error => {
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

<style lang="scss" scoped>

</style>