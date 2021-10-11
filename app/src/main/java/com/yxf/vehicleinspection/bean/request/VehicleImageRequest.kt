package com.yxf.vehicleinspection.bean.request

/**
 * 调用接口，查询对应流水号（Lsh）的检验照片，默认查询相同照片的最大次数。参数流水号（Lsh）不能为空。
 * @param Lsh 流水号
 */
data class VehicleImageRequest(
    val Lsh : String
)