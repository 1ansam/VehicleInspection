package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response

/**
 *   author:yxf
 *   time:2021/11/11
 *   系统变量数据库操作类
 */
@Dao
interface SystemParamsDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSystemParams(systemParamsList: List<SystemParamsR015Response>) : List<Long>

    @Update
    suspend fun updateSystemParams(systemParamsList: List<SystemParamsR015Response>)

    @Query("SELECT * FROM SystemParams WHERE Sjlb = :Sjlb")
    fun getSystemParamsFromDb(Sjlb: String) : LiveData<SystemParamsR015Response>
    @Query("SELECT * FROM SystemParams WHERE Sjlb = 'AJ'")
    fun getSystemParamsFromDb() : LiveData<List<SystemParamsR015Response>>
    @Query("DELETE FROM SystemParams")
    suspend fun deleteSystemParams()
    @Query("SELECT * FROM SystemParams WHERE Sjlb = 'AJ' OR Sjlb = 'HJ' LIMIT 1")
    fun getSystemParamsExist() : LiveData<SystemParamsR015Response>
    @Query("SELECT Jyjgbh FROM SystemParams WHERE Sjlb LIKE :Sjlb")
    fun getJyjgbh(Sjlb : String) : LiveData<String>

    @Query("SELECT Web_Pass FROM SystemParams WHERE Sjlb LIKE :Sjlb")
    fun getWebPass(Sjlb: String) : LiveData<String>
    @Query("SELECT LshSzm FROM SystemParams WHERE Sjlb LIKE 'AJ'")
    fun getLshSzm() : LiveData<String>
}