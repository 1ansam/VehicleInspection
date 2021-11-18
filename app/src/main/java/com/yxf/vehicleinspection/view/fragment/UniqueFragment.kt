package com.yxf.vehicleinspection.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentUniqueBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UniqueFragment : BaseBindingFragment<FragmentUniqueBinding>() {
    private var AjJyjghb = ""
    private var HjJyjghb = ""
    private var AjJkxlh = ""
    private var beginTime = ""
    private var endTime = ""
    lateinit var bean001 : UserInfoR001Response
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    private val dataDictionaryViewModel by viewModels<DataDictionaryViewModel> {
        DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository)
    }
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> { InspectionItemViewModelFactory((requireActivity().application as MyApp).inspectionItemRepository,(requireActivity().application as MyApp).serverTimeRepository) }
    lateinit var inspectionItemSelectAdapter: InspectionItemSelectAdapter
    private val args: UniqueFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        binding.includeTitle.btnSubmit.setOnClickListener {
            binding.pbUniqueSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.getServerTime().observe(this) {
                endTime = it.Sj
                var apiNumber = 0
                inspectionItemViewModel.postSaveVideoW008(getPostVideoData(UNIQUE_FRONT,
                    "")).observe(this){
                    if(it){
                        apiNumber+=1
                    }else{
                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                        binding.pbUniqueSubmit.visibility = View.GONE
                    }
                }
                inspectionItemViewModel.postSaveVideoW008(getPostVideoData(UNIQUE_BEHIND,
                    "")).observe(this){
                    if(it){
                        apiNumber+=1
                    }else{
                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                        binding.pbUniqueSubmit.visibility = View.GONE
                    }
                }
                inspectionItemViewModel.postArtificialProjectW011(getPostArtificialData(inspectionItemSelectAdapter)).observe(this){
                    if (it){
                        apiNumber+=1

                    }else{
                        binding.pbUniqueSubmit.visibility = View.GONE
                        Toast.makeText(this.context, "人工检验信息上传失败", Toast.LENGTH_SHORT).show()
                    }
                }
                if (apiNumber == 3){
                    binding.pbUniqueSubmit.visibility = View.GONE
                    inspectionItemViewModel.postProjectEndW012(getPostProjectEndData()).observe(this){
                        if (it){
                            findNavController().navigate(R.id.action_uniqueFragment_to_inspectionItemFragment)
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
        binding.vvF2.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                        takePictureIntent.putExtra("Dm","F2")
                        startActivityForResult(takePictureIntent, REQUEST_VIDEO_CAPTURE)

                }
            }
        }
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
    private fun getPostArtificialData(adapter: InspectionItemSelectAdapter): List<ArtificialProjectW011Request<UniqueArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<UniqueArtificialProjectRequest>>()
        val listXmlb = ArrayList<Xmlb>()
        for (index in 0 until adapter.itemCount){
            val holder = binding.rvSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(adapter.data[index].Xmdm,adapter.data[index].Xmms,ivTag, etBz?.text.toString())
            listXmlb.add(xmlb)
        }
        val chassisArtificialProjectRequest = UniqueArtificialProjectRequest(
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
            binding.etBghfdjxh.text.toString(),
            binding.etBghfdjh.text.toString(),
            "",
            bean001.TrueName,
            bean001.ID,
            binding.etUniqueBz.text.toString()
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,chassisArtificialProjectRequest))
        Log.e("TAG", "getPostArtificialData: $list", )
        return list

    }
    override fun onResume() {
        super.onResume()
        dataDictionaryViewModel.getMc("84","F2").observe(this){
            binding.tvF2.text = it
        }
        dataDictionaryViewModel.getMc("84","F3").observe(this){
            binding.tvF3.text = it
        }
        dataDictionaryViewModel.getMc("84","F4").observe(this){
            binding.tvF4.text = it
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("22222", "onActivityResult: ${data?.getStringExtra("Dm")}", )
    }
}