package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.bean.response.DataDictionaryR003Response

/**
 *   author:yxf
 *   time:2021/11/10
 */
class LineNumberAdapter(context: Context, private val resourceId : Int, dataListResponse : List<DataDictionaryR003Response>) : ArrayAdapter<DataDictionaryR003Response>(context,resourceId,dataListResponse) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
        val tvLineNumberDm = view.findViewById<TextView>(R.id.tvLineNumberDm)
        val tvLineNumberMc = view.findViewById<TextView>(R.id.tvLineNumberMc)
        val data = getItem(position)
        if (data != null){
            tvLineNumberDm.text = data.Dm
            tvLineNumberMc.text = data.Mc
        }
        return view
    }
}