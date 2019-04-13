<template>
    <div>
        <el-card>
            <el-form>
                <el-form-item label="姓名:">
                    <el-input v-model="userInfo.username"></el-input>
                </el-form-item>
                <el-form-item label="学校:">
                    <el-input type="password" v-model="userInfo.schoolName"></el-input>
                </el-form-item>
                <el-form-item label="专业:">
                    <el-input type="password" v-model="userInfo.majorName"></el-input>
                </el-form-item>
                <el-form-item label="学号:">
                    <el-input v-model="userInfo.userNum"></el-input>
                </el-form-item>
                <el-form-item label="简介:">
                    <el-input type="textarea" v-model="userInfo.introduction"></el-input>
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
                userInfo:{
                    username:'小明',
                    schoolName:'家里蹲',
                    majorName:'无业游民',
                    userNum:'123456789',
                    introduction:'这是简述!!!!!!!!',
                },
            }
        },
    }
</script>

<style lang="scss" scoped>

</style>