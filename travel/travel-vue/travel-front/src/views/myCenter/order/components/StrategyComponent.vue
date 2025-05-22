<template>
  <div class="Strategy-component">
    <el-table :data="data" style="width: 100%" height="250">
      <el-table-column
        fixed
        prop="appointmentTime"
        label="预定日期"
        width="150"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="name" label="攻略名称" width="180" align="center">
      </el-table-column>
      <el-table-column prop="province" label="省份" width="120" align="center">
      </el-table-column>
      <el-table-column
        prop="address"
        label="出发地址"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="number" label="数量" width="120" align="center">
      </el-table-column>
      <el-table-column prop="price" label="支付金额" width="120" align="center">
      </el-table-column>
      <el-table-column
        prop="paymentType"
        label="支付方式"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="支付日期"
        width="180"
        align="center"
      >
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state === 0" type="info" effect="dark">
            未使用
          </el-tag>
          <el-tag v-if="scope.row.state === 1" type="success" effect="dark">
            已使用
          </el-tag>
          <el-tag v-if="scope.row.state === 2" type="info" effect="dark">
            过期
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button
            @click="handleClick(scope.row.strategyId)"
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
          <el-select v-model="selectedReason" placeholder="请选择退款原因">
            <el-option
              v-for="item in reasons"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="selectedReason === 'other'" label="其他原因">
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
export default {
  name: "StrategyComponent",
  props: {
    data: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      reasons: [
        { label: "商品质量问题", value: "quality" },
        { label: "未按约定时间发货", value: "shipping" },
        { label: "商品与描述不符", value: "description" },
        { label: "其他", value: "other" },
      ],
      selectedReason: null,
      otherReason: "",
      refundDialogVisible: false,
      currentOrderId: null,
    };
  },
  methods: {
    handleClick(id) {
      this.$router.push(`/strategy/detail/${id}`);
    },
    async deleteOrder(id) {
      const { code, message } = await delOrder("strategy", id);
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
    submitRefund() {
      if (this.selectedReason === "other" && !this.otherReason) {
        this.$message.error("请输入其他退款原因");
        return;
      }
      let refundReason =
        this.selectedReason === "other"
          ? this.otherReason
          : this.selectedReason;
      this.$message({
        type: "success",
        message: `您的退款原因已提交: ${refundReason}`,
      });
      this.refundDialogVisible = false;
      this.selectedReason = null;
      this.otherReason = "";
    },
  },
};
</script>

<style scoped>

</style>