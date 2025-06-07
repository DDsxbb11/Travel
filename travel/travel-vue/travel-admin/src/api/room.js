import request from '@/utils/request'

//分页获取景点信息
export const getRoomByPage = (param) => {
    return request.post(`room`,param)
}

//获取景点
export const getRoomDetail = (id) => {
    return request.get(`room/detail/${id}`)
}

//删除主题
export const deleteRoom = (id) => {
    return request.delete(`room/${id}`)
}

export const addOrUpdateRoom=(info)=> {
    return request.post(`room/saveOrUpdate`,info)
}
