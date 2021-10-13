package com.yxf.vehicleinspection.view.activity

import android.Manifest
import android.content.Intent
import android.widget.Toast
import com.permissionx.guolindev.PermissionX
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityWelcomeBinding

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
        }
    }
}