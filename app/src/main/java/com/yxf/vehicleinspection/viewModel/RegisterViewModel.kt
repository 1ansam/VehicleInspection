package com.yxf.vehicleinspection.viewModel

import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.bean.request.SaveVehicleInfoW003Request
import com.yxf.vehicleinspection.bean.response.AppointmentAjR010Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR022Response
import com.yxf.vehicleinspection.repository.RegisterRepository
import com.yxf.vehicleinspection.utils.string2Date
import com.yxf.vehicleinspection.view.adapter.RegisterJyxmAdapter
import com.yxf.vehicleinspection.view.adapter.RegisterListAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

/**
 *   author:yxf
 *   time:2021/12/2
 */
class RegisterViewModel(val registerRepository: RegisterRepository) : ViewModel() {
    fun getVehicleInfo(Hphm : String, Hpzl : String, Clsbdh : String, Ajywlb : String, Hjywlb : String, Jyjgbh : String): LiveData<VehicleAllInfoR022Response> {
        return registerRepository.getVehicleInfo(Hphm, Hpzl, Clsbdh, Ajywlb, Hjywlb, Jyjgbh)
    }
    fun postSaveVehicleInfo(saveVehicleInfoW003Request: SaveVehicleInfoW003Request) : LiveData<Boolean>{
        return registerRepository.postSaveVehicleInfo(saveVehicleInfoW003Request)
    }
    fun getAppointmentAj(): LiveData<List<AppointmentAjR010Response>> {
        return registerRepository.getAppointmentAJ()
    }
    fun isFyy(syxz: String): Boolean {
        return syxz == "非营运"
    }
    fun isZk(cllx: String): Boolean {
        return cllx.contains("客车")
                || cllx.contains("校车")
                || cllx.contains("轿车")
                || cllx.contains("面包")
                || (cllx.contains("旅居") && !cllx.contains("挂"))
    }
    fun isFyyxwzk(cllx: String, syxz: String): Boolean {
        return (isZk(cllx) && (cllx.contains("小") || cllx.contains("微"))) && isFyy(syxz)
    }
    fun isHc(cllx: String): Boolean {
        return cllx.contains("货车")
                || cllx.contains("运输车")
                || cllx.contains("专项作业车")
                || cllx.contains("牵引")
    }
    fun isGc(cllx: String): Boolean {
        return (cllx.contains("挂") && !cllx.contains("牵引"))
    }
    fun isMtc(cllx: String): Boolean {
        return cllx.contains("摩托")
    }
    fun setAjJyxm(
        ajywlb: String,
        cllx: String,
        syxz: String,
        hdzk: Int,
        sfdzzc : CheckBox,
        sfdlxj : CheckBox,
        ajJyxm: CheckBox?,
        zs: Int,
        ccrq: String
    ) {
        val ccrqCalendar = Calendar.getInstance()
        val ccrqDate = string2Date(ccrq, "yyyy-MM-dd")
        ccrqCalendar.time = ccrqDate
        val nowCalendar = Calendar.getInstance()
        val betweenMonth =
            (nowCalendar.time.time - ccrqCalendar.time.time) / (1000L * 3600 * 24 * 30)
        if (ajywlb != "-"){
            when(ajJyxm?.text.toString()){
                "外观" -> ajJyxm?.isChecked = true
                "唯一性检查" -> ajJyxm?.isChecked = true
                "联网查询" -> ajJyxm?.isChecked = true
                "驻车制动" -> ajJyxm?.isChecked = !(sfdzzc.isChecked || isMtc(cllx) || isGc(cllx))
                "侧滑" -> ajJyxm?.isChecked = sfdlxj.isChecked
                "外廓尺寸" ->{
                    when{
                        ajywlb.contains("注册") ->{
                            ajJyxm?.isChecked = !(isFyyxwzk(cllx, syxz) || cllx.contains("二轮摩托车") || cllx.contains("侧三轮摩托车"))
                        }
                        ajywlb.contains("在用") ->{
                            ajJyxm?.isChecked = ((isHc(cllx) || isGc(cllx))
                                    && (cllx.contains("重") || cllx.contains("大") || cllx.contains("中"))
                                    && !cllx.contains("牵引")
                                    && !cllx.contains("非载货"))
                        }
                        else -> ajJyxm?.isChecked = false
                    }
                }
                "整备质量" ->{
                    when{
                        ajywlb.contains("注册") -> {
                            ajJyxm?.isChecked = !(isZk(cllx)||isMtc(cllx))
                        }
                        ajywlb.contains("在用") -> {
                            ajJyxm?.isChecked = ((isHc(cllx) || isGc(cllx))
                                    && (cllx.contains("重") || cllx.contains("大") || cllx.contains("中"))
                                    && !cllx.contains("牵引")
                                    && !cllx.contains("非载货"))
                        }
                    }
                }
                "底盘" ->{
                    when{
                        ajywlb.contains("注册") -> {
                            ajJyxm?.isChecked = !(isMtc(cllx) || (isFyyxwzk(cllx, syxz) && !cllx.contains("面包") || hdzk < 7))
                        }
                        ajywlb.contains("在用") -> {
                            ajJyxm?.isChecked = !(isMtc(cllx) || (isFyyxwzk(cllx, syxz) && hdzk < 7 && betweenMonth < 117 && !cllx.contains("面包")))
                        }
                    }

                }
                "底盘动态" ->{
                    when{
                        ajywlb.contains("注册") -> {
                            ajJyxm?.isChecked = !(isGc(cllx) || (isFyyxwzk(cllx, syxz) && !cllx.contains("面包") || hdzk < 7))
                        }
                        ajywlb.contains("在用") -> {
                            ajJyxm?.isChecked = !(isGc(cllx) || (isFyyxwzk(cllx, syxz) && hdzk < 7 && betweenMonth < 117 && !cllx.contains("面包")))
                        }
                    }

                }
                "一轴制动" -> ajJyxm?.isChecked = zs >= 1
                "二轴制动" -> ajJyxm?.isChecked = zs >= 2
                "三轴制动" -> ajJyxm?.isChecked = zs >= 3
                "四轴制动" -> ajJyxm?.isChecked = zs >= 4
                "五轴制动" -> ajJyxm?.isChecked = zs >= 5
                else -> ajJyxm?.isChecked = false
            }
        }else{
            ajJyxm?.isChecked = false
        }

    }
    fun getAjJyxm(jyxm : RecyclerView, adapter: RegisterJyxmAdapter): List<String> {
        val list = ArrayList<String>()
        for (index in 0 until adapter.itemCount) {
            val holder = jyxm.findViewHolderForAdapterPosition(index)
            val checkbox = holder?.itemView?.findViewById<CheckBox>(R.id.cbJyxm)
            if (checkbox != null && checkbox.isChecked) {
                list.add(checkbox.text.toString())
            }
        }
        return list
    }
    fun setHjJyxm(hjywlb : String, hjJyxm : CheckBox?, ccrq: String, zzl : Int){
        val ccrqDate = string2Date(ccrq, "yyyy-MM-dd")
        val obdDate1 = string2Date("2011-07-01","yyyy-MM-dd")
        val obdDate2 = string2Date("2013-07-01","yyyy-MM-dd")
        if (hjywlb != "-"){
            when(hjJyxm?.text.toString()){
                "环保外观" -> hjJyxm?.isChecked = true
                "环保底盘" -> hjJyxm?.isChecked = true
                "OBD" -> {
                    if (zzl <= 3500){
                        hjJyxm?.isChecked = ccrqDate >= obdDate1
                    }else{
                        hjJyxm?.isChecked = ccrqDate >= obdDate2
                    }
                }
                "尾气" -> hjJyxm?.isChecked = true
            }
        }else{
            hjJyxm?.isChecked = false
        }
    }
    fun getHjJyxmString(jyxm : RecyclerView, adapter: RegisterJyxmAdapter): String {
        var hjJyxmString = ""
        for (index in 0 until adapter.itemCount) {
            val holder = jyxm.findViewHolderForAdapterPosition(index)
            val checkbox = holder?.itemView?.findViewById<CheckBox>(R.id.cbJyxm)
            when(checkbox?.text.toString()){
                "环保外观" ->{

                    hjJyxmString += ",F1".takeIf { checkbox?.isChecked == true }?:""
                }
                "环保底盘" ->{

                    hjJyxmString += ",C1".takeIf { checkbox?.isChecked == true }?:""
                }
                "OBD" ->{

                    hjJyxmString += ",OBD".takeIf { checkbox?.isChecked == true }?:""
                }
                "尾气" ->{

                    hjJyxmString += ",WQ".takeIf { checkbox?.isChecked == true }?:""
                }
            }
        }
        return hjJyxmString
    }


