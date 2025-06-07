<template>
  <div class="attraction-component">
    <el-table :data="data" style="width: 100%" height="250">
      <el-table-column
        fixed
        prop="appointmentTime"
        label="预约日期"
        width="150"
      >
      </el-table-column>
      <el-table-column prop="name" label="景点名称" width="180">
      </el-table-column>
      <el-table-column prop="theme" label="主题" width="120"> </el-table-column>
      <el-table-column prop="province" label="省份" width="120">
      </el-table-column>
      <el-table-column prop="city" label="城市" width="120"> </el-table-column>
      <el-table-column
        prop="address"
        label="景点地址"
        width="300"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="adultNumber"
        label="成人票数"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="childrenNumber"
        label="儿童票数"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="totalPrice"
        label="总价 / 元"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="paymentType"
        label="支付方式"
        width="150"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="支付日期"
        width="200"
        align="center"
      >
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.state === 0"
            type="info"
            effect="dark"
            align="center"
          >
            未使用
          </el-tag>
          <el-tag
            v-if="scope.row.state === 1"
            type="success"
            effect="dark"
            align="center"
          >
            已使用
          </el-tag>
          <el-tag
            v-if="scope.row.state === 2"
            type="info"
            effect="dark"
            align="center"
          >
            过期
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button
            @click="handleClick(scope.row.attractionId)"
            type="success"
            size="small"
            >查看</el-button
          >
          <el-button
            v-if="scope.row.state === 0"
            type="warning"
            size="small"
            @click="open(scope.row.id)"
            >退款</el-button
          >
          <el-button
            v-else
            type="danger"
            size="small"
            @click="deleteOrder(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 自定义退款原因选择器 -->
    <el-dialog :visible.sync="refundDialogVisible" title="退款申请">
      <el-form>
        <el-form-item label="退款原因">
          <el-select v-model="reason" placeholder="请选择退款原因">
            <el-option
              v-for="item in reasons"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="reason === 'other'" label="其他原因">
          <el-input
            v-model="otherReason"
            placeholder="请输入其他退款原因"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="refundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitRefund">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
  
<script>
import { delOrder ,refund} from "@/api/request";

export default {
  name: "AttractionComponent",
  props: {
    data: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      reasons: [
        { label: "个人行程变更", value: "个人行程变更" },
        { label: "健康或安全问题", value: "健康或安全问题" },
        { label: "门票信息错误", value: "门票信息错误" },
        { label: "其他", value: "其他" },
      ],
      reason: null,
      otherReason: "",
      refundDialogVisible: false,
      currentOrderId: null,
    };
  },
  methods: {
    handleClick(id) {
      this.$router.push(`/attraction/detail/${id}`);
    },
    async deleteOrder(id) {
      const { code, message } = await delOrder("attraction", id);
      if (code === 200) {
        this.$message({
          message: "删除成功",
          type: "success",
        });
      } else {
        this.$message.error(message);
      }
    },
    open(orderId) {
      this.currentOrderId = orderId;
      this.refundDialogVisible = true;
    },
    async submitRefund() {
      if (this.reason === "other" && !this.otherReason) {
        this.$message.error("请输入其他退款原因");
        return;
      }
      let refundReason =
        this.reason === "other"
          ? this.otherReason
          : this.reason;
      const { code, message } = await refund("attraction", {
        id: this.currentOrderId,
        refundReason,
      });
      this.$message({
        type: "success",
        message: `您的退款申请已提交，退款原因: ${refundReason}，系统会在5个工作日给出结果`,
      });
      this.refundDialogVisible = false;
      this.reason = null;
      this.otherReason = "";
    },
  },
  mounted() {
    console.log("AttractionComponent data:", this.data);
  },
};
</script>
  
  <style scoped>
</style>