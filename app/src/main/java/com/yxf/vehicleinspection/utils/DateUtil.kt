package com.yxf.vehicleinspection.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 *   author:yxf
 *   time:2021/11/11
 */
object DateUtil {
    fun date2String(date: Date, format: String): String {
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(date)
    }

    fun string2Date(stringDate: String, format: String): Date {
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.parse(stringDate)
    }
    fun string2String(stringDate: String,oldFormat: String,newFormat: String) : String{
        var simpleDateFormat = SimpleDateFormat(oldFormat)
//        var simpleDateFormat = SimpleDateFormat(format)
        val date = simpleDateFormat.parse(stringDate)
        simpleDateFormat = SimpleDateFormat(newFormat)
        return simpleDateFormat.format(date)
    }
}