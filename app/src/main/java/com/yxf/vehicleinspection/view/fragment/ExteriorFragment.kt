package com.yxf.vehicleinspection.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Debug
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qingmei2.rximagepicker.core.ImagePickerController
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.bean.request.InspectionPhotoW007Request
import com.yxf.vehicleinspection.databinding.FragmentExteriorBinding
import com.yxf.vehicleinspection.imagepicker.DefaultImagePicker
import com.yxf.vehicleinspection.repository.ExteriorRepository
import com.yxf.vehicleinspection.utils.ImageUtil
import com.yxf.vehicleinspection.view.adapter.ExteriorImageAdapter
import com.yxf.vehicleinspection.view.adapter.ImageItemRvAdapter
import com.yxf.vehicleinspection.viewModel.ExteriorViewModel
import com.yxf.vehicleinspection.viewModel.ExteriorViewModelFactory
import io.reactivex.functions.Consumer
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    lateinit var currentPhotoPath: String
    lateinit var exteriorViewModel: ExteriorViewModel
    lateinit var exteriorImageAdapter: ExteriorImageAdapter
    private val args: ExteriorFragmentArgs by navArgs()
    private val REQUEST_IMAGE_CAPTURE = 101
    private var holder: RecyclerView.ViewHolder? = null
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        exteriorViewModel =
            ViewModelProvider(this, ExteriorViewModelFactory(ExteriorRepository())).get(
                ExteriorViewModel::class.java
            )

        exteriorImageAdapter = ExteriorImageAdapter()
        exteriorImageAdapter.onItemViewClickListener =
            object : BaseRvAdapter.OnItemViewClickListener {
                override fun onItemClick(view: View, position: Int) {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        // Ensure that there's a camera activity to handle the intent
                        takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                            // Create the File where the photo should go
                            val photoFile: File? = try {
                                createImageFile()
                            } catch (ex: IOException) {
                                null
                            }
                            // Continue only if the File was successfully created
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
                }
            }
        binding.rvImage.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvImage.adapter = exteriorImageAdapter
        binding.includeTitle.btnSubmit.setOnClickListener {

            exteriorViewModel.postInspectionPhotoW007(getPostData(exteriorImageAdapter))
                .observe(this) {
                    if (it) {
                        findNavController().navigate(R.id.action_exteriorFragment_to_signatureFragment)
                    } else {
//                        Toast.makeText(MyApp.context, "上传失败", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }


    private fun getImageData(Lsh: String, Jyxm: String, Ajywlb: String, Hjywlb: String) {
        exteriorViewModel.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb).observe(this) {
            exteriorImageAdapter.data = it
        }
    }

    fun getPostData(adapter: ExteriorImageAdapter): List<InspectionPhotoW007Request> {
        val list = ArrayList<InspectionPhotoW007Request>()
        for (index in 0 until adapter.itemCount) {
            val holder = binding.rvImage.findViewHolderForAdapterPosition(index)
            val tvZpajdm = holder?.itemView?.findViewById<TextView>(R.id.tvZpajdm)
            val tvZphjdm = holder?.itemView?.findViewById<TextView>(R.id.tvZphjdm)
            val imageView = holder?.itemView?.findViewById<ImageView>(R.id.ivImage)
            val drawable = imageView?.drawable
            if (drawable != null) {
                val bitmap = ImageUtil.getBitmapFromDrawable(drawable)
                val base64 = ImageUtil.bitmap2Base64(bitmap!!)
                val model = InspectionPhotoW007Request(
                    index, args.bean006.Lsh,
                    "",
                    "",
                    "1",
                    args.bean006.Jccs,
                    args.bean005.Hphm,
                    args.bean005.Hpzl,
                    args.bean005.Clsbdh,
                    base64,
                    "202111081113",
                    args.bean006.Xmbh,
                    adapter.data[index].Zpdm,
                    adapter.data[index].Zpmc,
                    adapter.data[index].Zpajdm,
                    adapter.data[index].Zphjdm,
                    adapter.data[index].Bcaj,
                    adapter.data[index].BcHj,
                    "202111081123"
                )
                list.add(model)
            }


        }
        return list

    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
        getImageData(
            args.bean006.Lsh, args.bean006.Xmbh,
            args.bean006.Ajywlb, args.bean006.Hjywlb
        )
        super.onResume()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageView = holder?.itemView?.findViewById<ImageView>(R.id.ivImage)!!
            val targetW: Int = imageView.width
            val targetH: Int = imageView.height

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true

                val photoW: Int = outWidth
                val photoH: Int = outHeight

                // Determine how much to scale down the image
                val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)

                // Decode the image file into a Bitmap sized to fill the View
                inJustDecodeBounds = false
                inSampleSize = scaleFactor
                inPurgeable = true
            }
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                imageView.setImageBitmap(bitmap)
            }

        }



    }


}