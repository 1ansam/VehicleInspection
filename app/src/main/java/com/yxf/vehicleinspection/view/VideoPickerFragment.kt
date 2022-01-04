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
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseUrlHelper
import com.yxf.vehicleinspection.bean.response.VehicleVideoR008Response

/**
 *   author:yxf
 *   time:2021/12/27
 */
class VideoPickerFragment(val bean : VehicleVideoR008Response) : DialogFragment() {
    lateinit var player : ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picker_video, container, false)
    }

    override fun onResume() {
        super.onResume()
        player = ExoPlayer.Builder(this.requireContext()).build()
        val videoView = view?.findViewById<StyledPlayerView>(R.id.vvVerify)
        videoView?.player = player
        val mediaItem = MediaItem.fromUri("${BaseUrlHelper.instance.httpUrl}${bean.Lxdz}")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}