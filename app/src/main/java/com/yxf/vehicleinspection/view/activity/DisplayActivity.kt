package com.yxf.vehicleinspection.view.activity

import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityDisplayBinding
import java.io.Serializable


/**
 * 容器Activity
 * 存储bean001 登录员信息
 */
class DisplayActivity : BaseBindingActivity<ActivityDisplayBinding>() {
    companion object{
        var bean001 : Serializable? = null
    }

    override fun init() {
        val bundle = intent.getBundleExtra("user")
        bean001 = bundle?.getSerializable("bean001")

    }






}