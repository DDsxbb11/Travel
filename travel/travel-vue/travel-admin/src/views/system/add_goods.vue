<script>
import {getAddStockRecordChoose,addGoods} from '@/api/request'
export default {
  data() {
    return {
      form: {
        name: '',
        supplierName: '',
        type:'',
        url: '',
        price: '',
        stock:'',
        unit: '',
        description: ''
      },
      supplierNames: [],
      goodsTypes:[],
      imgUrl:'',
    }
  },
  methods: {
    onSubmit() {
      this.$refs.upload.submit();
      console.log(this.form)
      addGoods(this.form).then(response => {
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
      this.form.type=item.type
    },
    //上传图片
    fileChange(file, fileList){
      this.imgUrl = URL.createObjectURL(file.raw);
    },
    handleAvatarSuccess(res, file) {
      this.form.url=res.data
      this.submitBollean=true
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
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
      <el-form-item label="商品名称">
        <el-input v-model="form.name" style="width: 360px"/>
      </el-form-item>
      <el-form-item label="供应商名称">
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
      <el-form-item label="商品类型">
        <el-autocomplete
          class="inline-input"
          v-model="form.type"
          :fetch-suggestions="queryGoodsTypesSearch"
          placeholder="请输入内容"
          @select="handleGoodsTypesSelect"
        >
        <template slot-scope="{ item }">
          <div class="name">{{ item.type }}</div>
        </template>
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="首图">
          <el-upload
            ref="upload"
            class="avatar-uploader"
            action="http://localhost:8080/goods/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :auto-upload="false"
            :on-change="fileChange"
            :before-upload="beforeAvatarUpload">
            <img  :src="imgUrl" class="avatar">
          </el-upload>
        </el-form-item>
      <el-form-item label="售价">
        <el-input v-model="form.price" style="width: 120px"/>
      </el-form-item>
      <el-form-item label="库存">
        <el-input v-model="form.stock" style="width: 120px"/>
      </el-form-item>
      <el-form-item label="单位">
        <el-radio-group v-model="form.unit">
          <el-radio label="箱"/>
          <el-radio label="袋"/>
          <el-radio label="包"/>
          <el-radio label="瓶"/>
        </el-radio-group>
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
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

