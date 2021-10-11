package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/10/11
 *   查询接口返回数据类型
 */
data class  CommonResponse<T>(

    val Body: List<T>?,
    val Code: String?,
    val Message: String?
)
