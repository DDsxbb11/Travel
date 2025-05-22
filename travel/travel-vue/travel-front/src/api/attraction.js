import request from '@/utils/request'

//分页获取景点信息
export const getAttractionByPage = (pageNum, pageSize,queryVo) => {
    return request.post(`attraction/${pageNum}/${pageSize}`,queryVo)
}

//获取景点详情
export const getAttractionDetail = (id) => {
    return request.get(`attraction/detail/${id}`)
}

//提交景点订单
export const submitAttractionOrder=(info)=>{
    return request.post("system/order/attraction",info)
}

//获取景点主题
export const getAttractionTheme = (id) => {
    return request.get(`/attraction/theme`)
}