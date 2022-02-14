package com.yxf.vehicleinspection.view.fragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.OnlineStatusR024Request
import com.yxf.vehicleinspection.bean.request.StartOnlineW015Request
import com.yxf.vehicleinspection.bean.response.Jcgw
import com.yxf.vehicleinspection.bean.response.OnlineStatusR024Response
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentOnlineGwBinding
import com.yxf.vehicleinspection.utils.FL_HPZL
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.OnlineAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.util.*
import java.util.logging.LogRecord
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class OnlineFragment : BaseBindingFragment<FragmentOnlineGwBinding>() {
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> { InspectionItemViewModelFactory((requireActivity().application as MyApp).inspectionItemRepository,(requireActivity().application as MyApp).serverTimeRepository) }
    private val dataDictionaryViewModel: DataDictionaryViewModel by viewModels {
        DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository)
    }
    private lateinit var bean001: UserInfoR001Response
    private val args : OnlineFragmentArgs by navArgs()
    val onlineAdapter = OnlineAdapter ()
    private val timer = Timer()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        binding.tvYcyName.text = bean001.UserName
        binding.tvLsh.text = args.bean005.Ajlsh
        binding.tvHphm.text = args.bean005.Hphm
        dataDictionaryViewModel.getMc(FL_HPZL,args.bean005.Hpzl).observe(this){
            binding.tvHpzl.text = it
        }
        binding.tvJcxh.text = args.bean006.Jcxh
        binding.rvOnline.apply {
            layoutManager = LinearLayoutManager(this@OnlineFragment.requireContext())
            setHasFixedSize(true)
            adapter = onlineAdapter
        }
        val handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                val liveData = msg.obj as LiveData<OnlineStatusR024Response>
                liveData.observe(this@OnlineFragment){
                    onlineAdapter.data = it.Jcgw
                    if (it.Xszt == "2"){
                        findNavController().navigate(OnlineFragmentDirections.actionOnlineFragmentPopIncludingInspectionItemFragment())
                    }

                }
            }
        }
        inspectionItemViewModel.startOnline(StartOnlineW015Request(args.jcxh,args.bean005.Ajlsh,bean001.TrueName)).observe(this){
            if (it){
                timer.schedule(
                    timerTask {
                        val message = Message()
                        message.obj = inspectionItemViewModel.getOnlineStatus(
                            OnlineStatusR024Request(args.bean005.Ajlsh)
                        )
                        handler.sendMessage(message)
                    },1000,3000
                )
            }else{
                Snackbar.make(this.requireView(),"线上开始失败",Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.btnStartInspection.setOnClickListener{

        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(OnlineFragmentDirections.actionOnlineFragmentPopIncludingInspectionItemFragment())
        }
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

}