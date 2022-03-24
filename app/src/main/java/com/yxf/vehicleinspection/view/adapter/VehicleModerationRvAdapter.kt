package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ModerationQueueR013Response
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.utils.FL_AJYWLB
import com.yxf.vehicleinspection.utils.FL_HJYWLB
import com.yxf.vehicleinspection.utils.FL_HPZL
import com.yxf.vehicleinspection.view.fragment.ModerationQueueFragmentDirections
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel


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


        binding.apply {
            tvHphm.text = bean.Hphm
            tvAjlsh.text = root.resources.getString(R.string.ajlsh_,bean.Ajlsh)
            tvHjlsh.text = root.resources.getString(R.string.hjlsh_,bean.Hjlsh)
            tvTime.text = bean.Ajjcsj
        }
        dataDictionaryViewModel.getMc(FL_HPZL,bean.Hpzl).observe(fragment){
            binding.tvHpzl.text = it
        }
        dataDictionaryViewModel.getMc(FL_AJYWLB,bean.Hjywlb).observe(fragment){
            binding.tvAjywlb.text = binding.root.resources.getString(R.string.ajywlb_,it)
        }
        dataDictionaryViewModel.getMc(FL_HJYWLB,bean.Hjywlb).observe(fragment){
            binding.tvHjywlb.text = binding.root.resources.getString(R.string.hjywlb_,it)
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

