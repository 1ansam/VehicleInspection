package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.databinding.RvItemVehicleInformationBinding
import com.yxf.vehicleinspection.utils.FL_CLLX
import com.yxf.vehicleinspection.utils.FL_HPYS
import com.yxf.vehicleinspection.utils.FL_HPZL
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
        binding.apply {
            tvAjlsh.text = bean.Ajlsh
            tvHjlsh.text = bean.Hjlsh
            tvHphm.text = bean.Hphm
            tvHpzl.text = bean.Hpzl
            tvHpys.text = bean.Hpys
            tvCllx.text = bean.Cllx
            tvWkcc.text = root.resources.getString(R.string.wkcc_,bean.Cwkc,bean.Cwkk,bean.Cwkg)
            tvZbzl.text = bean.Zbzl
            tvLtgg.text = bean.Ltgg
            tvCcrq.text = bean.Ccrq
        }
        dataDictionaryViewModel.getMc(FL_HPZL,bean.Hpzl).observe(fragment){
            binding.tvHpzl.text = it
        }
        dataDictionaryViewModel.getMc(FL_HPYS,bean.Hpys).observe(fragment){
            binding.tvHpys.text = it
        }
        dataDictionaryViewModel.getMc(FL_CLLX,bean.Cllx).observe(fragment){
            binding.tvCllx.text = it
        }

    }
}