<script>
export default {
  methods: {
    handleClick(row) {
      console.log(row);
    },
    handleSelectionChange(val) {
        this.multipleSelection = val;
      }
  },

  data() {
    return {
      input1: "",
      input2: "",
      input3: "",
      select: "",
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1518 弄",
          zip: 200333,
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1517 弄",
          zip: 200333,
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1519 弄",
          zip: 200333,
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          province: "上海",
          city: "普陀区",
          address: "上海市普陀区金沙江路 1516 弄",
          zip: 200333,
        },
      ],
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
            margin-top: 5px;
            position: absolute;
            color: #303133;
          "
        >
          姓名：
          <el-input
            placeholder="按姓名查找"
            v-model="input3"
            class="input-with-select"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              style="background-color: #1acf7d"
            ></el-button>
          </el-input>
        </div>
      </div>
    </div>
    <div class="opt">
      <el-button type="success" round size="small" style="margin-top: 4px;margin-left: 20px;">新增</el-button>
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
          <el-table-column type="selection" width="60" align="center"> </el-table-column>
          <el-table-column
            fixed
            prop="name"
            label="姓名"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="date" label="编号" width="80" align="center">
          </el-table-column>
          <el-table-column
            prop="province"
            label="账号"
            width="120"
            align="center"
          >
          </el-table-column
          ><el-table-column
            prop="province"
            label="密码"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="province"
            label="性别"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="province"
            label="电话"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column label="头像" width="120" align="center">
            <img
              src="../../imgs/header/header.jpg"
              style="width: 40px; height: 40px"
            />
          </el-table-column>
          <el-table-column
            prop="city"
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
          <el-table-column prop="zip" label="职能" width="120" align="center">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="100"
            align="center"
          >
            <template slot-scope="scope">
              <el-button
                @click="handleClick(scope.row)"
                type="text"
                size="small"
                >编辑
              </el-button>
              <el-button type="text" size="small" style="color: red"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            :page-size="10"
            :pager-count="8"
            layout="prev, pager, next"
            :total="1000"
            style="top: 20px; position: relative; margin-left: 35%"
          >
          </el-pagination>
        </div>
      </div>
    </div>
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

