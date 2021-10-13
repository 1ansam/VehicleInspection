package com.yxf.vehicleinspection.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 *   author:yxf
 *   time:2021/9/28
 */
abstract class BaseRvAdapter<E : Any, V : ViewBinding> : RecyclerView.Adapter<BaseRvViewHolder<V>>() {
    open var data : MutableList<E> = mutableListOf()
    set(value) {
        field = value
        notifyItemRangeChanged(0,value.size)
    }
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder<V>
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder : BaseRvViewHolder<V>,position : Int){
        onBindViewHolder(holder, holder.adapterPosition,holder.binding,data[holder.adapterPosition])
    }
    abstract fun onBindViewHolder(holder : BaseRvViewHolder<V>, position : Int,binding: V, bean : E)

}
open class BaseRvViewHolder<V : ViewBinding>(val binding : V) : RecyclerView.ViewHolder(binding.root)