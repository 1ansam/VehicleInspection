package com.yxf.vehicleinspection.view.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityPersonInspectionBinding
import com.yxf.vehicleinspection.repository.PersonInspectionRepository
import com.yxf.vehicleinspection.view.adapter.PersonInspcetionRvAdapter
import com.yxf.vehicleinspection.viewModel.PersonInspectionViewModel
import com.yxf.vehicleinspection.viewModel.PersonInspectionViewModelFactory

class PersonInspectionActivity : BaseBindingActivity<ActivityPersonInspectionBinding>() {
    override fun init() {
        val viewModel = ViewModelProvider(this, PersonInspectionViewModelFactory(
            PersonInspectionRepository())).get(PersonInspectionViewModel::class.java)
        if (viewModel.personInspectionData.value != null) {
            val adapter = PersonInspcetionRvAdapter(this, viewModel.personInspectionData.value!!)
            binding.rvPersonInspection.layoutManager = LinearLayoutManager(this)
            binding.rvPersonInspection.adapter = adapter
        }
    }
}
