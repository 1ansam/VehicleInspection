package com.yxf.vehicleinspection.bean.response

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   author:yxf
 *   time:2021/11/11
 */
@Entity(tableName = "SystemParams")
data class SystemParamsR015Response(
    val Jyjgbh : String,
    val Jcsjyxq : String,
    val Jcsjbcnx : String,
    val Web_Pass : String,
    val Dw_Xkzh : String,
    val Dw_Dhhm : String,
    val Dw_mc : String,
    val Dw_dz : String,
    val SFF : String,
    val KPF : String,
    val LshSzm : String,
    @PrimaryKey val Sjlb : String,
    val Appid : String,
    val Md5key : String,
    val C : String,
    val C1 : String

    )