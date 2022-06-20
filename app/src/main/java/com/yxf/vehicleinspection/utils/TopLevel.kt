package com.yxf.vehicleinspection.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Base64
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.CollectMoney
import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.singleton.GsonSingleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.StringBuilder
import java.net.URLEncoder
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.reflect.typeOf
import kotlin.collections.Map as Map

/**
 *   author:yxf
 *   time:2021/11/15
 *   kotlin顶层函数及常量
 *
 */
const val FILE_PROVIDER = "com.yxf.vehicleinspection.fileprovider"
//数据字典分类常量
const val FL_CLSYXZ = "01"
const val FL_RLZL = "02"
const val FL_QZDZ = "03"
const val FL_ZDMS = "04"
const val FL_SSDQ = "05"
const val FL_CSJC = "06"
const val FL_CLLX = "07"
const val FL_AJYWLB = "08"
const val FL_HPZL = "09"
const val FL_CSYS = "10"
const val FL_CLLB = "11"
const val FL_DYBBSJ = "12"
const val FL_CC = "13"
const val FL_GLBM = "14"
const val FL_CFJ = "15"
const val FL_SFXC = "16"
const val FL_QDFS = "17"
const val FL_GZGW = "18"
const val FL_JCXH = "19"
const val FL_PZGW = "20"
const val FL_CLYT = "21"
const val FL_YTSX = "22"
const val FL_ZDLY = "23"
const val FL_JCXLB = "24"
const val FL_HCLZL = "25"
const val FL_HPYS = "26"
const val FL_RYGG = "27"
const val FL_QGS = "28"
const val FL_ZJYWLB = "30"
const val FL_HJYWLB = "31"
const val FL_ZJDW = "32"
const val FL_AJCX = "34"
const val FL_JCGW = "35"
const val FL_XSPLX = "36"
const val FL_DGYXH = "37"
const val FL_DGCLFS = "38"
const val FL_DGYTKW = "39"
const val FL_DYSJLX = "40"
const val FL_DYCFLX = "41"
const val FL_ZJCX = "42"
const val FL_HJCX = "43"
const val FL_GLBZFS = "44"
const val FL_KCDJ = "45"
const val FL_HCCSXS = "46"
const val FL_SJLJFS = "47"
const val FL_SSLB = "50"
const val FL_ZFFS = "52"
const val FL_QYLX = "53"
const val FL_PZCX = "60"
const val FL_HBJCFS = "63"
const val FL_JYFHMSM = "70"
const val FL_JYLXSM = "71"
const val FL_ZXZFS = "80"
const val FL_CYCBZ = "81"
const val FL_YGNFTZ = "82"
const val FL_JYXM = "83"
const val FL_SPMC = "84"
const val FL_KPSP = "98"
const val FL_SFTMMX = "99"
const val FL_BSX = "bs"
const val FL_SJCJLX = "dt"
const val FL_DW = "dw"
const val FL_C1XH = "dx"
const val FL_GCJK = "gc"
const val FL_GYFS = "gy"
const val FL_HJSP = "hs"
const val FL_HBXH = "hx"
const val FL_HYYT = "hy"
const val FL_JQFS = "jq"
const val FL_NQXH = "nx"
const val FL_PFJD = "pj"
const val FL_SBLX = "sb"
const val FL_DCXH = "tx"
const val FL_UCXH = "ux"
const val FL_F1XH = "wx"
const val FL_XJXS = "xj"
const val FL_JCLSHZT = "zt"
//Api常量
/*
 *  QUERY_ALL_USER 查询所有用户信息
 *  QUERY_VEHICLE_QUEUE 查询车辆队列信息
 *  QUERY_DATA_DICTIONARY 查询数据字典
 *  QUERY_CHARGE_LIST 查询收费列表
 *  QUERY_VEHICLE_ALL_INFO 查询车辆详细信息
 *  QUERY_VEHICLE_INSPECTION_ITEM 查新检验项目信息
 *  QUERY_VEHICLE_IMAGE 查询机动车照片
 *  QUERY_VEHICLE_VIDEO 查询机动车视频
 *  QUERY_ADMISSION_INFO 查询入场信息
 *  QUERY_APPOINTMENT_INFO 查询预约信息
 *  QUERY_SERVER_TIME 查询服务器时间
 *  QUERY_BREAK_AND_AXIS_LOAD_CURVE 未定义
 *  QUERY_WAIT_VERIFY 未定义
 *  QUERY_CHARGE_RESULT 查询收费结果
 *  QUERY_SYSTEM_PARAMS 查询检验机构系统参数
 *  QUERY_IMAGE_ITEM 查询人工检验项目照片
 *  QUERY_ARTIFICIAL_PROJECT 查询人工检验项目
 *  WRITE_USER_LOGIN 写入用户登录
 *  WRITE_SAVE_SIGNATURE 写入保存签名
 *  WRITE_INSPECTION_PHOTO 写入人工检验照片
 *  WRITE_SAVE_VIDEO 写入保存视频
 *  WRITE_ARTIFICIAL_PROJECT 写入人工检验项目
 *  WRITE_PROJECT_END 写入项目结束
 */
