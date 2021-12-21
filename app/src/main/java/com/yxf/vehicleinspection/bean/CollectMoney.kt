package com.yxf.vehicleinspection.bean

/**
 *   author:yxf
 *   time:2021/12/21
 */
data class CollectMoney(
    val appid : String,
    val c : String,
    val oid : String,
    val amt : String,
    val trxreserve : String,
    val sign : String,
    val key : String,
) {

}