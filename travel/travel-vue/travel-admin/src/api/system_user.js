import request from '@/utils/request'

//分页获取景点信息
export const getSystemUserByPage = (param) => {
    return request.post(`systemUser`,param)
}


//删除主题
export const deleteSystemUser = (id) => {
    return request.delete(`systemUser/${id}`)
}

export const updateSystemUserStatus=(info)=> {
    return request.post(`systemUser/updateStatus`,info)
}

export const updateSystemUserPassword=(info)=> {
    return request.post(`systemUser/updatePassword`,info)
}