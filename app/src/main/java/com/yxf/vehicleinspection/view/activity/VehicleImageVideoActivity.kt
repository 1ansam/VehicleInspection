package com.yxf.vehicleinspection.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.ActivityVehicleImageVideoBinding
import com.yxf.vehicleinspection.repository.VehicleImageRepository
import com.yxf.vehicleinspection.repository.VehicleVideoRepository
import com.yxf.vehicleinspection.view.adapter.VehicleImageRvAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleVideoRvAdapter
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModel
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModelFactory

class VehicleImageVideoActivity : BaseBindingActivity<ActivityVehicleImageVideoBinding>() {
    lateinit var adapterLeft : VehicleImageRvAdapter
    lateinit var adapterRight : VehicleVideoRvAdapter
    lateinit var viewModel : VehicleImageVideoViewModel
    override fun init() {
//        Debug.waitForDebugger();
        val bundle = intent.getBundleExtra("bundle")
        val model : VehicleQueueResponse = bundle?.getSerializable("key") as VehicleQueueResponse
        val Lsh = model.Lsh
        val Jccs = "0"
        viewModel = ViewModelProvider(
            this,
            VehicleImageVideoViewModelFactory(
                VehicleImageRepository(),
                VehicleVideoRepository()
            )
        ).get(VehicleImageVideoViewModel::class.java)
        adapterLeft = VehicleImageRvAdapter(this,null)
        adapterRight = VehicleVideoRvAdapter(this, null)
        getImageData(Lsh,Jccs)
        binding.btnPass.setOnClickListener {
            intent = Intent(this,SignatureActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnExit.setOnClickListener {
            finish()
        }
        binding.btnReject.setOnClickListener {
            intent = Intent(this,SignatureActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun getImageData(Lsh : String , Jccs : String) {
        viewModel.getImageData(Lsh).observe(this, Observer {
            adapterLeft.setModel(it)
        })
        viewModel.getVideoData(Lsh, Jccs).observe(this, Observer {
//            adapterRight
        })
    }

}