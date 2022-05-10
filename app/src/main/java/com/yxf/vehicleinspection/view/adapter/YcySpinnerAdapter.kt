package com.yxf.vehicleinspection.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response

/**
 *   author:yxf
 *   time:2021/11/25
 */
class YcySpinnerAdapter(activity : Activity, private val resourceId : Int, val data : List<UserInfoR001Response>)
    : ArrayAdapter<UserInfoR001Response>(activity,resourceId,data){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(resourceId,parent,false)
        val ycyName = view.findViewById<TextView>(R.id.tvYcyName)
        data.forEach {
            ycyName.text = data[position].TrueName
        }
//        for (element in data){
//            ycyName.text = data[position].TrueName
//        }
        return view
    }
}