package com.yxf.vehicleinspection.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 *   author:yxf
 *   time:2021/9/28
 */
abstract class BaseRvAdapter<E : Any, V : ViewBinding> : RecyclerView.Adapter<BaseRvViewHolder<V>>() {
    //设置data
    open var data : List<E> = ArrayList()
        set(value) {
        field = value
        notifyDataSetChanged()
    }
    //设置itemClickListener
    open var onItemViewClickListener : OnItemViewClickListener<E>? = null
    set(value)  {
        field = value
    }
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder<V>
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder : BaseRvViewHolder<V>,position : Int){
        onBindViewHolder(holder, holder.bindingAdapterPosition,holder.binding,data[holder.bindingAdapterPosition])
    }
    abstract fun onBindViewHolder(holder : BaseRvViewHolder<V>, position : Int,binding: V, bean : E)


    interface OnItemViewClickListener<E>{
        fun onItemClick(view : View,position: Int, bean : E)
    }
}
open class BaseRvViewHolder<V : ViewBinding>(val binding : V) : RecyclerView.ViewHolder(binding.root)
