<template>
    <div>
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
            </el-table>
        </el-card>
    </div>
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
                }]
            }
        },
        mounted (){
            this.getUserDetail()
        },
        methods:{
            getUserDetail(){
                this.$axios.get(
                    "http://localhost:8081/user/topic",
                ).then(res =>{
                    this.data=res.data.data
                }).catch(error => {
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