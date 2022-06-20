package com.yxf.vehicleinspection

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.telephony.TelephonyManager
import android.text.format.Formatter
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.*
import java.lang.ref.SoftReference
import java.text.SimpleDateFormat
import java.util.*

/**
 *   author:yxf
 *   time:2022/6/10
 */


object CrashUtils {
    private var imei: String = ""

    // 默认为北京时间对应的东八区
    private val GMT = TimeZone.getTimeZone("GMT+8")
    // SD卡的最小剩余容量大小1MB
    private val DEFAULT_LIMIT_SIZE = 1

    val THREADLOCAL_FORMATS = object : ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>(){
        override fun initialValue(): SoftReference<Map<String, SimpleDateFormat>>? {
            return SoftReference<Map<String,SimpleDateFormat>>(HashMap())
        }
    }


    //获取手机信息/
    /**
     * 获取应用版本号
     *
     * @param mContext
     * @return
     */
    fun getAppVersionCode(context: Context) : Long{
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName,0)
        val version = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }
        return version
    }

    /**
     * 获取手机IMEI号
     *
     * @param mContext
     * @return
     */
    fun getPhoneIMEI(context: Context) : String{
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imei = telephonyManager.imei

        }
        return imei

    }

    /**
     * 获取手机型号
     *
     * @param mContext
     * @return
     */
    fun getPhoneModel(context: Context) : String{
        var manufacturer = ""
        var model = ""
        val build_class = Build::class.java
        //取得品牌
        val manu_field = build_class.getField("MANUFACTURER")
        manufacturer = manu_field[Build()] as String
        //取得型号
        val model_field = build_class.getField("MODEL")
        model = model_field[Build()] as String
        return "$manufacturer $model"
    }

    /**
     * 获取本地IP
     *
     * @return
     */

    fun getIpAddress(context: Context) : String {
        return getIpAddress(context)
    }

    /**
     * 获取android当前可用内存大小
     */
    fun getAvailMemory(mContext: Context): String? { // 获取android当前可用内存大小
        val am = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        //mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(mContext, mi.availMem) // 将获取的内存大小规格化
    }

    /**
     * 获得系统总内存
     */

    fun getTotalMemory(context: Context) : String{
        val str1 = "/proc/meminfo" // 系统内存信息文件

        val str2: String
        val initialMemory: Long

        val localFileReader = FileReader(str1)
        val localBufferedReader = BufferedReader(
            localFileReader, 8192
        )
        str2 = localBufferedReader.readLine() //读取meminfo第一行，系统总内存大小


        val arrayOfString: Array<String> = str2.split("\\s+").toTypedArray()
        for (num in arrayOfString) {
            Log.i(str2, num + "\t")
        }
        initialMemory = arrayOfString.get(1).toLong().times(1024) // 获得系统总内存，单位是KB，乘以1024转换为Byte

        localBufferedReader.close()
        return Formatter.formatFileSize(context,initialMemory)

    }

    fun writeToFile(dir : String, fileName : String, content : String, encoder : String){
        val file = File(dir,fileName)
        val parentFile = file.parentFile
        Log.e("crashLog", "writeToFile: " )
        var osw : OutputStreamWriter? = null
        var buffer : BufferedWriter? = null
        try {
            if (!parentFile.exists()){
                parentFile.mkdirs()
            }
            if (!file.exists()){
                file.createNewFile()
            }
            osw = OutputStreamWriter(FileOutputStream(file,true))
            buffer = BufferedWriter(osw)
            buffer.apply {
                append(content)
                append("\r\n")
                flush()
            }
        }catch (exception : IOException){
            throw IOException(exception)
        }
        finally {
            closeSilently(buffer)
            closeSilently(osw)
        }

    }
    /**
     * 关闭流操作
     */
    fun closeSilently(closeable : Closeable?){
        try{
            closeable?.close()
        }catch (e : IOException){
            throw IOException(e)
        }

    }


    //判断SD卡是否可用/
    fun isSDCardAvaiable(context: Context?): Boolean {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            getSDFreeSize() > DEFAULT_LIMIT_SIZE
        } else {
            false
        }
    }


    /**
     * 获取SDCard的剩余大小
     *
     * @return 多少MB
     */
    private fun getSDFreeSize(): Long {
        // 取得SD卡文件路径
        val path = Environment.getExternalStorageDirectory()
        val sf = StatFs(path.path)
        // 获取单个数据块的大小(Byte)
        // 获取单个数据块的大小(Byte)
        val blockSize = sf.blockSizeLong
        // 空闲的数据块的数量
        // 空闲的数据块的数量
        val freeBlocks = sf.availableBlocksLong
        // 返回SD卡空闲大小
        // 返回SD卡空闲大小
        return freeBlocks * blockSize / 1024 / 1024 // 单位MB

    }
}