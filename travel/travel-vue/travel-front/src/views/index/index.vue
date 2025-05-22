<template>
  <div class="carousel">
    <div style="height: 480px; display: flex; width: 96%; margin-left: 2%">
      <el-carousel height="480px" style="width: 100%; border-radius: 15px">
        <el-carousel-item v-for="item in carouselItems" :key="item.id">
          <router-link :to="{ path: `/attraction/detail/${item.id}` }">
            <img class="carousel-image" :src="item.imgUrl" />
            <div class="art-title">
              <span class="title-text">{{ item.name }}</span>
            </div>
          </router-link>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="home">
      <div class="container">
        <!-- 头部 -->
        <div class="header">
          <h2>热门推荐</h2>
          <el-button class="change-button" type="warning" round
            >换一批</el-button
          >
        </div>

        <!-- 主内容 -->
        <div class="attraction-list">
          <div
            class="attraction-item"
            v-for="attraction in attractions"
            :key="attraction.id"
          >
            <router-link :to="{ path: `/attraction/detail/${attraction.id}` }">
              <img
                :src="attraction.imgUrl"
                alt="attraction"
                class="attraction-image"
              />
              <div class="attraction-info">
                <h3 class="attraction-name">{{ attraction.name }}</h3>
                <p class="attraction-location">{{ attraction.province }}</p>
                <p class="attraction-type">{{ attraction.theme }}</p>
              </div>
            </router-link>
          </div>
        </div>

        <!-- 旅行日记 -->
        <div class="header" style="margin-top: 40px">
          <h2>旅行日记</h2>
        </div>
        <!-- 主内容 -->
        <div class="diary-list-container">
          <el-card
            class="diary-card"
            v-for="diary in recordList"
            :key="diary.id"
          >
            <div class="diary-content">
              <img
                :src="diary.imgUrl"
                alt="Diary Image"
                class="diary-image"
              />
              <div class="diary-header">
                <h3>{{ diary.name }}</h3>
                <p class="diary-date">{{ diary.createTime }}</p>
              </div>
              <div class="diary-description">
                <p>{{ diary.desc }}</p>
              </div>
              <div class="diary-like">
                <i class="el-icon-thumb" style="color: red" size="small"></i>
                <span style="margin-left: 10px">{{ 10 }}</span>
              </div>
            </div>
          </el-card>
        </div>
      </div>

      <!-- 畅销榜 el-icon--->
      <div class="recommendations">
        <div class="header">
          <h2>畅销榜</h2>
        </div>
        <div
          class="recommend-item"
          v-for="(item, index) in recommends"
          :key="index"
          :style="{ backgroundImage: 'url(' + item.image + ')' }"
        >
          <h3>{{ item.title }}</h3>
          <p class="location">{{ item.location }}</p>
          <p class="description">{{ item.description }}</p>
        </div>
        <div class="strategy">
          <div class="header">
            <h2>旅游攻略推荐</h2>
          </div>
          <div v-for="news in newsList" :key="news.id" class="news-item">
            <i
              class="el-icon-bicycle"
              style="
                font-size: 28px;
                margin-top: 15px;
                margin-left: 33px;
                color: #ffdd47;
              "
            ></i>
            <h3 style="margin-left: 30px">{{ news.title }}</h3>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getRecordByPage } from "@/api/request";
import {getAttractionByPage} from  "@/api/attraction"

