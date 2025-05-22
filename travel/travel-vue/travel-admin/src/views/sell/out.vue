<script>
import {getUUIDList,addReturnRecord} from '@/api/request'
export default {
  data() {
    return {
      form: {
        uuid: '',
        goods: '',
        number:'',
        price:'',
        description: ''
      },
      uuidList: [],
    }
  },
  methods: {
    onSubmit() {
      addReturnRecord(this.form).then(response => {
        console.log(this.form)
        this.form=""
        this.$message({
          message: '退货成功',
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
    querySearch(queryString, cb) {
        var uuidList = this.uuidList;
        var results = queryString ? uuidList.filter(this.createFilter(queryString)) : uuidList;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
    createFilter(queryString) {
      return (uuid) => {
        return (uuid.indexOf(queryString) === 0);
      };
    },
    handleSelect(item) {
      this.form.uuid=item
    },
  },
  mounted() {
    getUUIDList().then((response) => {
      this.uuidList=response.data.uuidList
    });

    }
}
</script>
<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="流水号">
        <el-autocomplete
          class="inline-input"
          v-model="form.uuid"
          :fetch-suggestions="querySearch"
          placeholder="请输入内容"
          @select="handleSelect"
        >
        <template slot-scope="{ item }">
          <div class="name">{{ item }}</div>
        </template>
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="商品">
        <el-input v-model="form.goods" style="width: 180px;"/>
      </el-form-item>
      <el-form-item label="数量">
        <el-input v-model="form.number" style="width: 100px;"/>
      </el-form-item>
      <el-form-item label="金额">
        <el-input v-model="form.price" style="width: 100px;"/> &nbsp;&nbsp;&nbsp;元
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.description" type="textarea" style="width: 400px;"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>



<style scoped>
.line {
  text-align: center;
}
</style>

