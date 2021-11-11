package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ArtificialProject
import com.yxf.vehicleinspection.databinding.ItemExteriorSelectBinding

/**
 *   author:yxf
 *   time:2021/11/9
 */
class InspectionItemSelectAdapter : BaseRvAdapter<ArtificialProject,ItemExteriorSelectBinding>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemExteriorSelectBinding> {
        val binding = ItemExteriorSelectBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseRvViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemExteriorSelectBinding>,
        position: Int,
        binding: ItemExteriorSelectBinding,
        bean: ArtificialProject,
    ) {
        holder.binding.tvXmms.text = bean.Xmms
        holder.binding.tvXmdm.text = bean.Xmdm
        var selected : Boolean? = null
        if (bean.Sycx == "1"){
            holder.binding.ibSelected.setImageResource(R.drawable.icon_yes)
            selected = true
        }

        holder.binding.ibSelected.setOnClickListener {
            if (selected != null){
                selected = !selected!!
                if (selected!!){
                    holder.binding.ibSelected.setImageResource(R.drawable.icon_yes)
                    holder.binding.etBz.visibility = View.GONE
                }else{
                    holder.binding.ibSelected.setImageResource(R.drawable.ic_baseline_clear_24)
                    holder.binding.etBz.visibility = View.VISIBLE
                }
            }

        }
        holder.binding.ibSelected.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                if (selected!=null){
                    selected = null
                    holder.binding.ibSelected.setImageResource(R.drawable.icon_block)
                    holder.binding.etBz.visibility = View.GONE
                }else{
                    selected = true
                    holder.binding.ibSelected.setImageResource(R.drawable.icon_yes)
                    holder.binding.etBz.visibility = View.GONE
                }


                return true
            }
        })


    }
}