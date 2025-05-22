<template>
  <div class="attraction-detail">
    <h1 class="attraction-name">{{ attraction.name }}</h1>
    <!-- 图片轮播图 -->
    <div
      style="
        height: 480px;
        display: flex;
        width: 100%;
        float: left;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08);
        background-color: #fff;
        border-radius: 10px;
      "
    >
      <el-carousel height="480px" style="width: 70%; border-radius: 15px">
        <el-carousel-item
          v-for="item in attraction.pictureList"
          :key="item.id"
          interval="1000"
        >
          <img class="carousel-image" :src="item.url" />
        </el-carousel-item>
      </el-carousel>
      <div style="width: 30%; margin-left: 50px">
        <div class="attraction-rating">
          <div class="rating">{{ attraction.rating }}&nbsp;/</div>
          <div class="rating-star">5</div>
          <el-rate
            v-model="attraction.rating"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
            style="margin-top: -20px; margin-left: 100px"
          >
          </el-rate>
        </div>
        <div class="attraction-desc">
          <div class="desc-name">景点主题：</div>
        </div>
        <div class="desc-content" style="color: green">
          {{ attraction.theme }}
        </div>
        <div class="attraction-desc">
          <div class="desc-name">景点地址：</div>
        </div>
        <div class="desc-content">{{ attraction.address }}</div>
        <div class="attraction-desc">
          <div class="desc-name">开放时间：</div>
        </div>
        <div class="desc-content">{{ attraction.openTime }}</div>
        <div class="attraction-desc">
          <div class="desc-name">门票价格：</div>
        </div>
        <div class="desc-content">
          <p class="price">成人票：{{ attraction.adultPrice }}元</p>
          <p class="price">儿童票：{{ attraction.childrenPrice }}元</p>
        </div>
        <el-button
          v-if="!isCollect"
          type="warning"
          class="collection-btn"
          @click="addCollection()"
          plain
          >收藏</el-button
        >
        <el-button v-else type="warning" class="collection-btn" disabled
          >已收藏</el-button
        >
        <el-button
          v-if="attraction.isFee == 2"
          type="success"
          class="attraction-btn"
          @click="showModal = true"
          >在线订票</el-button
        >

        <!-- 遮罩层 -->
        <div v-if="showModal" class="modal-mask" @click.self="closeModal">
          <!-- 弹窗内容 -->
          <div class="modal-container">
            <!-- 弹窗头部 -->
            <div class="modal-header">
              <h2>在线订票</h2>
              <button class="close-btn" @click="closeModal">&times;</button>
            </div>

            <!-- 弹窗底部 -->
            <div class="modal-body">
              <el-form ref="form" :model="paymentInfo" label-width="40px">
                <el-form-item label="成人">
                  <el-input
                    v-model="paymentInfo.adultNumber"
                    style="width: 100px"
                  ></el-input>
                  <el-col class="line">人</el-col>
                </el-form-item>
                <el-form-item label="儿童">
                  <el-input
                    v-model="paymentInfo.childrenNumber"
                    style="width: 100px"
                  ></el-input>
                  <el-col class="line">人</el-col>
                </el-form-item>
                <el-form-item label="预定时间">
                  <el-col :span="11">
                    <el-date-picker
                      type="date"
                      placeholder="选择日期"
                      v-model="paymentInfo.appointmentTime"
                      value-format="yyyy-MM-dd"
                      style="width: 100%"
                      :picker-options="pickerOptions"
                    ></el-date-picker>
                  </el-col>
                </el-form-item>
                <el-form-item label="总价">
                  <el-input
                    v-model="paymentInfo.totalPrice"
                    style="width: 100px"
                  ></el-input>
                  <el-col class="line">元</el-col>
                </el-form-item>
              </el-form>
            </div>

            <!-- 弹窗底部 -->
            <div class="modal-footer">
              <el-button type="success" @click="closeModal" size="medium " plain
                >取消</el-button
              >
              <el-popover placement="bottom" width="360" v-model="visible">
                <div
                  style="
                    text-align: left;
                    margin: 30px;
                    height: 60px;
                    font-size: 20px;
                  "
                >
                  支付金额：
                  <span style="color: red"
                    >{{ paymentInfo.totalPrice }}&nbsp;￥</span
                  >
                </div>
                <div
                  style="
                    text-align: left;
                    margin: 30px;
                    height: 60px;
                    font-size: 20px;
                  "
                >
                  支付方式：
                  <el-select
                    v-model="paymentInfo.paymentType"
                    placeholder="请选择"
                    style="width: 150px; top: -32px; margin-left: 100px"
                  >
                    <el-option
                      v-for="item in paymentTypes"
                      :key="item.value"
                      :label="item.label"
                      :value="item.label"
                    >
                    </el-option>
                  </el-select>
                </div>
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="visible = false"
                    >取消</el-button
                  >
                  <el-button
                    type="primary"
                    size="mini"
                    @click="paymentConfirm(attraction.id)"
                    >确定支付</el-button
                  >
                </div>
                <el-button type="success" slot="reference" size="medium " plain
                  >支付</el-button
                >
              </el-popover>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 景点概述 -->
    <div class="description-section">
      <h2 class="desc-theme">
        <span style="margin-left: 10px">景点概述</span>
      </h2>
      <p style="font-family: 'Courier New', Courier, monospace">
        {{ attraction.description }}
      </p>
    </div>

    <!-- 交通指南 -->
    <div class="description-section" style="margin-top: 20px">
      <h2 class="desc-theme">
        <span style="margin-left: 10px">地理坐标</span>
      </h2>
      <div id="map-container" style="width: 100%; height: 400px"></div>
    </div>

    <!-- 交通指南 -->
    <div class="description-section" style="margin-top: 20px">
      <h2 class="desc-theme">
        <span style="margin-left: 10px">交通指南</span>
      </h2>
      <p style="font-family: 'Courier New', Courier, monospace">
        {{ attraction.trafficDesc }}
      </p>
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
          <div class="review-imgs">
            <img
              class="carousel-image"
              v-for="(item, index) in review.images"
              :key="index"
              :src="item.url"
            />
          </div>
          <el-rate
            v-model="review.score"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
            class="review-score"
          >
          </el-rate>
          <div class="review-date">2022-01-01 23:15</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script >
