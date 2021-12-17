package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ModerationQueueR013Response
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.utils.FL_AJYWLB
import com.yxf.vehicleinspection.utils.FL_HJYWLB
import com.yxf.vehicleinspection.utils.FL_HPZL
import com.yxf.vehicleinspection.view.fragment.ModerationQueueFragmentDirections
import com.yxf.vehicleinspection.view.fragment.NavHostFragment
import com.yxf.vehicleinspection.view.fragment.VehicleQueueFragmentDirections
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel
import com.yxf.vehicleinspection.viewModel.SharedViewModel


/**
 *   author:yxf
 *   time:2021/10/15
 */
class VehicleModerationRvAdapter(private val fragment : Fragment , private val dataDictionaryViewModel: DataDictionaryViewModel) : BaseRvAdapter<ModerationQueueR013Response,PersonInspectionItemBinding>() {

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
        bean: ModerationQueueR013Response,
    ) {


        holder.apply {
            binding.tvHphm.text = bean.Hphm
            binding.tvAjlsh.text = "安检流水号：${bean.Ajlsh}"
            binding.tvHjlsh.text = "环检流水号：${bean.Hjlsh}"
            binding.tvTime.text = bean.Ajjcsj
        }
        dataDictionaryViewModel.getMc(FL_HPZL,bean.Hpzl).observe(fragment){
            binding.tvHpzl.text = it
        }
        dataDictionaryViewModel.getMc(FL_AJYWLB,bean.Hjywlb).observe(fragment){
            binding.tvAjywlb.text = "安检：${it}"
        }
        dataDictionaryViewModel.getMc(FL_HJYWLB,bean.Hjywlb).observe(fragment){
            binding.tvHjywlb.text = "环检：${it}"
        }
            holder.itemView.setOnClickListener {
                val action =
                    ModerationQueueFragmentDirections.actionModerationQueueFragmentToVehicleImageVideoFragment(
                        bean
                    )
                it.findNavController().navigate(action)
            }
    }




}

