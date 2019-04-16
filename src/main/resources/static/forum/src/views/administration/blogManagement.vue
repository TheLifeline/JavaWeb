<template>
    <el-card style="width:95%; margin:40px auto 0">
        <el-table width="80%" :data="data" border>
            <el-table-column type='index' width="50" align="center"></el-table-column>
            <el-table-column label="标题" align="center">
                <template slot-scope="scope">
                    <el-button type="text" @click="manageBlogContent(scope.row.id)">{{scope.row.topic}}</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="topicTime" label="发表时间" align="center"></el-table-column>
            <el-table-column prop="createUser" label="发表人" align="center"></el-table-column>
            <el-table-column prop="state" label="状态" align="center">
                <div slot-scope="scope" >
                    {{ scope.row.state | stateFilter }}
                </div>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <div slot-scope="scope" >
                    <template v-if="!scope.row.state">
                        <el-button @click="blogPass(scope.row.id)" type="info" plain>通过</el-button>
                        <el-button @click="blogrefuse(scope.row.id)" type="info" plain>拒绝</el-button>
                    </template>
                    <template v-else>
                        <el-button @click="blog" type="info" plain>查看</el-button>
                    </template>
                </div>
                
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
                    topic:'题目',
                    topicTime:'2019.1.14',
                    createUser:'小明',
                    state:1
                }]
            }
        },
        mounted(){
            var id = this.$route.query.id
            if(id==null){
                id = 0
            }
            this.getBlog(id)
        },
        methods:{
            getBlog(id) {  //
                this.$axios.get(
                    "http://localhost:8081/admin/topic",
                     {params: {id: id}}
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
            },
            manageBlogContent(id){
                this.$router.push({ path: '/administrator/blogContentManagement', query: { id: id }});
            },
            blogPass(id){
                this.$axios.get(
                    "http://localhost:8081/admin/blogAuditing",
                    {params: {id: id, state: 1}}
                ).then(res =>{
                    // list delete ----------todo

                    this.$message({
                            message:res.data.msg
                        });
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
            blogrefuse(id){
                this.$axios.get(
                    "http://localhost:8081/admin/blogAuditing",
                    {params: {id: id, state: 2}}
                ).then(res =>{
                    // list delete ----------todo

                    this.$message({
                            message:res.data.msg
                        });
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
        },
        filters:{
            stateFilter(state) {// ------------todo
                if(state){
                    return "已审核";
                }else{
                    return "未审核";
                }
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>