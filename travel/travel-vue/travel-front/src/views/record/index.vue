<template>
  <div class="attraction">
    <div class="search">
      <el-button type="success" class="search-button" icon="el-icon-search" @click="search"
        >搜索</el-button
      >
      <el-input
        placeholder="日记搜索"
        v-model="queryParams.keyword"
        class="search-input"
      />
    </div>
    <el-menu
      :default-active="queryParams.option"
      class="el-menu-demo"
      @select="handleMenuSelect"
      text-color="#000"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="all">全部</el-menu-item>
      <el-menu-item index="new">最新</el-menu-item>
      <el-menu-item index="hot">最热</el-menu-item>
      <el-menu-item index="my">我的</el-menu-item>
    </el-menu>
    <!-- 日记 -->
    <div class="diary-list-container">
      <div
        class="diary-card"
        v-for="item in recordList"
        :key="item.id"
      >
      <router-link :to="{ path: `/record/detail/${item.id}` }">
          <div class="diary-header">
            <h3>{{ item.name }}</h3>
            <p class="diary-date">{{ item.createTime }}</p>
          </div>
          <div class="diary-content">
            <img
              :src="item.imgUrl"
              alt="Diary Image"
              class="diary-image"
            />
            <p>{{ item.desc }}</p>
          </div>
          <div class="diary-like">
            <i class="el-icon-thumb" style="color: red" size="small"></i>
            <span style="margin-left: 10px">{{ item.like }}</span>
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
import {getRecordByPage} from "@/api/request";
export default {
  name: "Record",
  data() {
    return {
      pageNum: 1, // 当前页码
      pageSize:9,
      dataTotal: 0, // 总记录数
      queryParams: {
        keyword:"",
        option: "all"
      },
      recordList: [],
    };
  },  
  methods: {  
    handleMenuSelect(index) {
      this.queryParams.option = index; // 更新选中的选项
      this.search(); // 发起搜索请求
    },
    handleCurrentChange(newPage) {
      this.pageNum = newPage;
      this.getInfoList();
      const element = document.getElementById("target");
      if (element) {
        element.scrollIntoView({ behavior: "smooth" }); // 平滑滚动
      }
    },
    async getInfoList() {
      const { data } = await getRecordByPage(
        this.pageNum,
        this.pageSize,
        this.queryParams
      );
      this.recordList = data.records;
      this.dataTotal = data.total;
    },

    async search(){
      console.log(this.queryParams)
      this.pageNum = 1;
      this.getInfoList();
    } 
  },
  mounted() {
    this.getInfoList();
  },
};
</script>

<style scoped>
.attraction {
  position: relative;
}

.search {
  width: 100%;
  height: 100px;
  margin: 0 auto;
  background-image: url("../../imgs/111.jpg");
  background-size: cover;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: relative;
}

.search-input {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600px;
  height: 50px;
  border-radius: 25px;
  z-index: 1;
}

.diary-list-container {
  display: flex;
  position: relative;
  flex-direction: column;
  width: 80%;
  margin: 20px auto;
  gap: 20px;
}


.diary-card {
  width: 100%;
  height: 300px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.diary-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0 0 30px;
}

.diary-date {
  font-size: 0.8rem;
  color: #666;
  margin-right: 30px;
}

.diary-content {
  display: flex;
  gap: 20px;
  margin-left: 30px;
}

.diary-image {
  width: 300px;
  height: 180px;
  object-fit: cover;
  border-radius: 8px;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}
.diary-like {
  display: flex;
  margin-top: 10px;
  margin-left: 92%;
}
.diary-filter {
  margin-left: 10%;
  margin-top: 10px;
}
.el-menu-demo {
  background-color: #ebeef5;
  margin-left: 10%;
  margin-top: 30px;
  width: 80%;
  display: flex;
  border-radius: 8px;
  margin-bottom: -20px;
}

::v-deep .search-button {
  position: absolute;
  top: 26px;
  margin-left: 968px;
  height: 39px;
  border-radius: 0 3px 3px 0;
  z-index: 2;
}
</style>