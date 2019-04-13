<template>
    <div>
        <el-card>
            <el-form>
                <el-form-item label="姓名:">
                    <span>{{userInfo.username}}</span>
                </el-form-item>
                <el-form-item label="学校:">
                    <span>{{userInfo.schoolName}}</span>
                </el-form-item>
                <el-form-item label="专业:">
                    <span>{{userInfo.majorName}}</span>
                </el-form-item>
                <el-form-item label="学号:">
                    <span>{{userInfo.userNum}}</span>
                </el-form-item>
                <el-form-item label="简介:">
                    <el-input type="textarea" v-model="userInfo.introduction" readonly></el-input>
                </el-form-item>
            </el-form>
            <!-- <el-button @click="handleAdd" type="primary">确认添加</el-button> -->
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