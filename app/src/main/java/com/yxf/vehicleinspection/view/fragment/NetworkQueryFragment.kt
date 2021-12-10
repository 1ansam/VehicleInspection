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
import com.yxf.vehicleinspection.bean.NetworkQueryInfo
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentNetworkQueryBinding
import com.yxf.vehicleinspection.utils.string2String
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.view.adapter.NetworkQueryInfoAdapter
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
    lateinit var networkQueryInfoAdapter: NetworkQueryInfoAdapter
    lateinit var inspectionItemSelectAdapter: InspectionItemSelectAdapter
    private val args: NetworkQueryFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        systemParamsViewModel.getJyjgbh("AJ").observe(this) {
            AjJyjghb = it
            systemParamsViewModel.getJyjgbh("HJ").observe(this) {
                HjJyjghb = it
                systemParamsViewModel.getWebPass("AJ").observe(this){
                    AjJkxlh = it
                    inspectionItemViewModel.getServerTime().observe(this) {
                        beginTime = it.Sj
                        inspectionItemViewModel.postProjectStartW010(ProjectStartW010Request(
                            AjJyjghb,args.jcxh,args.bean005.Hphm,
                            args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,
                            beginTime,args.bean006.Ajywlb,args.bean006.Hjywlb,AjJkxlh,
                            args.bean002.Ajlsh,args.bean002.Hjlsh,
                            args.bean002.Ajjccs,args.bean002.Hjjccs
                        )).observe(this){
                            if (it){
                                getSelectData(args.bean006.Jcxm,
                                    args.bean006.Ajywlb, args.bean006.Hjywlb,
                                    args.bean002.Ajlsh,args.bean002.Hjlsh,)
                            }else{
                                Toast.makeText(MyApp.context, "写入项目开始失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
        binding.rvSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemSelectAdapter = InspectionItemSelectAdapter()
        binding.rvSelect.adapter = inspectionItemSelectAdapter
        binding.rvNetworkQueryInfo.layoutManager = LinearLayoutManager(this.requireContext())
        networkQueryInfoAdapter = NetworkQueryInfoAdapter()
        binding.rvNetworkQueryInfo.adapter = networkQueryInfoAdapter
        val networkQueryInfoList = ArrayList<NetworkQueryInfo>()
        networkQueryInfoList.add(NetworkQueryInfo(inspectionItemViewModel.getNetworkQueryInfoName()[0],""))
        networkQueryInfoList.add(NetworkQueryInfo(inspectionItemViewModel.getNetworkQueryInfoName()[1],args.bean005.Syr))
        networkQueryInfoList.add(NetworkQueryInfo(inspectionItemViewModel.getNetworkQueryInfoName()[2],args.bean005.Sjrdh))
        networkQueryInfoList.add(NetworkQueryInfo(inspectionItemViewModel.getNetworkQueryInfoName()[3],args.bean005.Lxdz))
        networkQueryInfoList.add(NetworkQueryInfo(inspectionItemViewModel.getNetworkQueryInfoName()[4],args.bean005.Zzxzqh))
        networkQueryInfoAdapter.data = networkQueryInfoList
        binding.includeTitle.btnSubmit.setOnClickListener {
            binding.pbNetworkQuerySubmit.visibility = View.VISIBLE
            inspectionItemViewModel.getServerTime().observe(this) {
                endTime = it.Sj
                inspectionItemViewModel.postArtificialProjectW011(getPostArtificialData(inspectionItemSelectAdapter,networkQueryInfoAdapter)).observe(this){
                    if (it){
                        binding.pbNetworkQuerySubmit.visibility = View.GONE
                        inspectionItemViewModel.postProjectEndW012(getPostProjectEndData()).observe(this){
                            if (it){
                                Toast.makeText(this.context, "联网查询项目结束", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_networkQueryFragment_to_inspectionItemFragment)
                            }else{
                                Toast.makeText(this.context, "联网查询项目结束失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        binding.pbNetworkQuerySubmit.visibility = View.GONE
                        Toast.makeText(this.context, "人工检验信息上传失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }
    private fun getSelectData(Jyxm: String, Ajywlb: String, Hjywlb: String, Ajlsh : String, Hjlsh : String){
        inspectionItemViewModel.getSelectItemData(Jyxm, Ajywlb, Hjywlb,Ajlsh, Hjlsh).observe(this){
            inspectionItemSelectAdapter.data = it[0].Xmlb
        }
    }
    private fun getPostProjectEndData(): ProjectEndW012Request {
        return ProjectEndW012Request(AjJyjghb,args.jcxh,
            args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,endTime,args.bean006.Ajywlb,
            args.bean006.Hjywlb,AjJkxlh,args.bean002.Ajlsh,args.bean002.Hjlsh,
            args.bean002.Ajjccs,args.bean002.Hjjccs)
    }
    private fun getPostArtificialData(inspectionItemSelectAdapter: InspectionItemSelectAdapter,networkQueryInfoAdapter: NetworkQueryInfoAdapter): List<ArtificialProjectW011Request<NetworkQueryArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<NetworkQueryArtificialProjectRequest>>()
        val listXmlb = ArrayList<Xmlb>()
        val listInfo = ArrayList<String>()
        for (index in 0 until inspectionItemSelectAdapter.itemCount){
            val holder = binding.rvSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(inspectionItemSelectAdapter.data[index].Xmdm,inspectionItemSelectAdapter.data[index].Xmms,ivTag, etBz?.text.toString())
            listXmlb.add(xmlb)
        }
        for (index in 0 until networkQueryInfoAdapter.itemCount){
            val holder = binding.rvNetworkQueryInfo.findViewHolderForAdapterPosition(index)
            val editValue = holder?.itemView?.findViewById<EditText>(R.id.tvNetworkQueryItemValue)
            listInfo.add(editValue?.text.toString())
        }
        val chassisArtificialProjectRequest = NetworkQueryArtificialProjectRequest(
            AjJyjghb,
            args.jcxh,
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
            listInfo[0],
            listInfo[1],listInfo[2],listInfo[3],listInfo[4],
            args.bean002.Ajlsh,args.bean002.Hjlsh,
            args.bean002.Ajjccs,args.bean002.Hjjccs
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,chassisArtificialProjectRequest))
        Log.e("TAG", "getPostArtificialData: $list", )
        return list

    }

}