<script lang="ls">
import {publishRecord} from '@/api/request'
import {uploadFile} from '@/api/file'
export default {
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const {data} = publishRecord(this.ruleForm);
          this.$message.success('发布成功')
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    async uploadBCImgUrl(file) {
      try {
        const {data} = await uploadFile(file.file)
        console.log(data)
        // 上传成功处理
        this.ruleForm.imgUrl = data
        
        this.$message.success('上传成功')
      } catch (error) {
        this.$message.error('上传失败')
      }
    },

    async customUpload(file) {
      try {
        const {data} = await uploadFile(file.file)
        console.log(data)
        // 上传成功处理
        this.routeInfo.imgUrl = data
        
        this.$message.success('上传成功')
      } catch (error) {
        this.$message.error('上传失败')
      }
    },
   
    // 上传前的校验
    beforeUpload(file) {
      const isImage = file.type.includes('image/')
      const isLt5M = file.size / 1024 / 1024 < 5
      
      if (!isImage) {
        this.$message.error('只能上传图片文件!')
      }
      if (!isLt5M) {
        this.$message.error('图片大小不能超过5MB!')
      }
      
      return isImage && isLt5M
    },
    closeModal() {
      this.showRouteModal=false;
    },
    async addRoute() {
      this.ruleForm.routeList.push(this.routeInfo);
      this.showRouteModal = false;
      this.routeInfo= {
          title: '',
          description: '',
          sort:100,
      };
    },
    async deleteRoute(title, description) {
      this.ruleForm.routeList = this.ruleForm.routeList.filter(item => 
        !(item.title === title && item.description === description)
      );
      this.$message.success('删除成功');
      console.log(this.ruleForm.routeList)
    },
  },

  mounted() {
    
  },
  
  data() {
    return {
      id:'',
        ruleForm: {
          id: '',
          name: '',
          imgUrl: '',
          routeList: [],
        },
        showRouteModal: false,
        routeInfo: {
          title: '',
          description: '',
          imgUrl: '',
          sort:100,
        },
        rules: {
          name: [
            { required: true, message: '请输入景点名称', trigger: 'blur' },
            { min: 3, message: '长度在大于3个字符', trigger: 'blur' }
          ],
        }
      };
  },
};
</script>
<template>
  <div class="form-box">
    <div class="title">发布日记</div>
    <div class="form-content">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="日记标题" prop="name">
          <el-input v-model="ruleForm.name" style="width: 280px;"></el-input>
        </el-form-item>
        <el-form-item label="日记主图" >
              <el-upload
                action="#"
                :http-request="uploadBCImgUrl"
                list-type="picture-card"
                :show-file-list="false"
                :before-upload="beforeUpload"
                accept="image/*"
                :multiple="false" 
              >
                <img v-if="ruleForm.imgUrl" :src="ruleForm.imgUrl"  alt="日记主图" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
                
            </el-form-item>
        <el-form-item label="内容描述">
          <div class="route-map">
            <el-timeline style="max-width: 700px" class="custom-timeline">
              <el-timeline-item
                v-for="(item, index) in ruleForm.routeList"
                :key="index"
                :timestamp="item.title"
                placement="top"
                icon="el-icon-video-camera"
                size="large"
              >
                <el-card>
                  <h3>{{ item.description }}</h3>
                  <div class="route-img">
                    <img :src="item.imgUrl" alt="" />
                  </div>
                </el-card>
                <el-button type="danger" size="mini" icon="el-icon-delete" circle class="strategy-route-delete" @click="deleteRoute(item.title, item.description)" ></el-button>
              </el-timeline-item>
            </el-timeline>
          </div>
        <el-button type="info" size="small" plain style="margin-top: 20px;margin-left: 20px;" @click="showRouteModal=true">添加内容</el-button>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div v-if="showRouteModal" class="modal-mask">
      <!-- 弹窗内容 -->
      <div class="modal-container">
        <div class="modal-header">
          <h2>添加路线</h2>
          <button class="close-btn" @click="closeModal">&times;</button>
        </div>

        <!-- 弹窗主体内容 -->
        <div class="modal-body">
          <el-form ref="form" :model="routeInfo" label-width="40px">
            <el-form-item label="标题">
              <el-input
                v-model="routeInfo.title"
                style="width: 300px"
              ></el-input>
            </el-form-item>
            <el-form-item label="描述">
              <el-input
                type="textarea"
                v-model="routeInfo.description"
                style="width: 300px;margin-top: 10px;"
              ></el-input>
            </el-form-item>
            <el-form-item label="图片" >
              <el-upload
                action="#"
                :http-request="customUpload"
                list-type="picture-card"
                :show-file-list="false"
                :before-upload="beforeUpload"
                accept="image/*"
                :multiple="false" 
              >
                <img v-if="routeInfo.imgUrl" :src="routeInfo.imgUrl"  alt="路线图片" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
                
            </el-form-item>
            <el-form-item label="排序">
              <el-input
                v-model="routeInfo.sort"
                style="width: 100px;margin-top: 10px;"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
        <!-- 弹窗底部 -->
        <div class="modal-footer">
          <el-button
            type="success"
            @click="closeModal"
            size="medium "
            plain
            >取消</el-button
          >
          <el-button
            type="success"
            size="medium"
            @click="addRoute"
            plain
            >添加</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.title {
  width: 100%;
  font-size: 20px;
  font-weight: bold;
  line-height: 60px;
  color: #55f616;
}

