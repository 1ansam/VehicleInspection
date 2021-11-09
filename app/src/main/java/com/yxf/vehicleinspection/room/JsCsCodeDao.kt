package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yxf.vehicleinspection.bean.JsCsCode

/**
 *   author:yxf
 *   time:2021/10/8
 */
@Dao
interface JsCsCodeDao {
    @Insert
    fun insertJsCsCode(jsCsCodeList : List<JsCsCode>)

    @Query("DELETE FROM JsCsCode")
    fun deleteAll()

    @Query("SELECT Mc FROM JsCsCode WHERE Fl LIKE :fl AND Dm LIKE :dm ")
    fun getMc(fl : String, dm : String) : String

    @Query("SELECT Dm FROM JsCsCode WHERE Fl LIKE :fl AND FlMc LIKE :flmc")
    fun getDM(fl : String, flmc : String) : String

    @Query("SELECT * FROM JsCsCode WHERE InfoID = 1")
    fun getJsCsCodeExist() : LiveData<JsCsCode>
    @Update
    fun updateJsCsCode(jsCsCodeList : List<JsCsCode>)
}