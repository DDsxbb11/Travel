<template>
  <div class="strategy">
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
        <!-- 省份选择 -->
        <div class="filter-group">
          <label>省份：</label>
          <el-radio-group v-model="queryVo.province">
            <el-radio
              v-for="province in provinceOptions"
              :key="province.id"
              :label="province.name"
              class="text-radio"
            >
              {{ province.name }}
            </el-radio>
          </el-radio-group>
        </div>

        <!-- 收费选择 -->
        <div class="filter-group">
          <label>收费类型：</label>
          <el-radio-group v-model="queryVo.feeType">
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
    <div class="strategy-list">
      <div
        class="strategy-item"
        v-for="item in strategys"
        :key="item.id"
      >
        <router-link :to="{ path: `/strategy/detail/${item.id}` }">
          <img :src="item.imgUrl" alt="strategy" class="strategy-image" />
          <div class="strategy-info">
            <h3 class="strategy-name">{{ item.name }}</h3>
            <p class="strategy-type">{{ item.location }}</p>
            <p class="strategy-joinNum">
              已有&nbsp;{{ item.joinNum==null? 0: item.joinNum}}&nbsp;人参团
            </p>
            <p class="strategy-fee">{{ item.price }}元/人</p>
          </div>
        </router-link>
      </div>
    </div>
    <div class="block">
      <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        :size="size"
        :disabled="disabled"
        :background="background"
        layout="total, prev, pager, next"
        :total="dataTotal"
        @current-change="handleCurrentChange"
        style="text-align: center"
      />
    </div>
  </div>
</template>



<script>
import {getProvince} from "@/api/request"
import { getStrategyByPage } from "@/api/strategy";
export default {
  name: "ExamList",
  data() {
    return {
      pageNum: 1,
      pageSize: 9,
      queryVo: {
        keyword: "",
        province: "全部",
        feeType: "0",
      },
      provinceOptions: [],
      feeOptions: [
        { value: "0", label: "全部" },
        { value: "2", label: "免费" },
        { value: "1", label: "收费" },
      ],
      strategys: [],
    };
  },
  methods: {
    truncateDescription: (desc) => {
      const maxLength = 40;
      if (!desc) return ""; // 空值处理
      return desc.length > maxLength ? desc.slice(0, maxLength) + "..." : desc;
    },

    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getInfo();
      const element = document.getElementById("target");
      if (element) {
        element.scrollIntoView({ behavior: "smooth" }); // 平滑滚动
      }
    },
    async getInfo() {
      console.log(this.queryVo);
      const { data } = await getStrategyByPage(this.pageNum,this.pageSize,this.queryVo);
      this.strategys = data.records;
      this.dataTotal = data.total;
    },

    search() {
      this.pageNum = 1;
      this.getInfo();
    }
  },
  mounted() {
    this.getInfo();
  },
  created() {
    getProvince().then((res) => {
      this.provinceOptions = res.data;
    });
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
.strategy {
  position: relative;
}

.search {
  width: 100%;
  height: 350px;
  margin: 0 auto;
  background-image: url("../../imgs/1691550223443253.png");
  background-size: cover;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.search-input {
  position: absolute;
  margin-top: 80px;
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
  margin-top: 20px;
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
.strategy-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  padding: 40px 5%;
  max-width: 1440px;
  margin: 0 auto;
}

/* 单个景点卡片 */
.strategy-item {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  position: relative;
}

.strategy-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.12);
}

/* 图片容器 */
.strategy-image {
  width: 100%;
  height: 220px;
  object-fit: cover;
  border-radius: 12px 12px 0 0;
  transition: transform 0.3s ease;
}

.strategy-item:hover .strategy-image {
  transform: scale(1.03);
}

/* 信息容器 */
.strategy-info {
  padding: 20px;
  height: 100px;
  position: relative;
}

/* 景点名称 */
.strategy-name {
  font-size: 1.25rem;
  color: #2c3e50;
  margin: 0 0 10px;
  font-weight: 600;
  line-height: 1.3;
  margin-top: -10px;
}

/* 景点描述 */
.strategy-description {
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
.strategy-type {
  position: absolute;
  height: 30px;
  top: -51px;
  right: 0px;
  background: #04ff00;
  color: white;
  padding: 6px 15px;
  font-size: 1.1rem;
  font-weight: 500;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.strategy-joinNum {
  position: absolute;
  top: 48px;
  right: 105px;
  color: rgb(128, 128, 128);
  font-size: 0.9rem;
  font-weight: 500;
}
/* 价格标签 */
.strategy-fee {
  position: absolute;
  bottom: -5px;
  right: 15px;
  color: rgb(255, 30, 30);
  padding: 8px 15px;
  border-radius: 5px;
  font-size: 1.05rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .strategy-list {
    grid-template-columns: 1fr;
    gap: 20px;
    padding: 20px;
  }

  .strategy-image {
    height: 180px;
  }

  .strategy-name {
    font-size: 1.1rem;
  }

  .strategy-type {
    font-size: 0.7rem;
    padding: 4px 12px;
  }
}

@media (min-width: 1200px) {
  .strategy-list {
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