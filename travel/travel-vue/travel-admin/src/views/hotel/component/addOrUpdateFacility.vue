<script lang="ls">
import {addOrUpdateFacility,getFacilityDetail} from "@/api/facility";
import { uploadFile } from "@/api/file";
export default {
  methods: {
    async submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.ruleForm)
          const {data} = addOrUpdateFacility(this.ruleForm);
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
   
    async customUpload(file) {
      try {
        const {data} = await uploadFile(file.file)
        console.log(data)
        // 上传成功处理
        this.ruleForm.imageList.push({
          url: data.data
        })
        
        this.$message.success('上传成功')
      } catch (error) {
        this.$message.error('上传失败')
      }
    },
    
    // 上传前的校验
    beforeSvgUpload(file) {
      const isSvg = file.type === 'image/svg+xml' || file.name.endsWith('.svg');
      const isLt1M = file.size / 1024 / 1024 < 1;
      
      if (!isSvg) {
        this.$message.error('只能上传SVG格式文件!');
        return false;
      }
      if (!isLt1M) {
        this.$message.error('SVG文件大小不能超过1MB!');
        return false;
      }
      return true;
    },
  },

  mounted() {
    const id = this.$route.params.id;
    if(id !=-1){
      // 修改景点
      getFacilityDetail(id).then((response) => {
      const { data } = response;
      if (!data) {
        return reject("网络异常.");
      }
      this.ruleForm = data.data;
    });
    }
    
  },
  data() {
    return {
        ruleForm: {
          name: '',
          type: '',
          svgUrl: '',
        },
        
        options: [
        { value: 'hotel_facility', label: '酒店设施'},
        { value: 'room_facility', label: '客房设施'},
        { value: 'room_bathroom_facility', label: '浴室设施' },
        { value: 'room_food_facility', label: '食品设施' }
      ],
      selectedValues: [],
        rules: {
          name: [
            { required: true, message: '请输入景点名称', trigger: 'blur' },
            { min: 2, max: 5, message: '长度在2-5个字符', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '请选择设施类型', trigger: 'blur' },
          ]
        }
      };
  },
};
</script>
<template>
  <div class="form-box">
    <div class="title">添加设施</div>

    <div class="form-content">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="设施名称" prop="name">
          <el-input v-model="ruleForm.name" style="width: 280px;"></el-input>
        </el-form-item>
        <el-form-item label="设施类型" prop="type">
          <el-select v-model="ruleForm.type" placeholder="请选择设施类型">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="Svg图片" >
           <el-upload
            action="#"
            :http-request="uploadFile"
            list-type="picture-card"
            :show-file-list="false"
            :before-upload="beforeSvgUpload"
            accept=".svg, image/svg+xml"
            :multiple="false" 
          >
            <img :src="ruleForm.svgUrl" class="avatar" alt="SVG图标"/>
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

.avatar {
  width: 100%;
  height: 100%;
  display: block;
}

</style>

