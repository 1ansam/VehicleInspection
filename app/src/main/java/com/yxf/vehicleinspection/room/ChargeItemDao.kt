package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yxf.vehicleinspection.bean.response.ChargeItemR004Response

/**
 *   author:yxf
 *   time:2021/12/21
 *   收费项目数据库操作类
 */
@Dao
interface ChargeItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChargeItem(chargeItemR004ResponseList : List<ChargeItemR004Response>) : List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChargeItem(chargeItemR004ResponseList : List<ChargeItemR004Response>)

    @Query("DELETE FROM ChargeItem")
    suspend fun deleteChargeItem()

    @Query("SELECT * FROM ChargeItem")
    fun getChargeItemFromDb() : LiveData<List<ChargeItemR004Response>>
}