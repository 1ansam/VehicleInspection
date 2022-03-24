package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.response.AppointmentAjR010Response
import com.yxf.vehicleinspection.databinding.FragmentAppointmentAjBinding
import com.yxf.vehicleinspection.view.adapter.AppointmentAjAdapter
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModelFactory
import com.yxf.vehicleinspection.viewModel.RegisterViewModel
import com.yxf.vehicleinspection.viewModel.RegisterViewModelFactory


class AppointmentAjFragment : BaseBindingFragment<FragmentAppointmentAjBinding>() {
    private val dataDictionaryViewModel: DataDictionaryViewModel by viewModels {
        DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository)
    }
    private val registerViewModel : RegisterViewModel by viewModels { RegisterViewModelFactory((requireActivity().application as MyApp).registerRepository) }
    lateinit var adapter: AppointmentAjAdapter
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
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
//        binding.svAppointmentAj.setOnClickListener{
//            binding.svAppointmentAj.onActionViewExpanded()
//        }
//        binding.svAppointmentAj.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                getAppointmentAjQueue()
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
        binding.refresh.setOnRefreshListener {
            getAppointmentAjQueue()
            binding.refresh.isRefreshing = false
        }
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