const val QUERY_ALL_USER = "LYYDJKR001" // 查询所有用户信息
const val QUERY_VEHICLE_QUEUE = "LYYDJKR002" // 查询待检车辆队列
const val QUERY_DATA_DICTIONARY = "LYYDJKR003" // 查询数据字典
const val QUERY_CHARGE_LIST = "LYYDJKR004" // 查询收费列表
const val QUERY_VEHICLE_ALL_INFO = "LYYDJKR005" // 查询车辆所有信息
const val QUERY_VEHICLE_INSPECTION_ITEM = "LYYDJKR006" // 查询车辆检验项目
const val QUERY_VEHICLE_IMAGE = "LYYDJKR007" // 查询车辆照片
const val QUERY_VEHICLE_VIDEO = "LYYDJKR008" // 查询车辆视频
const val QUERY_ADMISSION_INFO = "LYYDJKR009" // 查询入场信息
const val QUERY_APPOINTMENT_AJ = "LYYDJKR010" // 查询安检预约信息
const val QUERY_SERVER_TIME = "LYYDJKR011" // 查询服务器时间
const val QUERY_BREAK_AND_AXIS_LOAD_CURVE = "LYYDJKR012" // 查询制动和轴加载
const val QUERY_VERIFY_QUEUE = "LYYDJKR013" // 查询审核队列
const val QUERY_CHARGE_STATUS = "LYYDJKR014" // 查询收费状态
const val QUERY_SYSTEM_PARAMS = "LYYDJKR015" // 查询系统参数
const val QUERY_IMAGE_ITEM = "LYYDJKR017" // 查询照片项目
const val QUERY_LEASTEST_TIME = "LYYDJKR019" // 查询人工检验项目最短用时
const val QUERY_ARTIFICIAL_PROJECT = "LYYDJKR020" // 查询人工检验项目
const val QUERY_VERSION = "LYYDJKR021" // 查询app版本号
const val QUERY_VEHICLE_INFO = "LYYDJKR022" // 查询车辆信息
const val QUERY_ADMINISTRATIVE = "LYYDJKR023" // 查询行政区划
const val QUERY_ONLINE_STUTAS = "LYYDJKR024" // 查询上线状态
const val QUERY_INVOICE_PARAMS = "LYYDJKR025" // 查询开票信息
const val QUERY_BUYER_PARAMS = "LYYDJKR026" // 查询客户信息
const val WRITE_USER_LOGIN = "LYYDJKW001" // 写入用户登录
const val WRITE_SAVE_VEHICLE_INFO = "LYYDJKW003" // 写入车辆信息
const val WRITE_SAVE_CHARGE_INFO = "LYYDJKW004" // 写入收费信息
const val WRITE_SAVE_INVOICE_INFO = "LYYDJKW005" // 写入开票信息
const val WRITE_SAVE_SIGNATURE = "LYYDJKW006" // 写入签名信息
const val WRITE_INSPECTION_PHOTO = "LYYDJKW007" // 写入检验照片
const val WRITE_SAVE_VIDEO = "LYYDJKW008" // 写入保存视频
const val WRITE_TAKE_PHOTO = "LYYDJKW009" // 触发摄像头拍照
const val WRITE_PROJECT_START = "LYYDJKW010" // 写入项目开始
const val WRITE_ARTIFICIAL_PROJECT = "LYYDJKW011" // 写入人工检验项目
const val WRITE_PROJECT_END = "LYYDJKW012" // 写入项目结束
const val WRITE_VERIFY_INFO = "LYYDJKW013" // 写入审核信息
const val WRITE_START_ONLINE = "LYYDJKW015" // 写入上线开始
//视频代码常量
/*
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
const val SIDE_SLIP = "A1" // SIDE_SLIP 侧滑
const val PARKING_BREAK = "B0" // PARKING_BREAK 驻车制动
const val ONE_AXIS_BREAK = "B1" // ONE_AXIS_BREAK 一轴制动
const val TWO_AXIS_BREAK = "B2" // TWO_AXIS_BREAK 二轴制动
const val THREE_AXIS_BREAK = "B3" // THREE_AXIS_BREAK 三轴制动
const val FOUR_AXIS_BREAK = "B4" // FOUR_AXIS_BREAK 四轴制动
const val FIVE_AXIS_BREAK = "B5" // FIVE_AXIS_BREAK 五轴制动
const val CHASSIS = "C1" // CHASSIS 底盘
const val POWER = "CG" // POWER 动力性
const val DYNAMIC_BEHIND = "DC" // DYNAMIC_BEHIND 底盘动态后
const val DYNAMIC_FRONT = "DC0" // DYNAMIC_FRONT 底盘动态后
const val EXTERIOR_FRONT = "F1" // EXTERIOR_FRONT 外观前
const val EXTERIOR_BEHIDE = "F0" // EXTERIOR_BEHIDE 外观后
const val LEFT_LIGHT = "H1" // LEFT_LIGHT 左外灯
const val RIGHT_LIGHT = "H4" // RIGHT_LIGHT 右外灯
const val ONE_AXIS_LOAD_BREAK = "L1" // ONE_AXIS_LOAD_BREAK 一轴加载制动
const val TWO_AXIS_LOAD_BREAK = "L2" // TWO_AXIS_LOAD_BREAK 二轴加载制动
const val THREE_AXIS_LOAD_BREAK = "L3" // THREE_AXIS_LOAD_BREAK 三轴加载制动
const val FOUR_AXIS_LOAD_BREAK = "L4" // FOUR_AXIS_LOAD_BREAK 四轴加载制动
const val EXPANDED_SIZE_FRONT= "M1" // EXPANDED_SIZE_FRONT 外廓尺寸前
const val EXPANDED_SIZE_BESIDE = "M2" // EXPANDED_SIZE_BESIDE 外廓尺寸侧
const val ROAD_TEST_BREAK_START = "R" // ROAD_TEST_BREAK_START 路试制动开始
const val ROAD_TEST_BREAK_STOP = "R0" // ROAD_TEST_BREAK_STOP 路试制动结束
const val SPEED = "S1" // SPEED 车速
const val CURB_QUALITY_FRONT = "Z1" // CURB_QUALITY_FRONT 整备质量前
const val CURB_QUALITY_BEHIND = "Z2" // CURB_QUALITY_BEHIND 整备质量后
const val RAMP_PARKING_FRONT = "R8" // RAMP_PARKING_FRONT 坡道驻车前
const val RAMP_PARKING_BEHIDE = "R9" // RAMP_PARKING_BEHIDE 坡道驻车后
const val VIN_FAR_TO_CLOSED = "F2" // VIN_FAR_TO_CLOSED 车架号由远及近
const val AROUND_VEHICLE = "F3" // AROUND_VEHICLE 环视车辆一周
const val TIRE_TREAD_DEPTH = "F4" // TIRE_TREAD_DEPTH 测量轮胎花纹深度尺寸
const val UNIQUE_FRONT = "UC" // UNIQUE_FRONT 唯一性检查前
const val UNIQUE_BEHIND = "UC0" // UNIQUE_BEHIND = 唯一性检查后
const val ONE_AXIS_PARKING_BREAK = "B01" // ONE_AXIS_PARKING_BREAK 驻车制动（一轴）
const val TWO_AXIS_PARKING_BREAK = "B02" // TWO_AXIS_PARKING_BREAK 驻车制动（二轴）
const val THREE_AXIS_PARKING_BREAK = "B03" // THREE_AXIS_PARKING_BREAK 驻车制动（三轴）
const val FOUR_AXIS_PARKING_BREAK = "B04" // FOUR_AXIS_PARKING_BREAK 驻车制动（四轴）
const val FIVE_AXIS_PARKING_BREAK = "B05" // FIVE_AXIS_PARKING_BREAK 驻车制动（五轴）
const val ONLINE_FRONT_HJ = "100404" // ONLINE_FRONT_HJ 上线检测前视频
const val ONLINE_BEHIND_HJ = "100401" // ONLINE_BEHIND_HJ 上线检测后视频
const val ONLINE_MOVE_HJ = "100402" // ONLINE_MOVE_HJ 上线检测移动视频
const val CHASSIS_HJ = "100203" // CHASSIS_HJ 底盘检测视频
const val EXTERIOR_LEFT_FRONT_HJ = "100201" // EXTERIOR_LEFT_FRONT_HJ 外观检测左前视频
const val EXTERIOR_RIGHT_BEHIND_HJ = "100202" // EXTERIOR_RIGHT_BEHIND_HJ 外观检测右后视频

//请求拍照
const val REQUEST_IMAGE_CAPTURE = 101
const val REQUEST_SFZZP_CAPTURE = 110
const val REQUEST_XSZZP_CAPTURE = 111
//录制视频
const val REQUEST_VIDEO_CAPTURE_F2 = 102
const val REQUEST_VIDEO_CAPTURE_F3 = 103
const val REQUEST_VIDEO_CAPTURE_F4 = 104
//JsonUtil
/**
 * 将请求实体类包装通用请求jsonString
 * @param element 请求实体类实例化对象
 * @return String
 */
