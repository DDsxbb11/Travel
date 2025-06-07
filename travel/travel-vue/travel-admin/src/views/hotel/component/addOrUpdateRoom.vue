<script lang="ls">
import { getAllTheme,getProvinceAndCityAndDistrictCascade} from "@/api/option";
import {addOrUpdateRoom,getRoomDetail} from "@/api/room";
import { getFacilityByType } from "@/api/facility";
import { uploadFile } from "@/api/file";
export default {
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.handlerLocation();
          // const {data} = addOrUpdateRoom(this.ruleForm);
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
      if(type == 'room_facility'){
        this.roomFacilityList = data.data
      }else if(type == 'room_bathroom_facility'){
        this.roomBathroomFacilityList = data.data
      }else if(type == 'room_food_facility'){
        this.roomFoodFacilityList = data.data
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
    }
  },

  mounted() {
    const id = this.$route.params.id;
    if(id !=-1){
      // 修改景点
      getRoomDetail(id).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.ruleForm = data.data;
    });
    }
    this.getFacilityByType("room_facility");
    this.getFacilityByType("room_bathroom_facility");
    this.getFacilityByType("room_food_facility");
  },
  data() {
    return {
        ruleForm: {
          id: '',
          name: '',
          number: 0,
          imgUrl: '',
          roomFacilityList: [],
          roomBathroomFacilityList: [],
          roomFoodFacilityList: [],
        },
        roomFacilityList: [],
        roomBathroomFacilityList: [],
        roomFoodFacilityList: [],
        
        selectedValues: [],
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
          <el-select v-model="ruleForm.level" placeholder="请选择酒店">
            <el-option
              v-for="item in levelList"
              :key="item.label"
              :label="item.value"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间类型" prop="name">
          <el-input v-model="ruleForm.name" style="width: 280px;"></el-input>
        </el-form-item>
        <el-form-item label="房间图片" >
           <el-upload
            action="#"
            :http-request="customUpload"
            list-type="picture-card"
            :show-file-list="false"
            :before-upload="beforeUpload"
            accept="image/*"
            :multiple="false" 
          >
            <img v-if="ruleForm.imgUrl" :src="ruleForm.imgUrl"  alt="房间图片" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
            
        </el-form-item>
        
        <el-form-item label="房间数量" prop="number">
          <el-input v-model="ruleForm.number" style="width: 180px;"></el-input>
        </el-form-item>
        
        <el-form-item label="房间设施" prop="roomFacilityList">
          <el-select
            v-model="ruleForm.roomFacilityList"
            multiple
            placeholder="请选择"
            style="width: 60%"
            class="wrap-select"
          >
            <el-option
              v-for="item in roomFacilityList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="洗浴设施" prop="roomBathroomFacilityList">
          <el-select
            v-model="ruleForm.roomBathroomFacilityList"
            multiple
            placeholder="请选择"
            style="width: 60%"
            class="wrap-select"
          >
            <el-option
              v-for="item in roomBathroomFacilityList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="食品设施" prop="roomFoodFacilityList">
          <el-select
            v-model="ruleForm.roomFoodFacilityList"
            multiple
            placeholder="请选择"
            style="width: 60%"
            class="wrap-select"
          >
            <el-option
              v-for="item in roomFoodFacilityList"
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

</style>

