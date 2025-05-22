<script lang="ls">
import { getGoodsByPage, deleteGoodsById,updateGoodsById,getTest} from "@/api/request";
export default {
  methods: {
    updateInfo() {
      return new Promise(() => {
      updateGoodsById(this.form).then(response => {
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
        deleteGoodsById(id).then(response=>{
          this.getAll(this.pageNum)
          this.$message({
          message: '删除成功',
          type: 'success'
        });
        })
      })
    }, 
    updateGoods(goods){
      this.table=true
      this.form.id=goods.id
      this.form.name=goods.name
      this.form.price=goods.price
    },
    cancelForm(){
      this.form=" "
      this.table=false
    },
    getAll(val) {
      return new Promise(() => {
        getGoodsByPage(val, 7,this.input1,this.input2,this.input3).then((response) => {
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
    search(){
      getGoodsByPage(1, 7,this.input1,this.input2,this.input3).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.total = data.total;
      this.tableData = data.records;
    });
    },
    //上传图片
    handleAvatarSuccess(res, file) {
      this.form.url=res.data
      this.imageUrl = URL.createObjectURL(file.raw);
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
    }
  },

  mounted() {
    getGoodsByPage(1, 7,this.input1,this.input2,this.input3).then((response) => {
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
      input1: "",
      input2: "",
      input3: "",
      total: 0,
      pageNum: 1,
      tableData: [],
      table: false,
      imageUrl:'',
      form: {
        id:"",
        name: "",
        price:"",
        url:'',
      },
      imgl:[],
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
            placeholder="按商品名称查找"
            v-model="input1"
            class="input-with-select"
          >
          </el-input>
          <el-input
            size="small"
            placeholder="按供应商名称查找"
            v-model="input2"
            class="input-with-select"
          >
          </el-input>
          <el-input
            size="small"
            placeholder="按商品类别查找"
            v-model="input3"
            class="input-with-select"
          >
          </el-input>
          <el-button
            type="primary"
            size="small"
            plain style="margin-left: 10px"
            @click="search"
            >搜索</el-button
          >
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
          <el-table-column
            prop="supplierName"
            label="供应商"
            width="120"
            align="center"
          >
          </el-table-column
          ><el-table-column prop="type" label="类型" width="120" align="center">
          </el-table-column>
          <el-table-column label="图片" width="160" align="center">
            <template slot-scope="scope" >
              <el-image 
              :src=scope.row.url
              :preview-src-list="imgl"
              style="width: 40px; height: 40px"
            />
            </template>
          </el-table-column>
          <el-table-column prop="price" label="售价" width="120" align="center">
          </el-table-column>
          <el-table-column prop="stock" label="库存" width="120" align="center">
          </el-table-column>
          <el-table-column prop="unit" label="单位" width="120" align="center">
          </el-table-column>
          <el-table-column
            prop="description"
            label="备注"
            width="120"
            align="center"
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              <el-button type="text" @click="updateGoods(scope.row)" size="small"
                >修改</el-button
              >
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
        修改商品售价
      </h3>
      <div class="demo-drawer__content">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="商品名称">
            <el-input v-model="form.name" style="width: 200px" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="商品售价">
            <el-input v-model="form.price" style="width: 80px"></el-input>&nbsp;&nbsp;&nbsp;元
          </el-form-item>
          <el-form-item label="图片">
            <el-upload
              class="avatar-uploader"
              action="https://localhost:8080/goods/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
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