fun <T> getJsonData(element : T) : String{
    val requestArray = ArrayList<T>()
    requestArray.add(element)
    return GsonSingleton.instance.toJson(CommonRequest(requestArray))
}
/**
 * 将请求实体类列表包装为通用请求jsonString
 * @param elements 请求实体类实例化对象列表
 * @return String
 */
fun <T> getJsonData(elements : List<T>) : String{
    val requestArray = ArrayList<T>()
    elements.forEach {
        requestArray.add(it)
    }
//    for (element in elements){
//        requestArray.add(element)
//    }
    return GsonSingleton.instance.toJson(CommonRequest(requestArray))
}
//IpUtil
/**
 * 获取本机IP地址 格式为xxx.xxx.xxx.xxx
 */
fun getIpAddress() : String {
    val wifiManager : WifiManager = MyApp.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    if (wifiManager.isWifiEnabled){
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return int2Ip(ipAddress)

    }else{
        Toast.makeText(MyApp.context,"WIFI未打开", Toast.LENGTH_SHORT).show()
        return ""
    }

}

//获取Wifi信号强度

fun getWifiRssi(): Int {
    val wifiManager : WifiManager = MyApp.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiInfo = wifiManager.connectionInfo
    val connectivityManager = MyApp.context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (networkInfo?.isConnected == true){
        return wifiInfo.rssi
    }else{
        Toast.makeText(MyApp.context, "获取wifi信号强度失败", Toast.LENGTH_SHORT).show()
        return 99999
    }
}

