package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.DataDictionaryR003Request
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
import com.yxf.vehicleinspection.room.DataDictionaryDao
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/11/9
 *   数据字典仓库类
 *   @param dao 数据库操作对象
 */
class DataDictionaryRepository(private val dao : DataDictionaryDao) {
    /**
     * 从远程服务器获取数据字典
     */
    fun getDictionaryData() : LiveData<List<DataDictionaryR003Response>>{
        val liveData = MutableLiveData<List<DataDictionaryR003Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_DATA_DICTIONARY,
            getIpAddress(),
            getJsonData(DataDictionaryR003Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response,liveData)

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    /**
     * 插入数据列表
     * @param dataDictionaryListResponse 数据对象列表
     */
    suspend fun insertDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>) : List<Long>{
        return dao.insertDataDictionary(dataDictionaryListResponse)
    }
    suspend fun insertDataDictionary(dataDictionary: DataDictionaryR003Response): Long {
        return dao.insertDataDictionary(dataDictionary)
    }
    /**
     * 更新数据列表
     * 更新操作只可更新当前数据库中存在的数据
     * 如数据库结构发生改变请使用database Migrate迁移数据库版本
     * @param dataDictionaryListResponse 数据对象列表
     */
    suspend fun updateDataDictionary(dataDictionaryListResponse: List<DataDictionaryR003Response>){
        dao.updateDataDictionary(dataDictionaryListResponse)
    }
    suspend fun updateDataDictionary(dataDictionary: DataDictionaryR003Response) {
        dao.insertDataDictionary(dataDictionary)
    }
    /**
     *  删除数据库中所有数据
     */
    suspend fun deleteDataDictionary(){
        dao.deleteDataDictionary()
    }
    /**
     *  约束Fl和Dm字段得到Mc
     *  @param Fl 分类代码
     *  @param Dm 子类代码
     *  @return LiveData
     */
    fun getMc(Fl : String,Dm : String): LiveData<String> {
        return dao.getMc(Fl, Dm)
    }
    /**
     *  约束Fl得到该Fl所对应的对象列表
     *  @param Fl 分类代码
     *  @return LiveData
     */
    fun getListFromFl(Fl: String) : LiveData<List<DataDictionaryR003Response>>{
        return dao.getListFromFl(Fl)
    }
    /**
     *  根据Id = 1 查询数据库中是否存在数据
     *  @return Id = 1 的对象
     */
    fun getDataDictionaryExist(Id : Int) : LiveData<DataDictionaryR003Response>{
        return dao.getDataDictionaryExist(Id)
    }


}