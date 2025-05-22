<script>
import { getSellRecordByPage,updateSellRecordById} from "@/api/request";
import zhCn from 'element-ui/lib/locale/lang/zh-CN';

export default {
  methods: {
    updateInfo() {
      return new Promise(() => {
      updateSellRecordById(this.form).then(response => {
        this.table=false
        this.getAll(this.pageNum)
        this.$message({
          message: '修改成功',
          type: 'success'
        })
      })
    })
    }, 
    updateSellRecord(sellRecord){
      this.table=true
      this.form=sellRecord
      this.form.sellTime=this.timeHandle(sellRecord.sellTime)
    },
    cancelForm(){
      this.form=" "
      this.table=false
    },
    getAll(val) {
      return new Promise(() => {
        getSellRecordByPage(val, 7,this.input,this.dateValue).then((response) => {
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
      let arr=t.split("T");
      return arr[0]+" "+" "+arr[1].split(".")[0];
    },
    search(){
      getSellRecordByPage(1, 7,this.input,this.dateValue).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.total = data.total;
      this.tableData = data.records;
    });
    },
    addTip(val){
      return val+" "
    },
    goodsTip(val){
      let goods=val.split(";")
      goods.splice(goods.length-1,1)
      goods.forEach(item => {
        this.gridData.push(item)
      });
    },
    
  },

  mounted() {
    getSellRecordByPage(1, 7,this.input,this.dateValue).then((response) => {
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
      locale:zhCn,
      input: "",
      dateValue:'',
      total: 0,
      pageNum: 1,
      tableData: [],
      table: false,
      gridData:[],
      form: {
        id:"",
        uuid: "",
        sellTime:"",
        salesperson:"",
        description:"",
      },
      shopping: {
        name:"",
        number:"",
        price:"",
      },
      s:'',
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
            <el-config-provider :locale="locale">
            <el-date-picker
              size="small"
              v-model="dateValue"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </el-config-provider>
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
        <el-table :data="tableData" border style="width: 100%" >
          <el-table-column label="编号" width="70" align="center">
            <template slot-scope="scope" >
              <span >{{scope.$index+1}}</span>
            </template>
          </el-table-column>
          <el-table-column label="流水号" width="180" align="center">
            <template slot-scope="scope" >
              <el-popover
                placement="bottom"
                width="180"
                trigger="hover"
                :content=addTip(scope.row.uuid)>
                 <el-button type="text" slot="reference">{{scope.row.uuid.substring(0,10)}}</el-button>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="商品" width="180" align="center">
            <template slot-scope="scope" >
              <el-popover
                placement="bottom"
                width="220"
                trigger="click"
                :hide="gridData=[]"
                :show="goodsTip(scope.row.goods)"
                >
                  <el-table :data="gridData">
                    <el-table-column label="名称——数量——价格" width="180" align="center">
                      <template slot-scope="scope" >
                        <span style="margin-left: 10px">{{ gridData[scope.$index] }}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                 <el-button type="text" slot="reference" >查看详情</el-button>
              </el-popover>
            </template>
          </el-table-column>  
          <el-table-column label="金额" width="80" align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.price }}</span>
            </template>
          </el-table-column>
          <el-table-column label="支付类型" width="120" align="center">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.payType }}</span>
            </template>
          </el-table-column>
          <el-table-column label="销售时间" width="214" align="center">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ timeHandle(scope.row.sellTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="售货员" width="80" align="center"> 
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.salesperson }}</span>
            </template>
          </el-table-column>
          <el-table-column label="备注" width="140" align="center">
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
                @click="updateSellRecord(scope.row)"
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
          <el-form-item label="销售员">
            <el-input v-model="form.sellTime" style="width: 160px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="售货员">
            <el-input v-model="form.salesperson" style="width: 160px" :disabled="true"></el-input>
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