export default {
  data() {
    return {
      themes: [
        { id: 1, name: "自然风光" },
        { id: 2, name: "历史文化" },
        { id: 3, name: "美食之旅" },
        { id: 4, name: "购物天堂" },
      ],
      carouselItems: [
        { id: 1, title: "故宫", image: require("../../imgs/01.jpg") },
        { id: 2, title: "长城", image: require("../../imgs/02.jpg") },
        { id: 3, title: "峨眉山", image: require("../../imgs/03.jpg") },
        { id: 4, title: "老君山", image: require("../../imgs/04.jpg") },
      ],
      loading: true,
      courses: [], 
      newsList: [
        {
          id: 1,
          title: "巴黎举办国际旅游展",
        },
        {
          id: 2,
          title: "东京推出新旅游线路",
        },
        {
          id: 3,
          title: "纽约举办国际美食节",
        },
        {
          id: 4,
          title: "悉尼举办国际音乐节",
        },
        {
          id: 5,
          title: "伦敦举办国际艺术展",
        },
        {
          id: 1,
          title: "巴黎举办国际旅游展",
        },
        {
          id: 2,
          title: "东京推出新旅游线路",
        },
        {
          id: 3,
          title: "纽约举办国际美食节",
        },
        {
          id: 4,
          title: "悉尼举办国际音乐节",
        },
        {
          id: 5,
          title: "伦敦举办国际艺术展",
        },
      ],

      recommends: [
        {
          title: "希腊 | 科孚岛寻找德雷尔一家的夏日",
          location: "马来西亚",
          description: "丝汤，味道很棒，然后直奔景点哦",
          image: require("../../imgs/01.jpg"),
        },
        {
          title: "日本 | 神奈川",
          location: "日本",
          description: "神奈川，日本的一个县，位于东京湾畔",
          image: require("../../imgs/04.jpg"),
        },
        {
          title: "意大利 | 意大利小镇",
          location: "意大利",
          description: "意大利小镇，风景如画，让人流连忘返",
          image: require("../../imgs/01.jpg"),
        },
        {
          title: "法国 | 巴黎",
          location: "法国",
          description: "巴黎，浪漫之都，让人陶醉",
          image: require("../../imgs/02.jpg"),
        },
        {
          title: "美国 | 纽约",
          location: "美国",
          description: "纽约，繁华都市，让人流连忘返",
          image: require("../../imgs/03.jpg"),
        },
        {
          title: "澳大利亚 | 大堡礁",
          location: "澳大利亚",
          description: "大堡礁，美丽如画，让人流连忘返",
          image: require("../../imgs/01.jpg"),
        },
        {
          title: "加拿大 | 温哥华",
          location: "加拿大",
          description: "温哥华，美丽如画，让人流连忘返",
          image: require("../../imgs/02.jpg"),
        },
        {
          title: "新西兰 | 库克山",
          location: "新西兰",
          description: "库克山，美丽如画，让人流连忘返",
          image: require("../../imgs/04.jpg"),
        },
      ],

      //生成假数据
      attractions: [
        {
          id: 1,
          name: "故宫",
          location: "中国·北京",
          type: "文化古迹",
        },
        {
          id: 2,
          name: "长城",
          location: "中国·北京",
          type: "自然风光",
        },
        {
          id: 3,
          name: "颐和园",
          location: "中国·北京",
          type: "文化古迹",
        },
        {
          id: 4,
          name: "天坛",
          location: "中国·北京",
          type: "文化古迹",
        },
        {
          id: 5,
          name: "圆明园",
          location: "中国·北京",
          type: "文化古迹",
        },
        {
          id: 6,
          name: "八达岭长城",
          location: "中国·北京",
          type: "自然风光",
        },
        {
          id: 7,
          name: "天安门广场",
          location: "中国·北京",
          type: "文化古迹",
        },
        {
          id: 8,
          name: "鸟巢",
          location: "中国·北京",
          type: "文化古迹",
        },
        {
          id: 9,
          name: "水立方",
          location: "中国·北京",
          type: "文化古迹",
        },
      ],

      recordList: [
        {
          id: 1,
          title: "巴黎之旅",
          date: "2024-05-10",
          description:
            "在巴黎的每一天都充满了浪漫与惊喜。参观了埃菲尔铁塔、卢浮宫，还品尝了美味的法式甜点。",
          like: 10,
        },
        {
          id: 2,
          title: "日本之行",
          date: "2024-07-20",
          description:
            "日本的夏天非常炎热，但富士山的风景令人陶醉。还去了京都的清水寺，感受了浓厚的传统文化。",
          like: 10,
        },
        {
          id: 3,
          title: "巴厘岛度假",
          date: "2024-09-15",
          description:
            "巴厘岛的海滩非常美丽，海水清澈见底。每天都在海边享受阳光，还尝试了水上摩托。",
          like: 10,
        },
        {
          id: 4,
          title: "瑞士之旅",
          date: "2024-11-30",
          description:
            "瑞士的风景非常美丽，尤其是阿尔卑斯山的雪山。还去了日内瓦湖畔，欣赏了美丽的湖光山色。",
          like: 10,
        },
        {
          id: 5,
          title: "埃及之旅",
          date: "2024-12-25",
          description:
            "埃及的古迹非常壮观，金字塔、狮身人面像等都是世界文化遗产。还去了尼罗河畔，欣赏了美丽的河景。",
          like: 10,
        },
        // 更多日记...
      ],
    };
  },
  mounted() {
    getAttractionByPage(1, 9).then((res) => {
      this.attractions = res.data.records;
    });
    getAttractionByPage(2, 4).then((res) => {
      this.carouselItems = res.data.records;
    });
    getRecordByPage(0,5).then((res) => {
      this.recordList = res.data.records;
    })
  },
  methods: {
    // 获取用户当前位置
    // getLocation() {
    //   if (navigator.geolocation) {
    //     navigator.geolocation.getCurrentPosition(
    //       (position) => {
    //         const { latitude, longitude } = position.coords;
    //         console.log("纬度:", latitude);
    //         console.log("经度:", longitude);
    //         // 将用户位置设置为地图中心
    //         this.map.setCenter([longitude, latitude]);
    //       },
    //       (error) => {
    //         console.error("获取位置失败:", error.message);
    //       }
    //     );
    //   } else {
    //     alert("浏览器不支持地理定位");
    //   }
    // },
  },
};
</script>

