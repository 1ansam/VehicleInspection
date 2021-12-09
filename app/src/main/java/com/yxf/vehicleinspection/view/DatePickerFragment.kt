package com.yxf.vehicleinspection.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.view.fragment.RegisterFragment
import java.util.*

/**
*   author:yxf
*   time:2021/12/6
*/
class DatePickerFragment : DialogFragment(),DatePickerDialog.OnDateSetListener{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        return DatePickerDialog(this.requireContext(),this,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH))
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        when(this.tag){
            "ccrq" -> {
                val ccrq = parentFragment?.view?.findViewById<TextView>(R.id.tvCcrq)
                ccrq?.text = "$year-${month+1}-$dayOfMonth"
            }
            "djrq" ->{
                val djrq = parentFragment?.view?.findViewById<TextView>(R.id.tvDjrq)
                djrq?.text = "$year-${month+1}-$dayOfMonth"
            }
            "ccdjrq" ->{
                val ccdjrq = parentFragment?.view?.findViewById<TextView>(R.id.tvCcdjrq)
                ccdjrq?.text = "$year-${month+1}-$dayOfMonth"
            }

        }

    }


}