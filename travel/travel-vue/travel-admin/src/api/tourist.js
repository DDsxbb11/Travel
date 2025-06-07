import request from '@/utils/request'

//分页获取景点信息
export const getTouristByPage = (param) => {
    return request.post(`tourist`,param)
}


//删除主题
export const deleteTourist = (id) => {
    return request.delete(`tourist/${id}`)
}

export const updateTouristStatus=(info)=> {
    return request.post(`tourist/updateStatus`,info)
}
