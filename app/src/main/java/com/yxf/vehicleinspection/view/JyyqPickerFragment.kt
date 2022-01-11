package com.yxf.vehicleinspection.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.yxf.vehicleinspection.R

/**
 *   author:yxf
 *   time:2022/1/11
 */
class JyyqPickerFragment(val text : String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_jyyq_picker,container,false)
        return view
    }

    override fun onResume() {
        super.onResume()
        val tvJyyq = view?.findViewById<TextView>(R.id.tvJyyq)
        tvJyyq?.text = text
    }

}