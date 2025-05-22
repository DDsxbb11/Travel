<template>
  <div class="navbar">
    <div class="logo">
      <img src="@/imgs/logo.jpg" />
    </div>
    <div class="title">
      <span>去旅行</span>
    </div>

    <!-- 菜单在右侧 -->

    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      text-color="black"
      active-text-color="#ffd04b"
      background-color="hsl(0, 10%, 94%)"
    >
      <el-menu-item index="1" class="menu-item"><router-link to="/" >首页</router-link></el-menu-item>
      <el-menu-item index="2" class="menu-item"><router-link to="/attraction" >景点信息</router-link></el-menu-item>
      <el-menu-item index="3" class="menu-item"><router-link to="/hotel" >酒店服务</router-link></el-menu-item>
      <el-menu-item index="4" class="menu-item"><router-link to="/strategy" >旅游攻略</router-link></el-menu-item>
      <el-menu-item index="5" class="menu-item"><router-link to="/record" >旅行日记</router-link></el-menu-item>

    </el-menu>
    <el-button
      class="user-avatar"
      style="width: 100px; height: 40px; margin-top: 40px;margin-left: 20px;"
      v-if="userInfo==null"
      type="warning"
      plain
      ><router-link to="/login">登录/注册</router-link></el-button
    >
    <el-dropdown v-else>
      <div class="avatar-dropdown">
        <img class="user-avatar" src="@/assets/imgs/logoPng.jpg" />

        <el-dropdown-menu slot="dropdown" style="width: 100px">
          <el-button type="text" style="margin-left: 15px; font-size: 16px"
            ><router-link to="/myInfo">个人中心</router-link></el-button
          ><br />
          <el-button type="text" style="margin-left: 15px; font-size: 16px"
            ><router-link to="/collection">我的收藏</router-link></el-button
          ><br />
          <el-button type="text" style="margin-left: 15px; font-size: 16px"
            ><router-link to="/history">浏览历史</router-link></el-button
          ><br />
          <el-button type="text" style="margin-left: 15px; font-size: 16px"
            ><router-link to="/order">我的订单</router-link></el-button
          ><br />
          <el-button
            type="text"
            @click.native="logout"
            style="margin-left: 15px; font-size: 16px"
            >退出登录</el-button
          >
        </el-dropdown-menu>
      </div>
    </el-dropdown>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { getInfo } from "@/api/user";
import { logout } from "@/api/user";
import { removeToken,getToken } from "@/utils/auth";

export default {
  data() {
    return {
      activeIndex: '',
      userInfo: null,
      hasToken: getToken() !=null,
    };
  },
  components: {},
  computed: {
    ...mapGetters(["sidebar", "avatar"]),
  },
  methods: {
    async logout() {
      await logout();
      this.$store.dispatch("user/logout");
      removeToken();
      //刷新界面
      this.$router.go(0);
    },

    fileChange(file, fileList) {
      this.imgUrl = URL.createObjectURL(file.raw);
    },

    async getUserInfo() {
      const {data} = await getInfo();
      this.userInfo = data;
      console.log(this.userInfo);
    }
  },
  mounted() {
    if(this.hasToken){
      if (this.userInfo == null) {
        //延迟1秒
        setTimeout(() => {
          this.getUserInfo();
        }, 1000)
      }else{
        this.getUserInfo();
      }
      
    }
  },
};
</script>

<style lang="scss" scoped>
.navbar {
  display: flex;
  height: 100%;
  width: 100%;
  overflow: hidden;
  position: relative;
  background: hsl(0, 10%, 94%);
}
.logo {
  width: 85px;
  height: 70px;
  margin-left: 4%;
  margin-top: 21px;
  border-radius: 30px;
  img {
    width: 100%;
    height: 100%;
    border-radius: 30px;
  }
}
.title {
  font-size: 48px;
  font-weight: bold;
  margin-left: 20px;
  margin-top: 30px;
  font-family: "Pacifico", cursive; /* 使用艺术字体 */
  background: linear-gradient(to right, #ff7e5f, #feb47b, #ff7e5f);
  -webkit-background-clip: text;
  color: transparent;
}

.nav-link {
  color: #333; /* 默认字体颜色 */
  padding: 8px 12px;
  border-radius: 4px;
  background-color: transparent; /* 默认无背景 */
}

.nav-link:hover {
  background-color: #a4ffb9; /* 鼠标悬停背景色 */
  height: 35px;
  border-radius: 8px;
}

/* 使用深度选择器确保样式穿透 */
::v-deep(.nav-link.router-link-exact-active) {
  color: green; /* 选中时的字体颜色 */
  background-color: #00ff00; /* 选中时的背景颜色 */
  font-weight: bold; /* 可选：选中时加粗 */
}
.el-menu-demo{
  margin-left: 36%;
  margin-top: 28px;
}
.menu-item{
  font-size: 16px;
}
.avatar-dropdown {
  display: flex;
  margin-top: 50%;
  margin-left: 30px;
  height: 50px;

  .user-avatar {
    width: 40px;
    height: 40px;
    cursor: pointer;
    border-radius: 50%;
  }

  .user-name {
    position: relative;
    margin: 0px 6px;
    cursor: pointer;
  }
}
</style>