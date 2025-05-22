<template>
  <div class="record-detail">
    <div class="record-header">
      <img :src="record.imgUrl" alt="" />
      <div class="record-avatar">
        <img :src="record.authorAvatar" alt="" />
      </div>
      <div class="record-title">{{ record.name }}</div>
      <div class="record-author">{{ record.authorName }}</div>
      <div class="record-publish-time">发布时间：{{ record.createTime }}</div>
      <i class="el-icon-thumb record-btu"></i>
      <i
        class="el-icon-star-off record-btu"
        @click="addCollection()"
        :style="{ color: isCollect ? '#E6A23C' : '' }"
      ></i>
    </div>
    <div class="record-info">
      <el-timeline style="width: 900px" class="custom-timeline">
        <el-timeline-item
          v-for="(item, index) in record.routeList"
          :key="index"
          :timestamp="item.title"
          placement="top"
          icon="el-icon-video-camera"
          size="large"
        >
          <el-card>
            <h3 style="color: #909399">{{ item.description }}</h3>
            <div class="route-img">
              <img :src="item.imgUrl" alt="" />
            </div>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <!-- 驴友点评 -->
    <div class="reviews-section">
      <h2 class="reviews-title">
        <span style="margin-left: 10px">驴友点评</span>
      </h2>
      <el-button
        type="success"
        size="small"
        class="review-btn"
        @click="writeReview = true"
        >+ 写点评</el-button
      >
      <!-- 遮罩层 -->
      <div v-if="writeReview" class="modal-mask" @click.self="closeModal">
        <!-- 弹窗内容 -->
        <div class="modal-container" style="width: 800px">
          <!-- 弹窗头部 -->
          <div class="modal-header" style="height: 30px">
            <button
              style="margin-top: -40px; margin-left: 95%"
              class="close-btn"
              @click="closeModal"
            >
              &times;
            </button>
          </div>

          <!-- 弹窗主体内容 -->
          <div class="modal-body">
            <el-input
              type="textarea"
              :autosize="{ minRows: 2, maxRows: 4 }"
              placeholder="说点什么呢。。。。。。"
              v-model="commentInfo.content"
            >
            </el-input>
          </div>
          <el-rate
            v-model="commentInfo.score"
            show-text
            style="margin-top: 20px"
          ></el-rate>
          <el-upload
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            style="margin-top: 20px"
          >
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{ file }">
              <img
                class="el-upload-list__item-thumbnail"
                :src="file.url"
                alt=""
              />
              <span class="el-upload-list__item-actions">
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>
          <!-- 弹窗底部 -->
          <div class="modal-footer">
            <el-button type="success" @click="closeModal" size="medium " plain
              >取消</el-button
            >
            <el-button type="success" @click="confirmComment" size="medium "
              >发布</el-button
            >
          </div>
        </div>
      </div>
      <div v-for="(review, index) in reviews" :key="index" class="review-item">
        <div class="review-header">
          <div class="review-author-avatar">
            <img src="../../../imgs/02.jpg" />
          </div>
          <div class="review-author">{{ review.author }}</div>
        </div>
        <div class="review-content">
          <div class="review-text">{{ review.content }}</div>
          <div class="review-date">2022-01-01 23:15</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script >
import { addCollect, getRecordDetail,addHistory } from "@/api/request";
import { getToken } from "@/utils/auth";

export default {
  name: "AtttractionDetail",
  data() {
    return {
      map: null, // 用于存储地图实例
      visible: false,
      hasToken: getToken() != null,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      writeReview: false,
      showModal: false,
      isCollect: false,
      record: {},

      reviews: [
        {
          author: "旅行者暧昧",
          content: "九寨沟的风景太美了，尤其是五花海，色彩斑斓！",
          score: 4,
          images: [
            { id: 1, url: require("@/imgs/01.jpg") },
            { id: 1, url: require("@/imgs/04.jpg") },
            { id: 1, url: require("@/imgs/01.jpg") },
            { id: 1, url: require("@/imgs/02.jpg") },
            { id: 1, url: require("@/imgs/01.jpg") },
            { id: 1, url: require("@/imgs/03.jpg") },
          ],
        },
        {
          author: "旅行者爱上算法",
          content: "瀑布非常壮观，值得一去！",
          score: 5,
          images: [{ id: 1, url: require("@/imgs/01.jpg") }],
        },
        {
          author: "旅行者飞飞飞",
          content: "门票有点贵，但景色真的很值！",
          score: 3,
          images: [{ id: 1, url: require("@/imgs/01.jpg") }],
        },
      ],
      currentSlide: 0,
      commentInfo: {
        content: "",
        images: [],
        score: 3,
      },
      collectionInfo: {
        itemId: "",
        itemType: "",
        itemName: "",
        itemImg: "",
        itemDesc: "",
      },
      historyInfo: {
        itemId: "",
        itemType: "",
        itemName: "",
        itemImg: "",
        itemDesc: "",
      },
    };
  },
  watch: {
    // 监听 paymentInfo.adultPrice 和 paymentInfo.childrenPrice 的变化
    "paymentInfo.adultNumber"(newVal) {
      this.calculateTotalPrice();
    },
    "paymentInfo.childrenNumber"(newVal) {
      this.calculateTotalPrice();
    },
  },
  methods: {
    closeModal() {
      this.showModal = false;
      this.writeReview = false;
      this.paymentInfo = {}; // 重置支付信息
    },
    //确认支付
    paymentConfirm() {
      console.log(this.paymentInfo);
      this.closeModal();
      this.visible = false;
    },
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    //收藏
    async addCollection() {
      this.collectionInfo = {
        itemId: this.record.id,
        itemType: "record",
        itemName: this.record.name,
        itemImg: this.record.imgUrl,
        itemDesc: this.record.routeList[0].description,
      };
      if (!this.isCollect) {
        const { code, data } = await addCollect(this.collectionInfo);
        if (code == 200) {
          this.isCollect = true;
        }
      }
    },
    //浏览历史
    async addBrowseHistory() {
      this.historyInfo = {
        itemId: this.record.id,
        itemName: this.record.name,
        itemType: "record",
        itemImg: this.record.imgUrl,
        itemDesc: this.record.routeList[0].description,
      };
      console.log(this.historyInfo);
      const { code, message } = await addHistory(this.historyInfo);
      if (code == 200) {
      } else {
        this.$message.error(message);
      }
    },
  },
  mounted() {
    getRecordDetail(this.$route.params.id).then((res) => {
      this.record = res.data;
      this.isCollect = res.data.isCollect;
      if (this.hasToken) {
        console.log("用户已登 tianj");
        this.addBrowseHistory();
      }
    });
  },
};
</script>

