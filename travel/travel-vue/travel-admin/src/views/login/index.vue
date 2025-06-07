<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <div class="title-container">
        <img class="logo" src="@/imgs/logo.jpg" />
        <h3 class="title">去旅行</h3>
      </div>

      <!-- 手机号输入 -->
      <el-form-item prop="phone" style="margin-top: 40px">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="phone"
          v-model="loginForm.phone"
          placeholder="手机号"
          name="phone"
          type="text"
          tabindex="1"
          maxlength="11"
          show-word-limit
          @blur="validateField('phone')"
        />
      </el-form-item>

      <!-- 密码输入 -->
      <el-form-item prop="password" v-if="loginType === 'password'">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码（6-16位英文或数字）"
          name="password"
          tabindex="2"
          maxlength="16"
          show-word-limit
          @keyup.enter.native="handleLogin"
          @blur="validateField('password')"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon
            :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
          />
        </span>
      </el-form-item>

      <!-- 短信验证码输入 -->
      <div v-if="loginType === 'sms'">
        <el-form-item prop="smsCode">
          <span class="svg-container">
            <svg-icon icon-class="message" />
          </span>
          <el-input
            v-model="loginForm.smsCode"
            placeholder="短信验证码"
            type="text"
            tabindex="3"
            maxlength="6"
            @blur="validateField('smsCode')"
            style="width: 30%"
          />
          <el-button
            :disabled="smsCountdown > 0"
            @click="sendSmsCode"
            style="width: 30%; margin-left: 30%; margin-top: 12px"
          >
            {{ smsCountdown > 0 ? `${smsCountdown}s后重试` : "获取验证码" }}
          </el-button>
        </el-form-item>
      </div>

      <!-- 验证码输入 -->
      <div v-if="loginType === 'password'" style="display: flex; align-items: center;">
        <el-form-item prop="captcha" style="flex: 1; margin-right: 10px;">
          <el-input
            v-model="loginForm.captcha"
            placeholder="验证码"
            type="text"
            tabindex="3"
            maxlength="4"
            @blur="validateField('captcha')"
          />
        </el-form-item>
        <img :src="captchaImg" class="identifyingCode" @click="updateCaptcha" />
      </div>

      <!-- 登录按钮 -->
      <el-button
        :loading="loading"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        @click.native.prevent="handleLogin"
      >
        {{ loginType === "password" ? "登录" : "短信登录" }}
      </el-button>

      <!-- 登录方式切换 -->
      <div class="loginType-toggle">
        {{ loginType === "password" ? "密码登录" : "短信登录" }}
      </div>
      <div class="login-type-toggle">
        <el-button
          :type="loginType === 'password' ? 'primary' : 'info'"
          @click="switchLoginType('password')"
          circle
        >
          <svg-icon icon-class="eye"/>
        </el-button>
        <el-button
          :type="loginType === 'sms' ? 'primary' : 'info'"
          @click="switchLoginType('sms')"
          icon="el-icon-mobile-phone"
          circle
          style="margin-left: 10%;"
        >
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { getCaptcha, login, sendSms } from '@/api/user'
import { setToken,getToken } from '@/utils/auth'

