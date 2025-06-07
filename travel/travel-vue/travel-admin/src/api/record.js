import request from '@/utils/request'

//分页获取攻略信息
export const getRecordByPage = (param) => {
    return request.post(`record/getRecordByPage`,param)
}

//删除攻略
export const deleteRecord = (id) => {
    return request.delete(`record/${id}`)
}
