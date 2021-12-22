package com.yxf.vehicleinspection.bean.response

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author yxf
 * @param Spbh 商品编号
 * @param Spmc 商品名称
 * @param Spdj 商品单价
 */
@Entity(tableName = "ChargeItem")
data class ChargeItemR004Response(
    @PrimaryKey val Spbh: String,
    val Spmc: String,
    val Spdj: String
)