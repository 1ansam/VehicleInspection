package com.yxf.vehicleinspection.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.utils.getScreenHeight
import com.yxf.vehicleinspection.utils.getScreenWidth

/**
 *   author:yxf
 *   time:2021/12/15
 */
class SignaturePicker : DialogFragment(){
    lateinit var mPaintView: PaintView
    lateinit var btnClose : Button
    lateinit var btnClear : Button
    lateinit var btnCommit : Button
    lateinit var tablet : FrameLayout
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signature_picker,container,false)
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val layoutParams = window?.attributes
        layoutParams?.height = (25 * getScreenHeight(this.requireActivity()) / 32)
        layoutParams?.width = (8 * getScreenWidth(this.requireActivity()) / 9)
        window?.setLayout(layoutParams?.width!!, layoutParams?.height!!)
        mPaintView = PaintView(this.requireContext(), layoutParams!!.width,
            layoutParams.height,)

    }

    override fun onResume() {
        super.onResume()
        view?.let {
            btnClose = it.findViewById(R.id.btnBack)
            btnClear = it.findViewById(R.id.btnClear)
            btnCommit = it.findViewById(R.id.btnCommit)
            tablet = it.findViewById(R.id.tablet)
        }
//        mPaintView = PaintView(this.requireContext(), getScreenWidth(requireActivity()),
//            getScreenHeight(this.requireActivity())
//        )
        tablet.addView(mPaintView)
        mPaintView.requestFocus()
        btnClose.setOnClickListener {
            dismiss()
        }
        btnClear.setOnClickListener {
            mPaintView.clear()
        }
        btnCommit.setOnClickListener {
            when(this.tag){
                "czqm" ->{
                    val czqm = parentFragment?.view?.findViewById<ImageView>(R.id.ivCzqm)
                    czqm?.setImageBitmap(mPaintView.paintBitmap)
                    czqm?.tag = "1"
                    dismiss()
                }
                "dlyqm" ->{
                    val dlyqm = parentFragment?.view?.findViewById<ImageView>(R.id.ivDlyqm)
                    dlyqm?.setImageBitmap(mPaintView.paintBitmap)
                    dlyqm?.tag = "1"
                    dismiss()
                }
            }

        }
    }
}