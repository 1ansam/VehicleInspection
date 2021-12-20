package com.yxf.vehicleinspection.view.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.bean.request.InspectionPhotoW007Request
import com.yxf.vehicleinspection.bean.response.ImageItemR017Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemR006Response
import com.yxf.vehicleinspection.databinding.FragmentReplenishBinding
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.adapter.InspectionItemAdapter
import com.yxf.vehicleinspection.view.adapter.InspectionItemImageAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleAllInfoAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.*
import io.reactivex.rxkotlin.toObservable
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ReplenishFragment : BaseBindingFragment<FragmentReplenishBinding>() {
    private lateinit var vehicleInformationAdapter: VehicleAllInfoAdapter
    private val vehicleAllInfoViewModel by viewModels<VehicleAllInfoViewModel> {
        VehicleAllInfoViewModelFactory((requireActivity().application as MyApp).vehicleAllInfoRepository)
    }
    private val dataDictionaryViewModel by viewModels<DataDictionaryViewModel> { DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository) }

    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> {
        InspectionItemViewModelFactory(
            (requireActivity().application as MyApp).inspectionItemRepository,
            (requireActivity().application as MyApp).serverTimeRepository
        )
    }
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    val inspectionItemImageAdapter = InspectionItemImageAdapter()
    private val args: InspectionItemFragmentArgs by navArgs()
    lateinit var currentPhotoPath: String
    private var videoPath: String = ""
    private var holder: RecyclerView.ViewHolder? = null
    private var ivImage: ImageView? = null
    lateinit var bean005 : VehicleAllInfoR005Response
    private var AjJyjghb = ""
    private var HjJyjghb = ""
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        dataDictionaryViewModel.getMc(FL_SPMC, VIN_FAR_TO_CLOSED).observe(this){
            binding.tvF2.text = it
        }
        dataDictionaryViewModel.getMc(FL_SPMC, AROUND_VEHICLE).observe(this){
            binding.tvF3.text = it
        }
        dataDictionaryViewModel.getMc(FL_SPMC, TIRE_TREAD_DEPTH).observe(this){
            binding.tvF4.text = it
        }
        binding.btnRecordF2.alpha = 0.5F
        binding.btnRecordF3.alpha = 0.5F
        binding.btnRecordF4.alpha = 0.5F
        binding.rvVehicleInformation.layoutManager = LinearLayoutManager(this.requireContext())
        vehicleInformationAdapter = VehicleAllInfoAdapter(this,dataDictionaryViewModel)
        binding.rvVehicleInformation.adapter = vehicleInformationAdapter
        binding.rvVehicleInformation.setHasFixedSize(true)
        binding.rvImage.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvImage.adapter = inspectionItemImageAdapter
        inspectionItemImageAdapter.onItemViewClickListener =
            object : BaseRvAdapter.OnItemViewClickListener<ImageItemR017Response> {
                override fun onItemClick(view: View, position: Int, bean: ImageItemR017Response) {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                            val photoFile: File? = try {
                                createImageFile()
                            } catch (ex: IOException) {
                                null
                            }
                            photoFile?.also {
                                val photoURI: Uri = FileProvider.getUriForFile(
                                    requireContext(),
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
                            "com.example.android.fileprovider",
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
                            "com.example.android.fileprovider",
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
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
                        startActivityForResult(takePictureIntent, REQUEST_VIDEO_CAPTURE_F4)
                    }
                }
            }
        }
        binding.includeTitle.btnSubmit.setOnClickListener{
            systemParamsViewModel.getJyjgbh("AJ").observe(this){
                AjJyjghb = it
                systemParamsViewModel.getJyjgbh("HJ").observe(this){
                    HjJyjghb = it
                    inspectionItemViewModel.postInspectionPhotoW007(
                        getPostPhotoData(
                            inspectionItemImageAdapter
                        )
                    ).observe(this){
                        if (it){
                            Toast.makeText(this.requireContext(), "上传成功", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }
    private fun getData(Hphm: String, Hpzl: String, Clsbdh: String, Xszbh: String, Ajlsh : String, Hjlsh : String) {
        val vehicleInformationList =
            ArrayList<VehicleAllInfoR005Response>()
        vehicleAllInfoViewModel.getVehicleAllInfo(Hphm, Hpzl, Clsbdh, Xszbh, Ajlsh,Hjlsh)
            .observe(this) {
                for (element in it) {
                    vehicleInformationList.add(element)
                    bean005 = element
                }
                binding.btnRecordF2.isEnabled = true
                binding.btnRecordF3.isEnabled = true
                binding.btnRecordF4.isEnabled = true
                binding.btnRecordF2.alpha = 1F
                binding.btnRecordF3.alpha = 1F
                binding.btnRecordF4.alpha = 1F
                vehicleInformationAdapter.data = vehicleInformationList
            }
    }
    private fun getPostPhotoData(adapter: InspectionItemImageAdapter): List<InspectionPhotoW007Request> {
        val list = ArrayList<InspectionPhotoW007Request>()
        for (index in 0 until adapter.itemCount) {
            val holder = binding.rvImage.findViewHolderForAdapterPosition(index)
            val ivImage = holder?.itemView?.findViewById<ImageView>(R.id.ivImage)
            val drawable = ivImage?.drawable
            val ivTag = ivImage?.tag.toString()
            if (ivTag == "1" && ivTag != null) {
                val bitmap = getBitmapFromDrawable(drawable!!)
                val base64 = bitmap2Base64(bitmap)
                val model = InspectionPhotoW007Request(
                    index,
                    AjJyjghb,
                    HjJyjghb,
                    "1",
                    bean005.Hphm,
                    bean005.Hpzl,
                    bean005.Clsbdh,
                    base64,
                    date2String(Date(),"yyyyMMddHHmmss"),
                    "F1",
                    adapter.data[index].Zpdm,
                    adapter.data[index].Zpmc,
                    adapter.data[index].Zpajdm,
                    adapter.data[index].Zphjdm,
                    adapter.data[index].Bcaj,
                    adapter.data[index].BcHj,
                    bean005.Hjdlsj,
                    args.bean002.Ajlsh,
                    args.bean002.Hjlsh,
                    args.bean002.Ajjccs,
                    args.bean002.Hjjccs
                )
                list.add(model)
            }


        }

        return list

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
    private fun getImageData(
        Jyxm: String, Ajywlb: String, Hjywlb: String, Ajlsh: String,
        Hjlsh: String,
    ) {
        inspectionItemViewModel.getImageItemData(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh).observe(this) {
            inspectionItemImageAdapter.data = it
        }
    }
    override fun onResume() {
        super.onResume()
        getData(args.bean002.Hphm, args.bean002.Hpzl, "", "",args.bean002.Ajlsh,args.bean002.Hjlsh)
        getImageData(
                "F1",
            args.bean002.Ajywlb,
            args.bean002.Hjywlb,
            args.bean002.Ajlsh,
            args.bean002.Hjlsh
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
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
        if (requestCode == REQUEST_VIDEO_CAPTURE_F2 && resultCode == Activity.RESULT_OK){
            val file = File(videoPath)
            val mediaType = MediaType.parse("multipart/form-data")
            val requestBody = RequestBody.create(mediaType,file)
            inspectionItemViewModel.postUploadFile(file,requestBody).observe(this){ path ->
                if (path!=null&&path.isNotBlank()){
                    inspectionItemViewModel.getServerTime().observe(this){
                        inspectionItemViewModel.postSaveVideoW008(inspectionItemViewModel.getPostVideoData(
                            "1",args.bean002.Hphm,
                            args.bean002.Hpzl,VIN_FAR_TO_CLOSED,
                            VIN_FAR_TO_CLOSED, "",args.bean002.Ajywlb,args.bean002.Hjywlb,
                            it.Sj.substring(0,10),it.Sj.substring(11), date2String(Date(),"yyyyMMddHHmmss"),
                            string2String(it.Sj,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
                            "",bean005.Clpp1,bean005.Syr,
                            bean005.Hjdlsj,path,"1",
                            args.bean002.Ajlsh,args.bean002.Hjlsh,
                            args.bean002.Ajjccs,args.bean002.Hjjccs)
                        ).observe(this){
                            if (it){
                                Toast.makeText(MyApp.context, "车架号由远及近视频上传成功", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(MyApp.context, "车架号由远及近视频上传失败", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }else{
                    Toast.makeText(MyApp.context, "上传失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE_F3 && resultCode == Activity.RESULT_OK){
            val file = File(videoPath)
            val mediaType = MediaType.parse("multipart/form-data")
            val requestBody = RequestBody.create(mediaType,file)
            inspectionItemViewModel.postUploadFile(file,requestBody).observe(this){ path ->
                if (path!=null&&path.isNotBlank()){
                    inspectionItemViewModel.getServerTime().observe(this){
                        inspectionItemViewModel.postSaveVideoW008(inspectionItemViewModel.getPostVideoData(
                            "1",args.bean002.Hphm,
                            args.bean002.Hpzl,AROUND_VEHICLE,
                            AROUND_VEHICLE, "",args.bean002.Ajywlb,args.bean002.Hjywlb,
                            it.Sj.substring(0,10),it.Sj.substring(11), string2String(date2String(Date(),"yyyyMMddHHmmss"),
                                "yyyy-MM-dd HH:mm:ss",
                                "yyyyMMddHHmmss"),
                            string2String(it.Sj,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
                            "",bean005.Clpp1,bean005.Syr,
                            bean005.Hjdlsj,path,"1",
                            args.bean002.Ajlsh,args.bean002.Hjlsh,
                            args.bean002.Ajjccs,args.bean002.Hjjccs)
                        ).observe(this){
                            if (it){
                                Toast.makeText(MyApp.context, "环视车辆一周视频上传成功", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(MyApp.context, "环视车辆一周视频上传失败", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }else{
                    Toast.makeText(MyApp.context, "上传失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE_F4 && resultCode == Activity.RESULT_OK){
            val file = File(videoPath)
            val mediaType = MediaType.parse("multipart/form-data")
            val requestBody = RequestBody.create(mediaType,file)
            inspectionItemViewModel.postUploadFile(file,requestBody).observe(this){ path ->
                if (path!=null&&path.isNotBlank()){
                    inspectionItemViewModel.getServerTime().observe(this){
                        inspectionItemViewModel.postSaveVideoW008(inspectionItemViewModel.getPostVideoData(
                            "1",args.bean002.Hphm,
                            args.bean002.Hpzl,TIRE_TREAD_DEPTH,
                            TIRE_TREAD_DEPTH, "",args.bean002.Ajywlb,args.bean002.Hjywlb,
                            it.Sj.substring(0,10),it.Sj.substring(11), string2String(date2String(Date(),"yyyyMMddHHmmss"),
                                "yyyy-MM-dd HH:mm:ss",
                                "yyyyMMddHHmmss"),
                            string2String(it.Sj,"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"),
                            "",bean005.Clpp1,bean005.Syr,
                            bean005.Hjdlsj,path,"1",
                            args.bean002.Ajlsh,args.bean002.Hjlsh,
                            args.bean002.Ajjccs,args.bean002.Hjjccs)
                        ).observe(this){
                            if (it){
                                Toast.makeText(MyApp.context, "轮胎花纹深度视频上传成功", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(MyApp.context, "轮胎花纹深度视频上传失败", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }

                }else{
                    Toast.makeText(MyApp.context, "上传失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}