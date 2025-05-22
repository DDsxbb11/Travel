<template>
  <div class="hotel">
    <div class="search">
      <div class="search-title">下一站，住哪儿？</div>
      <div class="filter">
        <el-cascader
          v-model="location"
          :options="locationOptions"
          placeholder="请选择省/市/区"
          clearable
          style="width: 200px; height: 50px"
          class="location-cascader"
        ></el-cascader>
        <el-date-picker
          v-model="dataValue"
          type="daterange"
          range-separator=""
          start-placeholder="入住"
          end-placeholder="离店"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          class="custom-date-picker"
          :picker-options="pickerOptions"
        >
        </el-date-picker>
        <div class="search-date">至</div>
        <!-- 搜索框 -->
        <el-input
          placeholder="搜索关键词"
          prefix-icon="el-icon-search"
          v-model="queryVo.keyword"
          class="search-input"
        />
        <!-- 其他过滤条件 -->
        <el-popover
          placement="bottom"
          width="200"
          height="500"
          trigger="click"
          class="custom-popover"
        >
          <div>价格</div>
          <el-slider v-model="priceRange" range show-stops :max="1000"> </el-slider>
          <div>星级</div>
          <el-checkbox-group v-model="queryVo.levelList">
            <el-checkbox label="一星级"></el-checkbox>
            <el-checkbox label="二星级"></el-checkbox>
            <el-checkbox label="三星级"></el-checkbox>
            <el-checkbox label="四星级"></el-checkbox>
            <el-checkbox label="五星级"></el-checkbox>
            <el-checkbox label="白金星级"></el-checkbox>
          </el-checkbox-group>
          <el-button slot="reference">更多选择</el-button>
        </el-popover>
        <el-button type="success" class="search-button" icon="el-icon-search" id="target" @click="search()"
          >搜索</el-button
        >
      </div>
      <!-- 二级联动地点选择 -->
    </div>
    <div class="hotel-list" >
      <div
        class="hotel-item"
        v-for="hotel in hotels"
        :key="hotel.id"
      >
        <router-link :to="{ path: `/hotel/detail/${hotel.id}` }">
          <img
            :src="hotel.imgUrl"
            alt="hotel"
            class="hotel-image"
          />
          <div class="hotel-info">
            <h3 class="hotel-name">{{ hotel.name }}</h3>
            <p class="hotel-location">{{ hotel.address }}</p>
            <p class="hotel-type">{{ hotel.level }}</p>
            <p class="hotel-fee">{{ hotel.minPrice }}起</p>
          </div>
        </router-link>
      </div>
    </div>
    <div class="block">
      <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        :disabled="disabled"
        layout="total, prev, pager, next"
        :total="dataTotal"
        @current-change="handleCurrentChange"
        style="text-align: center"
      />
    </div>
  </div>
</template>



<script>
import { getOptions} from "@/api/request"
import { getHotelByPage } from "@/api/hotel";
export default {
  name: "HotelFilter",
  data() {
    return {
      pageNum: 1, // 当前页码
      pageSize:9,
      dataTotal: 0, // 总记录数
      queryVo: {
        keyword: "",
        province: "",
        city: "",
        district: "",
        inTime: "",
        outTime: "",
        levelList: [],
        minPrice: "",
        maxPrice: "",
      }, // 查询条件
      locationOptions: [],
      dataValue: [],
      location: [],
      priceRange: [0, 1000],
      hotels:[],
      pickerOptions: {
        disabledDate(time) {
          // 禁用今天之前的日期
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        }
      }
    };
  },
  methods: {
    processLocation() {
      if (Array.isArray(this.location)) {
        // 假设地区选择器返回的是[省,市,区]数组
        const [province, city, district] = this.location;
        this.queryVo.province = province || "";
        this.queryVo.city = city || "";
        this.queryVo.district = district || "";
      } else {
        // 清空地区选择
        this.queryVo.province = "";
        this.queryVo.city = "";
        this.queryVo.district = "";
      }
    },
    
    processDateRange() {
      if (this.dataValue && this.dataValue.length === 2) {
        const [startDate, endDate] = this.dataValue;
        this.queryVo.inTime = startDate ;
        this.queryVo.outTime = endDate ;
      } else {
        this.queryVo.inTime = "";
        this.queryVo.outTime = "";
      }
    },
    processPriceRange() {
      if (this.priceRange && this.priceRange.length === 2) {
        const [minPrice, maxPrice] = this.priceRange;
        this.queryVo.minPrice = minPrice ;
        this.queryVo.maxPrice = maxPrice ;
      } else {
        this.queryVo.minPrice = "";
        this.queryVo.maxPrice = "";
      }
    },
    truncateDescription(desc) {
      const maxLength = 40;
      if (!desc) return "";
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
      const { data } = await getHotelByPage(this.pageNum,this.pageSize,this.queryVo);
      this.hotels = data.records;
      this.dataTotal = data.total;
    },

    search() {
      this.processDateRange();
      this.processLocation();
      this.processPriceRange();
      this.getInfo();
    }

  },
  mounted() {
    this.getInfo();
  },
  created() {
    getOptions().then((res) => {
      this.locationOptions = res.data;
    });
  },
};
</script>

