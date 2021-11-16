package com.yxf.vehicleinspection.view.activity

import android.util.Log
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.ActivityDisplayBinding
import java.io.Serializable
import kotlin.math.log

class DisplayActivity : BaseBindingActivity<ActivityDisplayBinding>() {
    companion object{
        var bean001 : Serializable? = null
    }

    override fun init() {
        val bundle = intent.getBundleExtra("user")
        bean001 = bundle?.getSerializable("bean001")
    }

}