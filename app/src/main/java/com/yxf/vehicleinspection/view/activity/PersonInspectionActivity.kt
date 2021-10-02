package com.yxf.vehicleinspection.view.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.Data
import com.yxf.vehicleinspection.databinding.ActivityPersonInspectionBinding
import com.yxf.vehicleinspection.repository.PersonInspectionRepository
import com.yxf.vehicleinspection.view.adapter.PersonInspcetionRvAdapter
import com.yxf.vehicleinspection.viewModel.PersonInspectionViewModel
import com.yxf.vehicleinspection.viewModel.PersonInspectionViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PersonInspectionActivity : BaseBindingActivity<ActivityPersonInspectionBinding>() {
    lateinit var adapter: PersonInspcetionRvAdapter
    lateinit var viewModel : PersonInspectionViewModel
    override fun init() {
        viewModel = ViewModelProvider(this, PersonInspectionViewModelFactory(
            PersonInspectionRepository())).get(PersonInspectionViewModel::class.java)
        binding.rvPersonInspection.layoutManager = LinearLayoutManager(this)
        adapter = PersonInspcetionRvAdapter(this,null)
        binding.rvPersonInspection.adapter = adapter
        binding.rvPersonInspection.setHasFixedSize(true)
        getData("")
        binding.btnSercher.setOnClickListener {
            getData(binding.tvSercher.text.toString())
        }
    }
    private fun getData(hphm : String){
        viewModel.getData(hphm).observe(this, Observer {
            adapter.setModel(it)
        })
    }
}
