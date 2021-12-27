package com.yxf.vehicleinspection.view

import android.app.Dialog
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.DialogFragment
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseUrlHelper
import com.yxf.vehicleinspection.bean.response.VehicleVideoR008Response

/**
 *   author:yxf
 *   time:2021/12/27
 */
class VideoPickerFragment(val bean : VehicleVideoR008Response) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picker_video, container, false)
    }

    override fun onResume() {
        super.onResume()
        val videoView = view?.findViewById<VideoView>(R.id.vvVerify)
        videoView?.apply {
            setMediaController(MediaController(this.context))
            setVideoPath("${BaseUrlHelper.instance.httpUrl}${bean.Lxdz}")
            start()
            setOnCompletionListener{
                Toast.makeText(context, "播放结束", Toast.LENGTH_SHORT).show()
            }
            setOnErrorListener { mp, what, extra ->
                Toast.makeText(context, "播放出现错误", Toast.LENGTH_SHORT).show()
                false
            }
        }

    }
}