package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.InspectionItem
import com.yxf.vehicleinspection.databinding.RvItemInspectionItemBinding

/**
 *   author:yxf
 *   time:2021/10/19
 */
class InspectionItemAdapter : BaseRvAdapter<InspectionItem,RvItemInspectionItemBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<RvItemInspectionItemBinding> {
        return BaseRvViewHolder(RvItemInspectionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<RvItemInspectionItemBinding>,
        position: Int,
        binding: RvItemInspectionItemBinding,
        bean: InspectionItem,
    ) {
        holder.apply {
            binding.tvJyxm.text = bean.Jyxm
            binding.tvJyzt.text = bean.Jyzt
        }
        if (binding.tvJyzt.text.equals("完成"))
            holder.itemView.isEnabled = false
        holder.itemView.setOnClickListener {
            when(binding.tvJyxm.text){
                "外观" -> it.findNavController().navigate(R.id.exteriorFragment)
                "底盘" -> it.findNavController().navigate(R.id.chassisFragment)
                "动态" -> it.findNavController().navigate(R.id.dynamicFragment)
                "联网" -> it.findNavController().navigate(R.id.networkQueryFragment)
                "唯一性" -> it.findNavController().navigate(R.id.uniqueFragment)
            }
        }

    }
}