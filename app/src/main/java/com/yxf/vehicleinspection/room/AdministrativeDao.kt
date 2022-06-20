package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yxf.vehicleinspection.bean.response.AdministrativeR023Response

/**
 *   author:yxf
 *   time:2021/12/14
 *   行政区划数据库操作类
 */
@Dao
interface AdministrativeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdministrativeList(administrativeList : List<AdministrativeR023Response>) : List<Long>

    @Query("DELETE FROM Administrative")
    suspend fun deleteAdministrative()

    @Query("SELECT * FROM Administrative WHERE Xzqhmc LIKE '%'||:xzqhmc||'%'")
    fun getAdministrativeListFromMc(xzqhmc : String) : LiveData<List<AdministrativeR023Response>>
}