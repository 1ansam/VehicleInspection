package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/10/12
 */
data class CommonRequest<T>(
    val body : List<T>
)