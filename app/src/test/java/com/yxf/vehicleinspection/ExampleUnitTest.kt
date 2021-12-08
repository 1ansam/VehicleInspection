package com.yxf.vehicleinspection



import android.util.Log
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.utils.string2Date
import com.yxf.vehicleinspection.view.fragment.RegisterFragment
import org.junit.Test
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest(){
    @Test
    fun main(){
        val registerFragment = RegisterFragment()
        val cllx = "小型轿车"
        val syxz = "出租客运"
        val hdzk = 5
        val zs = 2
        val betweenMonth = 117L
        val date = Date()
        val date2 = Date()
        val boolean = registerFragment.isFyyxwzk(cllx, syxz)&&(hdzk < 7 || betweenMonth < 118)
        println(boolean)
    }

}

