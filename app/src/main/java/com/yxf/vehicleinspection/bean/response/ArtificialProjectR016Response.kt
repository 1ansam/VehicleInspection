package com.yxf.vehicleinspection.bean.response

/**
 *   author:yxf
 *   time:2021/11/9
 */
data class ArtificialProjectR016Response(
    val ID : Int,
    val Jyyw : String,
    val Xmsl : Int,
    val Xmlb : List<ArtificialProject>

)
data class ArtificialProject(
    val ID : Int,
    val Wjcxdm : String,
    val Wjcxmc : String,
    val Fldm : String,
    val Xmdm : String,
    val Xmms : String,
    val Sycx : String,

)