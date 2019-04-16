<template>
    <el-container width="200px">
      <el-aside>
        <el-menu :router="true">
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>用户管理</span>
                </template>
                <el-menu-item-group>
                    <template slot="title">用户管理</template>
                    <el-menu-item index="/administrator/blogManagement">博客管理</el-menu-item>
                    <el-menu-item index="/administrator/commentManagement">评论管理</el-menu-item>
                    <el-menu-item index="/administrator/userManagement">用户管理</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
      </el-aside>
      <el-mian style="width:100%">
        <router-view></router-view>
      </el-mian>
    </el-container>
</template>

<script>
export default {
  name: "administrator",
  data() {
    return {
      
    };
  },

  methods: {

  }
};
</script>