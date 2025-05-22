<template>
  <div class="common-collection">
    <h1 class="title">我的订单</h1>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      @select="handleSelect"
      text-color="#000"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="attraction">景点门票</el-menu-item>
      <el-menu-item index="hotel">酒店住宿</el-menu-item>
      <el-menu-item index="strategy">旅游攻略</el-menu-item>
    </el-menu>
    <div class="collection-list">
      <component :is="currentComponent" :data="data" @action="handleAction" />
    </div>
    <div class="block">
      <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="handleCurrentChange"
        style="text-align: center"
      />
    </div>
  </div>
</template>

<script>
import AttractionComponent from './components/AttractionComponent.vue';
import HotelComponent from './components/HotelComponent.vue';
import StrategyComponent from './components/StrategyComponent.vue';
import { getOrder } from "@/api/request";

export default {
  name: "Order",
  components: {
    AttractionComponent,
    HotelComponent,
    StrategyComponent
  },
  data() {
    return {
      activeIndex: "attraction",
      pageNum: 1,
      pageSize: 6,
      total: 0,
      data: [],
      cachedData: {
        attraction: [],
        hotel: [],
        strategy: []
      },
    };
  },
  computed: {
    currentComponent() {
      switch (this.activeIndex) {
        case 'attraction':
          return 'AttractionComponent';
        case 'hotel':
          return 'HotelComponent';
        case 'strategy':
          return 'StrategyComponent';
        default:
          return 'AttractionComponent';
      }
    }
  },
  methods: {
    async handleSelect(index) {
      this.activeIndex = index;
      if (!this.cachedData[index].length) {
        await this.getInfo(1, index);
      } else {
        this.data = this.cachedData[index];
      }
    },
    handleCurrentChange(newPage) {
      this.getInfo(newPage, this.activeIndex);
    },
    async getInfo(pageNum, index) {
      const { data } = await getOrder(pageNum, this.pageSize, index);
      this.cachedData[index] = data.records;
      this.data = this.cachedData[index];
      this.total = data.total;
    },
  
    handleAction(row) {
      console.log("操作订单", row);
    }
  },
  mounted() {
    this.getInfo(1, this.activeIndex);
  },
};
</script>

<style lang="scss" scoped>
.common-collection {
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
  margin-left: 4%;
}
.collection-list {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
}
.block {
  margin-top: 20px;
}
.el-menu-demo {
  background-color: #ebeef5;
  margin-top: 30px;
  width: 100%;
  display: flex;
  border-radius: 4px;
  align-items: center; /* 垂直居中 */
}
</style>