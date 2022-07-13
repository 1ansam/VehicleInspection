package com.yxf.vehicleinspection.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBean
import com.yxf.vehicleinspection.bean.CCbean
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
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

/**
 *   author:yxf
 *   time:2021/12/2
 */
class RegisterRepository {
    /**
     * 获取车辆信息
     * @param Hphm 号牌号码
     * @param Hpzl 号牌种类
     * @param Clsbdh 车辆识别代号
     * @param Ajywlb 安检业务类别
     * @param Hjywlb 环检业务类别
     * @param Jyjgbh 检验机构编号
     */
    fun getVehicleInfo(
        Hphm: String,
        Hpzl: String,
        Clsbdh: String,
        Ajywlb: String,
        Hjywlb: String,
        Jyjgbh: String
    ): LiveData<VehicleAllInfoR022Response> {
        val liveData = MutableLiveData<VehicleAllInfoR022Response>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_VEHICLE_INFO,
            getIpAddress(),
            getJsonData(VehicleAllInfoR022Request(Hphm, Hpzl, Clsbdh, Ajywlb, Hjywlb, Jyjgbh))
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2Bean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    /**
     * 上传车辆信息
     */
    fun postSaveVehicleInfo(saveVehicleInfoW003Request: SaveVehicleInfoW003Request): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val call = RetrofitService.create(WriteService::class.java).write(
            WRITE_SAVE_VEHICLE_INFO,
            getIpAddress(),
            getJsonData(saveVehicleInfoW003Request)
        )
        call.enqueue(object : Callback<ResponseBody> {
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

    /**
     * 获取安检预约信息
     */
    fun getAppointmentAJ(): LiveData<List<AppointmentAjR010Response>> {
        val liveData = MutableLiveData<List<AppointmentAjR010Response>>()
        val call = RetrofitService.create(QueryService::class.java).query(
            QUERY_APPOINTMENT_AJ,
            getIpAddress(),
            getJsonData(AppointmentAjR010Request())
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                response2ListBean(response, liveData)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(MyApp.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return liveData
    }

    /**
     * 将车辆登录需要填写的信息与json代号一一对应
     */
    fun getTextMap(): Map<String, String> {
        val textMap = mutableMapOf<String, String>()
        textMap["xszbh"] = "行驶证编号"
        textMap["clpp1"] = "车辆品牌"
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

    fun getTextListMap(): LinkedHashMap<String, MutableList<String>> {

        val textMap = LinkedHashMap<String, MutableList<String>>()
        textMap["行驶证编号"] = mutableListOf("xszbh", "")
        textMap["车辆品牌"] = mutableListOf("clpp1", "")
        textMap["车辆型号"] = mutableListOf("clxh", "")
        textMap["发动机型号"] = mutableListOf("fdjxh", "")
        textMap["发动机号"] = mutableListOf("fdjh", "")
        textMap["所有人"] = mutableListOf("syr", "")
        textMap["联系电话"] = mutableListOf("lxdh", "")
        textMap["机动车状态"] = mutableListOf("zt", "")
        textMap["制造厂名称"] = mutableListOf("zzcmc", "")
        textMap["驱动轴数"] = mutableListOf("qdzs", "")
        textMap["驱动轴位"] = mutableListOf("qdzw", "")
        textMap["驻车轴数"] = mutableListOf("zczs", "")
        textMap["驻车轴位"] = mutableListOf("zczw", "")
        textMap["轴数"] = mutableListOf("zs", "")
        textMap["轴距"] = mutableListOf("zj", "")
        textMap["主轴数"] = mutableListOf("zzs", "")
        textMap["前轴数"] = mutableListOf("qzs", "")
        textMap["前轮距"] = mutableListOf("qlj", "")
        textMap["后轮距"] = mutableListOf("hlj", "")
        textMap["总质量"] = mutableListOf("zzl", "")
        textMap["整备质量"] = mutableListOf("zbzl", "")
        textMap["空气悬架轴位"] = mutableListOf("kqxjzw", "")
        textMap["转向轴数"] = mutableListOf("zxzs", "")
        textMap["并装轴位"] = mutableListOf("bzzw", "")
        textMap["核定载客"] = mutableListOf("hdzk", "")
        textMap["核定载质量"] = mutableListOf("hdzzl", "")
        textMap["准牵引质量"] = mutableListOf("zqyzl", "")
        textMap["最大设计车速"] = mutableListOf("zdsjcs", "")
        textMap["功率"] = mutableListOf("gl", "")
        textMap["排量"] = mutableListOf("pl", "")
        textMap["排气管数"] = mutableListOf("pqgs", "")
        textMap["里程表读数"] = mutableListOf("lcbds", "")
        textMap["额定转速"] = mutableListOf("edzs", "")
        textMap["轮胎规格"] = mutableListOf("ltgg", "")
        textMap["气缸数"] = mutableListOf("qgs", "")
        textMap["联系地址"] = mutableListOf("lxdz", "")
        textMap["送检人姓名"] = mutableListOf("sjr", "")
        textMap["送检人电话"] = mutableListOf("sjrdh", "")
        textMap["送检人身份证号"] = mutableListOf("sjrsfzh", "")
        textMap["SCR（柴油）"] = mutableListOf("SCR", "")
        textMap["DPF（柴油）"] = mutableListOf("DPF", "")
        return textMap
    }

    /**
     * 将机动车状态代码存储在Map中以供调用
     */
    fun getZtDmMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["A"] = "正常"
        map["B"] = "转出"
        map["C"] = "盗抢嫌疑"
        map["D"] = "停驶"
        map["E"] = "注销"
        map["G"] = "违章未处理"
        map["H"] = "海关监管"
        map["I"] = "事故未处理"
        map["J"] = "嫌疑车"
        map["K"] = "查封"
        map["L"] = "暂扣"
        map["M"] = "强制注销"
        map["N"] = "事故逃逸"
        map["O"] = "锁定"
        map["Z"] = "其他"
        map["Q"] = "逾期未检验"
        return map
    }

    /**
     * 将机动车状态代码存储在Map中以供调用
     */

    fun getZtMcMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["正常"] = "A"
        map["转出"] = "B"
        map["盗抢嫌疑"] = "C"
        map["停驶"] = "D"
        map["注销"] = "E"
        map["违章未处理"] = "G"
        map["海关监管"] = "H"
        map["事故未处理"] = "I"
        map["嫌疑车"] = "J"
        map["查封"] = "K"
        map["暂扣"] = "L"
        map["强制注销"] = "M"
        map["事故逃逸"] = "N"
        map["锁定"] = "O"
        map["其他"] = "Z"
        map["逾期未检验"] = "Q"
        return map
    }

    /**
     * 环检检验项目
     */
    fun getHjJyxm(): List<String> {
        return mutableListOf("环保外观", "环保底盘", "OBD", "尾气")
    }


}