/**
 * 将16进制Int类型Ip地址转为10进制String类型
 * 格式为xxx.xxx.xxx.xxx
 * @param ipAddress 16进制Int类型Ip地址
 * @return String
 */
private fun int2Ip(ipAddress : Int) : String{
    return "${ipAddress and 0XFF}.${ipAddress shr 8 and 0XFF}.${ipAddress shr 16 and 0XFF}.${ipAddress shr 24 and 0XFF}"
}
//BitmapUtil
/**
 * 将Bitmap转成base64字符串
 * @param bitmap Bitmap对象
 * @return String base64字符串
 */
fun bitmap2Base64(bitmap: Bitmap) : String{

    var baos : ByteArrayOutputStream? = null
    try {
        baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        baos.flush()
        baos.close()
        val bitmapBytes = baos.toByteArray()
        return Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    }catch (e : Exception){
        e.message
    }
    finally {
        baos?.flush()
        baos?.close()
    }
    return ""
}
fun bitmapDrawable2Base64(bitmapDrawable : Drawable) : String{
    return bitmap2Base64(getBitmapFromDrawable(bitmapDrawable))
}

/**
 * 将base64转成Bitmap对象
 * @param base64 base64字符串
 * @return Bitmap对象
 */
fun base642Bitmap(base64: String?) : Bitmap?{
    val bytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
}

