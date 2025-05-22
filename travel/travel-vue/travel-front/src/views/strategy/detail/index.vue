<template>
  <div class="strategy-detail">
    <!-- 图片轮播图 -->
    <div
      style="
        position: relative;
        height: 400px;
        display: flex;
        width: 100%;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.08);
        background-color: #fff;
        border-radius: 10px;
        top: 20px;
      "
    >
      <div style="width: 55%; border-radius: 15px">
        <img class="carousel-image" :src="strategy.imgUrl" alt="Hotel Image" />
      </div>
      <div style="width: 40%; margin-left: 50px">
        <div class="strategy-name">【{{ strategy.name }}】</div>
        <div class="strategy-rating">
          <el-rate
            v-model="strategy.rating"
            disabled
            show-score
            text-color="#ff9900"
            score-template="{value}"
            style="margin-top: 20px; margin-left: 140px"
          >
          </el-rate>
        </div>

        <div class="strategy-desc">
          <div class="desc-name">服务保障：</div>
        </div>
        <div class="desc-content tag">
          <el-tag v-for="tag in combinedTags" :key="tag.name" :type="tag.type">
            {{ tag.name }}
          </el-tag>
        </div>
        <div class="strategy-desc" style="margin-top: 40px">
          <div class="desc-name">预售时间：</div>
        </div>
        <div class="desc-content">
          {{ strategy.startTime }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;——
        </div>
        <div class="desc-content" style="margin-top: -19px; margin-left: 215px">
          {{ strategy.endTime }}
        </div>
        <div class="strategy-desc" style="margin-top: 20px">
          <div class="desc-name">出发地点：</div>
        </div>
        <div class="desc-content">{{ strategy.address }}</div>
        <div class="strategy-desc" style="margin-top: -15px">
          <div class="strategy-price">
            <div class="price">价格：{{ strategy.price }}￥/人</div>
            <span>已售：{{ strategy.sale }}</span>
          </div>
        </div>
        <div class="btn-group">
          <el-button
            v-if="!isCollect"
            type="warning"
            class="collection-btn"
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
          <el-button
            type="success"
            class="strategy-btn"
            @click="showModal = true"
            >在线预购</el-button
          >
        </div>
        <!-- 遮罩层 -->
        <div v-if="showModal" class="modal-mask" @click.self="closeModal">
          <!-- 弹窗内容 -->
          <div class="modal-container">
            <!-- 弹窗头部 -->
            <div class="modal-header">
              <h2>在线预购</h2>
              <button class="close-btn" @click="closeModal">&times;</button>
            </div>

            <!-- 弹窗主体内容 -->
            <div class="modal-body">
              <el-form ref="form" :model="paymentInfo" label-width="40px">
                <el-form-item label="人数">
                  <el-input
                    v-model="paymentInfo.number"
                    style="width: 100px"
                  ></el-input>
                  <el-col class="line">人</el-col>
                </el-form-item>

                <el-form-item label="日期">
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
                    >{{ paymentInfo.price }}&nbsp;￥</span
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
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="visible = false"
                    >取消</el-button
                  >
                  <el-button type="primary" size="mini" @click="paymentConfirm"
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

    <!-- 攻略概述 -->
    <div class="description-section">
      <div class="desc-theme">
        <span style="margin-left: 10px">攻略概述</span>
      </div>
      <p
        style="
          font-family: 'Courier New', Courier, monospace;
          text-indent: 2em;
          margin-top: 20px;
        "
      >
        {{ strategy.description }}
      </p>
    </div>

    <!-- 攻略路线 -->
    <div class="description-section" style="margin-top: 20px">
      <div class="desc-theme">
        <span style="margin-left: 10px">攻略路线</span>
      </div>
      <div class="route-map">
        <el-timeline style="max-width: 700px" class="custom-timeline">
          <el-timeline-item
            v-for="(item, index) in strategy.routes"
            :key="index"
            :timestamp="item.title"
            placement="top"
            icon="el-icon-video-camera"
            size="large"
          >
            <el-card>
              <h3>{{ item.desc }}</h3>
              <div class="route-img">
                <img :src="item.imageUrl" alt="" />
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>
    <!-- 费用说明 -->
    <div class="description-section">
      <div class="desc-theme">
        <span style="margin-left: 20px; margin-bottom: 20px">费用说明</span>
      </div>
      <div class="hotel-policy" v-for="item in strategy.feeInfo" :key="item.id">
        <div class="hotel-policy-title">{{ item.title }}</div>
        <div class="hotel-policy-content">{{ item.desc }}</div>
        <div class="hotel-policy-line"></div>
      </div>
    </div>
    <!-- 购买须知 -->
    <div class="description-section" style="height: 470px">
      <h2 class="desc-theme">
        <span style="margin-left: 20px; margin-bottom: 20px">购买须知</span>
      </h2>
      <h4>服务信息</h4>
      <div class="service-info">
        <p>支付完成后商家最晚会在9个工作小时内（9:00-18:00）确认是否预定成功</p>
        <p>超时未确认系统将自动退款，预计1-7个工作日退还到支付账户</p>
        <p>
          本商品提供出团通知书/确认单，商家最晚在出行日期前1天发送，如未收到请及时与商家联系
        </p>
      </div>
      <h4 style="margin-top: 40px">取消政策</h4>
      <div class="cancel-policy">
        <p>出行日期前7天取消，扣除10%手续费；</p>
        <p>出行日期前3天取消，扣除30%手续费;</p>
        <p>出行日期前1天取消，扣除50%手续费；</p>
        <p>出行日期当天取消，扣除100%手续费；</p>
      </div>
      <h4 style="margin-top: 40px">温馨提示</h4>
      <div class="tips">
        <p>1. 请您在出行前确认好行程，如有需要请提前联系商家沟通</p>
        <p>
          2.
          请您在预订时务必提供准确、完整的信息（姓名、证件号码、联系方式等），以免产生预订错误，影响出行。如因您提供信息错误而造成损失，由您自行承担。
        </p>
      </div>
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
import { addCollect,addHistory } from "@/api/request";
import { getStrategyDetail, createStrategyOrder } from "@/api/strategy";
import {getToken} from '@/utils/auth'

export default {
  name: "AtttractionDetail",
  data() {
    return {
      isCollect: false,
      hasToken: getToken() != null,
      visible: false,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      paymentInfo: {
        strategyId: "",
        number: "",
        price: "",
        appointmentTime: "",
        paymentType: "",
      },
      tags: [
        "一切免年费",
        "标签二公开",
        "标签三",
        "标签四",
        "标签五",
        "标签六",
        "标签七",
      ],
      types: ["success", "warning", "info", "danger", "primary"],
      writeReview: false,
      showModal: false,
      strategy: {
        name: "上海迪士尼乐园三日游+上海野生动物园+上海海洋水族馆",
        rating: "3.8",
        price: "3500",
        startTime: "2022-01-01",
        endTime: "2022-01-03",
        address: "上海市浦东新区川沙新镇申迪西路333号",
        description:
          "上海迪士尼乐园是一个位于中国上海市的著名主题公园，以其独特的童话故事和精彩的表演而闻名。上海迪士尼乐园是一个位于中国上海市的著名主题公园，以其独特的童话故事和精彩的表演而闻名。上海迪士尼乐园是一个位于中国上海市的著名主题公园，以其独特的童话故事和精彩的表演而闻名。上海迪士尼乐园是一个位于中国上海市的著名主题公园，以其独特的童话故事和精彩的表演而闻名。",
        address: "上海市浦东新区川沙新镇申迪西路333号",
        remind: "支付后商家会在9小时内给你拨打电话确认行程",
        sale: 36,
        transport:
          "乘坐飞机或火车到达成都，再转乘长途汽车或包车前往九寨沟景区。",
        images: [
          require("@/imgs/01.jpg"),
          require("@/imgs/02.jpg"),
          require("@/imgs/03.jpg"),
          require("@/imgs/04.jpg"),
        ],
        routes: [
          {
            id: 1,
            title: " 打卡迪士尼乐园",
            desc: "我们可进行拍照留念，购买纪念品，品尝美食，感受童话般的世界",
            imageUrl: require("@/imgs/04.jpg"),
          },
          {
            id: 2,
            title: "第二天 打卡上海野生动物园",
            desc: "我们可进行拍照留念，购买纪念品，品尝美食，感受童话般的世界",
            imageUrl: require("@/imgs/03.jpg"),
          },
        ],
        feeInfo: [
          {
            id: 1,
            title: "门票费",
            desc: "包含沿途所有景点门票",
          },
          {
            id: 2,
            title: "住宿费",
            desc: "包含酒店住宿",
          },

          {
            id: 3,
            title: "交通费",
            desc: "包含往返交通费用",
          },
        ],
      },

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
          value: "选项1",
          label: "微信支付",
        },
        {
          value: "选项2",
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
        itemType: "strategy",
        itemName: "",
        itemImg: "",
        itemDesc: "",
      },
      historyInfo: {
        itemId: "",
        itemType: "strategy",
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
    // 计算总价
    calculateTotalPrice() {
      const adultPrice =
        parseFloat(this.paymentInfo.adultNumber) * this.strategy.adultPrice ||
        0;
      const childrenPrice =
        parseFloat(this.paymentInfo.childrenNumber) *
          this.strategy.childrenPrice || 0;

      // 计算总价
      this.paymentInfo.totalPrice = adultPrice + childrenPrice;
    },
    closeModal() {
      this.showModal = false;
      this.writeReview = false;
      this.paymentInfo = {}; // 重置支付信息
    },
    //确认支付
    async paymentConfirm() {
      // 参数校验
      if (!this.paymentInfo.number || this.paymentInfo.number <= 0) {
        this.$message.error("请输入有效的购买数量");
        return;
      }
      if (!this.paymentInfo.appointmentTime) {
        this.$message.error("请选择有效的预定日期");
        return;
      }
      if (!this.paymentInfo.paymentType) {
        this.$message.error("请选择支付方式");
        return;
      }
      try {
        this.paymentInfo.strategyId = this.$route.params.id;
        const { code } = await createStrategyOrder(this.paymentInfo);
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
    handleRemove(file) {
      console.log(file);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
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
        itemId: this.$route.params.id,
        itemType: "strategy",
        itemName: this.strategy.name,
        itemImg: this.strategy.imgUrl,
        itemDesc: this.strategy.description,
      };
      const { code } = await addCollect(this.collectionInfo);
      if (code == 200) {
        this.isCollect = true;
      }
    },
    // 计算总价
    calculateTotalPrice() {
      this.paymentInfo.price =
        parseFloat(this.paymentInfo.number) * this.strategy.price || 0;
    },
    async addBrowseHistory() {
      this.historyInfo = {
        itemId: this.strategy.id,
        itemName: this.strategy.name,
        itemType: "strategy",
        itemImg: this.strategy.imgUrl,
        itemDesc: this.strategy.description,
      };
      const { code, message } = await addHistory(this.historyInfo);
      if (code == 200) {
      }else {
          this.$message.error(message);
      }
    }
  },
  watch: {
    "paymentInfo.number"(newVal) {
      this.calculateTotalPrice();
    },
  },
  computed: {
    combinedTags() {
      return this.tags.map((name, index) => ({
        name: name,
        type: this.types[index % this.types.length],
      }));
    },
  },
  mounted() {
    getStrategyDetail(this.$route.params.id).then((res) => {
      this.strategy = res.data;
      this.isCollect = res.data.isCollect;
      if (this.hasToken) {
      this.addBrowseHistory();
    }
    });
    
  },
};
</script>

<style scoped>
.strategy-detail {
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

.strategy-name {
  margin-top: 20px;
  font-size: 26px;
  font-weight: bold;
  color: #48ff00;
  font-family: "Pacifico", cursive; /* 使用手写风格字体 */
}
.strategy-rating {
  position: relative;
  margin-top: 10px;
  margin-bottom: -15px;
  margin-left: 30%;
}
.rating {
  color: #ffb300;
  font-size: 20px;
}
.rating-star {
  font-size: 20px;
  margin-top: -25px;
  margin-left: 60px;
  color: #717171;
}
.strategy-desc {
  margin-top: 30px;
}
.desc-name {
  color: #000000;
  float: left;
  font-size: 16px;
}
.desc-content {
  color: #717171;
  margin-left: 90px;
  margin-right: 4px;
  font-size: 16px;
}
.tag {
  display: flex;
  flex-wrap: wrap; /* 允许子元素换行 */
  gap: 10px;
  width: 80%;
  height: 40px;
}
.el-tag {
  margin-bottom: -5px; /* 设置每个标签的底部边距 */
}
.strategy-price {
  margin-top: 30px;
  width: 100%;
  height: 50px;
  background-color: #f5f7fa;
}
.strategy-price .price {
  position: relative;
  display: flex;
  font-size: 24px;
  color: #fd7f00;
  margin-left: 20px;
  top: 10px;
}
.strategy-price span {
  position: relative;
  display: flex;
  font-size: 14px;
  color: #717171;
  margin-left: 220px;
  top: -9px;
}
.btn-group {
  position: relative;
  margin-top: 10px;
  margin-left: 50%;
}
.collection-btn {
  width: 90px;
  float: left;
}
.strategy-btn {
  margin-left: 10%;
}
.description-section {
  position: relative;
  width: 100%;
  font-size: 16px;
  color: #2b2b2b;
  background-color: #fff;
  border-radius: 10px;
  margin-top: 40px;
}
.description-section h4 {
  margin-left: 50px;
}
.desc-theme {
  position: relative;
  display: flex;
  width: 100%;
  height: 30px;
  border-left: 8px solid #7efeeb;
  top: 10px;
  margin-left: 10px;
  font-size: 24px;
  font-weight: 500;
  border-top-left-radius: 2px; /* 左上角圆角 */
  border-top-right-radius: 2px;
  border-bottom-left-radius: 2px; /* 左下角圆角 */
  border-bottom-right-radius: 2px;
}
.description-section p {
  color: #616161;
}
.service-info {
  width: 800px;
  height: auto;
  margin-left: 60px;
}
.service-info p {
  margin-bottom: -10px;
}
.cancel-policy {
  margin-top: 20px;
  margin-left: 50px;
  width: 800px;
  height: auto;
}
.cancel-policy p {
  margin-bottom: -10px;
  margin-left: 10px;
}
.tips {
  margin-top: 20px;
  margin-left: 50px;
  width: 800px;
  height: auto;
}
.tips p {
  margin-bottom: -10px;
  margin-left: 10px;
}

.route-map {
  position: relative;
  display: flex;
  margin-top: 30px;
  margin-left: 180px;
}
/* 深度选择器穿透组件作用域 */
::v-deep .custom-timeline .el-timeline-item__timestamp {
  font-size: 20px !important; /* 调整时间戳字体大小 */
  color: #000000; /* 可选：修改颜色 */
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
  width: 600px;
  height: 700px;
  border-radius: 20px;
}
.route-img img {
  width: 100%;
  height: 100%;
  border-radius: 20px;
  object-fit: cover;
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
  margin-left: 40px;
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