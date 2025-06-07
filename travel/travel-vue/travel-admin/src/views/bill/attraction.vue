<script lang="ls">
import { getAttractionOrderPage,deleteAttractionOrder} from "@/api/order";
export default {
  methods: {
    
    confirmEvent(id){
      return new Promise(()=>{
        console.log(id)
        deleteAttractionOrder(id).then(response=>{
          this.getAll(this.param.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    truncateStr(row, column, cellValue) {
      const maxLength = 12;
      if (!cellValue) return "";
      return cellValue.length > maxLength 
        ? cellValue.slice(0, maxLength) + "..." 
        : cellValue;
    },
    cancelForm(){
      this.param.phone=""
      this.param.itemName=""
    },
    getAll(val) {
      return new Promise(() => {
        this.param.pageNum=val
        getAttractionOrderPage(this.param).then((response) => {
          const { data } = response;
          console.log(data);
          if (!data) {
            return reject("网络异常.");
          }
          this.total = data.data.total;
          
          this.tableData = data.data.records;
        });
      });
    },
    
    search(){
      this.getAll(1)
    }
  },

  mounted() {
    this.getAll(1);
  },
  data() {
    return {
      total: 0,
      tableData: [],
      param: {
        pageNum: 1,
        pageSize: 7,
        phone:"",
        itemName:""
      },
    };
  },
};
</script>
<template>
  <div style="height: 700px;color: #000;">
    <div class="list_info">
      <div style="margin-top: 15px">
        <div
          class="input-suffix"
          style="margin-left: 40px; margin-top: 20px; position: absolute"
        >
          <el-input
            size="small"
            placeholder="按电话查找"
            v-model="param.phone"
            class="input-with-select"
          >
          </el-input>
          <el-input
            size="small"
            placeholder="按景点名称查找"
            v-model="param.itemName"
            class="input-with-select"
            style="margin-left: 10px;"
          ></el-input>
          <el-button
            type="primary"
            size="small"
            style="margin-left: 20px; width: 80px"
            @click="search"
            >搜索</el-button
          >
          <el-button
            type="info"
            size="small"
            style="margin-left: 20px; width: 80px"
            @click="cancelForm"
            >重置</el-button
          >
        </div>
      </div>

      <div class="ppp">
        <el-table :data="tableData" border style="width: 100%" max-height="490">
          <el-table-column label="编号" width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="username"
            label="姓名"
            width="100"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="phone"
            label="电话"
            width="120"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="attractionName"
            label="景点名称"
            width="220"
            align="center"
          ></el-table-column>
          <el-table-column label="详细地址" width="240" align="center">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>{{ scope.row.address }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{
                    truncateStr(scope.row, scope.column, scope.row.address)
                  }}</el-tag>
                </div>
              </el-popover>
            </template></el-table-column
          >
          <el-table-column
            prop="adultNumber"
            label="成人门票/人"
            width="80"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="childrenNumber"
            label="儿童门票/人"
            width="80"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="totalPrice"
            label="总价格/元"
            width="140"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="paymentType"
            label="支付方式"
            width="180"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="appointmentTime"
            label="预约时间"
            width="200"
            align="center"
          ></el-table-column>
          <el-table-column prop="tag" label="订单状态" width="100" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.state === 0" style="background-color: #f56c6c; color: #fff">未使用</el-tag>
              <el-tag v-if="scope.row.state === 1" style="background-color: #67c23a; color: #fff">已使用</el-tag>
              <el-tag v-if="scope.row.state === 2" style="background-color: #67c23a; color: #fff">已过期</el-tag>
              <el-tag v-if="scope.row.state === 4" style="background-color: #f56c6c; color: #fff">已取消</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="下单时间"
            width="220"
            align="center"
          ></el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="160"
            align="center"
          >
            <template slot-scope="scope">
              
              <el-popconfirm
                title="确定要删除吗？"
                @onConfirm="confirmEvent(scope.row.id)"
              >
                <el-button
                  slot="reference"
                  type="danger"
                  size="mini"
                  style="margin-left: 10px"
                  >删除</el-button
                >
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            :page-size="7"
            :page-count="8"
            layout="prev, pager, next"
            @current-change="getAll"
            :total="total"
            style="position: relative; margin-left: 35%"
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.list_info {
  width: 98%;
  height: 98%;
  margin-left: 1%;
  margin-top: 8px;
  border: 1px solid #dddddd;
  background-color: #fff;
  border-radius: 7px;
}

.obtion {
  width: 98%;
  margin-left: 1%;
  margin-top: 10px;
  margin-bottom: 10px;
  border-radius: 7px;
  background-color: #ffffff;
}
.ppp {
  width: 98%;
  margin-top: 100px;
  margin-left: 1%;
  border: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ffffff;
}

.el-select .el-input {
  width: 130px;
}

.input-with-select {
  width: 180px;
  margin-left: 5px;
  height: 4px;
}
</style>

