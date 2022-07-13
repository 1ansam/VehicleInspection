package com.yxf.vehicleinspection.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 *   author:yxf
 *   time:2022/7/1
 */
abstract class BaseRvMapAdapter<K : Any, V : Any?, VB : ViewBinding> : RecyclerView.Adapter<BaseRvMapViewHolder<VB>>()  {
    val list = ArrayList<BaseBean<K,V>>()
    //设置data
    open var data : Map<K, V> = TreeMap()
        set(value) {
            field = value
            data.entries.forEach {
                list.add(BaseBean(it.key,it.value))
            }
            notifyDataSetChanged()

        }


    //设置itemClickListener
    open var onItemViewClickListener : OnItemViewClickListener? = null
        set(value)  {
            field = value
        }
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvMapViewHolder<VB>
    override fun getItemCount(): Int {
        return data.keys.size
    }
    override fun onBindViewHolder(holder : BaseRvMapViewHolder<VB>,position : Int){
        onBindViewHolder(holder, holder.bindingAdapterPosition,holder.binding,list[holder.bindingAdapterPosition])
    }
    abstract fun onBindViewHolder(holder : BaseRvMapViewHolder<VB>, position : Int,binding: VB, bean : BaseBean<K,V>)


    interface OnItemViewClickListener{
        fun onItemClick(view : View, position: Int)
    }
}
open class BaseRvMapViewHolder<VB : ViewBinding>(val binding : VB) : RecyclerView.ViewHolder(binding.root)
data class BaseBean<K,V>(val key : K,val value : V?){

}