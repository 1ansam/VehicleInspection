package com.yxf.vehicleinspection.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.databinding.ActivityInspectionInfoBinding

class InspectionInfoActivity : BaseBindingActivity<ActivityInspectionInfoBinding>() {
    private val TAG = "InspectionInfoActivity"
    override fun init() {
        val bundle = intent.getBundleExtra("bundle")
        val baseinfoHand : BaseInfo_Hand = bundle?.getSerializable("key") as BaseInfo_Hand
        Log.e(TAG, "init: ${baseinfoHand.hphm}", )
    }

}