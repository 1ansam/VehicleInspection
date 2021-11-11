package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response

/**
 *   author:yxf
 *   time:2021/11/9
 */
@Dao
interface DataDictionaryDao {
    @Insert
    suspend fun insertDataDictionary(dataDictionaryListResponse : List<DataDictionaryR003Response>)

    @Query("DELETE FROM DataDictionary")
    suspend fun deleteAll()

    @Query("SELECT Mc FROM DataDictionary WHERE Fl LIKE :Fl AND Dm LIKE :Dm ")
    fun getMc(Fl : String, Dm : String) : LiveData<String>

    @Query("SELECT Mc FROM DataDictionary WHERE Fl LIKE :Fl Order by Dm")
    fun getMcList(Fl: String) : LiveData<List<String>>

    @Query("SELECT Dm FROM DataDictionary WHERE Fl LIKE :Fl AND FlMc LIKE :FlMc")
    fun getDM(Fl : String, FlMc : String) : LiveData<String>

    @Query("SELECT * FROM DataDictionary WHERE Fl LIKE :Fl")
    fun getListFromFl(Fl: String) : LiveData<List<DataDictionaryR003Response>>
    @Query("SELECT * FROM DataDictionary WHERE Id = 1")
    fun getDataDictionaryExist() : LiveData<DataDictionaryR003Response>
    @Update
    suspend fun updateDataDictionary(dataDictionaryListResponse : List<DataDictionaryR003Response>)
}