/**
 * 从drawable获取Bitmap
 * 多用于ImageView获取Bitmap对象
 * @param drawable Drawable对象
 * @return Bitmap对象
 */
fun getBitmapFromDrawable(drawable : Drawable) : Bitmap {
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0,0,drawable.intrinsicWidth,drawable.intrinsicHeight)
    drawable.draw(canvas)
    return bitmap
}
//DataUtil
/**
 * 将Date对象转为String并格式化
 * @param date Date对象
 * @param format 格式化要求
 * @return String
 */
fun date2String(date: Date, format: String): String {
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.format(date)
}
/**
 * 将String转化为Date对象
 * @param stringDate String日期
 * @param format stringDate所对应的格式化要求
 * @return Date
 */
fun string2Date(stringDate: String, format: String): Date {
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.parse(stringDate)
}
/**
 * 格式化StringDate
 * @param stringDate StringDate
 * @param oldFormat 旧格式
 * @param newFormat 新格式
 * @return 新格式的StringDate
 */
fun string2String(stringDate: String,oldFormat: String,newFormat: String) : String{
    var simpleDateFormat = SimpleDateFormat(oldFormat)
//        var simpleDateFormat = SimpleDateFormat(format)
    val date = simpleDateFormat.parse(stringDate)
    simpleDateFormat = SimpleDateFormat(newFormat)
    return simpleDateFormat.format(date)
}


/**
 * 调用函数根据responseCode判断请求是否成功并将boolean值存入LiveData中
 * @param response 远程服务器响应数据
 * @param liveData 初始化liveData
 * @return 返回response是否请求成功的LiveData
 */
