package com.yxf.vehicleinspection.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.databinding.VehicleInfoItemBinding

/**
 *   author:yxf
 *   time:2021/10/15
 */
class VehicleQueueRvAdapter() : BaseRvAdapter<VehicleQueueResponse,PersonInspectionItemBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<PersonInspectionItemBinding> {
        return BaseRvViewHolder(PersonInspectionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<PersonInspectionItemBinding>,
        position: Int,
        binding: PersonInspectionItemBinding,
        bean: VehicleQueueResponse,
    ) {
//        val model = data[position]
        holder.apply {
            binding.tvHphm.text = bean.Hphm
            binding.tvHpzl.text = bean.HpzlCc
            binding.tvAjywlb.text = "安检：${bean.AjywlbCc}"
            binding.tvHjjwlb.text = "环保：${bean.HjywlbCc}"
            binding.tvLsh.text = bean.Lsh
            binding.tvTime.text = bean.Djrq?.substring(0,16)
            binding.tvJyzt.text = bean.Jyzt
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("key", bean)
            it.findNavController().navigate(R.id.inspectionItemFragment,bundle)
        }
    }
}
