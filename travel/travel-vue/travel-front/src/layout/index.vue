<template>
  <div :class="classObj" class="app-wrapper" style="background-color: #F5F7FA;">
    <navbar class="header" />
    <div class="main" >
      <app-main />
    </div>
    
    <footer class="site-footer">
      <div class="footer-container">
        <!-- 公司信息 -->
        <div class="footer-section">
          <h3 class="footer-logo">去旅行</h3>
          <p class="footer-description">探索世界之美，体验非凡旅程</p>
        </div>

        <!-- 快速链接 -->
        <div class="footer-section">
          <h4 class="footer-title">快速导航</h4>
          <ul class="footer-links">
            <li v-for="(link, index) in quickLinks" :key="index">
              <router-link :to="link.path" class="footer-link">
                {{ link.name }}
              </router-link>
            </li>
          </ul>
        </div>

        <!-- 联系方式 -->
        <div class="footer-section">
          <h4 class="footer-title">联系我们</h4>
          <ul class="contact-info">
            <li>
              <i class="fas fa-map-marker-alt"></i> 甘肃省兰州理工大学东三947寝室
            </li>
            <li><i class="fas fa-phone"></i> 400-800-1234</li>
            <li><i class="fas fa-envelope"></i> contact@globaltravel.com</li>
          </ul>
        </div>

        <!-- 社交媒体 -->
        <div class="footer-section">
          <h4 class="footer-title">关注我们</h4>
          <div class="social-links">
            <a
              v-for="(social, index) in socialMedia"
              :key="index"
              :href="social.url"
              target="_blank"
              class="social-icon"
            >
              <i :class="social.icon"></i>
            </a>
          </div>
        </div>
      </div>

      <!-- 版权信息 -->
      <div class="copyright">
        <p>&copy; 2025 去旅行 版权所有</p>
        <p>京ICP备12345678号</p>
      </div>
    </footer>
  </div>
</template>

<script>
import { Navbar, AppMain } from "./components";
import ResizeMixin from "./mixin/ResizeHandler";

export default {
  name: "Layout",
  data() {
    return {
      quickLinks: [
        { name: "首页", path: "/" },
        { name: "关于我们", path: "/about" },
        { name: "旅游套餐", path: "/packages" },
        { name: "常见问题", path: "/faq" },
        { name: "联系我们", path: "/contact" },
      ],
      
    };
  },
  components: {
    Navbar,
    AppMain,
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar;
    },
    device() {
      return this.$store.state.app.device;
    },
    fixedHeader() {
      return this.$store.state.settings.fixedHeader;
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === "mobile",
      };
    },
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch("app/closeSideBar", { withoutAnimation: false });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";
@import "~@/styles/variables.scss";

.app-wrapper {
  @include clearfix;
  position: relative;
  height: auto;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}
.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  height: 100px; // 建议使用固定高度
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.main {
  margin-top: 100px; // 与导航栏高度一致
  height: auto;
  background-color: #F5F7FA;
}
.site-footer {
  background: #2c3e50;
  color: #ecf0f1;
  padding: 40px 0 20px;
  margin-top: 50px;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
  padding: 0 20px;
}

.footer-section {
  margin-bottom: 20px;
}

.footer-logo {
  font-size: 24px;
  color: #e67e22;
  margin-bottom: 15px;
}

.footer-title {
  font-size: 18px;
  border-bottom: 2px solid #e67e22;
  padding-bottom: 10px;
}

.footer-links li {
  margin-bottom: 8px;
}

.footer-link {
  color: #bdc3c7;
  text-decoration: none;
  transition: color 0.3s;
}

.footer-link:hover {
  color: #e67e22;
}

.contact-info li {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.social-links {
  display: flex;
  gap: 15px;
}

.social-icon {
  font-size: 24px;
  color: #bdc3c7;
  transition: color 0.3s;
}

.social-icon:hover {
  color: #e67e22;
}

.copyright {
  text-align: center;
  padding-top: 30px;
  border-top: 1px solid #34495e;
  font-size: 14px;
}

@media (max-width: 768px) {
  .footer-container {
    grid-template-columns: 1fr;
  }

  .footer-section {
    text-align: center;
  }

  .social-links {
    justify-content: center;
  }
}
</style>
