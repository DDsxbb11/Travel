<script>
import { getReturnRecordByPage,updateReturnRecordById} from "@/api/request";
export default {
  methods: {
    updateInfo() {
      return new Promise(() => {
      updateReturnRecordById(this.form).then(response => {
        this.table=false
        this.getAll(this.pageNum)
        this.$message({
          message: '修改成功',
          type: 'success'
        })
      })
    })
    }, 
    updateRetuenRecord(returnRecord){
      this.table=true
      this.form=returnRecord
      this.form.returnTime=this.timeHandle(returnRecord.returnTime)
    },
    cancelForm(){
      this.form=" "
      this.table=false
    },
    getAll(val) {
      return new Promise(() => {
        getReturnRecordByPage(val, 7,this.input,this.dateValue).then((response) => {
          this.pageNum=val
          const { data } = response;
          if (!data) {
            return reject("网络异常.");
          }
          this.total = data.total;
          this.tableData = data.records;
        });
      });
    },
    timeHandle(t){
      return t.split("T")[0];
    },
    search(){
      getReturnRecordByPage(1, 7,this.input,this.dateValue).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.total = data.total;
      this.tableData = data.records;
    });
    }
  },

  mounted() {
    getReturnRecordByPage(1, 7,this.input,this.dateValue).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.total = data.total;
      this.tableData = data.records;
    });
  },
  data() {
    return {
      input: "",
      dateValue:'',
      total: 0,
      pageNum: 1,
      tableData: [],
      table: false,
      form: {
        id:"",
        uuid: "",
        returnTime:"",
        operator:"",
        description:"",
      },
    };
  },
};
</script>
<template>
  <div>
    <div class="search">
      <div style="margin-top: 15px">
        <div
          class="input-suffix"
          style="
            margin-left: 50px;
            margin-top: 8px;
            position: absolute;
            color: #303133;
          "
        >
          <el-input
            size="small"
            placeholder="按售货员姓名查找"
            v-model="input"
            class="input-with-select"
          >
          </el-input>
          <div style="left:6px;position: relative;float:inline-end;">
            <el-date-picker
              size="small"
              v-model="dateValue"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </div>
          <el-button type="primary" size="small" plain style="right:-80px;position: absolute;" @click="search"
          >搜索
          </el-button
          >
        </div>
      </div>
    </div>
    <div class="list_info">
      <div class="ppp">
        <el-table :data="tableData" border style="width: 100%"  align="center">
          <el-table-column label="编号" width="70">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.$index+1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="流水号" width="180"  align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.uuid }}</span>
            </template>
          </el-table-column>
          <el-table-column label="商品" width="150"  align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.goods }}</span>
            </template>
          </el-table-column> 
          <el-table-column label="数量" width="80"  align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.number }}</span>
            </template>
          </el-table-column> 
          <el-table-column label="金额" width="90"  align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.price }}</span>
            </template>
          </el-table-column>
          <el-table-column label="退货时间" width="153"  align="center">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ timeHandle(scope.row.returnTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作员" width="100"  align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.operator }}</span>
            </template>
          </el-table-column>
          <el-table-column label="备注" width="180"  align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.description }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120" align="center">
            <template slot-scope="scope">
              <el-button
               type="warning" 
               plain
                size="mini"
                @click="updateRetuenRecord(scope.row)"
                >修改备注</el-button
              >
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
            style="top: 20px; position: relative; margin-left: 35%"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!-- 抽屉 -->
    <el-drawer :visible.sync="table" direction="rtl" size="50%">
      <h3 style="margin-top: -40px; margin-left: 30px; color: #909399">
        进货记录
      </h3>
      <div class="demo-drawer__content">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="流水号">
            <el-input v-model="form.uuid" style="width: 200px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="退货时间">
            <el-input v-model="form.returnTime" style="width: 160px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="操作员">
            <el-input v-model="form.operator" style="width: 160px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.description" style="width: 260px" type="textarea"></el-input>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer" style="margin-left: 300px">
          <el-button @click="cancelForm">取 消</el-button>
          <el-button type="primary" @click="updateInfo">提交</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<style scoped>
.search {
  width: 98%;
  height: 50px;
  margin-left: 1%;
  margin-top: 20px;
  border-right: 1px solid #e4e7ed;
  border-radius: 7px;
  background-color: #ebedf0;
  position: relative;
}

.list_info {
  width: 98%;
  height: 700px;
  margin-left: 1%;
  margin-top: 8px;
  border-right: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ebedf0;
  position: relative;
}

.table {
  width: 100%;
  height: 600px;
  border-right: 1px solid #dddddd;
  border-radius: 7px;
}

.ppp {
  width: 98%;
  height: 630px;
  margin-left: 1%;
  top: 10px;
  border-right: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ffffff;
  position: absolute;
}

.pagination {
  width: 100%;
  height: 99px;
  margin-top: -20px;
  border-right: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ffffff;
}

.el-select .el-input {
  width: 130px;
}

.input-with-select {
  background-color: #fff;
  width: 200px;
  height: 4px;
  margin-left: 5px;
}
</style>

