import request from '@/utils/request'

//分页获取景点信息
export const getLableByPage = (param) => {
    return request.post(`lable`,param)
}

//获取景点
export const getLableDetail = (id) => {
    return request.get(`lable/detail/${id}`)
}

//删除主题
export const deleteLable = (id) => {
    return request.delete(`lable/${id}`)
}

export const addOrUpdateLable=(info)=> {
    return request.post(`lable/saveOrUpdate`,info)
}
