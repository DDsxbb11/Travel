<script lang="ls">
import { getAllGoods,addSellRecord,addCustomer} from "@/api/request";
export default {
  methods: {
    delShop(val,price){
      this.price-=price
      this.form.buyShop.splice(val,1)
    },
    //搜索过滤
    querySearch(queryString, cb) {
      var goods = this.goods;
      var results = queryString ? goods.filter(this.createFilter(queryString)) : goods;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (good) => {
        return (good.name.indexOf(queryString) === 0);
      };
    },
    handleSelect(item) {
      item.stock=1
      this.form.price+=item.price
      this.form.buyShop.push(item)
    },
    onSubmit(){
        this.$prompt('请输入邮箱', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
        }).then(({ value }) => {
          this.form.phone=value
          addSellRecord(this.form).then(response => {
            this.$message({
              message: '结算成功',
              type: 'success'
            })
          })
        });
    },
    registCustomer(){
      addCustomer(this.customerForm).then(response=>{
        this.table=false
        this.$message({
          message:'注册成功',
          type:'success'
        })
      })
    },
    cancelForm(){
      console.log("===================")
      this.customerForm=''
      this.table=false
    }
  },

  mounted() {
    getAllGoods().then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.goods=data
    });
  },
  data() {
    return {
      state: "",
      goods:[],
      tableData: [],
      table: false,
      form:{
        phone:'',
        price:0,
        buyShop:[],
      },
      customerForm:{
        name:'',
        phone:''
      }
    };
  },
};
</script>
<template>
  <div class="container">
    <div class="count">
      <div class="num" style="margin-left: 6%">
        <div class="icon">
          <img
            src="../../imgs/icon/money.jpg"
            style="width: 65px; height: 65px; border-radius: 50px"
          />
        </div>
        <div class="fout">
          <h5 style="margin-top: 8px">销售金额:</h5>
          <br/>
          <i style="top: -34px; position: relative; left: 25px">1999元</i>
        </div>
      </div>
      <div class="num">
        <div class="icon">
          <img
            src="../../imgs/icon/purchase.jpg"
            style="width: 65px; height: 65px; border-radius: 50px"
          />
        </div>
        <div class="fout">
          <h5 style="margin-top: 8px">采购订单:</h5>
          <br/>
          <i style="top: -34px; position: relative; left: 25px">1999元</i>
        </div>
      </div>
      <div class="num">
        <div class="icon">
          <img
            src="../../imgs/icon/sale.jpg"
            style="width: 65px; height: 65px; border-radius: 50px"
          />
        </div>
        <div class="fout">
          <h5 style="margin-top: 8px">销售订单:</h5>
          <br/>
          <i style="top: -34px; position: relative; left: 25px">1999元</i>
        </div>
      </div>
    </div>
    <div class="main-border">
      <div class="goods-list" >
        <el-autocomplete
          popper-class="my-autocomplete"
          v-model="state"
          :fetch-suggestions="querySearch"
          placeholder="请输入商品名称"
          @select="handleSelect"
          style="width: 400px;"
          >
          <template slot-scope="{ item }">
            <div class="name">{{ item.name}}</div>
          </template>
        </el-autocomplete>
          <el-table
            :data="form.buyShop"
            height="530"
            style="width: 100%">
            <el-table-column label="编号" width="55" align="center">
              <template slot-scope="scope" >
                <span >{{scope.$index+1}}</span>
              </template>
            </el-table-column>
            <el-table-column label="名称" width="240" align="center">
              <template slot-scope="scope" >
                <span >{{scope.row.name}}</span>
              </template>
            </el-table-column>
            <el-table-column label="数量" width="140" align="center">
              <template slot-scope="scope" >
                <el-button type="text" size="mini" icon="el-icon-minus" @click="minus(scope.row.stock)"></el-button >
                  <span style="margin-left: 10px;">{{scope.row.stock}}</span>
                <el-button type="text" size="mini" icon="el-icon-plus" style="margin-left: 10px;"></el-button >
              </template>
            </el-table-column>
            <el-table-column label="价格" width="140" align="center">
              <template slot-scope="scope" >
                  <span >{{scope.row.price}}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
              <template slot-scope="scope" >
                <el-button type="danger" size="mini" icon="el-icon-delete" circle @click="delShop(scope.$index,scope.row.price)" ></el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="button-frame">
            <div class="money">
              <div style="margin-top: 10px;font-family: '楷书';margin-left: 20px;font-size: 20px;position: absolute;">总 额 :</div>
              <div style="margin-top: 13px;color: aqua;position: fixed;margin-left: 100px;font-size: 18px;position: absolute">{{ form.price.toFixed(2) }}元</div>
            </div>
            <el-button type="success" :showCancelButton="false" @click="onSubmit" style="margin-left: 550px;width: 150px;margin-top: -40px;position: absolute;">结算</el-button>
          </div>
        </div>
        <div class="customer-register">
          <el-button type="success" plain @click="table=true">会员注册</el-button>
        </div>
      </div>
      <!-- 抽屉 -->
    <el-drawer :visible.sync="table" direction="rtl" size="48%">
      <h3 style="margin-top: -40px; margin-left: 30px; color: #909399">
        会员注册
      </h3>
      <div class="demo-drawer__content">
        <el-form ref="form" :model="customerForm" label-width="120px" style="margin-top: 30px;">
          <el-form-item label="客户昵称">
            <el-input v-model="customerForm.name" style="width: 160px"/>
          </el-form-item>
          <el-form-item label="电话号码">
            <el-input v-model="customerForm.phone" style="width: 260px"/>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer" style="margin-left: 150px">
          <el-button @click="cancelForm()">取 消</el-button>
          <el-button type="primary" @click="registCustomer()">注册</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<style scoped>
.customer-register{
  margin-left: -98px;
}
.money{
  width: 200px;
  height: 40px;
  margin-left: 100px;
  /* border: 1px solid #DCDFE6; */
  
}
.input-with-select{
  float: left;
  margin-left: 0px;
  width: 300px;
  height: 30px;
  position: fixed;
}
.button-frame{
  width: 700px;
  height: 40px;
  margin-top: 0px;
  background-color: #FFFFFF;
  position: relative;
 
}

.regist{
  width:200px ;
}
.goods-list{
  width:700px;
  height: 600px;
  margin-left: 80px;
  border-radius: 20px;
}

.main-border{
  width: 98%;
  height: 600px;
  border-radius: 10px;
  left: 20px;
  margin-top: 30px;
  display: flex;
}
.container {
  width: 98%;
  height: 770px;
  background-image: url('../../imgs/R-C.jpg');
  background-size: cover;
}

.count {
  margin-top: 3px;
  height: 100px;
  display: flex;
}

.num {
  width: 25%;
  margin-top: 10px;
  margin-left: 8%;
  border: 4px solid rgb(60, 143, 66);
  border-radius: 15px;
  display: flex;
}

.icon {
  margin-left: 20px;
  margin-top: 10px;
}

.fout {
  width: 150px;
  height: 65px;
  margin-top: 10px;
  margin-left: 10px;
}
</style>

