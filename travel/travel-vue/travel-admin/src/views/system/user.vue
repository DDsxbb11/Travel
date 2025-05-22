<script>
import { getUserByPage, deleteUserById,updateUserById} from "@/api/request";
export default {
  methods: {
    updateInfo() {
      return new Promise(() => {
      updateUserById(this.form).then(response => {
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
        deleteUserById(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    
    updateUser(user){
      this.table=true
      this.form=user
    },
    cancelForm(){
      this.form=" "
      this.table=false
    },
    getAll(val) {
      return new Promise(() => {
        getUserByPage(val, 7,this.input).then((response) => {
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
      getUserByPage(1, 7,this.input).then((response) => {
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
    getUserByPage(1, 7,this.input).then((response) => {
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
      form: {},
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
          size="small"
          style="
            margin-left: 50px;
            margin-top: 5px;
            position: absolute;
            color: #303133;
          "
        >
          姓名：
          <el-input
            placeholder="按姓名查找"
            v-model="input"
            class="input-with-select"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              style="background-color: #1acf7d"
              @click="search"
            ></el-button>
          </el-input>
        </div>
      </div>
    </div>
    <div class="opt">
      <el-button type="danger" round size="small" style="margin-top: 4px;margin-left: 20px;">批量删除</el-button>
    </div>
    <div class="list_info">
      <div class="ppp">
        <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange"
          border
        >
          <el-table-column type="selection" width="60" align="center">
          </el-table-column>
          <el-table-column
            fixed
            prop="nickname"
            label="姓名"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="username"
            label="账号"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="gender"
            label="性别"
            width="80"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="phone"
            label="电话"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column label="图片" width="160" align="center">
            <template slot-scope="scope" >
              <img
              :src=scope.row.header
              style="width: 40px; height: 40px"
            />
            </template>
          </el-table-column>
          <el-table-column
            prop="idcard"
            label="身份证号"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="入职时间"
            width="220"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="role" label="职称" width="120" align="center">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              <el-popconfirm
                title="确定删除吗？"
                @onConfirm="confirmEvent(scope.row.id)"
              >
                <el-button
                  slot="reference"
                  type="danger"
                  size="small"
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
            style="top: 20px; position: relative; margin-left: 35%"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!-- 抽屉 -->
    <el-drawer :visible.sync="table" direction="rtl" size="50%">
      <h3 style="margin-top: -40px; margin-left: 30px; color: #909399">
        修改商品售价
      </h3>
      <div class="demo-drawer__content">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="商品名称">
            <el-input v-model="form.name" style="width: 200px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="商品售价">
            <el-input v-model="form.price" style="width: 80px"></el-input>&nbsp;&nbsp;&nbsp;元
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

.opt {
  width: 98%;
  height: 40px;
  margin-left: 1%;
  margin-top: 8px;
  border-right: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ebedf0;
  position: relative;
}

.list_info {
  width: 98%;
  height: 650px;
  margin-left: 1%;
  margin-top: 8px;
  border-right: 1px solid #dddddd;
  border-radius: 7px;
  background-color: #ebedf0;
  position: relative;
}

.table {
  width: 100%;
  height: 550px;
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
  height: 10px;
}
</style>

