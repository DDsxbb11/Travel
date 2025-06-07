<script lang="ls">
import { getAllTheme,getProvinceAndCityAndDistrictCascade} from "@/api/option";
import {addOrUpdateHotel,getHotelDetail,deletePolicy,savePolicy} from "@/api/hotel";
import { getFacilityByType } from "@/api/facility";
import { uploadFile } from "@/api/file";
export default {
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.handlerLocation();
          this.handleTime();
          // const {data} = addOrUpdateHotel(this.ruleForm);
          // this.$message.success(this.ruleForm.id?'修改成功':'添加成功')
          console.log(this.ruleForm)
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    handlerLocation() {
      if (Array.isArray(this.location)) { 
        const [province, city,district] = this.location; // 使用解构赋值
        this.ruleForm.province = province || "";
        this.ruleForm.city = city || "";
        this.ruleForm.district = district ||"";
      } else {
        this.ruleForm.province = "";
        this.ruleForm.city = "";
        this.ruleForm.district = "";
      }
    },

    handleTime(){
      this.ruleForm.openTime = this.openTime[0] + '-' + this.openTime[1]
    },

    async customUpload(file) {
      try {
        const {data} = await uploadFile(file.file)
        console.log(data)
        // 上传成功处理
        this.ruleForm.imgUrl = data.data
        
        this.$message.success('上传成功')
      } catch (error) {
        this.$message.error('上传失败')
      }
    },
    async getFacilityByType(type) {
      const param = {
        type: type,
      }
      const { data } =await getFacilityByType(param);
      this.facilityList = data.data;
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
      this.showModal = false;
    },
    async deletePolicy(title,content) {
      this.ruleForm.policyInfoList = this.ruleForm.policyInfoList.filter(item => 
        !(item.title === title && item.content === content)
      );
      this.$message.success('删除成功');
    },
    async addPolicy() {
      this.ruleForm.policyInfoList.push(this.policyInfo);
      this.showModal = false;
      this.policyInfo = {
        title: '',
        content: '',
        sort:100
      }
    },
    async getDetail(){
      getHotelDetail(this.id).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.ruleForm = data.data;
      this.policyInfo.itemId = data.data.id;
      this.location = [data.data.province, data.data.city,data.data.district];
    });
    }
  },

  mounted() {
    this.id = this.$route.params.id;
    if(this.id !=-1){
      // 修改景点
      this.getDetail();
    }
    getProvinceAndCityAndDistrictCascade().then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.locationOptions = data.data;
    });
    this.getFacilityByType("hotel_facility");
  },
  data() {
    return {
      id:'',
        ruleForm: {
          id: '',
          name: '',
          province: '',
          city: '',
          district: '',
          level: '',
          address: '',
          longitude: '',
          latitude: '',
          openTime: '',
          roomNumber: '',
          phone: '',
          description: '',
          trafficDesc: '',
          imgUrl: '',
          failityList: [],
          policyInfoList: [],
        },
        levelList: [
          { value: '一星级', label: '一星级' },
          { value: '二星级', label: '二星级' },
          { value: '三星级', label: '三星级' },
          { value: '四星级', label: '四星级' },
          { value: '五星级', label: '五星级' },
          { value: '一钻', label: '一钻' },
          { value: '二钻', label: '二钻' },
          { value: '三钻', label: '三钻' },
          { value: '四钻', label: '四钻' },
          { value: '五钻', label: '五钻' },
        ],
        locationOptions:[],
        location: [],
        facilityList: [],
        selectedValues: [],
        showModal: false,
        openTime: [],
        policyInfo: {
          title: '',
          content: '',
          sort:100
        },
        rules: {
          // name: [
          //   { required: true, message: '请输入景点名称', trigger: 'blur' },
          //   { min: 3, message: '长度在大于3个字符', trigger: 'blur' }
          // ],
          // theme: [
          //   { required: true, message: '请选择活动区域', trigger: 'change' }
          // ],
          // address: [
          //   { required: true, message: '请填写详细地址', trigger: 'change' }
          // ],
         
          // longitude: [
          //   { required: true, message: '请填写经度', trigger: 'change' }
          // ],
          // latitude: [
          //   { required: true, message: '请填写纬度', trigger: 'change' }
          // ],
          // resource: [
          //   { required: true, message: '请选择活动资源', trigger: 'change' }
          // ]
        }
      };
  },
};
</script>
<template>
  <div class="form-box">
    <div class="title">{{ ruleForm.id ? "修改酒店":"添加酒店" }}</div>

    <div class="form-content">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="酒店名称" prop="name">
          <el-input v-model="ruleForm.name" style="width: 280px;"></el-input>
        </el-form-item>
        <el-form-item label="酒店首图" >
           <el-upload
            action="#"
            :http-request="customUpload"
            list-type="picture-card"
            :show-file-list="false"
            :before-upload="beforeUpload"
            accept="image/*"
            :multiple="false" 
          >
            <img v-if="ruleForm.imgUrl" :src="ruleForm.imgUrl"  alt="酒店图片" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
            
        </el-form-item>
        <el-form-item label="地区" prop="location">
          <el-cascader
            v-model="location"
            :options="locationOptions"
            placeholder="请选择省 / 市 / 区"
            clearable
            class="location-cascader"
            style="width: 280px;"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="ruleForm.address" style="width: 380px;"></el-input>
        </el-form-item>
        <el-form-item label="经度" prop="longitude">
          <el-input v-model="ruleForm.longitude" style="width: 180px;"></el-input>
        </el-form-item>
        <el-form-item label="纬度" prop="latitude">
          <el-input v-model="ruleForm.latitude" style="width: 180px;"></el-input>
        </el-form-item>
        <el-form-item label="酒店等级" prop="level">
          <el-select v-model="ruleForm.level" placeholder="请选择景点主题">
            <el-option
              v-for="item in levelList"
              :key="item.label"
              :label="item.value"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开业时间" prop="openTime">
          <el-time-picker
            is-range
            v-model="openTime"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="房间数量" prop="roomNumber">
          <el-input v-model="ruleForm.roomNumber" style="width: 180px;"></el-input>
        </el-form-item>
        <el-form-item label="酒店电话" prop="phone">
          <el-input v-model="ruleForm.phone" style="width: 180px;"></el-input>
        </el-form-item>
        <el-form-item label="酒店政策">
          <div class="hotel-policy" v-for="item in ruleForm.policyInfoList" :key="item.id">
        <div class="hotel-policy-title">{{ item.title }}</div>
          <div class="hotel-policy-content">{{ item.content }}</div>
          <el-button type="danger" size="mini" icon="el-icon-delete" circle class="hotel-policy-delete" @click="deletePolicy(item.title,item.content)" ></el-button>
          <div class="hotel-policy-line"></div>
        </div>
        <el-button type="info" size="small" plain style="margin-top: 20px;margin-left: 20px;" @click="showModal=true">添加政策</el-button>
          <div v-if="showModal" class="modal-mask">
              <!-- 弹窗内容 -->
              <div class="modal-container">
                <div class="modal-header">
                  <h2>添加政策</h2>
                  <button class="close-btn" @click="closeModal">&times;</button>
                </div>

                <!-- 弹窗主体内容 -->
                <div class="modal-body">
                  <el-form ref="form" :model="policyInfo" label-width="40px">
                    <el-form-item label="标题">
                      <el-input
                        v-model="policyInfo.title"
                        style="width: 300px"
                      ></el-input>
                    </el-form-item>
                    <el-form-item label="内容">
                      <el-input
                        type="textarea"
                        v-model="policyInfo.content"
                        style="width: 300px;margin-top: 10px;"
                      ></el-input>
                    </el-form-item>
                    <el-form-item label="排序">
                      <el-input
                        v-model="policyInfo.sort"
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
                    @click="addPolicy"
                    plain
                    >添加</el-button
                  >
                </div>
              </div>
            </div>
        </el-form-item>
        <el-form-item label="酒店描述" prop="description">
          <el-input type="textarea" v-model="ruleForm.description" style="width: 480px;"></el-input>
        </el-form-item>
        <el-form-item label="交通描述" prop="trafficDesc">
          <el-input type="textarea" v-model="ruleForm.trafficDesc" style="width: 480px;"></el-input>
        </el-form-item>
        <el-form-item label="配套设施" prop="facility">
          <el-select
            v-model="ruleForm.failityList"
            multiple
            placeholder="请选择"
            style="width: 60%"
            class="wrap-select"
          >
            <el-option
              v-for="item in failityList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
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
  margin-left: 2%;
  width: 96%;
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
.hotel-policy {
  margin-left: 20px;
  width: 95%;
  font-size: 16px;
  color: black;
}
.hotel-policy-title {
  margin-left: 20px;
  font-size: 16px;
  width: 200px;
  color: rgb(59, 59, 59);
  font-weight: bold;
}
.hotel-policy-content {
  margin-top: -40px;
  margin-left: 220px;
  left: 80px;
  width: 65%;
  font-size: 16px;
  color: rgb(119, 119, 119);
}
.hotel-policy-delete{
  display: block;
  margin-top: -36px;
  margin-left: 880px;
}
.hotel-policy-line {
  margin-top: 5px;
  width: 100%;
  border-top: 1px solid #aeaeae;
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
</style>

