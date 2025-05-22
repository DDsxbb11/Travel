<template>
  <div class="hotel-detail">
    <div
      style="
        height: 400px;
        display: flex;
        width: 100%;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08);
        position: relative;
        margin-top: 20px;
        margin-bottom: 60px;
        background-color: #fff;
      "
    >
      <div class="carousel">
        <img class="carousel-image" :src="hotel.imgUrl" alt="Hotel Image" />
        <h2 class="hotel-name">{{ hotel.name }}</h2>
      </div>

      <div style="width: 40%; margin-left: 50px">
        <div class="hotel-rating">
          <div class="rating">{{ hotel.rating }}&nbsp;/</div>
          <div class="rating-star">5</div>
          <el-rate
            v-model="hotel.rating"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
            style="margin-top: -20px; margin-left: 100px"
          >
          </el-rate>
        </div>
        <div class="hotel-desc">
          <div class="desc-name">酒店等级：</div>
        </div>
        <div class="desc-content" style="color: green">
          {{ hotel.level }}
          <el-button
            v-if="!isCollect"
            type="warning"
            class="collection-btn"
            size="small"
            @click="addCollection()"
            plain
            >收藏</el-button
          >
          <el-button
            v-else
            type="warning"
            class="collection-btn"
            size="small"
            disabled
            >已收藏</el-button
          >
        </div>
        <div class="hotel-desc">
          <div class="desc-name">酒店地址：</div>
        </div>
        <div class="desc-content">{{ hotel.address }}</div>
        <div class="hotel-address">
          <div
            id="map-container"
            style="width: 100%; height: 100%; border-radius: 10px"
          ></div>
        </div>
      </div>
    </div>

    <div class="hotel-home" v-for="item in hotel.roomList" :key="item.id">
      <div class="home-type">{{ item.name }}</div>
      <div class="home-image">
        <img :src="item.imgUrl" alt="ROOM IMG" />
      </div>
      <div class="home-desc">
        <div class="home-desc-box" style="align-items: baseline">
          <div class="desc-title" style="border-radius: 10px 0 0 0">
            <p style="margin-top: 5px; margin-left: 10px">房间设施</p>
          </div>
          <div class="home-desc-box-food" style="height: 90%">
            <div
              class="home-desc-content"
              v-for="facility in item.roomFacilityList"
              :key="facility.id"
            >
              <img
                :src="facility.svgUrl"
                alt="SVG 图片"
                class="room-facility-icon"
              />
              <div class="home-lable-text">{{ facility.name }}</div>
            </div>
          </div>
        </div>
        <div class="home-desc-box" style="align-items: baseline">
          <div class="desc-title">
            <p style="margin-top: 5px; margin-left: 10px">洗浴设施</p>
          </div>
          <div class="home-desc-box-food" style="height: 90%">
            <div
              class="home-desc-content"
              v-for="facility in item.bathroomFacilityList"
              :key="facility.id"
            >
              <img
                :src="facility.svgUrl"
                alt="SVG 图片"
                class="room-facility-icon"
              />
              <div class="home-lable-text">{{ facility.name }}</div>
            </div>
          </div>
        </div>
        <div class="home-desc-box" style="align-items: baseline">
          <div class="desc-title" style="border-radius: 0 10px 0 0">
            <p style="margin-top: 5px; margin-left: 10px">食品饮料</p>
          </div>
          <div class="home-desc-box-food">
            <div
              class="home-desc-content"
              v-for="facility in item.foodFacilityList"
              :key="facility.id"
            >
              <img
                :src="facility.svgUrl"
                alt="SVG 图片"
                class="room-facility-icon"
              />
              <div class="home-lable-text">{{ facility.name }}</div>
            </div>
          </div>
          <div class="home-desc-box-price">
            <div class="home-price">今日特价：{{ item.price }}元</div>
            <el-button
              type="success"
              class="home-price-btn"
              @click="clickpayment(item.price)"
              round
              >在线订房</el-button
            >
            <!-- 遮罩层 -->
            <div v-if="showModal" class="modal-mask" @click.self="closeModal">
              <!-- 弹窗内容 -->
              <div class="modal-container">
                <!-- 弹窗头部 -->
                <div class="modal-header">
                  <h2>在线订房</h2>
                  <button class="close-btn" @click="closeModal">&times;</button>
                </div>

                <!-- 弹窗主体内容 -->
                <div class="modal-body">
                  <el-form ref="form" :model="paymentInfo" label-width="40px">
                    <el-form-item label="数量">
                      <el-input
                        v-model="paymentInfo.number"
                        style="width: 100px"
                      ></el-input>
                      <el-col class="line">间</el-col>
                    </el-form-item>
                    <el-form-item label="日期">
                      <el-col :span="11">
                        <el-date-picker
                          v-model="paymentInfo.date"
                          type="daterange"
                          range-separator="至"
                          start-placeholder="入住日期"
                          end-placeholder="离开日期"
                          value-format="yyyy-MM-dd"
                          @change="updateBookingDates"
                          :picker-options="pickerOptions"
                        ></el-date-picker>
                      </el-col>
                    </el-form-item>
                    <el-form-item label="总价">
                      <el-input
                        v-model="paymentInfo.price"
                        style="width: 100px"
                        disabled
                      ></el-input>
                      <el-col class="line">元</el-col>
                    </el-form-item>
                  </el-form>
                </div>

                <!-- 弹窗底部 -->
                <div class="modal-footer">
                  <el-button
                    type="success"
                    @click="closeModal"
                    size="medium "
                    plain
                    >取消</el-button
                  >

                  <el-popover placement="right" width="400" trigger="click">
                    <div title="支付确认" width="400px">
                      <div style="padding: 20px">
                        <div style="margin-bottom: 20px; font-size: 16px">
                          支付金额：<span style="color: red; font-weight: bold"
                            >{{ paymentInfo.price }}元</span
                          >
                        </div>

                        <div style="margin-bottom: 20px">
                          <el-select
                            v-model="paymentInfo.paymentType"
                            placeholder="选择支付方式"
                          >
                            <el-option
                              v-for="item in paymentTypes"
                              :key="item.value"
                              :label="item.label"
                              :value="item.label"
                            />
                          </el-select>
                        </div>

                        <div style="text-align: right">
                          <el-button @click="paymentVisible = false"
                            >取消</el-button
                          >
                          <el-button
                            type="primary"
                            @click="confirmPayment(item.id)"
                            >确认支付</el-button
                          >
                        </div>
                      </div>
                    </div>
                    <el-button
                      type="success"
                      slot="reference"
                      size="medium"
                      plain
                      >支付</el-button
                    >
                  </el-popover>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 酒店介绍 -->
    <div class="description-section">
      <h2 class="desc-theme">
        <span style="margin-left: 20px; margin-top: 15px">酒店介绍</span>
      </h2>
      <div class="hotel-info">
        <td>开业时间：{{ hotel.openTime }}&nbsp;年</td>
        <td style="margin-left: 50px; color: black">
          房间数：{{ hotel.roomNum }}&nbsp;间
        </td>
      </div>
      <div style="margin-left: 20px; font-weight: bold">
        联系电话：{{ hotel.phone }}
      </div>
      <p style="font-family: 'Courier New', Courier, monospace">
        {{ hotel.description }}
      </p>
    </div>
    <!-- 酒店政策 -->
    <div class="description-section">
      <h2 class="desc-theme">
        <span style="margin-left: 20px; margin-top: 15px">酒店政策</span>
      </h2>
      <div class="hotel-policy" v-for="item in hotel.policyList" :key="item.id">
        <div class="hotel-policy-title">{{ item.title }}</div>
        <div class="hotel-policy-content">{{ item.content }}</div>
        <div class="hotel-policy-line"></div>
      </div>
    </div>
    <!-- 酒店设施 -->
    <div class="description-section">
      <h2 class="desc-theme">
        <span style="margin-left: 20px; margin-top: 15px">酒店设施</span>
      </h2>
      <div class="hotel-facility">
        <div
          class="hotel-facility-item"
          v-for="item in hotel.facilityList"
          :key="item.id"
        >
          <img :src="item.svgUrl" alt="SVG 图片" class="hotel-facility-icon" />
          <div class="hotel-facility-name">{{ item.name }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script >
import { addCollect, addHistory } from "@/api/request";
import { getHotelDetail, createHotelOrder } from "@/api/hotel";
import AMapLoader from "@amap/amap-jsapi-loader";
import { getToken } from "@/utils/auth";

export default {
  name: "HotelDetail",

  data() {
    return {
      hasToken: getToken() !== null,
      paymentVisible: false,
      writeReview: false,
      isCollect: false,
      currentRoomMoney: 0,
      paymentInfo: {
        hotelId: "",
        roomId: "",
        number: "",
        startTime: "",
        endTime: "",
        paymentType: "",
        price: "",
      },
      showModal: false,
      hotel: {},
      currentSlide: 0,
      paymentTypes: [
        {
          value: "选项1",
          label: "微信支付",
        },
        {
          value: "选项2",
          label: "支付宝支付",
        },
      ],
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

  methods: {
    updateBookingDates(dates) {
      if (dates && dates.length === 2) {
        this.paymentInfo.startTime = dates[0];
        this.paymentInfo.endTime = dates[1];
        const start = new Date(dates[0]);
        const end = new Date(dates[1]);
        const timeDiff = end.getTime() - start.getTime();
        console.log(Math.ceil(timeDiff / (1000 * 60 * 60 * 24)));
        this.paymentInfo.price =
          Math.ceil(timeDiff / (1000 * 60 * 60 * 24)) *
          this.currentRoomMoney *
          this.paymentInfo.number;
      }
    },
    // 支付
    async confirmPayment(id) {
      // 参数校验
      if (!this.paymentInfo.number || this.paymentInfo.number <= 0) {
        this.$message.error("请输入有效的房间数量");
        return;
      }
      if (!this.paymentInfo.startTime || !this.paymentInfo.endTime) {
        this.$message.error("请选择有效的入住日期和离开日期");
        return;
      }
      if (!this.paymentInfo.paymentType) {
        this.$message.error("请选择支付方式");
        return;
      }
      try {
        this.paymentInfo.hotelId = this.hotel.id;
        this.paymentInfo.roomId = id;
        console.log(this.paymentInfo);
        const { code, message, data } = await createHotelOrder(
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
    closeModal() {
      this.showModal = false;
      this.writeReview = false;
      this.paymentInfo = {}; // 重置支付信息
    },
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
          zoom: 11, // 调大初始缩放级别
          center: [this.hotel.longitude, this.hotel.latitude],
        });

        // 添加控件
        this.map.addControl(new AMap.ToolBar());

        // 创建标记（带明确图标）
        const marker = new AMap.Marker({
          position: [this.hotel.longitude, this.hotel.latitude],
          icon: new AMap.Icon({
            image: "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
            size: new AMap.Size(32, 40),
          }),
          title: this.hotel.name,
        });

        // 添加标记并适配视图
        this.map.add(marker);
      } catch (e) {
        console.error("地图加载失败:", e);
        this.$message.error("地图加载失败，请刷新重试");
      }
    },
    //收藏
    async addCollection() {
      this.collectionInfo.itemId = this.hotel.id;
      this.collectionInfo.itemName = this.hotel.name;
      this.collectionInfo.itemImg = this.hotel.imgUrl;
      this.collectionInfo.itemDesc = this.hotel.description;
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
    async getDetail() {
      const { data } = await getHotelDetail(this.$route.params.id);
      this.hotel = data;
      this.isCollect = data.collect;
      if (this.hasToken) {
        this.addBrowseHistory();
      }
    },
    clickpayment(price) {
      this.currentRoomMoney = price;
      this.showModal = true;
    },
    //浏览历史
    async addBrowseHistory() {
      this.historyInfo = {
        itemId: this.hotel.id,
        itemName: this.hotel.name,
        itemType: "hotel",
        itemImg: this.hotel.imgUrl,
        itemDesc: this.hotel.description,
      };
      const { code, message } = await addHistory(this.historyInfo);
      if (code == 200) {
      } else {
        this.$message.error(message);
      }
    },
  },

  mounted() {
    this.initMap(); // 初始化地图
    this.getDetail();
  },
  beforeUnmount() {
    if (this.map) {
      this.map.destroy(); // 销毁地图实例
    }
  },
};
</script>

<style scoped>
.hotel-detail {
  position: relative;
  width: 74%;
  margin-left: 13%;
}
.room-facility-icon {
  width: 28px;
  height: 28px;
  margin-left: 40px;
  margin-top: 10px;
  filter: invert(40%) sepia(100%) saturate(100%) hue-rotate(800deg)
    brightness(100%);
  stroke: green; /* 设置边框颜色为绿色 */
  stroke-width: 10;
}
.carousel {
  width: 65%;
  position: relative; /* 设置为相对定位 */
  height: 400px; /* 设置容器高度 */
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
}

.carousel-image {
  position: absolute; /* 设置为绝对定位 */
  width: 100%; /* 图片宽度 */
  height: 100%; /* 图片高度 */
  object-fit: cover; /* 确保图片覆盖整个容器 */
  z-index: 2; /* 将图片置于底层 */
  border-radius: 10px;
}

.hotel-name {
  font-size: 40px;
  color: rgb(255, 76, 76); /* 设置文字颜色 */
  font-weight: bold; /* 设置文字加粗 */
  text-shadow: 2px 8px 4px rgba(0, 0, 0, 0.5); /* 添加文字阴影 */
  margin-top: -200px;
  z-index: 4;
}

.hotel-rating {
  position: relative;
  margin-top: 20px;
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
.hotel-desc {
  margin-top: 20px;
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
.hotel-btn {
  margin-top: 40px;
  margin-left: 65%;
}
.description-section {
  position: relative;
  height: auto;
  margin-top: 20px;
  font-size: 16px;
  color: #1b1b1b;
  background-color: #fff;
  border-radius: 10px;
}
.collection-btn {
  margin-left: 55%;
  margin-top: -70px;
}
.desc-theme {
  display: flex;
}

.hotel-home {
  position: relative;
  width: 100%;
  height: 400px;
  box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.08);
  margin-top: 20px;
  background-color: #fff;
  border-radius: 10px;
}
.home-type {
  position: relative;
  width: 90%;
  height: 30px;
  margin-left: 50px;
  top: 8px;
  font-size: 24px;
  color: #282828;
  font-weight: bold;
  display: flex;
}
.home-image {
  display: flex;
  margin-top: 10px;
  margin-left: 30px;
  width: 23%;
  height: 85%;
  border-radius: 10px;
  float: left;
}
.home-image img {
  width: 100%;
  height: 100%;
  border-radius: 10px;
}

.home-desc {
  margin-left: 28%;
  margin-top: 10px;
  width: 70%;
  height: 85%;
  display: flex; /* 使用Flexbox布局 */
}

.home-desc-box {
  width: 33%; /* 小盒子宽度 */
  height: 100%; /* 小盒子高度 */
  box-shadow: 2px 4px 6px rgba(0, 0, 0, 0.08);
}
.home-desc-box .desc-title {
  display: flex;
  width: 100%;
  height: 30px;
  font-size: 18px;
  font-weight: bold;
  font-family: "楷体";
  color: #000000;
  background-color: #f5f7fa;
}
.home-desc-content {
  width: 100%;
  height: 25px;
  margin-top: 5px;
  margin-bottom: 10px;
}
.home-desc-box-food {
  width: 100%;
  height: 50%;
  overflow: auto;
}
.home-desc-box-price {
  position: relative;
  width: 100%;
  height: 40%;
  box-shadow: 0 -4px 6px rgba(0, 0, 0, 0.08);
}
.home-lable {
  width: 100%;
  height: 30px;
  margin-left: 30px;
}
.home-lable-text {
  margin-top: -27px;
  margin-left: 80px;
  font-size: 17px;
  font-weight: bold;
  font-family: "Pacifico", cursive;
  color: #6a9057;
}
.hotel-address {
  width: 100%;
  height: 190px;
  margin-top: 20px;
  border-radius: 10px;
}
.home-price {
  position: absolute;
  width: auto;
  height: auto;
  top: 25px;
  left: 20px;
  font-size: 20px;
  font-weight: bold;
  border-radius: 25px;
  color: #ff0000;
  border: 1px solid #ff0000;
  text-align: center; /* 水平居中 */
}
.home-price-btn {
  margin-top: 80px;
  margin-left: 50%;
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
  width: 450px;
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
.hotel-info {
  margin-top: 20px;
  margin-left: 20px;
  width: 90%;
  height: 30px;
  font-size: 16px;
  color: black;
  font-weight: bold;
  display: flex;
}
.description-section p {
  margin-top: 20px;
  margin-left: 20px;
  width: 95%;
  font-size: 16px;
  color: black;
}
.hotel-policy {
  margin-top: 20px;
  margin-left: 20px;
  width: 95%;
  font-size: 16px;
  color: black;
}
.hotel-policy-title {
  margin-top: 15px;
  margin-left: 20px;
  font-size: 16px;
  width: 200px;
  color: rgb(59, 59, 59);
  font-weight: bold;
}
.hotel-policy-content {
  margin-top: -18px;
  margin-left: 220px;
  left: 80px;
  width: 65%;
  font-size: 16px;
  color: rgb(119, 119, 119);
}
.hotel-policy-line {
  margin-top: 15px;
  width: 100%;
  border-top: 1px solid #aeaeae;
}
.hotel-facility {
  margin-top: 20px;
  margin-left: 20px;
  width: 80%;
  display: flex; /* 使用flex布局 */
  flex-wrap: wrap; /* 允许子元素自动换行 */
  gap: 10px; /* 设置子元素之间的间距 */
}
.hotel-facility-item {
  width: 260px; /* 设置小盒子的宽度 */
  height: 40px; /* 设置小盒子的高度 */
  display: flex; /* 让小盒子内容居中显示 */
  align-items: center;
  gap: 20px;
}
.hotel-facility-icon {
  margin-left: 40px;
  width: 30px;
  height: 30px;
}
.hotel-facility-name {
  margin-left: -10px;
}
</style>