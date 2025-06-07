import axios from 'axios'
import {MessageBox, Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'

const service = axios.create({
  baseURL: "http://localhost:8081", // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    const whiteList = ['login/captcha', 'login']
    const isWhiteList = whiteList.some(path => config.url.includes(path))
    if (isWhiteList) {
      return config
    }
    // do something before request is sent
    const token = getToken()
    if (!token) {
      // 如果没有token，可以跳转到登录页或抛出错误
      window.location.href = 'http://localhost:9528/#/login'
      return Promise.reject(new Error('请先登录系统'))
    }
    config.headers['Token'] = token
    
    return config
  },
  error => {
    // do something with request error
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    const res = response.data
    
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 200) {
      Message({
        message: res.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      
      if (res.code === 601 || res.code === 602) {
        window.location.href = 'http://localhost:9528/#/login' 
      }
      return Promise.reject(new Error(res.message || 'Error'))
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
