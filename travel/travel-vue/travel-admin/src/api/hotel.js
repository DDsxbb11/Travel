import request from '@/utils/request'

//分页获取酒店信息
export const getHotelByPage = (pageNum, pageSize,param) => {
    return request.post(`hotel/${pageNum}/${pageSize}`,param)
}

//获取酒店详情
export const getHotelDetail = (id) => {
    return request.get(`hotel/detail/${id}`)
}

//删除酒店
export const deleteHotel = (id) => {
    return request.delete(`hotel/${id}`)
}

//删除酒店政策
export const deletePolicy = (id) => {
    return request.delete(`hotel/policy/${id}`)
}

//添加酒店政策
export const savePolicy = (info) => {
    return request.delete(`hotel/savePolicy`,info)
}
