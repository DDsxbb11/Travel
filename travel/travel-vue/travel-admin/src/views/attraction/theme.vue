attraction<script lang="ls">
import {getThemeByPage,deleteTheme,addOrUpdateTheme} from "@/api/theme";

export default {
  methods: {
    confirmEvent(id){
      return new Promise(()=>{
        deleteTheme(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    
    async addTheme() {
      this.$prompt('请输入主题名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: 'dzasda',
        //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        inputErrorMessage: '请输入2-6个字符'
      }).then(({ value }) => {
        addOrUpdateTheme({name:value}).then(response=>{
          this.getAll(this.pageNum)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消添加'
        });       
      });
    },
    updateTheme(id,name) {
      this.$prompt('请输入主题名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: name,
        //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        inputErrorMessage: '请输入2-6个字符'
      }).then(({ value }) => {
        addOrUpdateTheme({id:id,name:value}).then(response=>{
          this.getAll(this.pageNum)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消添加'
        });       
      });
    },
    cancelForm(){
      this.param.name=""
    },
    getAll(val) {
      return new Promise(() => {
        getThemeByPage(val, 7,this.param).then((response) => {
          this.pageNum=val
          const { data } = response;
          console.log(data);
          if (!data) {
            return reject("网络异常.");
          }
          this.total = data.data.total;
          this.tableData = data.data.records;
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
  },
  data() {
    return {
      total: 0,
      pageNum: 1,
      tableData: [],
      param: {
        name: "",
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
            placeholder="按主题名称查找"
            v-model="param.name"
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
        <div class="obtion">
          <el-button
            type="primary"
            size="small"
            class="el-icon-plus"
            @click="addTheme"
          >
            添加主题</el-button
          >
        </div>
        <el-table :data="tableData" border style="width: 100%" max-height="490">
          <el-table-column label="编号" width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="name"
            label="主题名称"
            width="380"
            align="center"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            label="创建时间"
            width="380"
            align="center"
          ></el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="260"
            align="center"
          >
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="mini"
                @click="updateTheme(scope.row.id, scope.row.name)"
                >修改</el-button
              >
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

