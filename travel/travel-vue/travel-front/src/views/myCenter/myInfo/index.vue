<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <!-- 头部 -->
      <div class="profile-header">
        <el-avatar :size="100" :src="userInfo.avatarUrl" class="avatar">
          {{ userInfo.name }}
        </el-avatar>
        <div class="header-info">
          <h2>{{ userInfo.nickname }}</h2>
        </div>
      </div>

      <!-- 编辑表单 -->
      <el-form
        v-if="isEditing"
        :model="formData"
        label-width="80px"
        ref="formRef"
        :rules="rules"
      >
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="formData.nickname" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">保存</el-button>
          <el-button @click="toggleEdit">取消</el-button>
          <el-button type="danger" @click="openPasswordDialog" style="margin-left: 50%">修改密码</el-button>
        </el-form-item>
      </el-form>

      <!-- 展示信息 -->
      <div v-else class="info-list">
        <div class="info-item">
          <span class="label">昵称：</span>
          <span class="value">{{ userInfo.nickname }}</span>
        </div>
        <div class="info-item">
          <span class="label">邮箱：</span>
          <span class="value">{{ userInfo.email }}</span>
        </div>
        <div class="info-item">
          <span class="label">手机号：</span>
          <span class="value">{{ userInfo.phone }}</span>
        </div>
      </div>

      <div class="edit-btn">
        <el-button
          type="primary"
          @click="toggleEdit"
          :icon="isEditing ? 'Close' : 'Edit'"
        >
          {{ isEditing ? "取消" : "编辑资料" }}
        </el-button>
      </div>
    </el-card>

    <!-- 修改密码对话框 -->
    <el-dialog :visible.sync="passwordDialogVisible" title="修改密码" style="width: 60%;margin: 0 auto;">
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef">
        <el-form-item label="当前密码" prop="currentPassword">
          <el-input
            v-model="passwordForm.currentPassword"
            type="password"
            show-password
            placeholder="请输入当前密码"
          />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="passwordForm.newPassword"
            type="password"
            show-password
            placeholder="请输入新密码"
          />
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input
            v-model="passwordForm.confirmPassword"
            type="password"
            show-password
            placeholder="请再次输入新密码"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="passwordDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPasswordForm">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getInfo,changePassword,changeUserInfo } from "@/api/user";

export default {
  name: "UserProfile",
  data() {
    return {
      // 用户信息
      userInfo: {},

      // 表单相关状态
      isEditing: false,
      formData: {
        nickname: "",
        email: "",
        phone: "",
      },
      formRef: null,

      // 验证规则
      rules: {
        nickname: [
          { required: true, message: "请输入昵称", trigger: "blur" },
          { min: 2, max: 10, message: "长度在2到10个字符", trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { type: "email", message: "邮箱格式不正确", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            pattern: /^1[3-9]\d{9}$/,
            message: "手机号格式不正确",
            trigger: "blur",
          },
        ],
      },

      // 修改密码相关
      passwordDialogVisible: false,
      passwordForm: {
        currentPassword: "",
        newPassword: "",
        confirmPassword: "",
      },
      passwordRules: {
        currentPassword: [
          { required: true, message: "请输入当前密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { min: 6, max: 18, message: "密码长度在6到18个字符", trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请再次输入新密码", trigger: "blur" },
          {
            validator: (rule, value, callback) => {
              if (value !== this.passwordForm.newPassword) {
                callback(new Error("两次输入的密码不一致"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    toggleEdit() {
      this.isEditing = !this.isEditing;
      if (!this.isEditing) {
        this.formData = {
          nickname: this.userInfo.nickname,
          email: this.userInfo.email,
          phone: this.userInfo.phone,
        };
      }
    },

    async submitForm() {
      try {
        await this.$refs.formRef.validate();
        const {code,message}=await changeUserInfo(this.formData);
        if (code === 200) {
          this.$message({
            message: "修改成功",
            type: "success",
          });
        } else {
          this.$message.error(message);
        }
        this.userInfo = { ...this.userInfo, ...this.formData };
        this.isEditing = false;
        this.$message.success("保存成功");
      } catch (error) {
        console.log("表单验证失败");
      }
    },

    openPasswordDialog() {
      this.passwordDialogVisible = true;
    },

    async submitPasswordForm() {
      try {
        await this.$refs.passwordFormRef.validate();
        const {code,message}=await changePassword(this.passwordForm);
        if (code === 200) {
          this.$message({
            message: "修改成功",
            type: "success",
          });
        } else {
          this.$message.error(message);
        }
        this.passwordDialogVisible = false;
        this.$message.success("密码修改成功");
      } catch (error) {
        this.$message.error("密码修改失败");
      }
    },
  },
  mounted() {
    getInfo().then((res) => {
      this.userInfo = res.data;
      this.formData = {
        nickname: this.userInfo.nickname,
        email: this.userInfo.email,
        phone: this.userInfo.phone,
      };
    });
  },
};
</script>

<style scoped>
.profile-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.avatar {
  margin-right: 30px;
}

.header-info h2 {
  margin: 0 0 10px 0;
}

.info-list {
  padding: 10px 0;
}

.info-item {
  margin: 15px 0;
  display: flex;
  align-items: flex-start;
}

.label {
  width: 80px;
  color: #666;
  flex-shrink: 0;
}

.value {
  flex: 1;
}

.bio {
  white-space: pre-wrap;
  line-height: 1.6;
}

@media (max-width: 768px) {
  .profile-header {
    flex-direction: column;
    text-align: center;
  }

  .avatar {
    margin-right: 0;
    margin-bottom: 15px;
  }
}
</style>