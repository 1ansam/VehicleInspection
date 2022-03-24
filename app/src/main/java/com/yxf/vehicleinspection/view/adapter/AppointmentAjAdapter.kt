package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.AppointmentAjR010Response
import com.yxf.vehicleinspection.databinding.ItemAppointmentAjBinding
import com.yxf.vehicleinspection.utils.FL_HPZL
import com.yxf.vehicleinspection.view.fragment.AppointmentAjFragmentDirections
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel

/**
 *   author:yxf
 *   time:2021/12/9
 */
class AppointmentAjAdapter(private val owner: LifecycleOwner, private val dataDictionaryViewModel: DataDictionaryViewModel) : BaseRvAdapter<AppointmentAjR010Response, ItemAppointmentAjBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRvViewHolder<ItemAppointmentAjBinding> {
        return BaseRvViewHolder(ItemAppointmentAjBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemAppointmentAjBinding>,
        position: Int,
        binding: ItemAppointmentAjBinding,
        bean: AppointmentAjR010Response
    ) {
        binding.apply {
            tvHphm.text = bean.hphm
            dataDictionaryViewModel.getMc(FL_HPZL,bean.hpzl).observe(owner){
                tvHpzl.text = it
            }

            tvClsbdh.text = bean.clsbdh
            tvSjr.text = bean.sjr
            tvSjrdh.text = bean.sjrdh
            tvSjrsfzh.text = bean.sjrsfzh
        }

        holder.itemView.setOnClickListener {
            val action = AppointmentAjFragmentDirections.actionAppointmentAjFragmentToRegisterFragment(bean)
            it.findNavController().navigate(action)
        }
    }
}