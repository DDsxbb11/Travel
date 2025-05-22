import request from '@/utils/request'

//分页获取酒店信息
export const getHotelByPage = (pageNum, pageSize,queryVo) => {
    return request.post(`hotel/${pageNum}/${pageSize}`,queryVo)
}

//获取日记详情
export const getHotelDetail = (id) => {
    return request.get(`hotel/detail/${id}`)
}

//提交酒店订单

export const createHotelOrder=(info)=>{
    return request.post("system/order/hotel",info)
}