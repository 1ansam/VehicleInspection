package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.os.CountDownTimer
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
import com.google.android.material.snackbar.Snackbar
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentDynamicBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModel
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory

class DynamicFragment : BaseBindingFragment<FragmentDynamicBinding>() {
    private lateinit var count: CountDownTimer
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
                                    args.bean002.Ajlsh,args.bean002.Hjlsh,
                                )
                            }else{
                                Toast.makeText(MyApp.context, "写入项目开始失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
        inspectionItemViewModel.getLeastestTime(args.bean005.Ajcx, args.bean006.Jcxm)
            .observe(this) {
                count = object : CountDownTimer(it.Yqsc.toInt() * 1000L, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.includeTitle.textView.text = "动态查验${millisUntilFinished / 1000}"
                    }
                    override fun onFinish() {
                        Snackbar.make(this@DynamicFragment.requireView(),"检验时间已到可以提交查验", Snackbar.LENGTH_SHORT).show()
                    }
                }.start()
            }
        binding.includeTitle.btnSubmit.setOnClickListener {
            if (binding.includeTitle.textView.text.toString().substring(4) != "0") {
                Snackbar.make(this.requireView(),"检验时间未到",Snackbar.LENGTH_SHORT).show()
            } else {


                binding.pbDynamicSubmit.visibility = View.VISIBLE
                inspectionItemViewModel.getServerTime().observe(this) {
                    endTime = it.Sj
                    inspectionItemViewModel.postSaveVideoW008(
                        getPostVideoData(
                            DYNAMIC_BEHIND,
                            ""
                        )
                    ).observe(this) {

                        if (it) {
                            inspectionItemViewModel.postSaveVideoW008(
                                getPostVideoData(
                                    DYNAMIC_FRONT,
                                    ""
                                )
                            ).observe(this) {
                                if (it) {
                                    inspectionItemViewModel.postArtificialProjectW011(
                                        getPostArtificialData(inspectionItemSelectAdapter)
                                    ).observe(this) {

                                        if (it) {
                                            inspectionItemViewModel.postProjectEndW012(
                                                getPostProjectEndData()
                                            ).observe(this) {
                                                binding.pbDynamicSubmit.visibility = View.GONE
                                                if (it) {
                                                    Toast.makeText(
                                                        this.context,
                                                        "底盘动态项目结束",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    val action =
                                                        DynamicFragmentDirections.actionDynamicFragmentToSignatureFragment(
                                                            args.bean005,
                                                            args.jcxh,
                                                            args.bean002,
                                                            args.bean006.Jcxm,
                                                            args.bean006.Ajywlb,
                                                            args.bean006.Hjywlb
                                                        )
                                                    findNavController().navigate(action)
                                                } else {
                                                    Toast.makeText(
                                                        this.context,
                                                        "底盘动态项目结束失败",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        } else {
                                            binding.pbDynamicSubmit.visibility = View.GONE
                                            Toast.makeText(
                                                this.context,
                                                "人工检验信息上传失败",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                            binding.pbDynamicSubmit.visibility = View.GONE
                        }
                    }

                }
            }

        }
        binding.rvSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemSelectAdapter = InspectionItemSelectAdapter(this)
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
            inspectionItemViewModel.postTakePhoto(TakePhotoW009Request(0,
                args.jcxh,args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,
                args.bean006.Jcxm,args.bean006.Ajywlb,args.bean006.Jcxm,"52",args.bean002.Ajlsh,args.bean002.Hjlsh,
                args.bean002.Ajjccs,args.bean002.Hjjccs)).observe(this){
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
            inspectionItemViewModel.postTakePhoto(TakePhotoW009Request(0,
                args.jcxh,args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,
                args.bean006.Jcxm,args.bean006.Ajywlb,args.bean006.Jcxm,"53",args.bean002.Ajlsh,args.bean002.Hjlsh,
                args.bean002.Ajjccs,args.bean002.Hjjccs)).observe(this){
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
    private fun getSelectData( Jyxm: String, Ajywlb: String, Hjywlb: String, Ajlsh : String, Hjlsh : String){
        inspectionItemViewModel.getSelectItemData(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh).observe(this){

            inspectionItemSelectAdapter.data = it[0].Xmlb
        }
    }
    private fun getPostVideoData(Spbhaj: String,Spbhhj: String) : SaveVideoW008Request {
        return SaveVideoW008Request(0,args.jcxh,args.bean005.Hphm,
            args.bean005.Hpzl,args.bean006.Jcxm,Spbhaj,Spbhhj,args.bean006.Ajywlb,args.bean006.Hjywlb,
            endTime.substring(0,10),endTime.substring(11), string2String(beginTime,
                "yyyy-MM-dd HH:mm:ss",
                "yyyyMMddHHmmss"),
            string2String(endTime,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
            "",args.bean005.Clpp1,args.bean005.Syr,
            args.bean005.Hjdlsj,"","0",
            args.bean002.Ajlsh,args.bean002.Hjlsh,
            args.bean002.Ajjccs,args.bean002.Hjjccs
        )
    }
    private fun getPostProjectEndData(): ProjectEndW012Request {
        return ProjectEndW012Request(AjJyjghb,args.jcxh,
            args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,endTime,args.bean006.Ajywlb,
            args.bean006.Hjywlb,AjJkxlh,args.bean002.Ajlsh,args.bean002.Hjlsh,
            args.bean002.Ajjccs,args.bean002.Hjjccs)
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
            "".takeIf { binding.etFxpzdzyzdl.text.toString().isBlank() }?:binding.etFxpzdzyzdl.text.toString(),
            "",
            bean001.TrueName,bean001.ID,ycy[0],ycy[1],"",binding.etDynamicBz.text.toString(),
            args.bean002.Ajlsh,args.bean002.Hjlsh,
            args.bean002.Ajjccs,args.bean002.Hjjccs
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,chassisArtificialProjectRequest))
        Log.e("TAG", "getPostArtificialData: $list", )
        return list

    }
    override fun onDestroyView() {
        super.onDestroyView()
        count.cancel()
    }

}