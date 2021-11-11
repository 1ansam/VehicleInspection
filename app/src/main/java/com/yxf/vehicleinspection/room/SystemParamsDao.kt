package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response

/**
 *   author:yxf
 *   time:2021/11/11
 */
@Dao
interface SystemParamsDao {
    @Insert
    suspend fun insertSystemParams(systemParamsList: List<SystemParamsR015Response>)

    @Update
    suspend fun updateSystemParams(systemParamsList: List<SystemParamsR015Response>)

    @Query("SELECT * FROM SystemParams WHERE Sjlb = 'AJ' OR Sjlb = 'HJ' ")
    fun getSystemParamsExist() : LiveData<SystemParamsR015Response>
    @Query("SELECT Jyjgbh FROM SystemParams WHERE Sjlb LIKE :Sjlb")
    fun getJyjgbh(Sjlb : String) : LiveData<String>
}