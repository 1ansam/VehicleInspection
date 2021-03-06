package com.yxf.vehicleinspection.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
import com.yxf.vehicleinspection.repository.DataDictionaryRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/10/8
 */
class DataDictionaryViewModel(private val dataDictionaryRepository: DataDictionaryRepository) : ViewModel() {
    var insertEnd = MutableLiveData<Boolean>()
    /**
     * 从远程服务器获取数据字典
     */
    fun getDataDictionary(): Pair<LiveData<List<DataDictionaryR003Response>>, LiveData<String>> {
        return dataDictionaryRepository.getDictionaryData()
    }

    fun getDataDictionaryFromDb() : LiveData<List<DataDictionaryR003Response>>{
        return dataDictionaryRepository.getDataDictionaryFromDb()
    }
    /**
     * 插入数据列表
     * @param dataDictionaryListResponse 数据对象列表
     */
    fun insertDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>) = viewModelScope.launch {
        val dataDictionary = dataDictionaryRepository.insertDataDictionary(dataDictionaryListResponse)
        if (dataDictionary.size == dataDictionaryRepository.rowNum){
            insertEnd.value = true
        }
    }
    /**
     * 更新数据列表
     * 更新操作只可更新当前数据库中存在的数据
     * 如数据库结构发生改变请使用database Migrate迁移数据库版本
     * @param dataDictionaryListResponse 数据对象列表
     */
    fun updateDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>) = viewModelScope.launch {
        dataDictionaryRepository.updateDataDictionary(dataDictionaryListResponse)
    }
    fun updateDataDictionary(dataDictionary : DataDictionaryR003Response) = viewModelScope.launch {
        dataDictionaryRepository.updateDataDictionary(dataDictionary)
    }
    /**
     *  删除数据库中所有数据
     */
    fun deleteDataDictionary() = viewModelScope.launch {
        dataDictionaryRepository.deleteDataDictionary()
    }
    /**
     *  约束Fl和Dm字段得到Mc
     *  @param Fl 分类代码
     *  @param Dm 子类代码
     *  @return LiveData
     */
    fun getMc(Fl : String, Dm : String) : LiveData<String>{
        return dataDictionaryRepository.getMc(Fl, Dm)
    }

    fun getDm(Fl : String,Mc : String) : LiveData<String>{
        return dataDictionaryRepository.getDm(Fl, Mc)
    }
    fun getDmList (Fl: String, McList : List<String>) : LiveData<List<String>>{
        return dataDictionaryRepository.getDmList(Fl, McList)
    }

    fun getMcListFromFl(Fl: String) : LiveData<List<String>>{
        return dataDictionaryRepository.getMcListFromFl(Fl)
    }
    /**
     *  约束Fl得到该Fl所对应的对象列表
     *  @param Fl 分类代码
     *  @return LiveData
     */
    fun getListFromFl(Fl: String): LiveData<List<DataDictionaryR003Response>> {
        return dataDictionaryRepository.getListFromFl(Fl)
    }

    fun getListFromFl(FlList : List<String>): LiveData<List<DataDictionaryR003Response>> {
        return dataDictionaryRepository.getListFromFl(FlList)
    }
    /**
     *  根据Id = 1 查询数据库中是否存在数据
     *  @return Id = 1 的对象
     */
    fun getDataDictionaryExist(Id : Int): LiveData<DataDictionaryR003Response> {
        return dataDictionaryRepository.getDataDictionaryExist(Id)
    }









}
class DataDictionaryViewModelFactory(private val dataDictionaryRepository: DataDictionaryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataDictionaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataDictionaryViewModel(dataDictionaryRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}