import AMapLoader from "@amap/amap-jsapi-loader";
import { addCollect, addHistory } from "@/api/request";
import { getAttractionDetail, submitAttractionOrder } from "@/api/attraction";
import { getToken } from "@/utils/auth";

export default {
  name: "AtttractionDetail",
  data() {
    return {
      map: null, // 用于存储地图实例
      hasToken: getToken() !== null,
      isCollect: false,
      visible: false,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      attractionId: null,
      collectionsId: null,
      paymentInfo: {
        attractionId: "",
        childrenNumber: "",
        adultNumber: "",
        appointmentTime: "",
        totalPrice: "",
        paymentType: "",
      },
      writeReview: false,
      showModal: false,
      attraction: {},
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
      paymentTypes: [
        {
          value: "1",
          label: "微信支付",
        },
        {
          value: "2",
          label: "支付宝支付",
        },
      ],
      commentInfo: {
        content: "",
        images: [],
        score: 3,
      },

      collectionInfo: {
        itemId: "",
        itemType: "hotel",
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
      pickerOptions: {
        disabledDate(time) {
          // 禁用今天之前的日期
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000;
        }
      }
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
    // 初始化地图
    async initMap() {
      window._AMapSecurityConfig = {
        securityJsCode: "5acd4d15fec1ed839168511d78ec4060",
      };

      try {
        const AMap = await AMapLoader.load({
          key: "81f30321976742be2fafbcd630ac42d9",
          version: "2.0",
          plugins: ["AMap.Scale", "AMap.ToolBar"],
        });

        // 创建地图实例
        this.map = new AMap.Map("map-container", {
          viewMode: "3D",
          zoom: 14, // 调大初始缩放级别
          center: [this.attraction.longitude, this.attraction.latitude],
        });

        // 添加控件
        this.map.addControl(new AMap.ToolBar());

        // 创建标记（带明确图标）
        const marker = new AMap.Marker({
          position: [this.attraction.longitude, this.attraction.latitude],
          icon: new AMap.Icon({
            image: "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
            size: new AMap.Size(32, 40),
          }),
          title: this.attraction.name,
        });

        // 添加标记并适配视图
        this.map.add(marker);
      } catch (e) {
        console.error("地图加载失败:", e);
        this.$message.error("地图加载失败，请刷新重试");
      }
    },

    // 计算总价
    calculateTotalPrice() {
      const adultPrice =
        parseFloat(this.paymentInfo.adultNumber) * this.attraction.adultPrice ||
        0;
      const childrenPrice =
        parseFloat(this.paymentInfo.childrenNumber) *
          this.attraction.childrenPrice || 0;

      // 计算总价
      this.paymentInfo.totalPrice = adultPrice + childrenPrice;
    },
    closeModal() {
      this.showModal = false;
      this.writeReview = false;
      this.paymentInfo = {}; // 重置支付信息
    },

    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },

    //获取景点详情
    async getInfo() {
      const { data } = await getAttractionDetail(this.attractionId);
      this.attraction = data;
      this.isCollect = data.isCollect;
      if (this.hasToken) {
        console.log("用户已登录");
        this.addBrowseHistory();
      }
      this.initMap(); // 初始化地图
    },

    //提交订单
    async paymentConfirm(id) {
      try {
        this.paymentInfo.attractionId = id;
        this.loading = true;

        const { code, message, data } = await submitAttractionOrder(
          this.paymentInfo
        );

        if (code === 200) {
          this.$message({
            message: "支付成功",
            type: "success",
          });
        } else {
          this.$message.error(message);
        }
      } catch (error) {
        this.handleError(error);
      } finally {
        this.closeModal();
        this.visible = false;
        this.loading = false;
        this.paymentInfo = {}; // 重置支付信息
      }
    },
    //收藏
    async addCollection() {
      this.collectionInfo = {
        itemId: this.attraction.id,
        itemType: "attraction",
        itemName: this.attraction.name,
        itemImg: this.attraction.pictureList[0].url,
        itemDesc: this.attraction.description,
      };
      const { code, data } = await addCollect(this.collectionInfo);
      if (code == 200) {
        this.isCollect = true;
        this.$message({
          message: "收藏成功",
          type: "success",
        });
      } else {
        this.$message.error(message);
      }
      this.collectionInfo = {};
    },
    //浏览历史
    async addBrowseHistory() {
      this.historyInfo = {
        itemId: this.attraction.id,
        itemName: this.attraction.name,
        itemType: "attraction",
        itemImg: this.attraction.pictureList[0].url,
        itemDesc: this.attraction.description,
      };
      console.log(this.historyInfo);
      const { code, message } = await addHistory(this.historyInfo);
      if (code == 200) {
        console.log("浏览历史添加成功");
      } else {
        this.$message.error(message);
      }
    },
  },
  mounted() {
    this.attractionId = this.$route.params.id;
    this.getInfo();
    
  },
  beforeUnmount() {
    if (this.map) {
      this.map.destroy(); // 销毁地图实例
    }
  },
};
</script>

<style scoped>
#map-container {
  border: 1px solid #ccc;
}
.attraction-detail {
  position: relative;
  margin-top: 20px;
  width: 74%;
  margin-left: 13%;
}

