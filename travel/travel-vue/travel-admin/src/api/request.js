import request from '@/utils/request'

//获取所有商品
export const getAllGoods=()=>{
    return request.get("goods/getAll")
}

//获取用户信息
export const getUserInfo=()=>{
    return request.get("system")
}

//查询所有供应商名称
export const getAddStockRecordChoose=()=>{
    return request.get("supplier/stockRecord/choose")
}

//查询所有流水号
export const getUUIDList=()=>{
    return request.get("table/returnRecord/uuid")
}

//分页获取供应商
export const getSupplierByPage = (pageNum, pageSize,input) => {
    return request.get(`supplier/${pageNum}/${pageSize}?name=${input}`)
}

//分页获取商品
export const getGoodsByPage = (pageNum, pageSize,input1,input2,input3) => {
    return request.get(`goods/${pageNum}/${pageSize}`,{
            params:{
                goodsName:input1,
                supplierName:input2,
                goodsType:input3
            }
        }
    )
}

//分页获取消费者
export const getCustomerByPage = (pageNum, pageSize,input) => {
    return request.get(`customer/${pageNum}/${pageSize}?name=${input}`)
}

//分页获取用户
export const getUserByPage = (pageNum, pageSize,input) => {
    return request.get(`system/${pageNum}/${pageSize}?nickname=${input}`)
}

//分页销售记录表
export const getSellRecordByPage = (pageNum, pageSize,input1,input2) => {
    return request.get(`table/sellRecord/${pageNum}/${pageSize}?salesperson=${input1}&date=${input2}`)
}

//分页退货记录表
export const getReturnRecordByPage = (pageNum, pageSize,input1,input2) => {
    return request.get(`table/returnRecord/${pageNum}/${pageSize}?operator=${input1}&date=${input2}`)
}

//分页库存记录表
export const getStockRecordByPage = (pageNum, pageSize,input1,input2) => {
    return request.get(`table/stockRecord/${pageNum}/${pageSize}?buyer=${input1}&date=${input2}`)
}

//更新商品信息
export const updateGoodsById=(info)=>{
    return request.put("goods",info)
}

//更新客户信息
export const updateCustomerById=(info)=>{
    return request.put("customer",info)
}

//更新供应商信息
export const updateSupplierById=(info)=>{
    return request.put("supplier",info)
}

//更新销售记录表
export const updateSellRecordById=(info)=>{
    return request.put("table/sellRecord",info)
}

//更新退货记录
export const updateReturnRecordById=(info)=>{
    return request.put("table/returnRecord",info)
}

//更新库存记录
export const updateStockRecordById=(info)=>{
    return request.put("table/stockRecord",info)
}

//更新用户信息
export const updateUserById=(info)=>{
    return request.put("system",info)
}

//更新密码
export const updatePasswordById=(info)=>{
    return request.put("system/password",info)
}

//添加商品
export const addGoods=(info)=>{
    return request.post("goods",info)
}

//添加消费者
export const addCustomer=(info)=>{
    return request.post("customer",info)
}

//添加供应商
export const addSupplier=(info)=>{
    return request.post("supplier",info)
}

//添加用户
export const addUser=(info)=>{
    return request.post("user",info)
}

//添加库存记录
export const addStockRecord=(info)=>{
    return request.post("table/stockRecord",info)
}

//添加退货记录
export const addReturnRecord=(info)=>{
    return request.post("table/returnRecord",info)
}

//添加销售记录
export const addSellRecord=(info)=>{
    return request.post("table/sellRecord",info)
}

//删除用户
export const deleteUserById=(id)=>{
    return request.delete(`/system/${id}`)
}

//删除消费者
export const deleteCustomerById=(id)=>{
    return request.delete(`/customer/${id}`)
}

//删除供应商
export const deleteSupplierById=(id)=>{
    return request.delete(`/supplier/${id}`)
}

//删除商品
export const deleteGoodsById=(id)=>{
    return request.delete(`/goods/${id}`)
}

//商品入库
export const goodsWarehousing=(info)=>{
    return request.post("/table/goodsWarehousing",info)
}

//商品销售
export const sell=(info)=>{
    return request.post("/table/sell",info)
}

//商品退货
export const returnGoods=(info)=>{
    return request.post("/table/returnGoods",info)
}



