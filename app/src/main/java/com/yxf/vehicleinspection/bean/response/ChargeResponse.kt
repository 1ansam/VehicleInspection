package com.yxf.vehicleinspection.bean.response

/**
 * @author yxf
 * @param Spbh 商品编号
 * @param Spmc 商品名称
 * @param Spdj 商品单价
 */
data class ChargeResponse(
    val Spbh: String,
    val Spmc: String,
    val Spdj: String
)