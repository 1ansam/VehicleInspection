package com.yxf.vehicleinspection.bean.request

/**
 *   author:yxf
 *   time:2021/11/10
 */
data class SaveSignatureW006Request(
    val ID : Int,
    val Hphm : String,
    val Qm : String,
    val Jcsj : String,
    val Jcxm : String,
    val Ryxm : String,
    val Ajywlb : String,
    val Hjywlb : String,
    val Ajlsh : String,
    val Hjlsh : String,
    val Ajjccs : Int,
    val Hjjccs : Int
)