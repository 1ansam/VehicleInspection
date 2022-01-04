package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/10/12
 *   封装公共请求
 *   @param body 请求体列表
 */
data class CommonRequest<T>(
    val body : List<T>
)