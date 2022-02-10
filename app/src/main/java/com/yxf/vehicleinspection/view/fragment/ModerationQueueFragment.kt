package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.provider.ContactsContract
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentModerationQueueBinding
import com.yxf.vehicleinspection.view.adapter.VehicleModerationRvAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.util.*

class ModerationQueueFragment : BaseBindingFragment<FragmentModerationQueueBinding>() {
    lateinit var adapter : VehicleModerationRvAdapter
    val verifyViewModel by viewModels<VerifyViewModel> { VerifyViewModelFactory((requireActivity().application as MyApp).verifyRepository) }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val dataDictionaryViewModel by viewModels<DataDictionaryViewModel> { DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository) }
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        adapter = VehicleModerationRvAdapter(this,dataDictionaryViewModel)
        binding.rvModerationQueue.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvModerationQueue.adapter = adapter
        binding.rvModerationQueue.setHasFixedSize(true)
        binding.rbTjsh.isChecked = true
        binding.rgShyw.setOnCheckedChangeListener { group, checkedId ->
            getQueueData(binding.svModerationQueue.query.toString(),group)
        }
        binding.svModerationQueue.setOnClickListener {
            binding.svModerationQueue.onActionViewExpanded()
        }

        binding.svModerationQueue.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getQueueData(query?:"",binding.rgShyw)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        binding.refresh.setOnRefreshListener {
            getQueueData(binding.svModerationQueue.query.toString(),binding.rgShyw)
            binding.refresh.isRefreshing = false
        }
    }

    private fun getQueueData(hphm: String, rgShyw : RadioGroup) {
        binding.pbVehicleQueue.visibility = View.VISIBLE
            when(rgShyw.checkedRadioButtonId){
                R.id.rbTjsh ->{
                    verifyViewModel.getVerifyDataQueue(hphm.uppercase(Locale.getDefault()),"0").observe(this) {
                        binding.pbVehicleQueue.visibility = View.GONE
                        adapter.data = it
                    }
                }
                R.id.rbAjsh ->{
                    verifyViewModel.getVerifyDataQueue(hphm.uppercase(Locale.getDefault()),"1").observe(this) {
                        binding.pbVehicleQueue.visibility = View.GONE
                        adapter.data = it
                    }
                }
                R.id.rbHjsh ->{
                    verifyViewModel.getVerifyDataQueue(hphm.uppercase(Locale.getDefault()),"2").observe(this) {
                        binding.pbVehicleQueue.visibility = View.GONE
                        adapter.data = it
                    }
                }
        }


    }

    override fun onResume() {
        super.onResume()
        getQueueData(binding.svModerationQueue.query.toString(),binding.rgShyw)
    }

}