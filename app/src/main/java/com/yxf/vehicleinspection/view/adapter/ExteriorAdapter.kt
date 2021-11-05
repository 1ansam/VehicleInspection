package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.bean.response.ImageItemResponse

/**
 *   author:yxf
 *   time:2021/11/5
 */
class ExteriorAdapter(context: Context, val resource : Int) : ArrayAdapter<ImageItemResponse>(context,resource) {
    var data : List<ImageItemResponse> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resource,parent,false)
        val ivImage = view.findViewById<ImageView>(R.id.ivImage)
        val tvTitle = view.findViewById<TextView>(R.id.tvZpmc)

            tvTitle.text = data[position].Zpmc

        return view
    }

    override fun getCount(): Int {
        return data.size
    }


}