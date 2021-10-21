package com.yxf.vehicleinspection.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.databinding.VehicleInfoItemBinding
import com.yxf.vehicleinspection.view.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView




/**
 *   author:yxf
 *   time:2021/10/15
 */
class VehicleQueueRvAdapter(val hostName : String) : BaseRvAdapter<VehicleQueueResponse,PersonInspectionItemBinding>() {
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
        val bundle = Bundle()
        holder.apply {
            binding.tvHphm.text = bean.Hphm
            binding.tvHpzl.text = bean.HpzlCc
            binding.tvAjywlb.text = "安检：${bean.AjywlbCc}"
            binding.tvHjjwlb.text = "环保：${bean.HjywlbCc}"
            binding.tvLsh.text = bean.Lsh
            binding.tvTime.text = bean.Djrq?.substring(0,16)
            binding.tvJyzt.text = bean.Jyzt
        }
        if(bean.Jyzt.equals("过程结束")){
            if (hostName == NavHostFragment.HOSTNAME_VERIFY_SIGNATURE){

            }
        }





        if (binding.tvJyzt.text.equals("过程结束")){
            bundle.putSerializable("key", bean)
            holder.itemView.setOnClickListener {
                it.findNavController().navigate(R.id.vehicleImageVideoFragment,bundle)
            }
        }else{
            holder.itemView.setOnClickListener {
                bundle.putSerializable("key", bean)

                it.findNavController().navigate(R.id.inspectionItemFragment,bundle)
            }
        }

    }
}
