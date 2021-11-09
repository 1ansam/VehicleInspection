package com.yxf.vehicleinspection.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemResponse
import com.yxf.vehicleinspection.databinding.InspectionInfoItemBinding
import com.yxf.vehicleinspection.databinding.RvItemInspectionItemBinding

/**
 * @author : yxf
 * @Date : 2021/11/4
 **/
abstract class TempAdapter<T : Any, V : ViewBinding>() : RecyclerView.Adapter<TempViewHolder<V>>(){
    open var data : T? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }


}
class TempViewHolder<V : ViewBinding>(val binding : V) : RecyclerView.ViewHolder(binding.root)