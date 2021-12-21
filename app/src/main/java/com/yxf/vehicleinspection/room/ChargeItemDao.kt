package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yxf.vehicleinspection.bean.response.ChargeR004Response

/**
 *   author:yxf
 *   time:2021/12/21
 */
@Dao
interface ChargeItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChargeItem(chargeR004ResponseList : List<ChargeR004Response>) : List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChargeItem(chargeR004ResponseList : List<ChargeR004Response>)

    @Query("DELETE FROM ChargeItem")
    suspend fun deleteChargeItem()

    @Query("SELECT * FROM ChargeItem")
    fun getChargeItemFromDb() : LiveData<List<ChargeR004Response>>
}