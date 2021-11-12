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
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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
import com.yxf.vehicleinspection.databinding.FragmentExteriorBinding
import com.yxf.vehicleinspection.singleton.StaticParams
import com.yxf.vehicleinspection.utils.DateUtil
import com.yxf.vehicleinspection.utils.ImageUtil
import com.yxf.vehicleinspection.view.adapter.InspectionItemImageAdapter
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    private var AjJyjghb = ""
    private var HjJyjghb = ""
    private var AjJkxlh = ""
    private var endTime = ""
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
    private val args: ExteriorFragmentArgs by navArgs()
    private val REQUEST_IMAGE_CAPTURE = 101
    private var holder: RecyclerView.ViewHolder? = null
    private var beginTime = ""
    override fun init() {
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
                inspectionItemViewModel.postSaveVideoW008(SaveVideoW008Request(0,args.bean005.Lsh,args.jcxh,args.bean006.Jccs,args.bean005.Hphm,
                    args.bean005.Hpzl,args.bean006.Jcxm,StaticParams.EXTERIOR_FRONT,StaticParams.EXTERIOR_LEFT_FRONT_HJ,args.bean006.Ajywlb,args.bean006.Hjywlb,
                    endTime.substring(0,10),endTime.substring(11), DateUtil.string2String(beginTime,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyyMMddHHmmss"), DateUtil.string2String(endTime,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyyMMddHHmmss"),"",args.bean005.Clpp1,args.bean005.Syr,
                    "0".takeIf { args.bean006.Ajywlb == "-" }?: "1",
                    "0".takeIf { args.bean006.Hjywlb == "-" }?: "1",
                    args.bean005.Hjdlsj
                )).observe(this){
                    if(it){
                        apiNumber+=1
                    }else{
                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT).show()
                        binding.pbExteriorSubmit.visibility = View.GONE
                    }
                }
                inspectionItemViewModel.postSaveVideoW008(SaveVideoW008Request(0,args.bean005.Lsh,args.jcxh,args.bean006.Jccs,args.bean005.Hphm,
                    args.bean005.Hpzl,args.bean006.Jcxm,StaticParams.EXTERIOR_BEHIDE,StaticParams.EXTERIOR_RIGHT_BEHIND_HJ,args.bean006.Ajywlb,args.bean006.Hjywlb,
                    endTime.substring(0,10),endTime.substring(11), DateUtil.string2String(beginTime,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyyMMddHHmmss"), DateUtil.string2String(endTime,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyyMMddHHmmss"),"",args.bean005.Clpp1,args.bean005.Syr,
                    "0".takeIf { args.bean006.Ajywlb == "-" }?: "1",
                    "0".takeIf { args.bean006.Hjywlb == "-" }?: "1",
                    args.bean005.Hjdlsj
                )).observe(this){
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
                    inspectionItemViewModel.postProjectEndW012(ProjectEndW012Request(args.bean005.Lsh,AjJyjghb,args.jcxh,args.bean006.Jccs,
                    args.bean005.Hphm,args.bean005.Hpzl,args.bean005.Clsbdh,args.bean006.Jcxm,args.bean006.Jcxm,endTime,args.bean006.Ajywlb,
                    args.bean006.Hjywlb,AjJkxlh)).observe(this){
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



    private fun getPostPhotoData(adapter: InspectionItemImageAdapter): List<InspectionPhotoW007Request> {
        val list = ArrayList<InspectionPhotoW007Request>()
        for (index in 0 until adapter.itemCount) {
            val holder = binding.rvImage.findViewHolderForAdapterPosition(index)
            val ivImage = holder?.itemView?.findViewById<ImageView>(R.id.ivImage)
            val drawable = ivImage?.drawable
            val ivTag = ivImage?.tag.toString()
            if (ivTag == "1"&& ivTag != null){
                val bitmap = ImageUtil.getBitmapFromDrawable(drawable!!)
                val base64 = ImageUtil.bitmap2Base64(bitmap)
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
                    DateUtil.string2String(beginTime,
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
            args.bean006.Jcxm,args.bean006.Ajywlb,args.bean006.Hjywlb,AjJkxlh,listXmlb,"","","","","","",
        "","","","","","","","","","","","","","",
            "","",""
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm,exteriorArtificialProjectRequest))
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