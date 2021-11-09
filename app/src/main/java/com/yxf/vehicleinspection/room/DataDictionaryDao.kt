package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yxf.vehicleinspection.bean.JsCsCode
import com.yxf.vehicleinspection.bean.response.DataDictionaryResponse003

/**
 *   author:yxf
 *   time:2021/11/9
 */
@Dao
interface DataDictionaryDao {
    @Insert
    suspend fun insertJsCsCode(dataDictionaryList : List<DataDictionaryResponse003>)

    @Query("DELETE FROM DataDictionary")
    suspend fun deleteAll()

    @Query("SELECT Mc FROM DataDictionary WHERE Fl LIKE :Fl AND Dm LIKE :Dm ")
    fun getMc(Fl : String, Dm : String) : LiveData<String>

    @Query("SELECT Dm FROM DataDictionary WHERE Fl LIKE :Fl AND FlMc LIKE :FlMc")
    fun getDM(Fl : String, FlMc : String) : LiveData<String>

    @Query("SELECT * FROM DataDictionary WHERE Id = 1")
    fun getJsCsCodeExist() : LiveData<DataDictionaryResponse003>
    @Update
    suspend fun updateJsCsCode(dataDictionaryList : List<DataDictionaryResponse003>)
}