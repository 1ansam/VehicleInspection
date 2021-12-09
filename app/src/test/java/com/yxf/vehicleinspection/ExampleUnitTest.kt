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
        val cllx = "面包车"
        val syxz = "非营运"
        val hdzk = 5
        val zs = 2
        val betweenMonth = 159L
        val boolean = registerFragment.isMtc(cllx) || ((registerFragment.isZk(cllx) && !cllx.contains("面包") &&(registerFragment.isZk(cllx)&& hdzk < 7)))
        println(boolean)
    }

}

