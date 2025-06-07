import request from '@/utils/request'

//分页获取景点信息
export const getThemeByPage = (pageNum, pageSize,param) => {
    return request.post(`theme/${pageNum}/${pageSize}`,param)
}

//获取景点
export const getThemeDetail = (id) => {
    return request.get(`theme/detail/${id}`)
}

//删除主题
export const deleteTheme = (id) => {
    return request.delete(`theme/${id}`)
}

export const addOrUpdateTheme=(info)=> {
    return request.post(`theme/saveOrUpdate`,info)
}
