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
            <el-button type="primary">确认修改</el-button>
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
                },
            }
        },
    }
</script>

<style lang="scss" scoped>

</style>