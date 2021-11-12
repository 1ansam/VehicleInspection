package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/12
 */
data class ArtificialProjectW011Request<T>(
    val Jyxm : String,
    val Jcsj : T
)
