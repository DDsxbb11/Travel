import request from '@/utils/request'

//分页获取景点信息
export const getFacilityByPage = (param) => {
    return request.post(`facility`,param)
}

//获取景点
export const getFacilityDetail = (id) => {
    return request.get(`facility/detail/${id}`)
}

//删除主题 /getFacilityByType
export const deleteFacility = (id) => {
    return request.delete(`facility/${id}`)
}

export const addOrUpdateFacility=(info)=> {
    return request.post(`facility/saveOrUpdate`,info)
}

//分页获取景点信息
export const getFacilityByType = (param) => {
    return request.post(`facility/getFacilityByType`,param)
}