export default {
  name: 'Login',
  data() {
    // 手机号校验规则
    const validatePhone = (rule, value, callback) => {
      const phoneReg = /^1[3-9]\d{9}$/
      if (!value) {
        callback(new Error('请输入手机号'))
      } else if (!phoneReg.test(value)) {
        callback(new Error('请输入正确的11位手机号'))
      } else {
        callback()
      }
    }

    // 密码校验规则
    const validatePassword = (rule, value, callback) => {
      const pwdReg = /^[a-zA-Z0-9]{6,16}$/
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (value.length < 6) {
        callback(new Error('密码长度不能少于6位'))
      } else if (!pwdReg.test(value)) {
        callback(new Error('密码需为6-16位英文或数字'))
      } else {
        callback()
      }
    }

    // 验证码校验规则
    const validateCaptcha = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入验证码'))
      } else if (value.length !== 4) {
        callback(new Error('验证码应为4位字符'))
      } else {
        callback()
      }
    }

    // 短信验证码校验规则
    const validateSmsCode = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入短信验证码'))
      } else if (value.length !== 6) {
        callback(new Error('验证码应为6位数字'))
      } else {
        callback()
      }
    }

    return {
      loginForm: {
        phone: '',
        password: '',
        key: '',
        captchaKey: '',
        captcha: '',
        smsCode: ''
      },
      loginRules: {
        phone: [{ required: true, trigger: 'blur', validator: validatePhone }],
        password: [
          { required: true, trigger: 'blur', validator: validatePassword }
        ],
        captcha: [
          { required: true, trigger: 'blur', validator: validateCaptcha }
        ],
        smsCode: [
          { required: true, trigger: 'blur', validator: validateSmsCode }
        ]
      },
      loading: false,
      passwordType: 'password',
      captchaImg: '',
      loginType: 'password', // 登录方式：password/sms
      smsCountdown: 0, // 短信倒计时
      smsTimer: null, // 倒计时定时器
      redirect: undefined // 重定向路径
    }
  },
  watch: {
    $route: {
      handler(route) {
        this.redirect = route.query?.redirect
      },
      immediate: true
    }
  },
  methods: {
    // 验证单个字段
    validateField(field) {
      this.$refs.loginForm.validateField(field)
    },

    // 切换登录方式
    switchLoginType(type) {
      this.loginType = type
      this.$nextTick(() => {
        this.$refs.loginForm.clearValidate()
      })
    },

    // 发送短信验证码
    sendSmsCode() {
      this.$refs.loginForm.validateField('phone', (error) => {
        if (!error) {
          this.loading = true
          sendSms({ phone: this.loginForm.phone })
            .then(() => {
              this.$message.success('验证码发送成功')
              this.startSmsCountdown()
            })
            .catch((error) => {
              console.error('发送短信验证码失败:', error)
              this.$message.error(error.message || '验证码发送失败')
            })
            .finally(() => {
              this.loading = false
            })
        }
      })
    },

    // 开始短信倒计时
    startSmsCountdown() {
      this.smsCountdown = 60
      this.smsTimer = setInterval(() => {
        if (this.smsCountdown <= 0) {
          clearInterval(this.smsTimer)
          return
        }
        this.smsCountdown--
      }, 1000)
    },

    // 显示/隐藏密码
    showPwd() {
      this.passwordType = this.passwordType === 'password' ? '' : 'password'
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },

    // 处理登录
    async handleLogin() {
      try {
        const valid = await this.$refs.loginForm.validate()
        if (!valid) {
          return false
        }

        this.loading = true
        
        // 准备登录参数
        const params = {
          phone: this.loginForm.phone,
          type: this.loginType
        }
        
        if (this.loginType === 'password') {
          params.password = this.loginForm.password
          params.captcha = this.loginForm.captcha
          params.captchaKey = this.loginForm.captchaKey
        } else {
          params.smsCode = this.loginForm.smsCode
        }

        // 调用登录接口
        const { data } = await login(params)
        // 存储token
        setToken(data)
        this.$message.success('登录成功')
        
        // 跳转到首页或redirect页面
        setTimeout(() => {
          this.$router.push({ path: this.redirect || '/' })
        }, 1000)
      } catch (error) {
        console.error('登录失败:', error)
        this.$message.error(error.message || '登录失败')
        // 登录失败刷新验证码
        if (this.loginType === 'password') {
          this.updateCaptcha()
        }
      } finally {
        this.loading = false
      }
    },

    // 更新验证码
    async updateCaptcha() {
      try {
        const { data } = await getCaptcha()
        this.captchaImg = data.image
        this.loginForm.captchaKey = data.key
        this.loginForm.captcha = '' // 清空验证码输入
      } catch (error) {
        console.error('获取验证码失败:', error)
        this.$message.error('获取验证码失败')
      }
    }
  },
  mounted() {
    this.updateCaptcha()
  },
  beforeDestroy() {
    if (this.smsTimer) {
      clearInterval(this.smsTimer)
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

.identifyingCode {
  width: 120px;
  height: 46px;
  margin-left: 10px;
  border-radius: 5px;
  cursor: pointer;
}

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  background-image: url("../../imgs/R-C.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  min-height: 100vh;
  
  .el-input {
    display: inline-block;
    height: 55px;
    width: 90%;
    background: transparent;
    
    input {
      background: transparent;
      border: 0px;
      border-radius: 0px;
      color: $light_gray;
      height: 45px;
      caret-color: $cursor;

      &:-webkit-autofill {
        margin-top: 10px;
        height: 40px;
        box-shadow: 0 0 70px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }
  
  .logo {
    width: 80px;
    height: 60px;
    border-radius: 20px;
    margin-left: 40px;
  }
  
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}

$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 40px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 40px;
      color: $light_gray;
      margin: 0px auto 20px auto;
      font-weight: bold;
      margin-top: -55px;
      margin-left: 160px;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}

.login-type-toggle {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.loginType-toggle {
  color: #fff;
  text-align: center;
  margin-bottom: 15px;
}
</style>