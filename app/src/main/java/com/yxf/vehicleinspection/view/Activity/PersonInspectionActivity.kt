package com.yxf.vehicleinspection.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.databinding.ActivityPersonInspectionBinding
import com.yxf.vehicleinspection.view.Adapter.PersonInspcetionAdapter

class PersonInspectionActivity : BaseBindingActivity<ActivityPersonInspectionBinding>(){
    override fun init() {



        val cars = ArrayList<BaseInfo_Hand>()
        for (index in 0 until 100){
            cars.add(BaseInfo_Hand("$index","20210928160801","小型汽车","晋K7058N","在用车定检","年检"))
        }
        val adapter = PersonInspcetionAdapter(this,cars)
        binding.rvPersonInspection.layoutManager = LinearLayoutManager(this)
        binding.rvPersonInspection.adapter = adapter
    }

}
