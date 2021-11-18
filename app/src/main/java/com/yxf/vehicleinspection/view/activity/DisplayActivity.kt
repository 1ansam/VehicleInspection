package com.yxf.vehicleinspection.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.ActivityDisplayBinding
import java.io.Serializable
import kotlin.math.log

class DisplayActivity : BaseBindingActivity<ActivityDisplayBinding>() {
    val TAG = "TAG"
    companion object{
        var bean001 : Serializable? = null
    }

    override fun init() {
        val bundle = intent.getBundleExtra("user")
        bean001 = bundle?.getSerializable("bean001")
    }

    override fun onPause() {
        Log.e(TAG, "onPause: ", )
        super.onPause()
        Toast.makeText(MyApp.context, "车辆检验正在后台运行", Toast.LENGTH_SHORT).show()
    }



}