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
    fun insertJsCsCode(jsCsCode: List<JsCsCode>)

    @Query("DELETE FROM JsCsCode")
    fun deleteAll()

    @Query("SELECT Mc FROM JsCsCode WHERE Fl LIKE :fl AND Dm LIKE :dm ")
    fun getMc(fl : String, dm : String) : String

    @Query("SELECT Dm FROM JsCsCode WHERE Fl LIKE :fl AND FlMc LIKE :flmc")
    fun getDM(fl : String, flmc : String) : String
}