package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
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
import com.yxf.vehicleinspection.databinding.FragmentDynamicBinding
import com.yxf.vehicleinspection.utils.CHASSIS
import com.yxf.vehicleinspection.utils.CHASSIS_HJ
import com.yxf.vehicleinspection.utils.DYNAMIC
import com.yxf.vehicleinspection.utils.string2String
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModel
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory

class DynamicFragment : BaseBindingFragment<FragmentDynamicBinding>() {
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
    private val args: DynamicFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        binding.includeTitle.btnSubmit.setOnClickListener {
            binding.pbDynamicSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.getServerTime().observe(this) {
                endTime = it.Sj
                inspectionItemViewModel.postSaveVideoW008(getPostVideoData(DYNAMIC,
                    "")).observe(this){
                    if(it){
                        inspectionItemViewModel.postArtificialProjectW011(getPostArtificialData(inspectionItemSelectAdapter)).observe(this){

                            if (it){
                                inspectionItemViewModel.postProjectEndW012(getPostProjectEndData()).observe(this){
                                    binding.pbDynamicSubmit.visibility = View.GONE
                                    if (it){
                                        Toast.makeText(this.context, "底盘动态项目结束", Toast.LENGTH_SHORT).show()
                                        val action = DynamicFragmentDirections.actionDynamicFragmentToSignatureFragment(args.bean006,args.bean005,args.jcxh)
                                        findNavController().navigate(action)
                                    }else{
                                        Toast.makeText(this.context, "底盘动态项目结束失败", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                binding.pbDynamicSubmit.visibility = View.GONE
                                Toast.makeText(this.context, "人工检验信息上传失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                        binding.pbDynamicSubmit.visibility = View.GONE
                    }
                }

            }


        }
        binding.rvSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemSelectAdapter = InspectionItemSelectAdapter()
        binding.rvSelect.adapter = inspectionItemSelectAdapter
        inspectionItemViewModel.getUserInfo().observe(this){
            val nameList = ArrayList<String>()
            for (element in it){
                nameList.add("${element.TrueName}+${element.ID}")
            }
            val ycyAdapter = ArrayAdapter(this.requireActivity(),R.layout.ycy_item_layout,nameList)
            binding.spYcy.adapter = ycyAdapter

        }
        binding.include2.btnLeftPhoto.text = "底盘动态检验开始"
        binding.include2.btnLeftPhoto.setOnClickListener{
            binding.pbDynamicSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.postTakePhoto(TakePhotoW009Request(0,args.bean005.Lsh,
                args.jcxh,args.bean006.Jccs,args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,
                args.bean006.Jcxm,args.bean006.Ajywlb,args.bean006.Jcxm,"52")).observe(this){
                if (it){
                    binding.pbDynamicSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "底盘动态检验开始拍照成功", Toast.LENGTH_SHORT).show()
                }else{
                    binding.pbDynamicSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "底盘动态检验开始拍照失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.include2.btnRightPhoto.text = "底盘动态检验结束"
        binding.include2.btnRightPhoto.setOnClickListener{
            binding.pbDynamicSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.postTakePhoto(TakePhotoW009Request(0,args.bean005.Lsh,
                args.jcxh,args.bean006.Jccs,args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,
                args.bean006.Jcxm,args.bean006.Ajywlb,args.bean006.Jcxm,"53")).observe(this){
                if (it){
                    binding.pbDynamicSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "底盘动态检验结束拍照成功", Toast.LENGTH_SHORT).show()
                }else{
                    binding.pbDynamicSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "底盘动态检验结束拍照失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getSelectData(Lsh: String, Jyxm: String, Ajywlb: String, Hjywlb: String){
        inspectionItemViewModel.getSelectItemData(Lsh, Jyxm, Ajywlb, Hjywlb).observe(this){

            inspectionItemSelectAdapter.data = it[0].Xmlb
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
    private fun getPostArtificialData(adapter: InspectionItemSelectAdapter): List<ArtificialProjectW011Request<DynamicArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<DynamicArtificialProjectRequest>>()
        val listXmlb = ArrayList<Xmlb>()
        val ycy = binding.spYcy.selectedItem.toString().split("+")
        for (index in 0 until adapter.itemCount){
            val holder = binding.rvSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(adapter.data[index].Xmdm,adapter.data[index].Xmms,ivTag, etBz?.text.toString())
            listXmlb.add(xmlb)
        }
        val chassisArtificialProjectRequest = DynamicArtificialProjectRequest(
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
            "".takeIf { binding.etFxpzdzyzdl.text.toString().isBlank() }?:binding.etFxpzdzyzdl.text.toString(),
            "",
            bean001.TrueName,bean001.ID,ycy[0],ycy[1],"",binding.etDynamicBz.text.toString()
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