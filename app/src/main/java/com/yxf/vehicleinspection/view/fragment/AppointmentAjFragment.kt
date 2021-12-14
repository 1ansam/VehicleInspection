package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.response.AppointmentAjR010Response
import com.yxf.vehicleinspection.databinding.FragmentAppointmentAjBinding
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.view.adapter.AppointmentAjAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.util.*


class AppointmentAjFragment : BaseBindingFragment<FragmentAppointmentAjBinding>() {
    private val dataDictionaryViewModel: DataDictionaryViewModel by viewModels {
        DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository)
    }
    private val registerViewModel : RegisterViewModel by viewModels { RegisterViewModelFactory((requireActivity().application as MyApp).registerRepository) }
    lateinit var adapter: AppointmentAjAdapter
    override fun init() {
        adapter = AppointmentAjAdapter(this, dataDictionaryViewModel)
        binding.rvAppointmentAj.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvAppointmentAj.setHasFixedSize(true)
        binding.rvAppointmentAj.adapter = adapter
        binding.btnEnterRegister.setOnClickListener {
            val action = AppointmentAjFragmentDirections.actionAppointmentAjFragmentToRegisterFragment(
                AppointmentAjR010Response("","","",0,"","","")
            )
            findNavController().navigate(action)
        }
        binding.svAppointmentAj.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getAppointmentAjQueue()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun getAppointmentAjQueue() {
        registerViewModel.getAppointmentAj().observe(this){
            adapter.data = it
        }

    }

    override fun onResume() {
        super.onResume()
        getAppointmentAjQueue()
    }


}