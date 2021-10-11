package com.yxf.vehicleinspection.repository

import android.widget.Toast
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.JsCsCode
import com.yxf.vehicleinspection.room.JsCsCodeDao
import com.yxf.vehicleinspection.service.JsCsCodeService
import com.yxf.vehicleinspection.singleton.RetrofitService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/10/8
 */
class JsCsCodeRepository(private val dao: JsCsCodeDao) {
    suspend fun insertJsCsCode(jsCsCodeList: List<JsCsCode>){
        dao.insertJsCsCode(jsCsCodeList)
    }
    suspend fun deleteAll(){
        dao.deleteAll()
    }
    fun downloadJsCsCode() : List<JsCsCode>{
        val arrayList = ArrayList<JsCsCode>()
        val call = RetrofitService.create(JsCsCodeService::class.java).getJsCsCode()
        call.enqueue(object : Callback<List<JsCsCode>>{
            override fun onResponse(
                call: Call<List<JsCsCode>>,
                response: Response<List<JsCsCode>>
            ) {
                val list = response.body()
                if (list != null){

                    GlobalScope.launch{
                        coroutineScope {
                            launch {
                                deleteAll()
                            }
                            launch {
                                insertJsCsCode(list)
                            }

                        }

                    }



                }



            }

            override fun onFailure(call: Call<List<JsCsCode>>, t: Throwable) {
                t.message
                Toast.makeText(MyApp.context,t.message,Toast.LENGTH_SHORT).show()
            }
        })
    return arrayList
    }
}