package com.yxf.vehicleinspection

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.utils.getIpAddress
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.*

/**
 *   author:yxf
 *   time:2022/6/10
 */


class CrashHandler(val context: Context) : Thread.UncaughtExceptionHandler{
    companion object{
        val LOG_FILE_CREATE_TIME_FORMAT = "yyyy-MM-dd_HH"
        val LOG_FILE_SUFFIX = "_crash.log"
        val LOG_RECORD_TIME_FORMAT ="yyyy-MM-dd HH:mm:ss"
        var instance : CrashHandler? = null
        fun getInstance(context: Context) : CrashHandler{
            return instance?: CrashHandler(context)
        }
    }
    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
    private var logDir = ""
    private var sb : StringBuffer? = null
    private var handlerListener: UncaughtExceptionHandlerListener? = null


    interface UncaughtExceptionHandlerListener {
        /**
         * 当获取未捕获异常时的处理
         * 一般用于关闭界面和数据库、网络连接等等
         */
        fun handlerUncaughtException(sb: StringBuffer?)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.e("crashLog", "uncaughtException: " )
        handlerListener?.handlerUncaughtException(sb)
    }

    fun setHandlerListener(handlerListener: UncaughtExceptionHandlerListener){
        this.handlerListener = handlerListener
    }

    /**
     * 设置日志所在文件夹路径
     * @param logDirPath
     */
    fun setCrashLogDir(logDirPath: String) {
        logDir = logDirPath
    }

    //崩溃日志的保存操作
    private fun hanldeException(ex: Throwable?) {
        Log.e("ssssssssss", "cuowu2")
        if (ex == null) {
            return
        }
        if (CrashUtils.isSDCardAvaiable(context) && !TextUtils.isEmpty(logDir)) {
            saveCrashInfoToFile(ex)
        }
    }

    private fun saveCrashInfoToFile(ex: Throwable) {
        val info: Writer = StringWriter()
        val printWriter = PrintWriter(info)
        ex.printStackTrace(printWriter)

        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        val content = info.toString()
        printWriter.close()
        val sb = StringBuffer()
        val time = System.currentTimeMillis()
        sb.append(">>>>>>>>>>>>>>时间 ")
        sb.append(date2String(Date(time), LOG_RECORD_TIME_FORMAT))
        sb.append(">>>>>>>>>>>>>> ")
        sb.append("\r\n")
        sb.append(">>>>>>>>>>>>>>手机型号 ")
        sb.append(CrashUtils.getPhoneModel(context))
        sb.append(">>>>>>>>>>>>>> ")
        sb.append("\r\n")
        sb.append(">>>>>>>>>>>>>>IMEI号 ")
        sb.append(CrashUtils.getPhoneIMEI(context))
        sb.append(">>>>>>>>>>>>>> ")
        sb.append("\r\n")
        sb.append(">>>>>>>>>>>>>>应用版本号 ")
        sb.append(CrashUtils.getAppVersionCode(context))
        sb.append(">>>>>>>>>>>>>> ")
        sb.append("\r\n")
        sb.append(">>>>>>>>>>>>>>可用/总内存 ")
        sb.append(
            CrashUtils.getAvailMemory(context).toString() + "/" + CrashUtils.getTotalMemory(
                context
            )
        )
        sb.append(">>>>>>>>>>>>>> ")
        sb.append("\r\n")
        sb.append(">>>>>>>>>>>>>>IP ")
        sb.append(getIpAddress())
        sb.append(">>>>>>>>>>>>>> ")
        sb.append("\r\n")
        sb.append(content)
        sb.append("\r\n")
        sb.append("\r\n")
        this.sb = sb
        CrashUtils.writeToFile(logDir, generateLogFileName("error", time), sb.toString(), "utf-8")
        return
    }
    //生成日志文件名
    private fun generateLogFileName(prefix: String, time: Long): String {
        val sb = StringBuilder()
        sb.append(prefix)
        sb.append("_")
        sb.append(date2String(Date(time), LOG_FILE_CREATE_TIME_FORMAT))
        sb.append(LOG_FILE_SUFFIX)
        return sb.toString()
    }
}