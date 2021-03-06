package com.yxf.vehicleinspection.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Xml
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
import com.yxf.vehicleinspection.base.clickWithTrigger
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentUniqueBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class UniqueFragment : BaseBindingFragment<FragmentUniqueBinding>() {
    private var videoPath: String = ""
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
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        dataDictionaryViewModel.getMc(FL_SPMC,VIN_FAR_TO_CLOSED).observe(this){
            binding.tvF2.text = it
        }
        dataDictionaryViewModel.getMc(FL_SPMC,AROUND_VEHICLE).observe(this){
            binding.tvF3.text = it
        }
        dataDictionaryViewModel.getMc(FL_SPMC,TIRE_TREAD_DEPTH).observe(this){
            binding.tvF4.text = it
        }
        binding.rvSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemSelectAdapter = InspectionItemSelectAdapter(this)
        binding.rvSelect.adapter = inspectionItemSelectAdapter
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
                                getSelectData( args.bean006.Jcxm,
                                    args.bean006.Ajywlb, args.bean006.Hjywlb,
                                    args.bean002.Ajlsh,args.bean002.Hjlsh,)
                            }else{
                                Toast.makeText(MyApp.context, "????????????????????????", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        binding.includeTitle.btnSubmit.clickWithTrigger {
            binding.pbUniqueSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.getServerTime().observe(this) {
                endTime = it.Sj
                inspectionItemViewModel.postSaveVideoW008(getPostVideoData(args.bean006.Jcxm,UNIQUE_FRONT,
                    "",endTime)).observe(this){
                    if(it){
                        inspectionItemViewModel.postSaveVideoW008(getPostVideoData(args.bean006.Jcxm,UNIQUE_BEHIND,
                            "",endTime)).observe(this){
                            if(it){
                                inspectionItemViewModel.postArtificialProjectW011(getPostArtificialData(inspectionItemSelectAdapter)).observe(this){
                                    if (it){

                                        inspectionItemViewModel.postProjectEndW012(getPostProjectEndData()).observe(this){
                                            binding.pbUniqueSubmit.visibility = View.GONE
                                            if (it){
                                                Toast.makeText(this.context, "?????????????????????", Toast.LENGTH_SHORT).show()
                                                findNavController().navigate(R.id.action_uniqueFragment_to_inspectionItemFragment)
                                            }else{
                                                Toast.makeText(this.context, "???????????????????????????", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }else{
                                        binding.pbUniqueSubmit.visibility = View.GONE
                                        Toast.makeText(this.context, "??????????????????????????????", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                Toast.makeText(this.context, "??????????????????", Toast.LENGTH_SHORT).show()
                                binding.pbUniqueSubmit.visibility = View.GONE
                            }
                        }
                    }else{
                        Toast.makeText(this.context, "??????????????????", Toast.LENGTH_SHORT).show()
                        binding.pbUniqueSubmit.visibility = View.GONE
                    }
                }
            }
        }

        binding.btnRecordF2.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                    val videoFile: File? = try {
                        createVideoFile(args.bean002.Ajlsh,args.bean002.Ajjccs,"F2")
                    } catch (ex: IOException) {
                        null
                    }
                    videoFile?.also {
                        val videoURI: Uri = FileProvider.getUriForFile(requireContext(),
                            FILE_PROVIDER,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                        startActivityForResult(takePictureIntent, REQUEST_VIDEO_CAPTURE_F2)
                    }
                }
            }
        }
        binding.btnRecordF3.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                    val videoFile: File? = try {
                        createVideoFile(args.bean002.Ajlsh,args.bean002.Ajjccs,"F3")
                    } catch (ex: IOException) {
                        null
                    }
                    videoFile?.also {
                        val videoURI: Uri = FileProvider.getUriForFile(requireContext(),
                            FILE_PROVIDER,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                        startActivityForResult(takePictureIntent, REQUEST_VIDEO_CAPTURE_F3)
                    }
                }
            }
        }
        binding.btnRecordF4.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                    val videoFile: File? = try {
                        createVideoFile(args.bean002.Ajlsh,args.bean002.Ajjccs,"F4")
                    } catch (ex: IOException) {
                        null
                    }
                    videoFile?.also {
                        val videoURI: Uri = FileProvider.getUriForFile(requireContext(),
                            FILE_PROVIDER,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                        startActivityForResult(takePictureIntent, REQUEST_VIDEO_CAPTURE_F4)
                    }
                }
            }
        }
    }

    private fun createVideoFile(Ajlsh: String, Ajjccs : Int, Jyxm: String): File {
        val storageDir: File? =
            this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "${Ajlsh}_${Ajjccs}_${Jyxm}-",
            ".mp4",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            videoPath = absolutePath
        }
    }



    private fun getSelectData(Jyxm: String, Ajywlb: String, Hjywlb: String, Ajlsh: String, Hjlsh : String){
        inspectionItemViewModel.getSelectItemData(Jyxm, Ajywlb, Hjywlb, Ajlsh,Hjlsh).observe(this){
            inspectionItemSelectAdapter.data = it[0].Xmlb
        }
    }
    private fun getPostVideoData(Jyxm: String,Spbhaj: String,Spbhhj: String,endTime : String) : SaveVideoW008Request {
        return SaveVideoW008Request(0,
            args.jcxh,
            args.bean005.Hphm,
            args.bean005.Hpzl,
            Jyxm,
            Spbhaj,
            Spbhhj,
            args.bean006.Ajywlb,
            args.bean006.Hjywlb,
            endTime.substring(0,10),
            endTime.substring(11),
            string2String(beginTime, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"),
            string2String(endTime,"yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"),
            "",
            args.bean005.Clpp1,
            args.bean005.Syr,
            args.bean005.Hjdlsj,
            "",
            "0",
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
    private fun getPostArtificialData(Ajadapter: InspectionItemSelectAdapter): List<ArtificialProjectW011Request<UniqueArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<UniqueArtificialProjectRequest>>()
        val listXmlb = ArrayList<Xmlb>()
        for (index in 0 until Ajadapter.itemCount){
            val holder = binding.rvSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(Ajadapter.data[index].Xmdm,Ajadapter.data[index].Xmms,ivTag, etBz?.text.toString())
            listXmlb.add(xmlb)
        }
        val chassisArtificialProjectRequest = UniqueArtificialProjectRequest(
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
            binding.etBghfdjxh.text.toString(),
            binding.etBghfdjh.text.toString(),
            "",
            bean001.TrueName,
            bean001.ID,
            binding.etUniqueBz.text.toString(),
            args.bean002.Ajlsh,args.bean002.Hjlsh,
            args.bean002.Ajjccs,args.bean002.Hjjccs
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,chassisArtificialProjectRequest))
        return list

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE_F2 && resultCode == RESULT_OK){
            val file = File(videoPath)
            val mediaType = MediaType.parse("multipart/form-data")
            val requestBody = RequestBody.create(mediaType,file)
            inspectionItemViewModel.postUploadFile(file,requestBody).observe(this){ path ->
                if (path!=null&&path.isNotBlank()){
                    inspectionItemViewModel.getServerTime().observe(this){
                        inspectionItemViewModel.postSaveVideoW008(inspectionItemViewModel.getPostVideoData(
                            args.jcxh,args.bean005.Hphm,
                            args.bean005.Hpzl,VIN_FAR_TO_CLOSED,
                            VIN_FAR_TO_CLOSED, "",args.bean006.Ajywlb,args.bean006.Hjywlb,
                            it.Sj.substring(0,10),it.Sj.substring(11), string2String(beginTime,
                                "yyyy-MM-dd HH:mm:ss",
                                "yyyyMMddHHmmss"),
                            string2String(it.Sj,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
                            "",args.bean005.Clpp1,args.bean005.Syr,
                            args.bean005.Hjdlsj,path,"1",
                            args.bean002.Ajlsh,args.bean002.Hjlsh,
                            args.bean002.Ajjccs,args.bean002.Hjjccs)
                        ).observe(this){
                            if (it){
                                Toast.makeText(MyApp.context, "???????????????????????????????????????", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(MyApp.context, "???????????????????????????????????????", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }else{
                    Toast.makeText(MyApp.context, "????????????", Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE_F3 && resultCode == RESULT_OK){
            val file = File(videoPath)
            val mediaType = MediaType.parse("multipart/form-data")
            val requestBody = RequestBody.create(mediaType,file)
            inspectionItemViewModel.postUploadFile(file,requestBody).observe(this){ path ->
                if (path!=null&&path.isNotBlank()){
                    inspectionItemViewModel.getServerTime().observe(this){
                        inspectionItemViewModel.postSaveVideoW008(inspectionItemViewModel.getPostVideoData(
                            args.jcxh,args.bean005.Hphm,
                            args.bean005.Hpzl,AROUND_VEHICLE,
                            AROUND_VEHICLE, "",args.bean006.Ajywlb,args.bean006.Hjywlb,
                            it.Sj.substring(0,10),it.Sj.substring(11), string2String(beginTime,
                                "yyyy-MM-dd HH:mm:ss",
                                "yyyyMMddHHmmss"),
                            string2String(it.Sj,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
                            "",args.bean005.Clpp1,args.bean005.Syr,
                            args.bean005.Hjdlsj,path,"1",
                            args.bean002.Ajlsh,args.bean002.Hjlsh,
                            args.bean002.Ajjccs,args.bean002.Hjjccs)
                        ).observe(this){
                            if (it){
                                Toast.makeText(MyApp.context, "????????????????????????????????????", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(MyApp.context, "????????????????????????????????????", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }else{
                    Toast.makeText(MyApp.context, "????????????", Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE_F4 && resultCode == RESULT_OK){
            val file = File(videoPath)
            val mediaType = MediaType.parse("multipart/form-data")
            val requestBody = RequestBody.create(mediaType,file)
            inspectionItemViewModel.postUploadFile(file,requestBody).observe(this){ path ->
                if (path!=null&&path.isNotBlank()){
                    inspectionItemViewModel.getServerTime().observe(this){
                        inspectionItemViewModel.postSaveVideoW008(inspectionItemViewModel.getPostVideoData(
                            args.jcxh,args.bean005.Hphm,
                            args.bean005.Hpzl,TIRE_TREAD_DEPTH,
                            TIRE_TREAD_DEPTH, "",args.bean006.Ajywlb,args.bean006.Hjywlb,
                            it.Sj.substring(0,10),it.Sj.substring(11), string2String(beginTime,
                                "yyyy-MM-dd HH:mm:ss",
                                "yyyyMMddHHmmss"),
                            string2String(it.Sj,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
                            "",args.bean005.Clpp1,args.bean005.Syr,
                            args.bean005.Hjdlsj,path,"1",
                            args.bean002.Ajlsh,args.bean002.Hjlsh,
                            args.bean002.Ajjccs,args.bean002.Hjjccs)
                        ).observe(this){
                            if (it){
                                Toast.makeText(MyApp.context, "????????????????????????????????????", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(MyApp.context, "????????????????????????????????????", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }else{
                    Toast.makeText(MyApp.context, "????????????", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}