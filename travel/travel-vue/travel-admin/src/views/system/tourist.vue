attraction<script lang="ls">
import {getTouristByPage,deleteTourist,updateTouristStatus} from "@/api/tourist";

export default {
  methods: {
    confirmEvent(id){
      return new Promise(()=>{
        deleteTourist(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    handleStatusChange(row) {
      const data = {
        id: row.id,
        status: row.status==1?0:1,
      };
      return new Promise(()=>{
        updateTouristStatus(data).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '修改成功',
          type: 'success'
        });
        })
      })
    },
    cancelForm(){
      this.param.username=""
      this.param.phone=""
      this.getAll(1)
    },
    getAll(val) {
      this.param.pageNum = val;
      return new Promise(() => {
        getTouristByPage(this.param).then((response) => {
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
      pageNum: 1,
      tableData: [],
      param: {
        pageNum: 1,
        pageSize: 5,
        username: "",
        phone: "",
      }
    };
  },
};
</script>
<template>
  <div style="height: 700px; color: #000">
    <div class="list_info">
      <div style="margin-top: 15px">
        <div
          class="input-suffix"
          style="margin-left: 40px; margin-top: 20px; position: absolute"
        >
          <el-input
            size="small"
            placeholder="按游客姓名查找"
            v-model="param.username"
            class="input-with-select"
          >
          </el-input>
          <el-input
            size="small"
            placeholder="按电话查找"
            v-model="param.phone"
            class="input-with-select"
          >
          </el-input>

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
          <el-table-column label="编号" width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="username"
            label="姓名"
            width="120"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="nickname"
            label="昵称"
            width="120"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="phone"
            label="电话"
            width="120"
            align="center"
          ></el-table-column>
          <el-table-column label="头像" width="240" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.avatarUrl" style="width: 100px; height: 70px;">
            </template>
          </el-table-column>
          <el-table-column
            prop="email"
            label="邮箱"
            width="180"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="注册时间"
            width="180"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="isOnline"
            label="状态"
            width="100"
            align="center"
          >
            <template #default="{ row }">
              <el-switch
                v-model="row.status==1"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @change="handleStatusChange(row)"
              ></el-switch>
            </template>
          </el-table-column>
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
            :page-size=this.param.pageSize
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

