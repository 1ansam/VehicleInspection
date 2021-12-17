package com.yxf.vehicleinspection.bean.response

import java.io.Serializable

/**
 *   author:yxf
 *   time:2021/12/9
 */
data class AppointmentAjR010Response(
    val clsbdh : String,
    val hphm : String,
    val hpzl : String,
    val id : Int,
    val sjr : String,
    val sjrdh : String,
    val sjrsfzh : String,
): Serializable