.form-box {
  margin-left: 20%;
  width: 60%;
  margin-top: 2%;
  background-color: #fff;
  border-radius: 10px;
  display: flex;
  flex-direction: column; /* 改为垂直排列 */
  padding: 20px; /* 添加内边距 */
  box-sizing: border-box;
}

.form-content {
  width: 100%;
}
/* 核心样式 - 实现自动换行 */
.wrap-select ::v-deep .el-tag {
  margin: 2px;
  max-width: 60%;
  white-space: normal;
  height: auto;
  line-height: 1.5;
  display: inline-flex;
}

/* 选择框整体样式调整 */
.wrap-select ::v-deep .el-select__tags {
  white-space: normal;
  flex-wrap: wrap;
  display: flex;
  align-items: center;
  row-gap: 5px;
}

/* 输入框样式调整 */
.wrap-select ::v-deep .el-select__input {
  min-width: 50px !important;
  margin-left: 0 !important;
}
.avatar {
  width: 100%;
  height: 100%;
  display: block;
}
.strategy-fee {
  margin-left: 20px;
  width: 95%;
  font-size: 16px;
  color: black;
}
.strategy-fee-title {
  margin-left: 20px;
  font-size: 16px;
  width: 200px;
  color: rgb(59, 59, 59);
  font-weight: bold;
}
.strategy-fee-content {
  margin-top: -40px;
  margin-left: 220px;
  left: 80px;
  width: 65%;
  font-size: 16px;
  color: rgb(119, 119, 119);
}
.strategy-fee-delete{
  display: block;
  margin-top: -36px;
  margin-left: 880px;
}
.strategy-fee-line {
  margin-top: 5px;
  width: 100%;
  border-top: 1px solid #aeaeae;
}
.route-map {
  position: relative;
  display: flex;
  margin-top: 30px;
}
/* 深度选择器穿透组件作用域 */
::v-deep .custom-timeline .el-timeline-item__timestamp {
  font-size: 20px !important; /* 调整时间戳字体大小 */
  color: #000000; /* 可选：修改颜色 */
  font-weight: bold; /* 可选：加粗 */
}
::v-deep .custom-timeline .el-timeline-item__icon {
  font-size: 25px !important; /* 调整图标大小（适用于字体图标） */
  width: 80px !important; /* 调整图标容器宽度 */
  height: 25px !important; /* 调整图标容器高度 */
  background-color: rgb(174, 174, 174) !important;
  border-radius: 50% !important;
}
.route-img {
  width: 200px;
  height: 220px;
  border-radius: 20px;
}
.route-img img {
  width: 100%;
  height: 100%;
  border-radius: 20px;
  object-fit: cover;
}
/* 遮罩层样式 */
.modal-mask {
  position: fixed;
  z-index: 999;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity 0.3s ease;
}
.date-picker-top {
  z-index: 1000; /* 确保这个值高于其他元素的 z-index */
}
/* 弹窗容器 */
.modal-container {
  width: 450px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  padding: 20px;
  animation: modal-in 0.3s ease;
}

/* 弹窗进入动画 */
@keyframes modal-in {
  from {
    transform: translateY(-20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

/* 弹窗头部 */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
  border-bottom: 1px solid #eee;
  margin-bottom: 15px;
}

/* 关闭按钮 */
.close-btn {
  border: none;
  background: none;
  font-size: 28px;
  cursor: pointer;
  margin-top: -80px;
}

/* 弹窗底部 */
.modal-footer {
  margin-top: 15px;
  text-align: right;
}

.modal-footer button {
  margin-left: 10px;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}
.line {
  margin-top: -40px;
  margin-left: 110px;
  color: #717171;
  font-size: 15px;
  font-weight: bold;
}
.strategy-route-delete{
  display: block;
  margin-left: 260px;
  margin-top: -40px;
}
</style>