    fun getValueListMap(view : RecyclerView) : LinkedHashMap<String,MutableList<String>>{
        val map = textListMap
        for(index in 0 until view.adapter!!.itemCount){
            val holder = view.findViewHolderForAdapterPosition(index)
            val textView = holder?.itemView?.findViewById<TextView>(R.id.key)
            val clearEditText = holder?.itemView?.findViewById<EditText>(R.id.value)
            map.get(textView?.text.toString())?.set(1,clearEditText?.text.toString())
        }

        return map
    }

    fun getValueMap(textList : RecyclerView,adapter: RegisterListAdapter): MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        for (index in 0 until adapter.itemCount) {
            val holder = textList.findViewHolderForAdapterPosition(index)
            val clearEditText = holder?.itemView?.findViewById<EditText>(R.id.value)
            if (index == 7){
                if (clearEditText?.text.toString().contains(",")){
                    val ztMcList = clearEditText?.text.toString().split(",")
                    var keyString = ""
                    for (element in ztMcList){
                        if (ztMcMap[element]!=null){
                            keyString += ztMcMap[element]!!
                        }
                    }
                    map[textMap.keys.toList()[index]] = keyString
                }else{
                    val key = ztMcMap[clearEditText?.text.toString()]
                    map[textMap.keys.toList()[index]] = key.takeIf { !key.isNullOrBlank() }?:""
                }

            }else{
                map[textMap.keys.toList()[index]] = clearEditText?.text.toString()
            }

        }


