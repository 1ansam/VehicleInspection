package com.yxf.vehicleinspection

import android.content.Context
import android.text.format.DateUtils
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yxf.vehicleinspection.room.DataDictionaryDao
import com.yxf.vehicleinspection.room.DataDictionaryDatabase
import com.yxf.vehicleinspection.utils.DateUtil
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest(){
    @Test
    fun main(){
        val string = "2021-11-11 14:08:36"
        val str = DateUtil.string2String(string,"yyyyMMddHHmmss")
        println(str)
    }
}

