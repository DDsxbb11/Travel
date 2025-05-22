import request from '@/utils/request'

//分页获取攻略信息
export const getStrategyByPage = (pageNum, pageSize,queryVo) => {
    return request.post(`strategy/${pageNum}/${pageSize}`,queryVo)
}

//获取攻略详情
export const getStrategyDetail = (id) => {
    return request.get(`strategy/detail/${id}`)
}

//提交酒店订单

export const createStrategyOrder=(info)=>{
    return request.post("system/order/strategy",info)
}
