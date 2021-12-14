package com.yxf.vehicleinspection.bean.response

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   author:yxf
 *   time:2021/12/14
 */
@Entity(tableName = "Administrative")
data class AdministrativeR023Response(
    @PrimaryKey val Id : Int,
    val Xzqhdm : String,
    val Xzqhmc : String,
) {
}