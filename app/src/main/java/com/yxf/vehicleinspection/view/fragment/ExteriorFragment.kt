package com.yxf.vehicleinspection.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.ui.gallery.DefaultGalleryMimes
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleImageResponse
import com.yxf.vehicleinspection.databinding.FragmentExteriorBinding
import com.yxf.vehicleinspection.databinding.VehicleImageItemBinding
import com.yxf.vehicleinspection.imagepicker.DefaultImagePicker
import com.yxf.vehicleinspection.repository.ImageItemRepository
import com.yxf.vehicleinspection.view.adapter.ExteriorAdapter
import com.yxf.vehicleinspection.view.adapter.ImageItemRvAdapter
import com.yxf.vehicleinspection.viewModel.ExteriorViewModel
import com.yxf.vehicleinspection.viewModel.ExteriorViewModelFactory
import io.reactivex.functions.Consumer
import java.text.FieldPosition

class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    lateinit var imageItemRvAdapter: ImageItemRvAdapter
    lateinit var exteriorViewModel: ExteriorViewModel
    lateinit var adapter: ExteriorAdapter
    val args : ExteriorFragmentArgs by navArgs()
    val REQUEST_IMAGE_CAPTURE = 101
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        exteriorViewModel = ViewModelProvider(this,ExteriorViewModelFactory(ImageItemRepository())).get(ExteriorViewModel::class.java)
        binding.includeTitle.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_exteriorFragment_to_signatureFragment)
        }
        adapter = ExteriorAdapter(this.requireContext(),R.layout.vehicle_image_item)
        binding.lvTitle.adapter = adapter
        binding.lvTitle.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                        intent -> intent.resolveActivity(requireActivity().packageManager).also {
                            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
                    }
                    }

            }
        }


    }


    fun getImageData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String){
        exteriorViewModel.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb).observe(this){
            adapter.data = it
        }
    }
    override fun onResume() {
        getImageData(args.bean.Lsh,args.jyxm,
            args.bean.Ajywlb,args.bean.Hjywlb)
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
            if (data != null) {
                val imageView = view?.findViewById<ImageView>(R.id.ivImage)
                imageView?.setImageBitmap(data.extras?.get("data") as Bitmap)
            }

    }

}