.carousel-image {
  width: 100%; /* 图片宽度自适应 */
  height: 100%; /* 图片高度自适应 */
  object-fit: cover; /* 确保图片填充整个容器 */
}

.attraction-name {
  font-size: 40px;
  font-weight: bold;
  margin-top: 80px;
  margin-left: 60px;
  color: #797979;
  font-family: "Pacifico", cursive; /* 使用手写风格字体 */
}
.attraction-rating {
  position: relative;
  margin-top: 50px;
  margin-bottom: 50px;
}
.rating {
  color: #ffb300;
  font-size: 30px;
}
.rating-star {
  font-size: 20px;
  margin-top: -25px;
  margin-left: 60px;
  color: #717171;
}
.attraction-desc {
  margin-top: 30px;
}
.desc-name {
  color: #000000;
  float: left;
  font-size: 18px;
}
.desc-content {
  color: #717171;
  margin-left: 90px;
  margin-right: 4px;
  font-size: 18px;
}
.collection-btn {
  width: 90px;
  margin-top: 30px;
  margin-left: 5%;
}
.attraction-btn {
  margin-top: -100px;
  margin-left: 20%;
}
.description-section {
  margin-top: 560px;
  font-size: 18px;
  color: #717171;
  background-color: #fff;
  border-radius: 10px;
}

.desc-theme {
  margin-top: 40px;
  border-left: 6px solid #7efeeb;
}
.description-section p {
  color: #616161;
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
  width: 70px;
  height: 70px;
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

.review-imgs {
  display: flex; /* 使用flex布局 */
  flex-wrap: wrap; /* 允许换行 */
  gap: 10px; /* 图片之间的间距 */
  padding: 10px;
}
.review-imgs img {
  width: 260px; /* 图片宽度 */
  height: 160px; /* 保持图片比例 */
  border-radius: 20px;
}
.review-date {
  margin-top: -23px;
  font-size: 18px;
  margin-left: 80%;
  color: #636363;
  font-family: "方正舒体";
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