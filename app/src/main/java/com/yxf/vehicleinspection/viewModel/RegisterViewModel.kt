package com.yxf.vehicleinspection.viewModel

import android.widget.CheckBox
import android.widget.EditText
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
    val textMap = registerRepository.getTextMap()
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