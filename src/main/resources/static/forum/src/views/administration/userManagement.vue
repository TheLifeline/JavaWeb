<template>
    <el-card style="width:95%; margin:40px auto 0">
        <el-table width="80%" :data="data" border>
            <el-table-column type='index' width="50" align="center"></el-table-column>
            <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
            <el-table-column prop="name" label="姓名" align="center"></el-table-column>
            <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
            <el-table-column prop="school" label="学校" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button @click="checkBlogs(scope.row.id)" type="info" plain>查看博客</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<script>
    export default {
        data(){
            return{
                data:[{
                    id:1,
                    nickname:"xiaoming",
                    name:'小明',
                    email:'123@qq.com',
                    school:'buaa'
                }]
            }
        },
        mounted (){
            this.getAllUser();
        },
        methods:{
            checkBlogs(id) {
                this.$router.push({ path: '/administrator/blogManagement', query: { id: id.toString() }});
            },
            getAllUser() {
                this.$axios.get('http://localhost:8081/admin/getAllUser').then(res => {
                    this.data = res.data.data
                }).catch(error => {
                    if(error.response){
                        this.$message({
                            message:error.response.data.msg,
                            type:"warning"
                        });
                    }
                })
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>