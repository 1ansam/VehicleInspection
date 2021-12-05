package com.yxf.vehicleinspection.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response

/**
 *   author:yxf
 *   time:2021/11/9
 *   Room Dao
 *   用于操作数据库
 */
@Dao
interface DataDictionaryDao {
    /**
     * 插入数据列表
     * @param dataDictionaryListResponse 数据对象列表
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataDictionary(dataDictionaryListResponse : List<DataDictionaryR003Response>) : List<Long>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataDictionary(dataDictionary : DataDictionaryR003Response) : Long
    /**
     * 更新数据列表
     * 更新操作只可更新当前数据库中存在的数据
     * 如数据库结构发生改变请使用database Migrate迁移数据库版本
     * @param dataDictionaryListResponse 数据对象列表
     */
    @Update
    suspend fun updateDataDictionary(dataDictionaryListResponse : List<DataDictionaryR003Response>)
    @Update
    suspend fun updateDateDictionary(dataDictionary : DataDictionaryR003Response)
    /**
     *  删除数据库中所有数据
     */
    @Query("DELETE FROM DataDictionary")
    suspend fun deleteDataDictionary()
    /**
     *  约束Fl和Dm字段得到Mc
     *  @param Fl 分类代码
     *  @param Dm 子类代码
     *  @return LiveData
     */
    @Query("SELECT Mc FROM DataDictionary WHERE Fl LIKE :Fl AND Dm LIKE :Dm ")
    fun getMc(Fl : String, Dm : String) : LiveData<String>

    @Query("SELECT Dm FROM DataDictionary WHERE Fl LIKE :Fl AND Mc LIKE :Mc")
    fun getDm(Fl: String, Mc : String) : LiveData<String>

    @Query("SELECT * FROM DataDictionary")
    fun getDataDictionaryFromDb() : LiveData<List<DataDictionaryR003Response>>
    /**
     *  约束Fl得到该Fl所对应的对象列表
     *  @param Fl 分类代码
     *  @return LiveData
     */
    @Query("SELECT * FROM DataDictionary WHERE Fl LIKE :Fl")
    fun getListFromFl(Fl: String) : LiveData<List<DataDictionaryR003Response>>

    @Query("SELECT * FROM DataDictionary WHERE Fl IN (:FlList)")
    fun getListFromFl(FlList : List<String>) : LiveData<List<DataDictionaryR003Response>>


    @Query("SELECT Mc FROM DataDictionary WHERE Fl LIKE :Fl")
    fun getListMcFromFl(Fl : String) : LiveData<List<String>>
    /**
     *  根据Id = 1 查询数据库中是否存在数据
     *  @return Id = 1 的对象
     */
    @Query("SELECT * FROM DataDictionary WHERE Id = :Id LIMIT 1")
    fun getDataDictionaryExist(Id : Int) : LiveData<DataDictionaryR003Response>

}