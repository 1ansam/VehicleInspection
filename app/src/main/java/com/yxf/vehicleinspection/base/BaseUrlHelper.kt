package com.yxf.vehicleinspection.base

import okhttp3.HttpUrl
import com.yxf.vehicleinspection.base.BaseUrlHelper
import java.lang.reflect.Field

/**
 *   author:yxf
 *   time:2021/10/6
 *   运行时修改Retrofit(Okhttp) baseUrl
 */
class BaseUrlHelper private constructor(val httpUrl: HttpUrl) {

    companion object {
        //协议
        private var schemeField: Field? = null

        //主机
        private var hostField: Field? = null

        //端口
        private var portField: Field? = null

        //url
        private var urlField: Field? = null
        val instance: BaseUrlHelper
            get() = Instance.instance

        init {
            var scheme: Field? = null
            var host: Field? = null
            var port: Field? = null
            var url: Field? = null
            try {
                scheme = HttpUrl::class.java.getDeclaredField("scheme")
                port = HttpUrl::class.java.getDeclaredField("port")
                host = HttpUrl::class.java.getDeclaredField("host")
                url = HttpUrl::class.java.getDeclaredField("url")
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }
            urlField = url
            hostField = host
            portField = port
            schemeField =scheme
        }
    }

    fun setHostField(host: String?) {
        try {
            hostField?.isAccessible = true
            hostField?.set(httpUrl, host)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun setUrlField(url: String?) {
        try {
            urlField?.isAccessible = true
            urlField?.set(httpUrl, url)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun setSchemeField(scheme: String?) {
        try {
            schemeField?.isAccessible = true
            schemeField?.set(httpUrl, scheme)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun setPortField(port: Int) {
        try {
            portField?.isAccessible = true
            portField?.set(httpUrl, port)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    private object Instance {
        val instance = BaseUrlHelper(
            HttpUrl.get(baseApi)
        )

        //此处BaseApi的生成，可以参考上一小节的内容
        private val baseApi: String
//            private get() = "http://192.168.31.70:8066"
            private get() = "http://192.168.2.156:8066"
    }
}