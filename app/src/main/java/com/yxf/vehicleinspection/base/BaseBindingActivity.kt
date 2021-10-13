package com.yxf.vehicleinspection.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 *   author:yxf
 *   time:2021/9/3
 *   ViewBinding 在Activity中的封装
 */
abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var binding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType){
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val method = clazz.getMethod("inflate",LayoutInflater::class.java)
            binding = method.invoke(null,layoutInflater) as VB
            setContentView(binding.root)
        }
        init()
    }
    abstract fun init()
}