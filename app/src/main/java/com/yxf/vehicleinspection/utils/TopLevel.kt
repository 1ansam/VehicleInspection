package com.yxf.vehicleinspection.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.wifi.WifiManager
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.bean.request.CommonRequest
import com.yxf.vehicleinspection.bean.response.CommonResponse
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.singleton.GsonSingleton
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *   author:yxf
 *   time:2021/11/15
 *   kotlin顶层函数及常量
 */
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
const val QUERY_ALL_USER = "LYYDJKR001"
const val QUERY_VEHICLE_QUEUE = "LYYDJKR002"
const val QUERY_DATA_DICTIONARY = "LYYDJKR003"
const val QUERY_CHARGE_LIST = "LYYDJKR004"
const val QUERY_VEHICLE_ALL_INFO = "LYYDJKR005"
const val QUERY_VEHICLE_INSPECTION_ITEM = "LYYDJKR006"
const val QUERY_VEHICLE_IMAGE = "LYYDJKR007"
const val QUERY_VEHICLE_VIDEO = "LYYDJKR008"
const val QUERY_ADMISSION_INFO = "LYYDJKR009"
const val QUERY_APPOINTMENT_INFO = "LYYDJKR010"
const val QUERY_SERVER_TIME = "LYYDJKR011"
const val QUERY_BREAK_AND_AXIS_LOAD_CURVE = "LYYDJKR012"
const val QUERY_WAIT_VERIFY = "LYYDJKR013"
const val QUERY_CHARGE_RESULT = "LYYDJKR014"
const val QUERY_SYSTEM_PARAMS = "LYYDJKR015"
const val QUERY_IMAGE_ITEM = "LYYDJKR017"
const val QUERY_ARTIFICIAL_PROJECT = "LYYDJKR020"
const val WRITE_USER_LOGIN = "LYYDJKW001"
const val WRITE_SAVE_SIGNATURE = "LYYDJKW006"
const val WRITE_INSPECTION_PHOTO = "LYYDJKW007"
const val WRITE_SAVE_VIDEO = "LYYDJKW008"
const val WRITE_ARTIFICIAL_PROJECT = "LYYDJKW011"
const val WRITE_PROJECT_END = "LYYDJKW012"
//视频代码常量
/*
 * SIDE_SLIP 侧滑
 * PARKING_BREAK 驻车制动
 * ONE_AXIS_BREAK 一轴制动
 * TWO_AXIS_BREAK 二轴制动
 * THREE_AXIS_BREAK 三轴制动
 * FOUR_AXIS_BREAK 四轴制动
 * FIVE_AXIS_BREAK 五轴制动
 * CHASSIS 底盘
 * POWER 动力性
 * DYNAMIC 底盘动态
 * EXTERIOR_FRONT 外观前
 * EXTERIOR_BEHIDE 外观后
 * LEFT_LIGHT 左外灯
 * RIGHT_LIGHT 右外灯
 * ONE_AXIS_LOAD_BREAK 一轴加载制动
 * TWO_AXIS_LOAD_BREAK 二轴加载制动
 * THREE_AXIS_LOAD_BREAK 三轴加载制动
 * FOUR_AXIS_LOAD_BREAK 四轴加载制动
 * EXPANDED_SIZE_FRONT 外廓尺寸前
 * EXPANDED_SIZE_BESIDE 外廓尺寸侧
 * ROAD_TEST_BREAK_START 路试制动开始
 * ROAD_TEST_BREAK_STOP 路试制动结束
 * SPEED 车速
 * CURB_QUALITY_FRONT 整备质量前
 * CURB_QUALITY_BEHIND 整备质量后
 * RAMP_PARKING_FRONT 坡道驻车前
 * RAMP_PARKING_BEHIDE 坡道驻车后
 * VIN_FAR_TO_CLOSED 车架号由远及近
 * AROUND_VEHICLE 环视车辆一周
 * TIRE_TREAD_DEPTH 测量轮胎花纹深度尺寸
 * UNIQUE_FRONT 唯一性检查前
 * UNIQUE_BEHIND = 唯一性检查后
 * ONE_AXIS_PARKING_BREAK 驻车制动（一轴）
 * TWO_AXIS_PARKING_BREAK 驻车制动（二轴）
 * THREE_AXIS_PARKING_BREAK 驻车制动（三轴）
 * FOUR_AXIS_PARKING_BREAK 驻车制动（四轴）
 * FIVE_AXIS_PARKING_BREAK 驻车制动（五轴）
 * ONLINE_FRONT_HJ 上线检测前视频
 * ONLINE_BEHIND_HJ 上线检测后视频
 * ONLINE_MOVE_HJ 上线检测移动视频
 * CHASSIS_HJ 底盘检测视频
 * EXTERIOR_LEFT_FRONT_HJ 外观检测左前视频
 * EXTERIOR_RIGHT_BEHIND_HJ 外观检测右后视频
 */