<style scoped>
.carousel {
  display: block;
  margin-top: 20px;
  width: 100%;
  justify-content: center; /* 水平居中 */
}
.carousel-image {
  width: 100%; /* 图片宽度自适应 */
  height: 100%; /* 图片高度自适应 */
  object-fit: cover; /* 确保图片填充整个容器 */
}
/* 艺术字标题样式 */
.art-title {
  position: absolute;
  top: 80%;
  left: 75%;
  transform: translate(-50%, -50%);
  text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.5);
}

.title-text {
  font-family: "Lobster", cursive;
  font-size: 6.5rem;
  color: #fff;
  background: linear-gradient(45deg, #ffe355, #ff1900);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  animation: titleGlow 2s ease-in-out infinite alternate;
}

/* 动画效果 */
@keyframes titleGlow {
  from {
    text-shadow: 0 0 10px rgba(254, 218, 74, 0.512),
      0 0 20px rgba(0, 208, 255, 0.5);
  }
}

.location {
  color: rgb(255, 67, 67);
  font-size: 20px;
  font-weight: bold;
}

.el-carousel__item h3 {
  color: #38c76c;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item {
  text-align: center;
  line-height: 200px;
}
.el-carousel__item h3 {
  color: #38c76c;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.strategy {
  position: relative;
  float: right;
  height: 20px;
  margin-top: 50px;
  margin-left: 10px;
}

h2 {
  font-size: 24px;
  margin-left: 20px;
  margin-bottom: 20px;
  color: #000000;
}

.news-item {
  display: flex;
  font-size: 18px;
  width: 350px;
  color: #6cfab5;
  transition: transform 0.3s ease;
  margin-top: 10px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.news-item:hover {
  transform: translateY(-5px);
}

.home {
  position: relative;
  margin-left: 6%;
  margin-top: 20px;
  width: 88%;
  height: 2720px;
}

.container {
  width: 68%;
  margin-left: 30px;
  float: left;
}

.theme {
  border-left: 4px solid #5cfb89;
  margin-left: 10px;
  margin-top: -10px;
  width: 100%;
  height: 25px;
  background-color: #f5f7fa;
  gap: 10px;
  display: flex;
  align-items: center;
  padding-left: 20px;
}

.theme-item {
  height: 30px;
  background-color: #fff;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.header {
  border-left: 6px solid #ff4757;
  margin-left: 0;
  margin-bottom: 30px;
}
.change-button {
  position: relative;
  display: block;
  margin-left: 800px;
  margin-top: -52px;
}

/* 日期样式 */
.date {
  color: #ff4757;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.main-content {
  margin-top: 8px;
  display: grid;
}

.recommendations {
  float: right;
}

.recommend-item {
  padding: 15px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  margin-bottom: 20px;
  /* 背景设置 */
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  margin-top: 10px;
}

.description {
  color: #edfe00;
  line-height: 1.6;
}

/* 景点列表容器 */
.attraction-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  padding: 20px 1%;
  max-width: 1440px;
  margin: 0 auto;
}

/* 单个景点卡片 */
.attraction-item {
  background: #fff;
  height: 280px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  position: relative;
}

.attraction-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.12);
}

