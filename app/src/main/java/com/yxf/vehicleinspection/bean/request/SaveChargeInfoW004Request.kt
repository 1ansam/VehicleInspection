package com.yxf.vehicleinspection.bean.request

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/12/22
 */
data class SaveChargeInfoW004Request(
    val chargeOrder : ChargeOrder,
    val chargeDetails : List<ChargeDetail>
) : Serializable {
}

data class ChargeOrder(
    val Ajlsh : String,
    val Appid : String,
    val C : String,
    val Oid : String,
    val Amt : String,
    val Trxreserve : String,
    var Sign : String,
    val Addtime : String,
    val Queryflag : String = "0"
) {


}

data class ChargeDetail(
    val Orderno : String,
    val Detailno : String,
    val Goodsno : String,
    val Goodsname : String,
    val Price : String,
    val Zje : String,
    val Num : String = "1",

) : Serializable {

}
