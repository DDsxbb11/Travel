<script lang="ls">
import { getSupplierByPage, deleteSupplierById,updateSupplierById} from "@/api/request";
export default {
  methods: {
    updateInfo() {
      return new Promise(() => {
      updateSupplierById(this.form).then(response => {
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
        deleteSupplierById(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    },
    updateSupplier(supplier){
      console.log(supplier)
      this.table=true
      this.form=supplier
    },
    cancelForm(){
      this.form=" "
      this.table=false
    },
    getAll(val) {
      return new Promise(() => {
        getSupplierByPage(val, 8,this.input).then((response) => {
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
    addTip(address){
      return address+" "
    },
    search(){
      getSupplierByPage(1, 8,this.input).then((response) => {
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
    getSupplierByPage(1, 8,this.input).then((response) => {
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
      select: "",
      total: 0,
      pageNum: 1,
      tableData: [],
      table: false,
      form: {
        id:"",
        name: "",
        address:"",
        phone:"",
        emil:"",
        legalName:"",
        bank:"",
        bankNumber:"",
        state:0,
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
            placeholder="按供应商名称查找"
            v-model="input"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </div>
      </div>
    </div>
    <div class="list_info">
      <div class="ppp">
        <el-table :data="tableData" border class="table">
          <el-table-column label="编号" width="80" align="center">
            <template slot-scope="scope" >
              <span >{{scope.$index+1}}</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed
            prop="name"
            label="名称"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column label="地址" width="260" align="center">
            <template slot-scope="scope" >
              <el-popover
                placement="bottom"
                width="180"
                trigger="hover"
                :content=addTip(scope.row.address)>
            <el-button type="text" slot="reference">{{scope.row.address.substring(0,10)}}</el-button>
          </el-popover>
            </template>
          </el-table-column>
          <el-table-column
            prop="phone"
            label="电话"
            width="160"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="emil"
            label="邮箱"
            width="170"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="legalName"
            label="法定联系人"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="bank"
            label="开户银行"
            width="140"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="bankNumber"
            label="银行卡号"
            width="220"
            align="center"
          >
          </el-table-column>
          
          <el-table-column label="状态" width="120" align="center">
            <template slot-scope="scope" >
              <el-button type="success" size="small" disabled="false" round>{{ scope.row.state==0? '可用' :'不可用'}}</el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="140"
            align="center"
          >
          <template slot-scope="scope">
              <el-button  type="text" @click="updateSupplier(scope.row)" size="small">编辑</el-button>
              <el-popconfirm
                title="这是一段内容确定删除吗？"
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
        修改供应商
      </h3>
      <div class="demo-drawer__content">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="form.name" style="width: 100px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="form.address" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="form.phone" style="width: 180px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.emil" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="form.legalName" style="width: 100px"></el-input>
          </el-form-item>
          <el-form-item label="开户银行">
            <el-input v-model="form.bank" style="width: 160px"></el-input>
          </el-form-item>
          <el-form-item label="银行卡号">
            <el-input v-model="form.bankNumber" style="width: 240px"></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="form.state">
              <el-radio  label=0>可用</el-radio>
              <el-radio label=1>不可用</el-radio>
            </el-radio-group>
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

