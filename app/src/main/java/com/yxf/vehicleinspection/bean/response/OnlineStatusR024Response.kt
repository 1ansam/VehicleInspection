package com.yxf.vehicleinspection.bean.response

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/12/20
 */
data class OnlineStatusR024Response(
    val Ajlsh : String,
    val Xszt : String,
    val Jcgw : List<Jcgw>
):Serializable
data class Jcgw(
    val Gwmc : String,
    val Gwzt : String,
)