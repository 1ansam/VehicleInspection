package com.yxf.vehicleinspection.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yxf.vehicleinspection.bean.JsCsCode

/**
 *   author:yxf
 *   time:2021/10/8
 */
@Dao
interface JsCsCodeDao {
    @Insert
    fun insertJsCsCode(jsCsCode: JsCsCode)

    @Query("delete from JsCsCode")
    fun deleteAll()
}