<style scoped>
.red-star {
  color: red;
}
.record-detail {
  position: relative;
  margin-top: 20px;
  width: 74%;
  margin-left: 13%;
  background-color: #fff;
}
.record-header {
  top: 10px;
  position: relative;
  display: flex;
  width: 100%;
  height: 460px; /* 容器高度 */
}
.record-header img {
  display: flex;
  width: 1120px;
  height: 400px;
  object-fit: cover;
}
.record-avatar {
  width: 150px;
  height: 150px; /* 上层盒子高度 */
  border-radius: 50%;
  margin-left: -950px;
  margin-top: 300px;
}
.record-avatar img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.record-title {
  width: 600px;
  height: 40px;
  font-size: 32px;
  color: #ffffff;
  font-family: "楷体";
  margin-left: 20px;
  margin-top: 350px;
  font-weight: bold;
}
.record-author {
  width: auto;
  height: 20px;
  font-size: 20px;
  color: #ff6a00;
  font-family: "楷体";
  margin-left: -580px;
  margin-top: 410px;
  font-weight: bold;
}
.record-publish-time {
  width: 220px;
  height: 20px;
  font-size: 16px;
  color: #919191;
  font-family: "楷体";
  margin-top: 415px;
  margin-left: 30px;
}
.record-btu {
  width: 40px;
  height: 40px;
  font-size: 20px;
  color: #919191;
  margin-top: 413px;
  margin-left: 30px;
}
.record-info {
  padding: 30px;
  position: relative;
  width: 100%;
  height: auto;
  margin-left: 40px;
}
/* 深度选择器穿透组件作用域 */
::v-deep .custom-timeline .el-timeline-item__timestamp {
  font-size: 20px !important; /* 调整时间戳字体大小 */
  color: #33ff55; /* 可选：修改颜色 */
  font-weight: bold; /* 可选：加粗 */
}
::v-deep .custom-timeline .el-timeline-item__icon {
  font-size: 25px !important; /* 调整图标大小（适用于字体图标） */
  width: 80px !important; /* 调整图标容器宽度 */
  height: 25px !important; /* 调整图标容器高度 */
  background-color: rgb(174, 174, 174) !important;
  border-radius: 50% !important;
}
.route-img {
  width: 800px;
  height: 400px;
}
.route-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.reviews-section {
  position: relative;
  margin-top: 50px;
  background-color: #fff;
  border-radius: 10px;
}
.reviews-title {
  font-size: 30px;
  color: #717171;
  border-left: 6px solid #7efeeb;
  border-bottom: 1px solid #717171;
}
.review-btn {
  display: flex;
  font-size: 15px;
  margin-top: -60px;
  margin-left: 90%;
}
.review-item {
  position: relative;
  margin-top: 30px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  padding: 30px 50px 30px 20px;
  border-bottom: 1px dashed #d4d4d4;
}
.review-header {
  display: flex;
  width: 18%;
  padding: 10px;
}
.review-author-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 10px;
}
.review-author-avatar img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.review-author {
  width: 100px;
  margin-top: 10px;
  font-size: 17px;
  font-family: "楷体";
  color: #ff8000;
}

.review-content {
  width: 80%;
  margin-left: -10px;
}
.review-text {
  font-size: 18px;
  color: #636363;
  margin-top: 20px;
  margin-left: 10px;
  font-family: "宋体";
}

/* 遮罩层样式 */
.modal-mask {
  position: fixed;
  z-index: 999;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity 0.3s ease;
}
.date-picker-top {
  z-index: 1000; /* 确保这个值高于其他元素的 z-index */
}
/* 弹窗容器 */
.modal-container {
  width: 400px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  padding: 20px;
  animation: modal-in 0.3s ease;
}

/* 弹窗进入动画 */
@keyframes modal-in {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

/* 弹窗头部 */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
  border-bottom: 1px solid #eee;
  margin-bottom: 15px;
}

/* 关闭按钮 */
.close-btn {
  border: none;
  background: none;
  font-size: 28px;
  cursor: pointer;
  margin-top: -80px;
}

/* 弹窗底部 */
.modal-footer {
  margin-top: 15px;
  text-align: right;
}

.modal-footer button {
  margin-left: 10px;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}
.line {
  margin-top: -40px;
  margin-left: 110px;
  color: #717171;
  font-size: 15px;
  font-weight: bold;
}
.review-score {
  margin-top: 20px;
  margin-left: 10px;
}
</style>