/* 图片容器 */
.attraction-image {
  width: 100%;
  height: 210px;
  object-fit: cover;
  border-radius: 12px 12px 0 0;
  transition: transform 0.3s ease;
}

.attraction-item:hover .attraction-image {
  transform: scale(1.03);
}

/* 信息容器 */
.attraction-info {
  padding: 20px;
  position: relative;
}

/* 景点名称 */
.attraction-name {
  font-size: 1.25rem;
  color: #2c3e50;
  margin-top: -15px;
  font-weight: 600;
  line-height: 1.3;
}

/* 位置信息 */
.attraction-location {
  position: absolute;
  font-size: 1rem;
  color: #666;
  align-items: center;
  margin-top: -15px;
  margin-left: 150px;
}

.attraction-location::before {
  content: "📍";
  margin-right: 8px;
}

/* 景点描述 */
.attraction-description {
  font-size: 0.9rem;
  color: #666;
  line-height: 1.6;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
.el-menu-demo {
  background-color: #ebeef5;
  margin-top: 20px;
  width: 100%;
  height: 50px;
  display: flex;
  border-radius: 8px;
  margin-bottom: -10px;
}
.el-menu-demo .el-menu-item {
  padding-top: -8px;
}
.el-menu-demo .el-menu-item.is-active {
  background-color: #4c4c4c !important; /* 激活背景颜色 */
  color: #ffd04b !important; /* 激活文字颜色 */
  height: 50px;
  border-radius: 8px;
}
/* 类型标签 */
.attraction-type {
  position: absolute;
  width: 85px;
  height: 25px;
  top: -28px;
  right: 15px;
  background: #4dff00;
  color: white;
  padding: 6px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .attraction-list {
    grid-template-columns: 1fr;
    gap: 20px;
    padding: 20px;
  }

  .attraction-image {
    height: 180px;
  }

  .attraction-name {
    font-size: 1.1rem;
  }

  .attraction-type {
    font-size: 0.7rem;
    padding: 4px 12px;
  }
}

@media (min-width: 1200px) {
  .attraction-list {
    grid-template-columns: repeat(3, 1fr);
  }
}

/* 颜色变量 */
:root {
  --primary-color: #4dff00;
  --secondary-color: #067ff8;
  --text-dark: #2c3e50;
  --text-light: #666;
}
.diary-list-container {
  display: flex;
  position: relative;
  flex-direction: column;
  width: 100%;
  gap: 20px;
}

.diary-list-container::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  gap: 20px;
}

.diary-card {
  width: 100%;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.diary-header {
  position: absolute;
  width: 800px;
  margin-left: 220px;
}

.diary-date {
  position: absolute;
  margin-top: -40px;
  margin-left: 540px;
  font-size: 0.8rem;
  color: #666;
}

.diary-content {
  display: flex;
  gap: 20px;
}

.diary-image {
  position: absolute;
  width: 180px;
  height: 180px;
  object-fit: cover;
  border-radius: 8px;
}

.diary-description {
  display: flex;
  width: 90%;
  height: 180px;
  margin-left: 220px;
  margin-top: 40px;
}

.diary-like {
  position: absolute;
  margin-top: 170px;
  margin-left: 780px;
}

@media (max-width: 768px) {
  .diary-list-container {
    flex-direction: column;
    align-items: center;
  }

  .diary-card {
    width: 100%;
  }

  .diary-content {
    flex-direction: column;
  }

  .diary-image {
    width: 100%;
  }
}
</style>