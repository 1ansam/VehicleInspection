package com.yxf.vehicleinspection.bean

import java.io.Serializable

data class DataJson(
    val code: String,
    val data: List<Data>,
    val message: String
)

data class Data(
    val ajywlb: String,
    val hjywlb: String,
    val hphm: String,
    val hpzl: String,
    val lsh: String,
    val time: String
) : Serializable