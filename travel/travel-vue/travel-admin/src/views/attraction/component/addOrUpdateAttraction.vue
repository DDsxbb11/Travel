<script lang="ls">
import { getAllTheme,getProvinceAndCityCascade} from "@/api/option";
import {addOrUpdateAttraction,getAttractionDetail} from "@/api/attraction";
import { uploadFile } from "@/api/file";
export default {
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.handleTime();
          this.fillIsFee();
          this.handlerLocation();
          const {data} = addOrUpdateAttraction(this.ruleForm);
          this.$message.success(this.ruleForm.id?'修改成功':'添加成功')
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    
    fillIsFee(){
      if(this.delivery){
        this.ruleForm.isFee=1
      }else{
        this.ruleForm.isFee=0
      }
    },

    handlerLocation() {
      if (Array.isArray(this.location)) { 
        const [province, city] = this.location; // 使用解构赋值
        this.ruleForm.province = province || "";
        this.ruleForm.city = city || "";
      } else {
        this.ruleForm.province = "";
        this.ruleForm.city = "";
      }
    },

    handleTime(){
      this.ruleForm.openTime = this.openTime[0] + '-' + this.openTime[1]
    },

    handleRemove(file, fileList) {
      this.ruleForm.imageList = fileList;
    },
    async customUpload(file) {
      try {
        const {data} = await uploadFile(file.file)
        console.log(data)
        // 上传成功处理
        this.ruleForm.imageList.push({
          url: data
        })
        
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
    }
  },

  mounted() {
    const id = this.$route.params.id;
    if(id !=-1){
      // 修改景点
      getAttractionDetail(id).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.ruleForm = data;
      //为openTime赋值 7:00 - 17:00
      this.openTime = [data.openTime.split('-')[0],data.openTime.split('-')[1]];
      //为location赋值
      this.location = [data.province,data.city];
      //为isFee赋值
      this.delivery = data.isFee === 1;
    });
    }
    getAllTheme().then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.themeList = data;
      console.log(this.themeList);
    });
    getProvinceAndCityCascade().then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.locationOptions = data;
    });
  },
  data() {
    return {
        ruleForm: {
          name: '',
          theme: '',
          province: '',
          city: '',
          address: '',
          longitude: '',
          latitude: '',
          openTime: '',
          isFee: 0,
          childrenPrice: '',
          adultPrice: '',
          ticketDesc: '',
          type: [],
          description: '',
          trafficDesc: '',
          imageList: [],
        },
        delivery: false,
        dialogImageUrl:'',
        themeList: [],
        locationOptions:[],
        location: [],
        openTime: [],
        options: [
        { value: '选项1', label: '黄金糕' },
        { value: '选项2', label: '双皮奶' },
        { value: '选项3', label: '蚵仔煎' },
        { value: '选项4', label: '龙须面' },
        { value: '选项5', label: '北京烤鸭' }
      ],
      selectedValues: [],
        rules: {
          name: [
            { required: true, message: '请输入景点名称', trigger: 'blur' },
            { min: 3, message: '长度在大于3个字符', trigger: 'blur' }
          ],
          theme: [
            { required: true, message: '请选择活动区域', trigger: 'change' }
          ],
          address: [
            { required: true, message: '请填写详细地址', trigger: 'change' }
          ],
         
          longitude: [
            { required: true, message: '请填写经度', trigger: 'change' }
          ],
          latitude: [
            { required: true, message: '请填写纬度', trigger: 'change' }
          ],
          resource: [
            { required: true, message: '请选择活动资源', trigger: 'change' }
          ]
        }
      };
  },
};
</script>
<template>
  <div class="form-box">
    <div class="title">添加景点</div>

    <div class="form-content">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="景点名称" prop="name">
          <el-input v-model="ruleForm.name" style="width: 280px;"></el-input>
        </el-form-item>
        <el-form-item label="景点主题" prop="theme">
          <el-select v-model="ruleForm.theme" placeholder="请选择景点主题">
            <el-option
              v-for="item in themeList"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地区" prop="location">
          <el-cascader
            v-model="location"
            :options="locationOptions"
            placeholder="请选择省 / 市"
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
        <el-form-item label="开放时间" prop="openTime">
          <el-time-picker
            is-range
            v-model="openTime"
            range-separator="至"
            value-format="HH:mm"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            placeholder="选择时间范围">
          </el-time-picker>
        </el-form-item>
        <el-form-item label="是否免费" prop="delivery">
          <el-switch v-model="delivery"></el-switch>
        </el-form-item>
        
        <el-form-item v-if="!delivery" label="儿童票价" prop="childrenPrice">
          <el-input v-model="ruleForm.childrenPrice" style="width: 180px;"></el-input>
        </el-form-item>
        <el-form-item v-if="!delivery" label="成人票价" prop="adultPrice">
          <el-input v-model="ruleForm.adultPrice" style="width: 180px;"></el-input>
        </el-form-item>
        <el-form-item label="景点描述" prop="description">
          <el-input type="textarea" v-model="ruleForm.description" style="width: 480px;"></el-input>
        </el-form-item>
        <el-form-item label="交通描述" prop="trafficDesc">
          <el-input type="textarea" v-model="ruleForm.trafficDesc" style="width: 480px;"></el-input>
        </el-form-item>
        <!-- <el-form-item label="配套设施" prop="facility">
          <el-select
            v-model="selectedValues"
            multiple
            placeholder="请选择"
            style="width: 100%"
            class="wrap-select"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item> -->
        <el-form-item label="图片" >
           <el-upload
            action="#"  
            :http-request="customUpload" 
            list-type="picture-card"
            :file-list="ruleForm.imageList"
            :on-remove="handleRemove"
            :before-upload="beforeUpload"
            accept="image/*"
            multiple
          >
            <i class="el-icon-plus"></i>
          </el-upload>
    
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
  max-width: 100%;
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
</style>

