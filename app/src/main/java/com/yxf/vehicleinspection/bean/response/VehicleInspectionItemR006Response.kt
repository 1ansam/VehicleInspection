package com.yxf.vehicleinspection.bean.response

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/11/4
 */
data class VehicleInspectionItemR006Response(
    val ID : Int?,
    val Lsh : String,
    val Jcxh : String?,
    val Jccs : Int,
    val Ajywlb : String,
    val Hjywlb : String,
    val Jcxm : String,
    val Xmmc : String,
    val Jcry_01 : String?,
    val Jcry_02 : String?,
    val Jckssj : String?,
    val Jcjssj : String?,
    val Jcpj : String?,
    val Jczt : String?,

    ): Serializable {
}