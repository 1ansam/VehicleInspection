package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentNetworkQueryBinding
import com.yxf.vehicleinspection.utils.DYNAMIC
import com.yxf.vehicleinspection.utils.string2String
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModel
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory

class NetworkQueryFragment : BaseBindingFragment<FragmentNetworkQueryBinding>() {
    private var AjJyjghb = ""
    private var HjJyjghb = ""
    private var AjJkxlh = ""
    private var beginTime = ""
    private var endTime = ""
    lateinit var bean001 : UserInfoR001Response
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> { InspectionItemViewModelFactory((requireActivity().application as MyApp).inspectionItemRepository,(requireActivity().application as MyApp).serverTimeRepository) }
    lateinit var inspectionItemSelectAdapter: InspectionItemSelectAdapter
    private val args: NetworkQueryFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        binding.includeTitle.btnSubmit.setOnClickListener {
            binding.pbNetworkQuerySubmit.visibility = View.VISIBLE
            inspectionItemViewModel.getServerTime().observe(this) {
                endTime = it.Sj
                var apiNumber = 0
                inspectionItemViewModel.postArtificialProjectW011(getPostArtificialData(inspectionItemSelectAdapter)).observe(this){
                    if (it){
                        apiNumber+=1

                    }else{
                        binding.pbNetworkQuerySubmit.visibility = View.GONE
                        Toast.makeText(this.context, "人工检验信息上传失败", Toast.LENGTH_SHORT).show()
                    }
                }
                if (apiNumber == 1){
                    binding.pbNetworkQuerySubmit.visibility = View.GONE
                    inspectionItemViewModel.postProjectEndW012(getPostProjectEndData()).observe(this){
                        if (it){
                            findNavController().navigate(R.id.action_networkQueryFragment_to_inspectionItemFragment)
                        }else{
                            Toast.makeText(this.context, "项目结束失败", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

        }
        binding.rvSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemSelectAdapter = InspectionItemSelectAdapter()
        binding.rvSelect.adapter = inspectionItemSelectAdapter
    }
    private fun getSelectData(Lsh: String, Jyxm: String, Ajywlb: String, Hjywlb: String){
        inspectionItemViewModel.getSelectItemData(Lsh, Jyxm, Ajywlb, Hjywlb).observe(this){
            val artificialProjectR016Response = it
            inspectionItemSelectAdapter.data = artificialProjectR016Response.Xmlb
        }
    }
    private fun getPostProjectEndData(): ProjectEndW012Request {
        return ProjectEndW012Request(args.bean005.Lsh,AjJyjghb,args.jcxh,args.bean006.Jccs,
            args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,endTime,args.bean006.Ajywlb,
            args.bean006.Hjywlb,AjJkxlh)
    }
    private fun getPostArtificialData(adapter: InspectionItemSelectAdapter): List<ArtificialProjectW011Request<NetworkQueryArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<NetworkQueryArtificialProjectRequest>>()
        val listXmlb = ArrayList<Xmlb>()
        for (index in 0 until adapter.itemCount){
            val holder = binding.rvSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(adapter.data[index].Xmdm,adapter.data[index].Xmms,ivTag, etBz?.text.toString())
            listXmlb.add(xmlb)
        }
        val chassisArtificialProjectRequest = NetworkQueryArtificialProjectRequest(
            args.bean005.Lsh,
            AjJyjghb,
            args.jcxh,
            args.bean006.Jccs,
            args.bean005.Hphm,
            args.bean005.Hpzl,
            args.bean005.Clsbdh,
            args.bean006.Jcxm,
            args.bean006.Ajywlb,
            args.bean006.Hjywlb,
            AjJkxlh,
            listXmlb,
            string2String(beginTime,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
            string2String(endTime,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
            bean001.TrueName,
            "",
            args.bean005.Syr,args.bean005.Sjrdh,args.bean005.Lxdz,""
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,chassisArtificialProjectRequest))
        Log.e("TAG", "getPostArtificialData: $list", )
        return list

    }
    override fun onResume() {
        systemParamsViewModel.getJyjgbh("AJ").observe(this) {
            AjJyjghb = it
        }
        systemParamsViewModel.getJyjgbh("HJ").observe(this) {
            HjJyjghb = it
        }
        systemParamsViewModel.getWebPass("AJ").observe(this){
            AjJkxlh = it
        }
        inspectionItemViewModel.getServerTime().observe(this) {
            beginTime = it.Sj
            getSelectData(args.bean006.Lsh, args.bean006.Jcxm,
                args.bean006.Ajywlb, args.bean006.Hjywlb)
        }
        super.onResume()
    }
}