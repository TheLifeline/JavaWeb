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
                    <template v-if="scope.row.state==='0'">
                        <el-button @click="blogPass(scope.row.id)" type="info" plain>通过</el-button>
                        <el-button @click="blogrefuse(scope.row.id)" type="info" plain>拒绝</el-button>
                    </template>
                    <template v-else>
                        <el-button @click="manageBlogContent(scope.row.id)" type="info" plain>查看</el-button>
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
            getBlog(id) {  
                this.$axios.post(
                    "http://localhost:8081/admin/topic",
                    {id: id}
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
                this.$axios.post(
                    "http://localhost:8081/admin/blogAuditing",
                    {id: id, state: "1"}
                ).then(res =>{
                    for(var i=0;i<this.data.length;i++){
                        if(id == this.data[i].id){
                            this.data[i].state = "1";
                            break;
                        }
                    }
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
                this.$axios.post(
                    "http://localhost:8081/admin/blogAuditing",
                    {id: id, state: "2"}
                ).then(res =>{
                    for(var i=0;i<this.data.length;i++){
                        if(id == this.data[i].id){
                            this.data[i].state = "2";
                        }
                    }

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
            stateFilter(state) {
                if(state=="0"){
                    return "未审核";
                }else if(state=="1"){
                    return "已通过";
                }else{
                    return "未通过";
                }
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>