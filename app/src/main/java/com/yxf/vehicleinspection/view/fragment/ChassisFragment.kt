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
import com.yxf.vehicleinspection.databinding.FragmentChassisBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModel
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory

class ChassisFragment : BaseBindingFragment<FragmentChassisBinding>() {
    private var AjJyjghb = ""
    private var HjJyjghb = ""
    private var AjJkxlh = ""
    private var beginTime = ""
    private var endTime = ""
    lateinit var bean001 : UserInfoR001Response
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> { InspectionItemViewModelFactory((requireActivity().application as MyApp).inspectionItemRepository,(requireActivity().application as MyApp).serverTimeRepository) }
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    lateinit var inspectionItemSelectAdapter: InspectionItemSelectAdapter
    private val args: ChassisFragmentArgs by navArgs()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        binding.includeTitle.btnSubmit.setOnClickListener {
            binding.pbChassisSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.getServerTime().observe(this) {
                endTime = it.Sj
                inspectionItemViewModel.postSaveVideoW008(getPostVideoData(CHASSIS,
                    CHASSIS_HJ)).observe(this){
                    if(it){
                        inspectionItemViewModel.postArtificialProjectW011(getPostArtificialData(inspectionItemSelectAdapter)).observe(this){
                            if (it){
                                binding.pbChassisSubmit.visibility = View.GONE
                                inspectionItemViewModel.postProjectEndW012(getPostProjectEndData()).observe(this){
                                    if (it){
                                        Toast.makeText(this.context, "底盘项目结束", Toast.LENGTH_SHORT).show()
                                        val action = ChassisFragmentDirections.actionChassisFragmentToSignatureFragment(
                                            args.bean006,
                                            args.bean005,
                                            args.jcxh)
                                        findNavController().navigate(action)
                                    }else{
                                        Toast.makeText(this.context, "底盘项目结束失败", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                binding.pbChassisSubmit.visibility = View.GONE
                                Toast.makeText(this.context, "人工检验信息上传失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                        binding.pbChassisSubmit.visibility = View.GONE
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
    private fun getPostVideoData(Spbhaj: String,Spbhhj: String) : SaveVideoW008Request {
        return SaveVideoW008Request(0,args.bean005.Lsh,args.jcxh,args.bean006.Jccs,args.bean005.Hphm,
            args.bean005.Hpzl,args.bean006.Jcxm,Spbhaj,Spbhhj,args.bean006.Ajywlb,args.bean006.Hjywlb,
            endTime.substring(0,10),endTime.substring(11), string2String(beginTime,
                "yyyy-MM-dd HH:mm:ss",
                "yyyyMMddHHmmss"),
            string2String(endTime,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
            "",args.bean005.Clpp1,args.bean005.Syr,
            "0".takeIf { args.bean006.Ajywlb == "-" }?: "1",
            "0".takeIf { args.bean006.Hjywlb == "-" }?: "1",
            args.bean005.Hjdlsj,"","0"
        )
    }
    private fun getPostProjectEndData(): ProjectEndW012Request {
        return ProjectEndW012Request(args.bean005.Lsh,AjJyjghb,args.jcxh,args.bean006.Jccs,
            args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,endTime,args.bean006.Ajywlb,
            args.bean006.Hjywlb,AjJkxlh)
    }
    private fun getPostArtificialData(adapter: InspectionItemSelectAdapter): List<ArtificialProjectW011Request<ChassisArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<ChassisArtificialProjectRequest>>()
        val listXmlb = ArrayList<Xmlb>()
        for (index in 0 until adapter.itemCount){
            val holder = binding.rvSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(adapter.data[index].Xmdm,adapter.data[index].Xmms,ivTag, etBz?.text.toString())
            listXmlb.add(xmlb)
        }
        val chassisArtificialProjectRequest = ChassisArtificialProjectRequest(
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
            "".takeIf { binding.etFxpzdzyzdl.text.toString().isBlank() }?:"${binding.etFxpzdzyzdl.text.toString()}°",
            "",
            bean001.TrueName,bean001.ID,"","","",binding.etChassisBz.text.toString()
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,chassisArtificialProjectRequest))
        Log.e("TAG", "getPostArtificialData: $list", )
        return list

    }

    override fun onResume() {
        super.onResume()
        systemParamsViewModel.getJyjgbh("AJ").observe(this) {
            AjJyjghb = it
            systemParamsViewModel.getJyjgbh("HJ").observe(this) {
                HjJyjghb = it
                systemParamsViewModel.getWebPass("AJ").observe(this){
                    AjJkxlh = it
                    inspectionItemViewModel.getServerTime().observe(this) {
                        beginTime = it.Sj
                        inspectionItemViewModel.postProjectStartW010(ProjectStartW010Request(
                            args.bean005.Lsh,AjJyjghb,args.jcxh,args.bean006.Jccs,args.bean005.Hphm,
                            args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,
                            beginTime,args.bean006.Ajywlb,args.bean006.Hjywlb,AjJkxlh
                        )).observe(this){
                            if (it){
                                getSelectData(args.bean006.Lsh, args.bean006.Jcxm,
                                    args.bean006.Ajywlb, args.bean006.Hjywlb)
                            }else{
                                Toast.makeText(MyApp.context, "写入项目开始失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}