        return map
    }



//    val textMap = LinkedHashMap<String,MutableList<String>>()
//    textMap["xszbh"  ] = mutableListOf("行驶证编号","")
//    textMap["clpp1"  ] = mutableListOf("车辆品牌","")
//    textMap["clxh"   ] = mutableListOf("车辆型号","")
//    textMap["fdjxh"  ] = mutableListOf("发动机型号","")
//    textMap["fdjh"   ] = mutableListOf("发动机号","")
//    textMap["syr"    ] = mutableListOf("所有人","")
//    textMap["lxdh"   ] = mutableListOf("联系电话","")
//    textMap["zt"     ] = mutableListOf("机动车状态","")
//    textMap["zzcmc"  ] = mutableListOf("制造厂名称","")
//    textMap["qdzs"   ] = mutableListOf("驱动轴数","")
//    textMap["qdzw"   ] = mutableListOf("驱动轴位","")
//    textMap["zczs"   ] = mutableListOf("驻车轴数","")
//    textMap["zczw"   ] = mutableListOf("驻车轴位","")
//    textMap["zs"     ] = mutableListOf("轴数","")
//    textMap["zj"     ] = mutableListOf("轴距","")
//    textMap["zzs"    ] = mutableListOf("主轴数","")
//    textMap["qzs"    ] = mutableListOf("前轴数","")
//    textMap["qlj"    ] = mutableListOf("前轮距","")
//    textMap["hlj"    ] = mutableListOf("后轮距","")
//    textMap["zzl"    ] = mutableListOf("总质量","")
//    textMap["zbzl"   ] = mutableListOf("整备质量","")
//    textMap["kqxjzw" ] = mutableListOf("空气悬架轴位","")
//    textMap["zxzs"   ] = mutableListOf("转向轴数","")
//    textMap["bzzw"   ] = mutableListOf("并装轴位","")
//    textMap["hdzk"   ] = mutableListOf("核定载客","")
//    textMap["hdzzl"  ] = mutableListOf("核定载质量","")
//    textMap["zqyzl"  ] = mutableListOf("准牵引质量","")
//    textMap["zdsjcs" ] = mutableListOf("最大设计车速","")
//    textMap["gl"     ] = mutableListOf("功率","")
//    textMap["pl"     ] = mutableListOf("排量","")
//    textMap["pqgs"   ] = mutableListOf("排气管数","")
//    textMap["lcbds"  ] = mutableListOf("里程表读数","")
//    textMap["edzs"   ] = mutableListOf("额定转速","")
//    textMap["ltgg"   ] = mutableListOf("轮胎规格","")
//    textMap["qgs"    ] = mutableListOf("气缸数","")
//    textMap["lxdz"   ] = mutableListOf("联系地址","")
//    textMap["sjr"    ] = mutableListOf("送检人姓名","")
//    textMap["sjrdh"  ] = mutableListOf("送检人电话","")
//    textMap["sjrsfzh"] = mutableListOf("送检人身份证号","")
//    textMap["SCR"    ] = mutableListOf("SCR（柴油）","")
//    textMap["DPF"    ] = mutableListOf("DPF（柴油）","")

    val textMap = registerRepository.getTextMap()
    val textListMap = registerRepository.getTextListMap()
    val hjJyxmList = registerRepository.getHjJyxm()
    val ztDmMap = registerRepository.getZtDmMap()
    val ztMcMap = registerRepository.getZtMcMap()
}
class RegisterViewModelFactory(val registerRepository: RegisterRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(registerRepository) as T
        }else{
            throw IllegalArgumentException("未知ViewModel")
        }
    }

}