package com.yxf.vehicleinspection.base

import com.yxf.vehicleinspection.singleton.SharedP
import okhttp3.HttpUrl
import java.lang.reflect.Field

/**
 *   author:yxf
 *   time:2021/10/6
 *   运行时修改Retrofit(Okhttp) baseUrl
 */
class BaseUrlHelper private constructor(val httpUrl: HttpUrl) {

    companion object {
        //协议
        private var schemeField: Field = HttpUrl::class.java.getDeclaredField("scheme")

        //主机
        private var hostField: Field = HttpUrl::class.java.getDeclaredField("host")

        //端口
        private var portField: Field = HttpUrl::class.java.getDeclaredField("port")

        //url
        private var urlField: Field = HttpUrl::class.java.getDeclaredField("url")
        val instance: BaseUrlHelper
            get() = Instance.instance

    }

    fun setHostField(host: String) {
        try {
            hostField.isAccessible = true
            hostField.set(httpUrl, host)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }


    }

    fun setUrlField(url: String) {
        try {
            urlField.isAccessible = true
            urlField.set(httpUrl, url)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun setSchemeField(scheme: String) {
        try {
            schemeField.isAccessible = true
            schemeField.set(httpUrl, scheme)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
    //port != 0
    fun setPortField(port: Int) {
        try {
            portField.isAccessible = true
            portField.set(httpUrl, port)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    private object Instance {
        val string = "http://${SharedP.instance.getString("ipAddress","192.168.2.132")!!}:${SharedP.instance.getString("ipPort","80")!!}"
        val instance = BaseUrlHelper(
            HttpUrl.get(baseApi)
        )


        private val baseApi: String
            //            private get() = "http://192.168.31.70:8066"
//            private get() = "http://192.168.2.156:8066"
//            get() = "http://192.168.2.157:10000"
//            get() = "http://192.168.2.156:8011"

//            get() = "http://192.168.2.157:10000"

            get() = string
    }
}