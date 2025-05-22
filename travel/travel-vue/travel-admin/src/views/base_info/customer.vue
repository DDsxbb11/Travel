<script lang="ls">
import { getCustomerByPage,deleteCustomerById,updateCustomerById} from "@/api/request";
export default {
  methods: {
    updateInfo() {
      return new Promise(() => {
        updateCustomerById(this.form).then(response => {
        this.table=false
        this.getAll(this.pageNum)
        this.$message({
          message: '修改成功',
          type: 'success'
        })
      })
    })
    },
    confirmEvent(id){
      return new Promise(()=>{
        deleteCustomerById(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    
    updateCustomer(customer){
      this.table=true
      this.form.id=customer.id
      this.form.name=customer.name
      this.form.phone=customer.phone
    },
    cancelForm(){
      this.form=" "
      this.table=false
    },
    getAll(val) {
      return new Promise(() => {
        getCustomerByPage(val, 9,this.input).then((response) => {
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
    loadImg(url){
      return require(url)
    },
    search(){
      getCustomerByPage(1, 9,this.input).then((response) => {
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
    getCustomerByPage(1, 9,this.input).then((response) => {
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
      total: 0,
      pageNum: 1,
      tableData: [],
      table: false,
      form: {
        id:"",
        name: "",
        phone:"",
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
            margin-top: -5px;
            position: absolute;
            color: #303133;
          "
        >
          <el-input
            size="small"
            placeholder="按商品名称查找"
            v-model="input"
            class="input-with-select"
          >
          </el-input>
          <el-button type="primary" size="small" plain style="margin-left: 10px" @click="search"
            >搜索</el-button
          >
        </div>
      </div>
    </div>
    <div class="list_info">
      <div class="ppp">
        <el-table :data="tableData" border  style="width: 100%">
          <el-table-column label="编号" width="100" align="center">
            <template slot-scope="scope" >
              <span >{{scope.$index+1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="姓名" width="240" align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column label="电话" width="260" align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.phone }}</span>
            </template>
          </el-table-column>  
          <el-table-column label="积分" width="180" align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.integral }}</span>
            </template>
          </el-table-column>
          <el-table-column label="注册时间" width="280" align="center" >
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ timeHandle(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="updateCustomer(scope.row)" size="small"
                >修改</el-button
              >
              <el-popconfirm
                title="确定要删除吗？"
                @onConfirm="confirmEvent(scope.row.id)"
              >
                <el-button
                  slot="reference"
                  type="text"
                  size="small"
                  style="margin-left: 10px; color: red"
                  >删除</el-button
                >
                <!-- <el-button slot="reference">删除</el-button> -->
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
            style="top: 20px; position: relative; margin-left: 35%"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!-- 抽屉 -->
    <el-drawer :visible.sync="table" direction="rtl" size="50%">
      <h3 style="margin-top: -40px; margin-left: 30px; color: #909399">
        客户信息
      </h3>
      <div class="demo-drawer__content">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="客户姓名">
            <el-input v-model="form.name" style="width: 160px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="form.phone" style="width: 200px"></el-input>
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
  border: 1px solid #e4e7ed;
  border-radius: 7px;
  background-color: #ebedf0;
  position: relative;
}

.list_info {
  width: 98%;
  height: 700px;
  margin-left: 1%;
  margin-top: 8px;
  border: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ebedf0;
  position: relative;
}

.table {
  width: 100%;
  height: 600px;
  border: 1px solid #dddddd;
  border-radius: 7px;
}

.ppp {
  width: 98%;
  height: 630px;
  margin-left: 1%;
  top: 10px;
  border: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ffffff;
  position: absolute;
}

.pagination {
  width: 100%;
  height: 99px;
  margin-top: -20px;
  border: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ffffff;
}

.el-select .el-input {
  width: 130px;
}

.input-with-select {
  background-color: #fff;
  width: 180px;
  margin-left: 5px;
  height: 4px;
}
</style>

