<template>
  <div class="attraction">
    <div class="search">
      <!-- 搜索框 -->
      <div class="search-container">
        <el-button type="success" class="search-button" icon="el-icon-search" @click="search()"
          >搜索</el-button
        >
        <el-input
          placeholder="搜索景点名称或关键词"
          prefix-icon="el-icon-search"
          v-model="queryVo.keyword"
          class="search-input"
        />
      </div>
      <!-- 筛选条件容器 -->
      <div class="filters">
        <!-- 类型选择 -->
        <div class="filter-group">
          <label>主题：</label>
          <el-radio-group v-model="queryVo.theme">
            <el-radio
              v-for="(item, index) in themeList"
              :key="index"
              :label="item.name"
              class="text-radio"
            >
              {{ item.name }}
            </el-radio>
          </el-radio-group>
        </div>

        <!-- 省份选择 -->
        <div class="filter-group">
          <label>省份：</label>
          <el-radio-group v-model="queryVo.province">
            <el-radio
              v-for="province in provinceOptions"
              :key="province.name"
              :label="province.name"
              class="text-radio"
            >
              {{ province.name }}
            </el-radio>
          </el-radio-group>
        </div>

        <!-- 收费选择 -->
        <div class="filter-group" id="target">
          <label>收费类型：</label>
          <el-radio-group v-model="queryVo.isFee">
            <el-radio
              v-for="fee in feeOptions"
              :key="fee.value"
              :label="fee.value"
              class="text-radio"
            >
              {{ fee.label }}
            </el-radio>
          </el-radio-group>
        </div>
      </div>
    </div>
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
            <p class="attraction-location">{{ attraction.address }}</p>
            <p class="attraction-description">
              {{ truncateDescription(attraction.description) }}
            </p>
            <p class="attraction-type">{{ attraction.theme }}</p>
            <p class="attraction-fee" v-if="attraction.isFee === 1">免费</p>
            <p class="attraction-fee" v-else>收费</p>
          </div>
        </router-link>
      </div>
    </div>
    <div class="block">
      <el-pagination
        :current-page="pageNum"
        :page-size=9
        layout="total, prev, pager, next"
        :total="dataTotal"
        @current-change="handleCurrentChange"
        style="text-align: center"
      />
    </div>
  </div>
</template>



<script>
import {  getProvince } from "@/api/request";
import { getAttractionByPage ,getAttractionTheme } from "@/api/attraction";
export default {
  name: "ExamList",
  data() {
    return {
      pageNum: 1, // 当前页码
      pageSize:9,
      dataTotal: 0, // 总记录数
      queryVo: { // 查询条件
        keyword:"",
        theme:"全部",
        province:"全部",
        isFee:""
      }, 
      themeList: [],
      provinceOptions: [],
      feeOptions: [
        { value: "", label: "全部" },
        { value: "1", label: "免费" },
        { value: "2", label: "收费" },
      ],
      attractions: [],
    };
  },
  methods: {
    truncateDescription(desc) {
      const maxLength = 40;
      if (!desc) return "";
      return desc.length > maxLength ? desc.slice(0, maxLength) + "..." : desc;
    },
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getAttractionList();
      const element = document.getElementById("target");
      if (element) {
        element.scrollIntoView({ behavior: "smooth" }); // 平滑滚动
      }
    },
    async getAttractionList() {
      const { data } = await getAttractionByPage(
        this.pageNum,
        this.pageSize,
        this.queryVo
      );
      this.attractions = data.records;
      this.dataTotal = data.total;
    },

    async getProvinceList() {
      const { data } = await getProvince();
      this.provinceOptions = data;
    },
    async search(){
      const { data } = await getAttractionByPage(
        1,
        9,
        this.queryVo
      );
      this.attractions = data.records;
      this.dataTotal = data.total;
    },
    async getThemeList() {
      const { data } =await getAttractionTheme();
      this.themeList = data;
    }
  },
  mounted() {
    this.getProvinceList();
    this.getAttractionList();
    this.getThemeList();
  },
};
</script>

<style scoped>
::v-deep .search-button {
  position: absolute;
  top: 55px;
  margin-left: 1000px;
  height: 40px;
  border-radius: 0 3px 3px 0;
  z-index: 2;
}
.attraction {
  position: relative;
}

.search {
  width: 100%;
  height: 400px;
  margin: 0 auto;
  background-image: url("../../imgs/1691550223443253.png");
  background-size: cover;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.search-input {
  position: absolute;
  top: 20%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600px;
  height: 50px;
  z-index: 1;
}

.filters {
  position: absolute;
  top: 25%;
  left: 40%;
  transform: translateX(-35%);
  display: flex;
  flex-direction: column;
  gap: 9px;
  padding: 20px;
}

.filter-group {
  margin-bottom: 8px;
}

.filter-group label {
  display: block;
  margin-bottom: 10px;
  font-weight: 500;
  color: #606266;
}

.el-radio-group {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.el-radio {
  margin: 0 !important;
}

.el-radio.is-bordered {
  padding: 8px 20px;
}

.el-radio.is-bordered.is-checked {
  border-color: #067ff8;
}

.el-radio-group {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.filter-group label {
  float: left;
  color: #000000;
  font-size: 20px;
  font-family: "Microsoft YaHei";
}

/* 新增单选文字样式 */
::v-deep .el-radio__input {
  display: none !important;
}

::v-deep .el-radio__label {
  padding-left: 0 !important;
  color: #abffc1;
  font-size: 18px;
  transition: all 0.2s;
  position: relative;
  cursor: pointer;
}

::v-deep .el-radio.is-checked .el-radio__label {
  color: #4dff00;
  font-weight: 500;
}
/* 景点列表容器 */
.attraction-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  padding: 40px 5%;
  max-width: 1440px;
  margin: 0 auto;
}

/* 单个景点卡片 */
.attraction-item {
  background: #fff;
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
  height: 220px;
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
  height: 150px;
  position: relative;
}

/* 景点名称 */
.attraction-name {
  font-size: 1.25rem;
  color: #2c3e50;
  margin: 0 0 10px;
  font-weight: 600;
  line-height: 1.3;
}

/* 位置信息 */
.attraction-location {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 12px;
  display: flex;
  align-items: center;
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

/* 类型标签 */
.attraction-type {
  position: absolute;
  top: -28px;
  right: 15px;
  background: #4dff00;
  color: white;
  padding: 6px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 价格标签 */
.attraction-fee {
  position: absolute;
  bottom: -5px;
  right: 15px;
  background: rgba(38, 38, 38, 0.8);
  color: white;
  padding: 8px 15px;
  border-radius: 5px;
  font-size: 0.9rem;
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
</style>