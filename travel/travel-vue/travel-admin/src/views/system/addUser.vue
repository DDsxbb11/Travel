<script>
import {getAddStockRecordChoose,addStockRecord} from '@/api/request'
export default {
  data() {
    return {
      form: {
        supplierName: '',
        goodsName: '',
        goodsType:'',
        buyer: '',
        number: '',
        unit: '',
        price:'',
        stockTime: '',
        description: ''
      },
      supplierNames: [],
      goodsTypes:[],
    }
  },
  methods: {
    onSubmit() {
      addStockRecord(this.form).then(response => {
        this.form=""
        this.$message({
          message: '添加成功',
          type: 'success'
        })
      })
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    querySupplierNameSearch(queryString, cb) {
        var supplierNames = this.supplierNames;
        var results = queryString ? supplierNames.filter(this.createSupplierNameFilter(queryString)) : supplierNames;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
    createSupplierNameFilter(queryString) {
      return (supplierName) => {
        return (supplierName.indexOf(queryString) === 0);
      };
    },
    handleSupplierNameSelect(item) {
      this.form.supplierName=item
    },
    //商品属性提示信息
    queryGoodsTypesSearch(queryString, cb) {
        var goodsTypes = this.goodsTypes;
        var results = queryString ? goodsTypes.filter(this.createGoodsTypesFilter(queryString)) : goodsTypes;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
    createGoodsTypesFilter(queryString) {
      return (goodsTypes) => {
        return (goodsTypes.type.indexOf(queryString) === 0);
      };
    },
    handleGoodsTypesSelect(item) {
      this.form.goodsType=item.type
    },
  },
  mounted() {
    getAddStockRecordChoose().then((response) => {
      this.supplierNames=response.data.supplierNames 
      this.goodsTypes=response.data.goodsTypes   
    });

    }
}
</script>
<template>
  <div class="app-container">
    <el-button type="success" round size="small" style="margin-top: 4px;margin-left: 40px;">批量导入</el-button>
    <el-form ref="form" :model="form" label-width="120px" style="margin-top: 30px;">
      <el-form-item label="供应商名称" style="width: 400px">
        <el-autocomplete
          class="inline-input"
          v-model="form.supplierName"
          :fetch-suggestions="querySupplierNameSearch"
          placeholder="请输入内容"
          @select="handleSupplierNameSelect"
        >
        <template slot-scope="{ item }">
          <div class="name">{{ item }}</div>
        </template>
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="商品名称">
        <el-input v-model="form.goodsName" style="width: 360px"/>
      </el-form-item>
      <el-form-item label="商品类型">
        <el-autocomplete
          class="inline-input"
          v-model="form.goodsType"
          :fetch-suggestions="queryGoodsTypesSearch"
          placeholder="请输入内容"
          @select="handleGoodsTypesSelect"
        >
        <template slot-scope="{ item }">
          <div class="name">{{ item.type }}</div>
        </template>
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="数量">
        <el-input v-model="form.number" style="width: 120px"/>
      </el-form-item>
      <el-form-item label="单位">
        <el-radio-group v-model="form.unit">
          <el-radio label="箱"/>
          <el-radio label="袋"/>
          <el-radio label="包"/>
          <el-radio label="瓶"/>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="价格">
        <el-input v-model="form.price" style="width: 120px"/>
      </el-form-item>
      <el-form-item label="订购时间">
        <el-col :span="11">
          <el-date-picker
           v-model="form.stockTime"
          type="date" 
          placeholder="选择日期" 
          style="width: 60%;" 
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          />
        </el-col>
      </el-form-item>
      <el-form-item label="备注" style="width: 600px">
        <el-input v-model="form.description" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="onCancel">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>



<style scoped>
.line {
  text-align: center;
}
</style>

