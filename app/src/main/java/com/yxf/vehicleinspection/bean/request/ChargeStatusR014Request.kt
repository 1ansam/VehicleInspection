package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/12/22
 *   查询收费状态
 *   @param oid 订单号
 *   @param Ajlsh 安检流水号
 */
data class ChargeStatusR014Request(
    val oid : String,
    val Ajlsh : String,
) {
}