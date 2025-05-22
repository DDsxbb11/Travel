import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: "http://localhost:8080", 
  timeout: 5000
})

// request interceptor
service.interceptors.request.use(
  config => {

    // 检查是否是/system/开头的路径
    const isSystemPath = config.url && config.url.startsWith('/system/')
    
    // 如果是系统路径，强制要求Token
    if (isSystemPath) {
      const token = getToken()
      if (!token) {
        // 如果没有token，可以跳转到登录页或抛出错误
        this.$router.push({ path: '/login' })
        return Promise.reject(new Error('请先登录系统'))
      }
      config.headers['token'] = token
    } 
    else if (getToken() != null) {
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      if (res.code === 601 || res.code === 602 ) {
        this.$router.push({ path: '/login' })
      }
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
