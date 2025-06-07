import request from '@/utils/request'

//分页获取景点信息
export const getAttractionByPage = (pageNum, pageSize,param) => {
    return request.post(`attraction/${pageNum}/${pageSize}`,param)
}

//获取景点详情
export const getAttractionDetail = (id) => {
    return request.get(`attraction/detail/${id}`)
}

//删除景点
export const deleteAttraction = (id) => {
    return request.delete(`attraction/${id}`)
}

//提交添加或更新景点
export const addOrUpdateAttraction=(info)=>{
    return request.post("attraction/saveOrUpdate",info)
}

//获取景点主题
export const getAttractionTheme = (id) => {
    return request.get(`/attraction/theme`)
}