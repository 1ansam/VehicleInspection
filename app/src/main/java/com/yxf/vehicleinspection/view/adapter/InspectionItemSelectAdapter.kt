package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ArtificialProject
import com.yxf.vehicleinspection.databinding.ItemExteriorSelectBinding
import com.yxf.vehicleinspection.view.JyyqPickerFragment

/**
 *   author:yxf
 *   time:2021/11/9
 */
class InspectionItemSelectAdapter(val fragment : Fragment) : BaseRvAdapter<ArtificialProject,ItemExteriorSelectBinding>(){

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
        binding.tvXmms.text = bean.Xmms
        binding.tvXmdm.text = bean.Xmdm
        binding.ivJyyq.setOnClickListener {
            JyyqPickerFragment(bean.Jyyq).show(fragment.childFragmentManager,bean.Xmdm)
        }
        var selected : Boolean? = null
        binding.ivSelected.tag = "0"
        if (bean.Sycx == "1"){
            binding.ivSelected.setImageResource(R.drawable.icon_yes)
            binding.ivSelected.tag = "1"
            selected = true
        }

        binding.ivSelected.setOnClickListener {
            if (selected != null){
                selected = !selected!!
                if (selected!!){
                    binding.ivSelected.setImageResource(R.drawable.icon_yes)
                    binding.ivSelected.tag = "1"
                    binding.etBz.visibility = View.GONE
                }else{
                    binding.ivSelected.setImageResource(R.drawable.ic_baseline_clear_24)
                    binding.ivSelected.tag = "2"
                    binding.etBz.visibility = View.VISIBLE
                }
            }

        }
        binding.ivSelected.setOnLongClickListener {
            if (selected != null) {
                selected = null
                binding.ivSelected.setImageResource(R.drawable.icon_block)
                binding.ivSelected.tag = "0"
                binding.etBz.visibility = View.GONE
            } else {
                selected = true
                binding.ivSelected.setImageResource(R.drawable.icon_yes)
                binding.ivSelected.tag = "1"
                binding.etBz.visibility = View.GONE
            }


            true
        }


    }
}