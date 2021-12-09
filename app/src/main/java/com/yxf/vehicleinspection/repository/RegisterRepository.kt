package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.AppointmentAjR010Request
import com.yxf.vehicleinspection.bean.request.SaveVehicleInfoW003Request
import com.yxf.vehicleinspection.bean.request.VehicleAllInfoR022Request
import com.yxf.vehicleinspection.bean.response.AppointmentAjR010Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR022Response
import com.yxf.vehicleinspection.service.QueryService
import com.yxf.vehicleinspection.service.WriteService
import com.yxf.vehicleinspection.singleton.RetrofitService
import com.yxf.vehicleinspection.utils.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *   author:yxf
 *   time:2021/12/2
 */
class RegisterRepository() {
    fun getVehicleInfo(Hphm : String, Hpzl : String, Clsbdh : String, Ajywlb : String, Hjywlb : String, Jyjgbh : String) : LiveData<VehicleAllInfoR022Response>{
        val liveData = MutableLiveData<VehicleAllInfoR022Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_VEHICLE_INFO,
            getIpAddress(),
            getJsonData(VehicleAllInfoR022Request(Hphm, Hpzl, Clsbdh, Ajywlb, Hjywlb, Jyjgbh))
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Bean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }
    fun postSaveVehicleInfo(saveVehicleInfoW003Request: SaveVehicleInfoW003Request) : LiveData<Boolean>{
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_SAVE_VEHICLE_INFO,
            getIpAddress(),
            getJsonData(saveVehicleInfoW003Request)
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Boolean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                liveData.value = false
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun getAppointmentAJ(): LiveData<List<AppointmentAjR010Response>> {
        val liveData = MutableLiveData<List<AppointmentAjR010Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_APPOINTMENT_AJ,
            getIpAddress(),
            getJsonData(AppointmentAjR010Request())
        )
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    fun getTextMap() : Map<String, String> {
        val textMap = mutableMapOf<String,String>()
        textMap["xszbh"] = "行驶证编号"
        textMap["clpp1"] = "车辆品牌1"
        textMap["clxh"] = "车辆型号"
        textMap["fdjxh"] = "发动机型号"
        textMap["fdjh"] = "发动机号"
        textMap["syr"] = "所有人"
        textMap["lxdh"] = "联系电话"
        textMap["zt"] = "机动车状态"
        textMap["zzcmc"] = "制造厂名称"
        textMap["qdzs"] = "驱动轴数"
        textMap["qdzw"] = "驱动轴位"
        textMap["zczs"] = "驻车轴数"
        textMap["zczw"] = "驻车轴位"
        textMap["zs"] = "轴数"
        textMap["zj"] = "轴距"
        textMap["zzs"] = "主轴数"
        textMap["qzs"] = "前轴数"
        textMap["qlj"] = "前轮距"
        textMap["hlj"] = "后轮距"
        textMap["zzl"] = "总质量"
        textMap["zbzl"] = "整备质量"
        textMap["kqxjzw"] = "空气悬架轴位"
        textMap["zxzs"] = "转向轴数"
        textMap["bzzw"] = "并装轴位"
        textMap["hdzk"] = "核定载客"
        textMap["hdzzl"] = "核定载质量"
        textMap["zqyzl"] = "准牵引质量"
        textMap["zdsjcs"] = "最大设计车速"
        textMap["gl"] = "功率"
        textMap["pl"] = "排量"
        textMap["pqgs"] = "排气管数"
        textMap["lcbds"] = "里程表读数"
        textMap["edzs"] = "额定转速"
        textMap["ltgg"] = "轮胎规格"
        textMap["qgs"] = "气缸数"
        textMap["lxdz"] = "联系地址"
        textMap["sjr"] = "送检人姓名"
        textMap["sjrdh"] = "送检人电话"
        textMap["sjrsfzh"] = "送检人身份证号"
        textMap["SCR"] = "SCR（柴油）"
        textMap["DPF"] = "DPF（柴油）"
        return textMap
    }
    fun getTextList() : List<String>{
        return mutableListOf<String>(
                    "xszbh",
                    "clpp1",
                    "clxh",
                    "fdjxh",
                    "fdjh",
                    "syr",
                    "lxdh",
                    "zt",
                    "zzcmc",
                    "qdzs",
                    "qdzw",
                    "zczs",
                    "zs",
                    "zj",
                    "zzs",
                    "qzs",
                    "qlj",
                    "hlj",
                    "zzl",
                    "zbzl",
                    "kqxjzw",
                    "zxzs",
                    "bzzw",
                    "hdzk",
                    "hdzzl",
                    "zqyzl",
                    "zdsjcs",
                    "gl",
                    "pl",
                    "pqgs",
                    "lcbds",
                    "edzs",
                    "ltgg",
                    "qgs",
                    "lxdz",
                    "sjr",
                    "sjrdh",
                    "sjrsfzh",
                    "SCR",
                    "DPF",
        )
    }

    fun getFlList() : List<String>{
        val list = mutableListOf<String>(
            FL_HPZL,
            FL_HPYS,
            FL_AJYWLB,
            FL_HJYWLB,
            FL_ZJYWLB,
            FL_CLLX,
            FL_CLYT,
            FL_YTSX,
            FL_PZCX,
            FL_GCJK,
            FL_QDFS,
            FL_ZDLY,
            FL_RLZL,
            FL_RYGG,
            FL_QZDZ,
            FL_SSLB,
            FL_GYFS,
            FL_JQFS,
            FL_BSX,
            FL_DW,
            FL_CC,
            FL_HCLZL,
            FL_HYYT,
            FL_ZXZFS,
            FL_HBXH
        )
        return list
    }
    fun getSpinnerMap(): MutableMap<String, String> {
        return mutableMapOf()
    }



}