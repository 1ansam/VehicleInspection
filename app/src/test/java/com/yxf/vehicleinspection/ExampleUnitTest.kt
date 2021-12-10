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
        val cllx = "摩托车"
        val syxz = "非营运"
        val hdzk = 4
        val zs = 2
        val betweenMonth = 119L
        val boolean = (!registerFragment.isMtc(cllx) || !(registerFragment.isFyyxwzk(cllx, syxz) && (hdzk >= 7 ||betweenMonth >= 118 || cllx.contains("面包"))))
        val boolean2 = !(registerFragment.isMtc(cllx) || (registerFragment.isFyyxwzk(cllx,syxz) && hdzk <7 && betweenMonth < 118 && !cllx.contains("面包")))
        println(boolean2)
    }

}

