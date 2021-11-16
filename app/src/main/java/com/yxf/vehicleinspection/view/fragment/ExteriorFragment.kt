package com.yxf.vehicleinspection.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentExteriorBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemImageAdapter
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.view.adapter.InspectionItemVehicleFeatureAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    private var AjJyjghb = ""
    private var HjJyjghb = ""
    private var AjJkxlh = ""
    private var endTime = ""
    private var dczxllthwsd = false
    private var dcqtllthwsd = false
    private var gclthwsd = false
    private var Sfqssq = false
    private var Sfdzzc = false
    private var Sfkqxj = false
    lateinit var bean001 : UserInfoR001Response
    private var ivImage : ImageView? = null
    lateinit var currentPhotoPath: String
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> {
        InspectionItemViewModelFactory((requireActivity().application as MyApp).inspectionItemRepository,
            (requireActivity().application as MyApp).serverTimeRepository)
    }
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }

    lateinit var inspectionItemImageAdapter: InspectionItemImageAdapter
    lateinit var inspectionItemSelectAdapter: InspectionItemSelectAdapter
    lateinit var inspectionItemVehicleFeatureAdapter: InspectionItemVehicleFeatureAdapter
    private val args: ExteriorFragmentArgs by navArgs()
    private var holder: RecyclerView.ViewHolder? = null
    private var beginTime = ""
    override fun init() {
       bean001 = DisplayActivity.bean001 as UserInfoR001Response
        Log.e("TAG", "init: $bean001", )
        Log.e("TAG", "init: ${bean001.ID}", )
        Log.e("TAG", "init: ${bean001.TrueName}", )
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        inspectionItemImageAdapter = InspectionItemImageAdapter()
        binding.rvImage.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvImage.adapter = inspectionItemImageAdapter
        inspectionItemImageAdapter.onItemViewClickListener =
            object : BaseRvAdapter.OnItemViewClickListener {
                override fun onItemClick(view: View, position: Int) {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                            val photoFile: File? = try {
                                createImageFile()
                            } catch (ex: IOException) {
                                null
                            }
                            photoFile?.also {
                                val photoURI: Uri = FileProvider.getUriForFile(requireContext(),
                                    "com.example.android.fileprovider",
                                    it
                                )
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                            }
                        }
                    }
                    holder = binding.rvImage.findViewHolderForAdapterPosition(position)
                    ivImage = holder?.itemView?.findViewById(R.id.ivImage)
                }
            }

        binding.rvSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemSelectAdapter = InspectionItemSelectAdapter()
        binding.rvSelect.adapter = inspectionItemSelectAdapter
//        binding.rvVehicleFeature.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemVehicleFeatureAdapter = InspectionItemVehicleFeatureAdapter()
        inspectionItemVehicleFeatureAdapter.data = inspectionItemViewModel.getVehicleFeature()
