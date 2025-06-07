<template>
<div>
  <div class="navbar">
    <hamburger
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper" style="width: 40px;height: 40px;">
          <img :src=userInfo.avatarUrl style="position:absolute;width: 40px;border-radius: 10px;"/>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown" style="width: 80px;">
          <el-button type="text" @click="updateUserInfo = true" style="margin-left: 8px;">修改信息</el-button><br>
          
          <el-button type="text" @click.native="logout" style="margin-left: 8px;">退出登录</el-button>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    
  </div>
  <el-drawer
  :visible.sync="updateUserInfo"
  size="40%">
  <div>
    <!-- 个人信息 -->
      <h1 style="margin-top: -20px;margin-left: 220px;color:#10e3c7;font-family:'隶书';">个人信息</h1>
      <h2 style="margin-top: 20px;margin-left: 30px;color: #10a4e3;">{{ userInfo.role }}
      </h2>
      <el-form ref="form" :model="userInfo" label-width="80px">
        <el-form-item label="昵称">
          <el-input v-model="userInfo.nickname" style="width: 200px;"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="userInfo.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            ref="upload"
            class="avatar-uploader"
            action="http://localhost:8080/system/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :auto-upload="false"
            :on-change="fileChange"
            :before-upload="beforeAvatarUpload">
            <img  :src="imgUrl" class="avatar">
          </el-upload>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="userInfo.phone" style="width: 160px;"></el-input>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="userInfo.idcard" style="width: 220px;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="updateUserInfo=false">取消</el-button>
        </el-form-item>
        <el-form-item style="margin-top: 60px;">
          <el-button type="danger" @click="updatePassword = true" style="margin-left: 300px;">修改密码</el-button>
        </el-form-item>
      </el-form>
    
      <!-- 修改密码抽屉 -->
    <el-drawer
        :append-to-body="true"
        :visible.sync="updatePassword">
        <h3 style="margin-top: -30px;margin-left: 160px;color: #E6A23C;">修改密码</h3>
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" 
          class="demo-ruleForm" style="margin-top: 80px;">
          <el-form-item label="账号" prop="username" disabled="false">
            <el-input v-model="ruleForm.username" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="password">
            <el-input v-model="ruleForm.password" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="rePassword">
            <el-input v-model="ruleForm.rePassword" style="width: 200px;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateUserPassword('ruleForm')">确认</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
  </div>
</el-drawer>
</div>  
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import {updatePasswordById,updateUserById,getUserInfo} from '@/api/user'

export default {
  data() {
    const validateUsername = (rule, value, callback) => {
      const usernameReg = /^[a-zA-Z0-9]{4,10}$/
      if (!usernameReg.test(value)) {
        callback(new Error('用户名格式错误'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      const userPwdReg = /^[a-zA-Z0-9]{6,10}$/
      if (!userPwdReg.test(value)) {
        callback(new Error('密码格式错误'))
      } else {
        callback()
      }
    }
    const validateRePassword = (rule, value, callback) => {
      if (value==this.password) {
        callback(new Error('密码不一致'))
      } else {
        callback()
      }
    }
    return {
      updatePassword: false,
      updateUserInfo:false,
      imgUrl:'',
      ruleForm: {
          username: '',
          password:'',
          rePassword:'',
        },
      rules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePassword}],
        rePassword: [{required: true, trigger: 'blur', validator: validateRePassword}]
      },
      userInfo: {
          id:1,
          nickname: '',
          gender: '',
          header: '',
          phone: '',
          idcard: '',
          role:'',
        }
    };
  },
  components: {
    Breadcrumb,
    Hamburger,
  },
  computed: {
    ...mapGetters(["sidebar", "avatar"]),
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      await this.$store.dispatch("user/logout");
      this.$router.push(`/login?redirect=${this.$route.fullPath}`);
    },

    fileChange(file, fileList){
      this.imgUrl = URL.createObjectURL(file.raw);
    },
    //提交函数
    //上传图片
    handleAvatarSuccess(res, file) {
      this.userInfo.header=res.data
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
    //修改密码
    updateUserPassword(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updatePasswordById(this.ruleForm).then(response=>{
              this.$message({
              message: '修改成功,请重新登录',
              type: 'success'
              })
              this.updatePassword=false
              this.$router.push(`/login`);
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //修改信息
    async onSubmit(){ 
      this.$refs.upload.submit();
      setTimeout(() => {
        updateUserById(this.userInfo).then(response=>{
          this.$message({
                message: '修改成功!',
                type: 'success'
                })
        })
        this.$router.go(0)
        this.updateUserInfo=false 
      }, 1000);
    }
  },
  mounted(){
    getUserInfo().then(response=>{
      this.userInfo=response.data
      console.log(this.userInfo)
      this.imgUrl=response.data.avatarUrl
      console.log(this.imgUrl)
    })  
  }
  
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }
  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        width: 40px;
        height: 40px;
        margin-top: 5px;
        border-radius: 10px;
        position: relative;

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
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
