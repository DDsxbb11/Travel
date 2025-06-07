import request from '@/utils/request'

//分页获取景点信息
export const getAttractionOrderPage = (param) => {
    return request.post(`/order/attraction`,param)
}

//删除景点订单
export const deleteAttractionOrder = (id) => {
    return request.delete(`/order/attraction/${id}`)
}

//分页获取酒店信息
export const getHotelOrderPage = (param) => {
    return request.post(`/order/hotel`,param)
}

//删除酒店订单
export const deleteHotelOrder = (id) => {
    return request.delete(`/order/hotel/${id}`)
}

//分页获取攻略信息
export const getStrategyOrderPage = (param) => {
    return request.post(`/order/strategy`,param)
}

//删除攻略订单
export const deleteStrategyOrder = (id) => {
    return request.delete(`/order/strategy/${id}`)
}