//        binding.rvVehicleFeature.adapter = inspectionItemVehicleFeatureAdapter
        binding.includeTitle.btnSubmit.setOnClickListener {
            binding.pbExteriorSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.getServerTime().observe(this) {
                endTime = it.Sj
                var apiNumber = 0
                inspectionItemViewModel.postInspectionPhotoW007(getPostPhotoData(
                    inspectionItemImageAdapter))
                    .observe(this) {
                        if (it) {
                           apiNumber+=1

                        } else {
                            binding.pbExteriorSubmit.visibility = View.GONE
                        Toast.makeText(MyApp.context, "上传照片失败" , Toast.LENGTH_SHORT).show()
                        }

                    }
                inspectionItemViewModel.postSaveVideoW008(getPostVideoData(EXTERIOR_FRONT,
                    EXTERIOR_LEFT_FRONT_HJ)).observe(this){
                    if(it){
                        apiNumber+=1
                    }else{
                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                        binding.pbExteriorSubmit.visibility = View.GONE
                    }
                }
                inspectionItemViewModel.postSaveVideoW008(getPostVideoData(EXTERIOR_BEHIDE,
                    EXTERIOR_RIGHT_BEHIND_HJ)).observe(this){
                    if(it){
                        apiNumber+=1
                    }else{
                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                        binding.pbExteriorSubmit.visibility = View.GONE
                    }
                }
                inspectionItemViewModel.postArtificialProjectW011(getPostArtificialData(inspectionItemSelectAdapter)).observe(this){
                    if (it){
                        apiNumber+=1

                    }else{
                        binding.pbExteriorSubmit.visibility = View.GONE
                        Toast.makeText(this.context, "人工检验信息上传失败", Toast.LENGTH_SHORT).show()
                    }
                }
                if (apiNumber == 4){
                    binding.pbExteriorSubmit.visibility = View.GONE
                    inspectionItemViewModel.postProjectEndW012(getProjectEndData()).observe(this){
                        if (it){
                            val action =
                                ExteriorFragmentDirections.actionExteriorFragmentToSignatureFragment(
                                    args.bean006,
                                    args.bean005,
                                    args.jcxh)
                            findNavController().navigate(action)
                        }else{
                            Toast.makeText(this.context, "项目结束失败", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }


        }
        binding.tvDczxlhwsd.setOnClickListener{
            dczxllthwsd = !dczxllthwsd
            if (dczxllthwsd){
                binding.llDczxlhwsd.visibility = View.VISIBLE
            }else{
                binding.llDczxlhwsd.visibility = View.GONE
            }
        }
        binding.tvDcqtlhwsd.setOnClickListener{
            dcqtllthwsd = !dcqtllthwsd
            if (dcqtllthwsd){
                binding.llDcqtlhwsd.visibility = View.VISIBLE
            }else{
                binding.llDcqtlhwsd.visibility = View.GONE
            }
        }
        binding.tvGclthwsd.setOnClickListener{
            gclthwsd = !gclthwsd
            if (gclthwsd){
                binding.llGclthwsd.visibility = View.VISIBLE
            }else{
                binding.llGclthwsd.visibility = View.GONE
            }
        }
        if (args.bean005.Dzss == "0"){
            Sfdzzc = false
        }else if (args.bean005.Dzss == "1"){
            Sfdzzc = true
        }
        binding.cbSfdzzc.isChecked = Sfdzzc
        if (binding.cbSfdzzc.isChecked){
            binding.cbSfdzzc.text = "是"
        }else{
            binding.cbSfdzzc.text = "否"
        }
        binding.cbSfdzzc.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                binding.cbSfdzzc.text = "是"
            }else{
                binding.cbSfdzzc.text = "否"
            }
        }


    }


    private fun getImageData(Lsh: String, Jyxm: String, Ajywlb: String, Hjywlb: String) {
        inspectionItemViewModel.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb).observe(this) {
            inspectionItemImageAdapter.data = it
        }
    }

    private fun getSelectData(Lsh: String, Jyxm: String, Ajywlb: String, Hjywlb: String) {
        inspectionItemViewModel.getSelectItemData(Lsh, Jyxm, Ajywlb, Hjywlb).observe(this) {
            val artificialProjectR020Response = it
            inspectionItemSelectAdapter.data = artificialProjectR020Response.Xmlb
        }
    }
    private fun getPostVideoData(Spbhaj: String,Spbhhj: String) : SaveVideoW008Request{
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

    private fun getProjectEndData():ProjectEndW012Request{
        return ProjectEndW012Request(args.bean005.Lsh,AjJyjghb,args.jcxh,args.bean006.Jccs,
            args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,endTime,args.bean006.Ajywlb,
            args.bean006.Hjywlb,AjJkxlh)
    }

    private fun getPostPhotoData(adapter: InspectionItemImageAdapter): List<InspectionPhotoW007Request> {
        val list = ArrayList<InspectionPhotoW007Request>()
        for (index in 0 until adapter.itemCount) {
            val holder = binding.rvImage.findViewHolderForAdapterPosition(index)
            val ivImage = holder?.itemView?.findViewById<ImageView>(R.id.ivImage)
            val drawable = ivImage?.drawable
            val ivTag = ivImage?.tag.toString()
            if (ivTag == "1"&& ivTag != null){
                val bitmap = getBitmapFromDrawable(drawable!!)
                val base64 = bitmap2Base64(bitmap)
                val model = InspectionPhotoW007Request(
                    index, args.bean006.Lsh,
                    AjJyjghb,
                    HjJyjghb,
                    args.jcxh,
                    args.bean006.Jccs,
                    args.bean005.Hphm,
                    args.bean005.Hpzl,
                    args.bean005.Clsbdh,
                    base64,
                    string2String(beginTime,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyyMMddHHmmss"),
                    args.bean006.Jcxm,
                    adapter.data[index].Zpdm,
                    adapter.data[index].Zpmc,
                    adapter.data[index].Zpajdm,
                    adapter.data[index].Zphjdm,
                    adapter.data[index].Bcaj,
                    adapter.data[index].BcHj,
                    args.bean005.Hjdlsj
                )
                list.add(model)
            }


        }

        return list

    }

    private fun getPostArtificialData(adapter: InspectionItemSelectAdapter): List<ArtificialProjectW011Request<ExteriorArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<ExteriorArtificialProjectRequest>>()
        val listXmlb = ArrayList<Xmlb>()
        for (index in 0 until adapter.itemCount){
            val holder = binding.rvSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(adapter.data[index].Xmdm,adapter.data[index].Xmms,ivTag, etBz?.text.toString())
            listXmlb.add(xmlb)
        }
        val exteriorArtificialProjectRequest = ExteriorArtificialProjectRequest(
            args.bean005.Lsh,AjJyjghb,args.jcxh,args.bean006.Jccs,args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,
            args.bean006.Jcxm,args.bean006.Ajywlb,args.bean006.Hjywlb,AjJkxlh,listXmlb,
            string2String(beginTime,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),string2String(endTime,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),binding.etCwkc.text.toString(),binding.etCwkk.text.toString(),binding.etCwkg.text.toString(),"",binding.etCxlbgd.text.toString(),getDczxlhwsd(),
        "","",binding.etYzzgd.text.toString(),binding.etYzygd.text.toString(),
            abs((binding.etYzzgd.text.toString().toInt()-binding.etYzygd.text.toString().toInt())).toString(),binding.etZhzzgd.text.toString(),binding.etZhzygd.text.toString(),abs((binding.etZhzzgd.text.toString().toInt()-binding.etZhzygd.text.toString().toInt())).toString(),"1".takeIf { binding.cbSfqssq.isChecked }?:"0","1".takeIf { binding.cbSfdzzc.isChecked }?:"0","1".takeIf { binding.cbSfkqxj.isChecked }?:"0",binding.etKqxjz.text.toString(),binding.etZxzsl.text.toString(),"",
            bean001.TrueName,bean001.ID,binding.etExteriorBz.text.toString()
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,exteriorArtificialProjectRequest))
        return list
    }

    private fun getDczxlhwsd():String {
        val A1 = binding.etTurnA1.text.toString()
        val A2 = binding.etTurnA2.text.toString()
        val A3 = binding.etTurnA3.text.toString()
        val A4 = binding.etTurnA4.text.toString()
        val B1 = binding.etTurnB1.text.toString()
        val B2 = binding.etTurnB2.text.toString()
        val B3 = binding.etTurnB3.text.toString()
        val B4 = binding.etTurnB4.text.toString()
        val list1 = listOf(A1,A2)
        val list2 = listOf(B1,B2)
        val list3 = listOf(A1,A2,B1,B2)
        val list4 = listOf(A1,A2,A3,A4)
        val list5 = listOf(B1,B2,B3,B4)
        val list6 = listOf(A1,A2,B1,B2,B3,B4)
        val list7 = listOf(A1,A2,A3,A4,B1,B2)
        val list8 = listOf(A1,A2,A3,A4,B1,B2,B3,B4)
        if (isNotBlank(list8)){
            return "A1:$A1/A2:$A2/A3:$A3/A4:$A4/B1:$B1/B2:$B2/B3:$B3/B4:$B4"
        }else if (isNotBlank(list7)){
            return "A1:$A1/A2:$A2/A3:$A3/A4:$A4/B1:$B1/B2:$B2"
        }else if (isNotBlank(list6)){
            return "A1:$A1/A2:$A2/B1:$B1/B2:$B2/B3:$B3/B4:$B4"
        }else if (isNotBlank(list5)){
            return "B1:$B1/B2:$B2/B3:$B3/B4:$B4"
        }else if (isNotBlank(list4)){
            return "A1:$A1/A2:$A2/A3:$A3/A4:$A4"
        }else if (isNotBlank(list3)){
            return "A1:$A1/A2:$A2/B1:$B1/B2:$B2"
        }else if (isNotBlank(list2)){
            return "B1:$B1/B2:$B2"
        }else if (isNotBlank(list1)){
            return "A1:$A1/A2:$A2"
        }else {
            return ""
        }
    }
    private fun isNotBlank(stringList: List<String>):Boolean{
        val booleanList = ArrayList<Boolean>()
        for (element in stringList){
            booleanList.add(element.isNotBlank())
        }
        for (element in booleanList){
            if (!element){
                return false
            }
        }
    return true
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onResume() {
        super.onResume()
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
            getImageData(
                args.bean006.Lsh, args.bean006.Jcxm,
                args.bean006.Ajywlb, args.bean006.Hjywlb
            )
            getSelectData(args.bean006.Lsh, args.bean006.Jcxm,
                args.bean006.Ajywlb, args.bean006.Hjywlb)
        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val targetW: Int = ivImage!!.width
            val targetH: Int = ivImage!!.height

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true

                val photoW: Int = outWidth
                val photoH: Int = outHeight

                // Determine how much to scale down the image
                val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)
                inJustDecodeBounds = false
                inSampleSize = 4
                inPurgeable = true
            }
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                ivImage!!.setImageBitmap(bitmap)
                ivImage!!.tag = "1"
            }

        }

    }


}