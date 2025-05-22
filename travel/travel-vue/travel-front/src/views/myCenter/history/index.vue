<template>
  <div class="common-history" id="target">
    <h1 class="title">浏览历史</h1>
    <div class="history-list">
      <el-card
        class="history-card"
        v-for="history in historyList"
        :key="history.id"
      >
        <router-link :to="{ path: `/${history.itemType}/detail/${history.itemId}` }">
          <div class="history-content">
            <img
              :src="history.itemImg"
              alt="history Image"
              class="history-image"
            />
            <div class="history-header">
              <h3>{{ history.itemName }}</h3>
              <!-- 添加分类标签 -->
              <el-tag
                :class="['type-tag', history.type]"
                :style="{ backgroundColor: getTagColor(history.itemType) }"
              >
                {{ getTypeName(history.itemType) }}
              </el-tag>
              <p class="history-date">{{ history.updateTime }}</p>
            </div>
            <div class="history-description">
              <p>{{ history.itemDesc }}</p>
            </div>
          </div>
        </router-link>
      </el-card>
    </div>
    <div class="block">
      <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="dataTotal"
        @current-change="handleCurrentChange"
        style="text-align: center"
      />
    </div>
  </div>
</template>

<script>
import { getHistory } from "@/api/request";
export default {
  name: "history",
  data() {
    return {
      pageNum: 1,
      pageSize: 5,
      dataTotal: 0,
      historyList: [],
    };
  },
  methods: {
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getInfo();
      const element = document.getElementById("target");
      if (element) {
        element.scrollIntoView({ behavior: "smooth" }); // 平滑滚动
      }
    },
    async getInfo() {
      const { data } = await getHistory(this.pageNum, this.pageSize);
      this.historyList = data.records;
      this.dataTotal = data.total;
    },
    getTagColor(type) {
      const colorMap = {
        hotel: "#4CAF50",
        attraction: "#FF9800",
        strategy: "#F44336",
        record: "#40e6dd",
      };
      return colorMap[type] || "#9E9E9E"; // 默认灰色
    },
    // 获取类型名称
    getTypeName(type) {
      const nameMap = {
        hotel: "酒店",
        attraction: "景点",
        strategy: "攻略",
        record: "日记",
      };
      return nameMap[type] || "其他";
    },
  },
  mounted() {
    this.getInfo();
  },
};
</script>

<style lang="scss" scoped>
.common-history {
  width: 80%;
  height: auto;
  margin: 0 auto;
  padding: 30px;
  background-color: #fff;
  border-radius: 10px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #ff9500;
  margin-bottom: 40px;
  margin-left: 10%;
}

.history-list {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.history-card {
  width: 100%;
  padding: 20px;
  border-radius: 8px;
  height: 200px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.history-header {
  position: absolute;
  width: 800px;
  margin-left: 180px;
  margin-top: -15px;
}

.history-date {
  position: absolute;
  margin-top: -40px;
  margin-left: 680px;
  font-size: 0.8rem;
  color: #666;
}

.history-content {
  display: flex;
  gap: 20px;
}

.history-image {
  position: absolute;
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
}

.history-description {
  display: flex;
  width: 90%;
  height: 180px;
  margin-left: 180px;
  margin-top: 40px;
}

.block {
  margin-top: 20px;
}

/* 新增标签样式 */
.history-tag {
  position: absolute;
  left: 200px;
  top: 35px;
  font-size: 12px;
}
.type-tag {
  position: absolute;
  right: -80px;
  top: -15px;
  height: 24px;
  line-height: 24px;
  padding: 0 10px;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 500;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border: none;
  text-transform: capitalize;

  // 微调不同标签的样式
  &.hotel {
    background: linear-gradient(135deg, #4caf50, #8bc34a);
  }
  &.attraction {
    background: linear-gradient(135deg, #ff9800, #ffc107);
  }
  &.strategy {
    background: linear-gradient(135deg, #f44336, #ff5252);
  }
  &.diary {
    background: linear-gradient(135deg, #40e6dd, #40f3de);
  }

  // 添加悬停效果
  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
    transition: all 0.3s ease;
  }

  // 添加小箭头装饰
  &::after {
    content: "";
    position: absolute;
    left: 10px;
    bottom: -5px;
    width: 0;
    height: 0;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-top: 5px solid currentColor;
    opacity: 0.7;
  }
}
</style>