package com.yxf.vehicleinspection



import android.app.Dialog
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.yxf.vehicleinspection.bean.CollectMoney
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.utils.getStringFromCollectMoney
import com.yxf.vehicleinspection.utils.string2Date
import com.yxf.vehicleinspection.view.fragment.RegisterFragment
import org.junit.Test
import java.util.*
import kotlin.concurrent.timerTask


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest(){

    @Test
    fun main(){
        val collection = CollectMoney("appid","c","oid",100,"trxreserve","sign","key")
        print(getStringFromCollectMoney(collection))

    }

}

