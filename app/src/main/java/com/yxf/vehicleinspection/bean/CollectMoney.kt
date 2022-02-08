package com.yxf.vehicleinspection.bean

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/12/21
 *   收费信息集合
 */
data class CollectMoney(
    val appid : String,
    val c : String,
    val oid : String,
    val amt : Int,
    val trxreserve : String,
    val sign : String,
    val key : String,
) : Serializable {

}