<template>
    <div>
        <el-card>
            <el-form>
                <el-form-item label="姓名:">
                    <el-input v-model="data.name"></el-input>
                </el-form-item>
                <el-form-item label="学校:">
                    <el-input v-model="data.school"></el-input>
                </el-form-item>
                <el-form-item label="专业:">
                    <el-input v-model="data.major"></el-input>
                </el-form-item>
                <el-form-item label="学号:">
                    <el-input v-model="data.number"></el-input>
                </el-form-item>
                <el-form-item label="简介:">
                    <el-input type="textarea" v-model="data.userStatement"></el-input>
                </el-form-item>
            </el-form>
            <el-button type="primary" @click="postUserDetail">确认修改</el-button>
        </el-card>
    </div>
</template>

<script>
    export default {
        data(){
            return{
                data:{
                    name:'',
                    school:'',
                    major:'',
                    number:'',
                    userStatement:'',
                },
                id:localStorage.getItem("id")
            }
        },
        mounted (){
            this.getUserDetail()
        },
        methods:{
            getUserDetail(){
                this.$axios.get(
                    "http://localhost:8081/user/info"
                ).then(res =>{
                    this.data=res.data.data;
                }).catch(error => {
                        if(error.response){
                            this.$message({
                                message:error.response.data.msg,
                                type:"warning"
                            });
                        }
                    });
            },
            postUserDetail(){
                this.$axios.post(
                    "http://localhost:8081/user",
                    {
                        "name":this.data.name,
                        "school":this.data.school,
                        "major":this.data.major,
                        "number":this.data.number,
                        "userStatement":this.data.userStatement,
                        "id":localStorage.getItem("id")
                    }
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