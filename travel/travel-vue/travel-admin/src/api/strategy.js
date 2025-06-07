import request from '@/utils/request'

//分页获取攻略信息
export const getStrategyByPage = (param) => {
    return request.post(`strategy/getStrategyByPage`,param)
}

//获取攻略详情
export const getStrategyDetail = (id) => {
    return request.get(`strategy/detail/${id}`)
}

//删除攻略
export const deleteStrategy = (id) => {
    return request.delete(`strategy/${id}`)
}

//提交添加或更新攻略
export const addOrUpdateStrategy=(info)=>{
    return request.post("strategy/saveOrUpdate",info)
}
