package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/10
 */
data class SaveSignatureW006Request(
    val ID : Int,
    val Lsh : String,
    val Jccs : Int,
    val Hphm : String,
    val Qm : String,
    val Jcsj : String,
    val Jcxm : String,
    val Ryxm : String,
    val Bcaj : String,
    val BcHj : String,
) {
}