@OptIn(ExperimentalStdlibApi::class)
fun response2Boolean(response : Response<ResponseBody>, liveData: MutableLiveData<Boolean>) : LiveData<Boolean>{
    if (response != null) {
        if (response.isSuccessful) {
            val stringResponse = response.body()?.string()
            val commonResponse = GsonSingleton.instance
                .fromJson(stringResponse, CommonResponse::class.java)
            if (commonResponse.Code == "1") {
                liveData.value = true
            } else {
                liveData.value = false
                if (commonResponse.Code == null) {
                    Toast.makeText(
                        MyApp.context,
                        "${typeOf<Boolean>()}Code=Null",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            liveData.value = false
            Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
        }
    }else{
        Toast.makeText(MyApp.context, "response = null", Toast.LENGTH_SHORT).show()
    }
    return liveData
}

/**
 * 调用函数将response转为List存入LiveData中
 * @param response 远程服务器响应数据
 * @param liveData 初始化liveData
 * @param E 响应体body实体类
 * @return 返回response转换后的LiveData
 */
@OptIn(ExperimentalStdlibApi::class)
inline fun <reified E> response2ListBean(response : Response<ResponseBody>, liveData: MutableLiveData<List<E>>) : LiveData<List<E>>{
    if (response != null){
        if (response.isSuccessful){
            val stringResponse = response.body()?.string()
            val commonResponse = GsonSingleton.instance
                .fromJson(stringResponse, CommonResponse::class.java)
            if (commonResponse.Code == "1"){
                val beanList = ArrayList<E>()
                for (element in commonResponse.Body) {
                    val bodyJson =
                        GsonSingleton.instance.toJson(element)
                    beanList.add(GsonSingleton.instance
                        .fromJson(bodyJson, E::class.java))
                }
                liveData.value = beanList
            }else{
                if (commonResponse.Code == null){
                    Toast.makeText(MyApp.context, "Code=Null", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
        }
    }else {
        Toast.makeText(MyApp.context, "response = null", Toast.LENGTH_SHORT).show()
    }

    return liveData
}
/**
 * 调用函数将response转为List并将第一个元素存入LiveData中
 * @param response 远程服务器响应数据
 * @param liveData 初始化liveData
 * @param E 响应体body实体类
 * @return 返回response转换后的LiveData
 */
@OptIn(ExperimentalStdlibApi::class)
inline fun <reified E> response2Bean(response : Response<ResponseBody>, liveData: MutableLiveData<E>) : LiveData<E>{
    if (response != null){
        if (response.isSuccessful){
            val stringResponse = response.body()?.string()
            val commonResponse = GsonSingleton.instance
                .fromJson(stringResponse, CommonResponse::class.java)
            if (commonResponse.Code == "1"){
                val beanList = ArrayList<E>()
                for (element in commonResponse.Body) {
                    val bodyJson =
                        GsonSingleton.instance.toJson(element)
                    beanList.add(GsonSingleton.instance
                        .fromJson(bodyJson, E::class.java))
                }
                if (beanList.isNotEmpty()){
                    liveData.value = beanList[0]
                }
            }else{
                if (commonResponse.Code == null){
                    Toast.makeText(MyApp.context, "${typeOf<E>()}Code=Null", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
        }
    }else{
        Toast.makeText(MyApp.context, "${typeOf<E>()}response = null", Toast.LENGTH_SHORT).show()
    }

    return liveData
}

// 获取上传文件Body
fun uploadFile(fileName : String,file : File,requestBody : RequestBody) : MultipartBody.Part{
    val part = MultipartBody.Part.createFormData(fileName,file.name,requestBody)
    return part
}

//获取屏幕高度
fun getScreenHeight(activity: Activity): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        val windowMetrics = activity.window.windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.height() - insets.left - insets.right
    }else{
        val mDisplayMetrics = DisplayMetrics()
        activity.window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
        mDisplayMetrics.heightPixels
    }
}

// 获取屏幕宽度
fun getScreenWidth(activity : Activity): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        val windowMetrics = activity.window.windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    }else{
        val mDisplayMetrics = DisplayMetrics()
        activity.window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
        mDisplayMetrics.widthPixels
    }
}

//Spinner扩展函数 设置字符串（只能设置spinner中有的）
fun Spinner.setText(text: String) {
    for (i in 0 until this.adapter.count) {
        if (this.adapter.getItem(i).toString().contains(text)) {
            this.setSelection(i)
        }
    }
}

// 获取收费信息字符串以供加密
fun getStringFromCollectMoney(collectMoney: CollectMoney) : String{
    val comparator = kotlin.Comparator{
        key1 : String, key2 : String ->
        key1.compareTo(key2)
    }
    val treeMap = TreeMap<String,String>(comparator)
    val stringBuilder = StringBuilder()
    treeMap["appid"] = collectMoney.appid
    treeMap["c"] = collectMoney.c
    treeMap["oid"] = collectMoney.oid
    treeMap["amt"] = collectMoney.amt.toString()
    treeMap["trxreserve"] = collectMoney.trxreserve
    treeMap["sign"] = collectMoney.sign
    treeMap["key"] = collectMoney.key
    treeMap.forEach {
        if (it.value.isNotBlank()){
            stringBuilder.append(it.key).append("=").append(URLEncoder.encode(it.value,"utf-8")).append("&")
        }
    }
//    for(element in treeMap.entries){
//        if (element.value.isNotBlank()){
//            stringBuilder.append(element.key).append("=").append(URLEncoder.encode(element.value,"utf-8")).append("&")
//        }
//    }
    if (stringBuilder.isNotBlank()){
        val string = stringBuilder.toString()
        return string.substring(0,string.length -1)
    }else{
        throw IllegalArgumentException("未找到付款信息")
    }

}

// MD5加密方法
fun md5(b : ByteArray) : String {
    val md = MessageDigest.getInstance("MD5")
    md.reset()
    md.update(b)
    val hash = md.digest()
    val outStrBuf = StringBuffer(32)
    for (i in hash.indices){
        val v : Int= hash[i].toInt() and 0xff
        if (v < 16){
            outStrBuf.append('0')
        }
        outStrBuf.append(v.toString(16).lowercase())
    }
    return outStrBuf.toString()
}

//获取Sign
fun getSignFromCollectMoney(collectMoney: CollectMoney) : String{
    val comparator = kotlin.Comparator{
            key1 : String, key2 : String ->
        key1.compareTo(key2,false)
    }
    val treeMap = TreeMap<String,String>(comparator)
    var stringBuilder = StringBuilder()
    treeMap["appid"] = collectMoney.appid
    treeMap["c"] = collectMoney.c
    treeMap["oid"] = collectMoney.oid
    treeMap["amt"] = collectMoney.amt.toString()
    treeMap["trxreserve"] = collectMoney.trxreserve
    treeMap["sign"] = collectMoney.sign
    treeMap["key"] = collectMoney.key
    for(element in treeMap.entries){
        if (element.value.isNotBlank()){
            if (element.key == "trxreserve" || element.key == "returl"){
                stringBuilder.append(element.key).append("=").append(element.value).append("&")
            }else{
                stringBuilder.append(element.key).append("=").append(URLEncoder.encode(element.value,"utf-8")).append("&")
            }

        }
    }
    if (stringBuilder.isNotBlank()){
        val string = stringBuilder.toString().substring(0,stringBuilder.length -1)
        return md5(string.toByteArray()).uppercase()
    }else{
        throw IllegalArgumentException("未找到付款信息")
    }
}


fun setForceUnable(viewGroup: ViewGroup, isUnable : Boolean){
    for (index in 0 until viewGroup.childCount){
        viewGroup[index].isEnabled = isUnable
    }
}


//设置View可见性
fun setVisibility(activity: Activity, view : View, visibility : Boolean){
    if (visibility){
        view.visibility = View.VISIBLE
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }else{
        view.visibility = View.GONE
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}


inline fun <reified E> sureBodyType(list : List<Any?>) : List<E>{
    val newList = ArrayList<E>()
    list.forEach {
        newList.add(
            GsonSingleton.instance.fromJson(GsonSingleton.instance.toJson(it),E::class.java)
        )
    }
    return newList
}

