package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.view.fragment.NavHostFragment
import com.yxf.vehicleinspection.view.fragment.VehicleQueueFragmentDirections
import com.yxf.vehicleinspection.viewModel.SharedViewModel


/**
 *   author:yxf
 *   time:2021/10/15
 */
class VehicleQueueRvAdapter(private val owner: LifecycleOwner, private val sharedViewModel: SharedViewModel) : BaseRvAdapter<VehicleQueueR002Response,PersonInspectionItemBinding>() {

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
        bean: VehicleQueueR002Response,
    ) {


        holder.apply {
            binding.tvHphm.text = bean.Hphm
            binding.tvHpzl.text = bean.HpzlCc
            binding.tvAjywlb.text = "安检：${bean.AjywlbCc}"
            binding.tvHjjwlb.text = "环保：${bean.HjywlbCc}"
            binding.tvLsh.text = bean.Lsh
            binding.tvTime.text = bean.Djrq
            binding.tvJyzt.text = "${bean.Ywlb}  ${bean.Jyzt}"
        }
        sharedViewModel.hostName.observe(owner,{ hostName ->

            when {
                hostName.equals(NavHostFragment.HOSTNAME_CHARGE) -> {
                    holder.itemView.setOnClickListener{

                        val action = VehicleQueueFragmentDirections.actionVehicleQueueFragmentToChargeFragment(bean)
                        it.findNavController().navigate(action)
                    }
                }
                hostName.equals(NavHostFragment.HOSTNAME_VEHICLE_INSPECTION) -> {
                    holder.itemView.setOnClickListener {

                        val action = VehicleQueueFragmentDirections.actionVehicleQueueFragmentToInspectionItemFragment(bean)
                        it.findNavController().navigate(action)
                    }
                }
                hostName.equals(NavHostFragment.HOSTNAME_DISPATCH) -> {
                    holder.itemView.setOnClickListener{

                        val action = VehicleQueueFragmentDirections.actionVehicleQueueFragmentToDispatchFragment(bean)
                        it.findNavController().navigate(action)
                    }
                }
                hostName.equals(NavHostFragment.HOSTNAME_VERIFY_SIGNATURE) -> {
                    holder.itemView.setOnClickListener {
                        val action = VehicleQueueFragmentDirections.actionVehicleQueueFragmentToVehicleImageVideoFragment(bean)
                        it.findNavController().navigate(action)
                    }
                }
            }
        })


    }




}

