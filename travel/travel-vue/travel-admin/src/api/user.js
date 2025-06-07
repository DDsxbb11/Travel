import request from '@/utils/request'

export function login(data) {
  return request.post("login",data)
}

export function getUserInfo() {
  return request({
    url: '/user',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

//获取验证码
export const getCaptcha=()=>{
  return request.get(
    "login/captcha"
    )
}

//修改密码
export const changePassword=(data)=>{
  return request.post("user/changePassword",data)
}

//修改用户信息
export const changeUserInfo=(data)=>{
  return request.post("user/changeUserInfo",data)
}
