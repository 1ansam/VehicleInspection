package com.yxf.vehicleinspection.view.activity

import androidx.compose.ui.text.toUpperCase
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.base.BaseUrlHelper
import com.yxf.vehicleinspection.bean.Data
import com.yxf.vehicleinspection.databinding.ActivityPersonInspectionBinding
import com.yxf.vehicleinspection.repository.PersonInspectionRepository
import com.yxf.vehicleinspection.view.adapter.PersonInspcetionRvAdapter
import com.yxf.vehicleinspection.viewModel.PersonInspectionViewModel
import com.yxf.vehicleinspection.viewModel.PersonInspectionViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

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
//            修改使用BaseUrlHelper类反射方法
//            BaseUrlHelper.instance.setHostField("192.168.31.70")
            getData(binding.tvSercher.text.toString())
        }
//        在Edittext文字改变后自动获取数据
        binding.tvSercher.doAfterTextChanged {
            getData(binding.tvSercher.text.toString())
        }
    }
    private fun getData(hphm : String){
        viewModel.getData(hphm.uppercase(Locale.getDefault())).observe(this, Observer {
            adapter.setModel(it)
        })
    }
}
