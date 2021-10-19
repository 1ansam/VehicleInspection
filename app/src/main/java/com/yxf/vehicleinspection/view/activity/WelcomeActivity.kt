package com.yxf.vehicleinspection.view.activity

import android.Manifest
import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.permissionx.guolindev.PermissionX
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker_extension.MimeType
import com.qingmei2.rximagepicker_extension_zhihu.ZhihuConfigurationBuilder
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityWelcomeBinding
import com.yxf.vehicleinspection.imagepicker.DefaultImagePicker
import com.yxf.vehicleinspection.imagepicker.ZhihuImagePicker
import io.reactivex.functions.Consumer

class WelcomeActivity : BaseBindingActivity<ActivityWelcomeBinding>() {

    override fun init() {
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "已获取所需权限", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        "未获取到: $deniedList",
                        Toast.LENGTH_LONG).show()
                }
            }
        binding.btnStartEnjoy.setOnClickListener {

            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
//            RxImagePicker
//                .create(DefaultImagePicker::class.java)
//                .openGallery(this)
//                .subscribe(Consumer {
//                    Glide.with(this)
//                        .load(it.uri)
//                        .into(binding.corporationLogo)
//                })

//            RxImagePicker
//                .create(DefaultImagePicker::class.java)
//                .openCamera(this)
//                .subscribe(Consumer {
//                    Glide.with(this)
//                        .load(it.uri)
//                        .into(binding.corporationLogo)
//                })
//            RxImagePicker
//                .create(ZhihuImagePicker::class.java)
//                .openGalleryAsDracula(this,
//                    ZhihuConfigurationBuilder(MimeType.ofImage(),false)
//                        .maxSelectable(9)
//                        .countable(true)
//                        .spanCount(4)
//                        .theme(R.style.Zhihu_Dracula)
//                        .build())
//                .subscribe {
//                    Glide.with(this)
//                        .load(it.uri)
//                        .into(binding.corporationLogo)
//                }


        }

    }
}