<style scoped>
::v-deep .search-button {
  position: absolute;
  margin-left: 750px;
  height: 50px;
  border-radius: 0 15px 15px 0;
}
.custom-popover {
  position: relative;
  left: 255px;
}

::v-deep .custom-popover .el-popover {
  /* 保持原有定位属性 */
  position: absolute;
  display: flex;

  /* 调整尺寸 */
  min-height: 50px !important; /* 与输入框高度一致 */
  height: auto !important;

  /* 恢复边框样式 */
  border: 1px solid #c7c7c7 !important;
  border-radius: 4px !important;
}

/* 调整触发按钮对齐 */
::v-deep .custom-popover .el-button {
  height: 50px; /* 与输入框高度一致 */
  width: 140px;
  margin-left: 29px;
  margin-top: 0.6px;
  border-radius: 0 0 0 0;
}

/* 保持内容区域高度 */
::v-deep .custom-popover .el-popover__content {
  height: auto;
  min-height: 180px; /* 保证内容区域最小高度 */
}

/* 调整滑块和复选框布局 */
::v-deep .custom-popover .el-slider,
::v-deep .custom-popover .el-checkbox-group {
  margin: 15px 0;
}
.hotel {
  position: relative;
}

.search {
  width: 100%;
  height: 340px;
  background-image: url("../../imgs/12.jpg");
  background-size: cover;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}
.search-title {
  width: 400px;
  height: 50px;
  font-size: 40px;
  position: absolute;
  margin-top: 110px;
  left: 21%;
  color: #74ff18;
  font-family: "Pacifico", cursive; /* 使用手写风格字体 */
  text-shadow: 10px 10px 20px rgba(231, 9, 9, 0.2);
}
.search-input {
  position: absolute;
  top: 29.2px;
  left: 575.2px;
  transform: translate(-50%, -50%);
  width: 150px;
  height: 60px;
  border-radius: 0;
  z-index: 1;
}
.filter {
  /* 保持原有样式 */
  width: 52%;
  height: 49px;
  border-radius: 25px;
  position: absolute;
  top: 60%;
  left: 46%;
  transform: translate(-50%, -50%);
  display: flex; /* 新增 */
  align-items: center; /* 新增 */
  padding: 0 20px; /* 新增 */
  background-color: #fff;
}

/* 级联选择器样式调整 */

::v-deep .location-cascader .el-input__inner {
  height: 50px !important;
  height: 49.5px;
  border-radius: 15px 0 0 15px;
  margin-left: -25px;
  font-size: 16px;
  margin-top: 0.5px;
  width: 110%;
}

::v-deep .location-cascader .el-input__icon {
  line-height: 50px;
  border-radius: 0;
}
.custom-date-picker.el-date-editor {
  width: 300px; /* 设置宽度 */
  height: 50px;
  margin-left: -10px;
  margin-top: 0.9px;
  border-radius: 0;
}

/* 设置输入框高度 */
.custom-date-picker .el-input__inner {
  height: 50px; /* 设置高度 */
  width: 300px;
  line-height: 50px;
  border-radius: 0;
}

/* 设置下拉面板宽度（可选） */
.custom-date-picker .el-picker-panel {
  width: 600px;
}

/* 设置下拉面板内容区域高度（可选） */
.custom-date-picker .el-picker-panel__body {
  min-height: 100px;
}
.search-date {
  position: relative;
  margin-left: -160px;
  color: #a2a2a2;
}
/* 搜索框样式微调 */
::v-deep .search-input .el-input__inner {
  margin-top: 0.5px;
  height: 50.5px;
  border-radius: 0;
  font-size: 16px;
  width: 170px;
}

/* 景点列表容器 */
.hotel-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  padding: 40px 5%;
  max-width: 1440px;
  margin: 0 auto;
}

/* 单个景点卡片 */
.hotel-item {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  position: relative;
}

.hotel-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.12);
}

/* 图片容器 */
.hotel-image {
  width: 100%;
  height: 220px;
  object-fit: cover;
  border-radius: 12px 12px 0 0;
  transition: transform 0.3s ease;
}

.hotel-item:hover .hotel-image {
  transform: scale(1.03);
}

/* 信息容器 */
.hotel-info {
  padding: 20px;
  position: relative;
  height: 90px;
}

/* 景点名称 */
.hotel-name {
  font-size: 1.25rem;
  color: #2c3e50;
  margin-top: -15px;
  font-weight: 600;
  line-height: 1.3;
}

/* 位置信息 */
.hotel-location {
  font-size: 1.03rem;
  height: 10px;
  color: #666;
  display: flex;
  align-items: center;
}

.hotel-location::before {
  content: "📍";
  margin-right: 8px;
}

/* 类型标签 */
.hotel-type {
  position: absolute;
  top: -20px;
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
.hotel-fee {
  position: absolute;
  bottom: -15px;
  right: 15px;
  color: rgb(255, 162, 0);
  padding: 8px 15px;
  font-size: 1.04rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hotel-list {
    grid-template-columns: 1fr;
    gap: 20px;
    padding: 20px;
  }

  .hotel-image {
    height: 180px;
  }

  .hotel-name {
    font-size: 1.1rem;
  }

  .hotel-type {
    font-size: 0.7rem;
    padding: 4px 12px;
  }
}

@media (min-width: 1200px) {
  .hotel-list {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>