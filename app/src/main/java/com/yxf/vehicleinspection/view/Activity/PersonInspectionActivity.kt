package com.yxf.vehicleinspection.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.databinding.ActivityPersonInspectionBinding
import com.yxf.vehicleinspection.view.Adapter.PersonInspcetionAdapter

class PersonInspectionActivity : BaseBindingActivity<ActivityPersonInspectionBinding>(){
    override fun init() {



        val cars = ArrayList<BaseInfo_Hand>()
        for (index in 0 until 10){
            cars.add(BaseInfo_Hand("$index","20210928160801","02","02661","dingjian","nianjian"))
        }
        val adapter = PersonInspcetionAdapter(cars)
        binding.rvPersonInspection.adapter = adapter
    }

}
