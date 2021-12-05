package com.yxf.vehicleinspection.viewModel

import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response
import com.yxf.vehicleinspection.repository.DataDictionaryRepository
import com.yxf.vehicleinspection.repository.RegisterRepository

/**
 *   author:yxf
 *   time:2021/12/2
 */
class RegisterViewModel(val registerRepository: RegisterRepository) : ViewModel() {

    fun getFlList(): List<String> {
        val list = ArrayList<String>()
        list.add("11")
        list.add("21")
        list.add("22")
        list.add("60")
        list.add("gc")
        list.add("17")
        list.add("23")
        list.add("02")
        list.add("02")
        list.add("27")
        list.add("03")
        list.add("hx")
        list.add("50")
        list.add("gy")
        list.add("jq")
        list.add("bs")
        list.add("dw")
        list.add("13")
        list.add("25")
        return list
    }
    val textMap = registerRepository.getTextMap()
    val spinnerMap = registerRepository.getSpinnerMap()
//    val CllxSpinner = dataDictionaryRepository.getMcListFromFl("11")
//    val ClytSpinner = dataDictionaryRepository.getMcListFromFl("21")
//    val YtsxSpinner = dataDictionaryRepository.getMcListFromFl("22")
//    val WgchxSpinner = dataDictionaryRepository.getMcListFromFl("60")
//    val GcjkSpinner = dataDictionaryRepository.getMcListFromFl("gc")
//    val QdxsSpinner = dataDictionaryRepository.getMcListFromFl("17")
//    val ZdlySpinner = dataDictionaryRepository.getMcListFromFl("23")
////    val XzqhSpinner = dataDictionaryRepository.getMcListFromFl("22")
//    val Rlzl1Spinner = dataDictionaryRepository.getMcListFromFl("02")
//    val Rlzl2Spinner = dataDictionaryRepository.getMcListFromFl("02")
//    val RyggSpinner = dataDictionaryRepository.getMcListFromFl("27")
//    val QzdzSpinner = dataDictionaryRepository.getMcListFromFl("03")
//    val JcxhSpinner = dataDictionaryRepository.getMcListFromFl("hx")
//    val JdcsslbSpinner = dataDictionaryRepository.getMcListFromFl("50")
//    val GyfsSpinner = dataDictionaryRepository.getMcListFromFl("gy")
//    val JqfsSpinner = dataDictionaryRepository.getMcListFromFl("jq")
//    val BsxsSpinner = dataDictionaryRepository.getMcListFromFl("bs")
//    val DwsSpinner = dataDictionaryRepository.getMcListFromFl("dw")
//    val CcsSpinner = dataDictionaryRepository.getMcListFromFl("13")
//    val HclfsSpinner = dataDictionaryRepository.getMcListFromFl("25")
//    fun getSpinnerList(): LiveData<List<LiveData<List<String>>>>{
//        val liveData = MutableLiveData<List<LiveData<List<String>>>>()
//        val list = ArrayList<LiveData<List<String>>>()
//        list.add(CllxSpinner)
//        list.add(ClytSpinner)
//        list.add(YtsxSpinner)
//        list.add(WgchxSpinner)
//        list.add(GcjkSpinner)
//        list.add(QdxsSpinner)
//        list.add(ZdlySpinner)
//        list.add(Rlzl1Spinner)
//        list.add(Rlzl2Spinner)
//        list.add(RyggSpinner)
//        list.add(QzdzSpinner)
//        list.add(JcxhSpinner)
//        list.add(JdcsslbSpinner)
//        list.add(GyfsSpinner)
//        list.add(JqfsSpinner)
//        list.add(BsxsSpinner)
//        list.add(DwsSpinner)
//        list.add(CcsSpinner)
//        list.add(HclfsSpinner)
//        liveData.value = list
//        return liveData
//    }


}
class RegisterViewModelFactory(val registerRepository: RegisterRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.equals(RegisterViewModel::class.java)){
            return RegisterViewModel(registerRepository) as T
        }else{
            throw IllegalArgumentException("未知ViewModel")
        }
    }

}