const val SIDE_SLIP = "A1"
const val PARKING_BREAK = "B0"
const val ONE_AXIS_BREAK = "B1"
const val TWO_AXIS_BREAK = "B2"
const val THREE_AXIS_BREAK = "B3"
const val FOUR_AXIS_BREAK = "B4"
const val FIVE_AXIS_BREAK = "B5"
const val CHASSIS = "C1"
const val POWER = "CG"
const val DYNAMIC = "DC"
const val EXTERIOR_FRONT = "F1"
const val EXTERIOR_BEHIDE = "F0"
const val LEFT_LIGHT = "H1"
const val RIGHT_LIGHT = "H4"
const val ONE_AXIS_LOAD_BREAK = "L1"
const val TWO_AXIS_LOAD_BREAK = "L2"
const val THREE_AXIS_LOAD_BREAK = "L3"
const val FOUR_AXIS_LOAD_BREAK = "L4"
const val EXPANDED_SIZE_FRONT= "M1"
const val EXPANDED_SIZE_BESIDE = "M2"
const val ROAD_TEST_BREAK_START = "R"
const val ROAD_TEST_BREAK_STOP = "R0"
const val SPEED = "S1"
const val CURB_QUALITY_FRONT = "Z1"
const val CURB_QUALITY_BEHIND = "Z2"
const val RAMP_PARKING_FRONT = "R8"
const val RAMP_PARKING_BEHIDE = "R9"
const val VIN_FAR_TO_CLOSED = "F2"
const val AROUND_VEHICLE = "F3"
const val TIRE_TREAD_DEPTH = "F4"
const val UNIQUE_FRONT = "UC"
const val UNIQUE_BEHIND = "UC0"
const val ONE_AXIS_PARKING_BREAK = "B01"
const val TWO_AXIS_PARKING_BREAK = "B02"
const val THREE_AXIS_PARKING_BREAK = "B03"
const val FOUR_AXIS_PARKING_BREAK = "B04"
const val FIVE_AXIS_PARKING_BREAK = "B05"
const val ONLINE_FRONT_HJ = "100404"
const val ONLINE_BEHIND_HJ = "100401"
const val ONLINE_MOVE_HJ = "100402"
const val CHASSIS_HJ = "100203"
const val EXTERIOR_LEFT_FRONT_HJ = "100201"
const val EXTERIOR_RIGHT_BEHIND_HJ = "100202"

//请求拍照
const val REQUEST_IMAGE_CAPTURE = 101
//录制视频
const val REQUEST_VIDEO_CAPTURE = 102
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
    for (element in elements){
        requestArray.add(element)
    }
    return GsonSingleton.instance.toJson(CommonRequest(requestArray))
}
//IpUtil
/**
 * 获取本机IP地址 格式为xxx.xxx.xxx.xxx
 */
fun getIpAddress() : String {
    val wifiManager : WifiManager = MyApp.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//    if (wifiManager.isWifiEnabled){
//        val wifiInfo = wifiManager.connectionInfo
//        val ipAddress = wifiInfo.ipAddress
//        return int2Ip(ipAddress)
//
//    }else{
//        Toast.makeText(MyApp.context,"WIFI未打开", Toast.LENGTH_SHORT).show()
//        return ""
//    }
    return "192.168.2.132"

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
fun response2Boolean(response : Response<ResponseBody>, liveData: MutableLiveData<Boolean>) : LiveData<Boolean>{
    if (response.isSuccessful){
        val stringResponse = response.body()?.string()
        val commonResponse = GsonSingleton.instance
            .fromJson(stringResponse, CommonResponse::class.java)
        if (commonResponse.Code == "1"){
            liveData.value = true
        }else{
            liveData.value = false
            if (commonResponse.Code == null){
                Toast.makeText(MyApp.context, "服务器Code=Null", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
            }
        }
    }else{
        liveData.value = false
        Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
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
inline fun <reified E> response2ListBean(response : Response<ResponseBody>, liveData: MutableLiveData<List<E>>) : LiveData<List<E>>{
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
                Toast.makeText(MyApp.context, "服务器Code=Null", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
            }
        }
    }else{
        Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
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
inline fun <reified E> response2Bean(response : Response<ResponseBody>, liveData: MutableLiveData<E>) : LiveData<E>{
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
                Toast.makeText(MyApp.context, "服务器Code=Null", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(MyApp.context, commonResponse.Message, Toast.LENGTH_SHORT).show()
            }
        }
    }else{
        Toast.makeText(MyApp.context, response.message(), Toast.LENGTH_SHORT).show()
    }
    return liveData
}
