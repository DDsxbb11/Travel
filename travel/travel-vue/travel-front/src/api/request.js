import request from '@/utils/request'



//获取省份
export const getProvince = () => {
    return request.get(`address/province`)
}
//分页获取日记信息
export const getRecordByPage = (pageNum, pageSize,queryVo) => {
    return request.post(`record/${pageNum}/${pageSize}`,queryVo)
}
//获取用户信息
export const getUserInfo=()=>{
    return request.get("system/user")
}

//收藏
export const addCollect=(info)=>{
    return request.post("system/collections",info)
}
//取消收藏
export const delCollect=(id)=>{
    return request.delete(`system/collections/${id}`)
}
//获取地区选择信息
export const getOptions=()=>{
    return request.get("/address/options")
}

//获取日记详情
export const getRecordDetail = (id) => {
    return request.get(`record/detail/${id}`)
}

//获取我的收藏
export const getMyCollect = (pageNum, pageSize) => {
    return request.get(`system/collections/${pageNum}/${pageSize}`)
}
//获取订单
export const getOrder = (pageNum, pageSize,type) => {
    return request.get(`system/order/${type}/${pageNum}/${pageSize}`)
}

//获取订单
export const delOrder = (type,id) => {
    return request.delete(`system/order/${type}/${id}`)
}

//添加浏览历史
export const addHistory=(info)=>{
    return request.post("system/history",info)
}

//获取浏览历史
export const getHistory = (pageNum, pageSize) => {
    return request.get(`system/history/${pageNum}/${pageSize}`)
}