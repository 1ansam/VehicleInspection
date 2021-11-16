package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.databinding.RvItemVehicleInformationBinding
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel

/**
 *   author:yxf
 *   time:2021/10/19
 */
class VehicleAllInfoAdapter(val fragment: Fragment, private val dataDictionaryViewModel: DataDictionaryViewModel) : BaseRvAdapter<VehicleAllInfoR005Response,RvItemVehicleInformationBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<RvItemVehicleInformationBinding> {
        return BaseRvViewHolder(RvItemVehicleInformationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<RvItemVehicleInformationBinding>,
        position: Int,
        binding: RvItemVehicleInformationBinding,
        bean: VehicleAllInfoR005Response,
    ) {
        holder.apply {
            binding.tvLsh.text = bean.Lsh
            binding.tvHphm.text = bean.Hphm
            binding.tvHpzl.text = bean.Hpzl
            binding.tvHpys.text = bean.Hpys
            binding.tvCllx.text = bean.Cllx
            binding.tvWkcc.text = "${bean.Cwkc}*${bean.Cwkk}*${bean.Cwkg}"
            binding.tvZbzl.text = bean.Zbzl
            binding.tvLtgg.text = bean.Ltgg
            binding.tvCcrq.text = bean.Ccrq
        }
        dataDictionaryViewModel.getMc("09",bean.Hpzl).observe(fragment){
            holder.binding.tvHpzl.text = it
        }
        dataDictionaryViewModel.getMc("26",bean.Hpys).observe(fragment){
            holder.binding.tvHpys.text = it
        }
        dataDictionaryViewModel.getMc("07",bean.Cllx).observe(fragment){
            holder.binding.tvCllx.text = it
        }

    }
}