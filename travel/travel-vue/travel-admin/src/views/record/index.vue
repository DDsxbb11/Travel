<script lang="ls">
import {getRecordByPage,deleteRecord} from "@/api/record";

export default {
  methods: {
    confirmEvent(id){
      return new Promise(()=>{
        deleteRecord(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    handleStatusChange(row) {
      console.log(row);
    },
    
    cancelForm(){
      this.param.recordName=""
      this.param.username=""
      this.param.phone=""
    },

    truncateStr(row, column, cellValue) {
      const maxLength = 12;
      if (!cellValue) return "";
      return cellValue.length > maxLength 
        ? cellValue.slice(0, maxLength) + "..." 
        : cellValue;
    },
    getAll(val) {
      this.param.pageNum = val;
      return new Promise(() => {
        getRecordByPage(this.param).then((response) => {
          const { data } = response;
          if (!data) {
            return reject("网络异常.");
          }
          this.total = data.total;
          this.tableData = data.records;
          console.log(this.tableData);
        });
      });
    },
    
    search(){
      this.getAll(1)
    }
  },

  mounted() {
    this.getAll(1);
    getAllProvince().then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.provinceList = data;
      console.log(this.provinceList);
    });
  },
  data() {
    return {
      total: 0,
      tableData: [],
      provinceList: [],
      param: {
        pageNum: 1,
        pageSize: 5,
        recordName: "",
        username: "",
        phone:""
      },
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
            placeholder="按日记名称查找"
            v-model="param.recordName"
            class="input-with-select"
          >
          </el-input>
          <el-input
            size="small"
            placeholder="按作者名称查找"
            v-model="param.username"
            class="input-with-select"
          >
          </el-input>
          <el-input
            size="small"
            placeholder="按作者电话查找"
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
            label="作者"
            width="180"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="nickname"
            label="昵称"
            width="120"
            align="center"
          ></el-table-column>
          
          <el-table-column label="标题" width="240" align="center">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>{{ scope.row.name }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{
                    truncateStr(scope.row, scope.column, scope.row.name)
                  }}</el-tag>
                </div>
              </el-popover>
            </template></el-table-column
          >
          <el-table-column label="主图" width="240" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.imgUrl" style="width: 100px; height: 70px;">
            </template>
          </el-table-column>
          <el-table-column label="内容" width="240" align="center">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>{{ scope.row.name }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium" style="background-color: #67C23A;color: #fff;">{{
                    truncateStr(scope.row, scope.column, scope.row.name)
                  }}</el-tag>
                </div>
              </el-popover>
            </template></el-table-column>
          <el-table-column
            prop="phone"
            label="电话"
            width="180"
            align="center"
          >  
          </el-table-column>
          
          <el-table-column
            prop="createTime"
            label="发布时间"
            width="180"
            align="center"
          ></el-table-column>
          
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

