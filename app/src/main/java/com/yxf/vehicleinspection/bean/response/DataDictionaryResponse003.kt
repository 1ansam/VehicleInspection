package com.yxf.vehicleinspection.bean.response

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   author:yxf
 *   time:2021/11/9
 *   @param Id 主键
 *   @param Fl 分类
 *   @param FlMc 分类名称
 *   @param Dm 子类代码
 *   @param Mc 子类名称
 */
@Entity(tableName = "DataDictionary")
data class DataDictionaryResponse003(
    @PrimaryKey val Id : Int,
    val Fl : String,
    val FlMc : String,
    val Dm : String,
    val Mc : String,
) {
}