package com.yxf.vehicleinspection



import org.junit.Test



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest(){
    @Test
    fun main(){
        val list = listOf<Boolean>(true,true,true,false)
        val list2 = listOf<Boolean>(true,true,true,true)
        val list3 = listOf<Boolean>(false,false,false,false)
        val list4 = listOf<Boolean>(false,true,true,true)
        println(isAllNotBlank(list))
        println(isAllNotBlank(list2))
        println(isAllNotBlank(list3))
        println(isAllNotBlank(list4))
    }
    private fun isAllNotBlank(booleanList: List<Boolean>):Boolean{
        for (element in booleanList){
            if (!element){
                return false
            }
        }
        return true
    }
}

