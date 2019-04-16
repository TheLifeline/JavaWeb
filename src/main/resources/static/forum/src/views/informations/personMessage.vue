<template>
    <div>
        <el-card>
            <el-form>
                <el-form-item label="姓名:">
                    <span>{{data.name}}</span>
                </el-form-item>
                <el-form-item label="学校:">
                    <span>{{data.school}}</span>
                </el-form-item>
                <el-form-item label="专业:">
                    <span>{{data.major}}</span>
                </el-form-item>
                <el-form-item label="学号:">
                    <span>{{data.number}}</span>
                </el-form-item>
                <el-form-item label="简介:">
                    <el-input type="textarea" v-model="data.userStatement" readonly></el-input>
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
                data:{
                    name:'小明',
                    school:'家里蹲',
                    major:'无业游民',
                    number:'123456789',
                    userStatement:'这是简述!!!!!!!!',
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
                    "http://localhost:8081/user",
                    {params: {id: this.id}}
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
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>