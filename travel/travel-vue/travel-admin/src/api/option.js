import request from '@/utils/request'

//获取主题列表
export const getAllTheme = (id) => {
    return request.get(`option/theme`)
}

//获取省份列表
export const getAllProvince = (id) => {
    return request.get(`option/province`)
}

//获取城市列表
export const getCityByProvincId = (id) => {
    return request.get(`option/city/${id}`)
}

//获取地区列表 location
export const getDistrictByCityId = (id) => {
    return request.get(`option/district/${id}`)
}

//获取地区列表 getProvinceAndCityAndDistrictCascade
export const getProvinceAndCityCascade = () => {
    return request.get(`option/getProvinceAndCityCascade`)
}

//获取地区列表 getProvinceAndCityAndDistrictCascade
export const getProvinceAndCityAndDistrictCascade = () => {
    return request.get(`option/getProvinceAndCityAndDistrictCascade`)
}

//获取攻略标签
export const getAllLable = (id) => {
    return request.get(`option/getAllStrategyLable`)
}


