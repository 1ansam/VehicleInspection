package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.view.fragment.NavHostFragment
import com.yxf.vehicleinspection.view.fragment.VehicleQueueFragmentDirections
import com.yxf.vehicleinspection.viewModel.*


/**
 *   author:yxf
 *   time:2021/10/15
 */
class VehicleQueueRvAdapter(private val fragment: Fragment, private val sharedViewModel: SharedViewModel) : BaseRvAdapter<VehicleQueueR002Response,PersonInspectionItemBinding>() {
    private val vehicleAllInfoViewModel = ViewModelProvider(fragment,VehicleAllInfoViewModelFactory((fragment.requireActivity().application as MyApp).vehicleAllInfoRepository))[VehicleAllInfoViewModel::class.java]
    private val chargeViewModel = ViewModelProvider(fragment,ChargeViewModelFactory((fragment.requireActivity().application as MyApp).chargeRepository))[ChargeViewModel::class.java]
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


        binding.apply {
            tvHphm.text = bean.Hphm
            tvHpzl.text = bean.HpzlCc
            tvAjywlb.text = root.resources.getString(R.string.ajywlb_,bean.AjywlbCc)
            tvHjywlb.text = root.resources.getString(R.string.hjywlb_,bean.HjywlbCc)
            tvAjlsh.text = root.resources.getString(R.string.ajlsh_,bean.Ajlsh)
            tvHjlsh.text = root.resources.getString(R.string.ajlsh_,bean.Hjlsh)
            tvTime.text = bean.Djrq
            tvJyzt.text = root.resources.getString(R.string.jyzt_,bean.Ywlb,bean.Jyzt)
            when(bean.Sfsf){
                "0" ->{
                    binding.tvSfsf.apply {
                        text = "未收费 "
                        setTextColor(resources.getColor(R.color.red,null))
                    }
                }
                "1" ->{
                    binding.tvSfsf.apply {
                        text = "已收费 "
                    }
                }
            }
            when(bean.Sfkp){
                "0" ->{
                    binding.tvSfkp.apply {
                        text = "未开票"
                        setTextColor(resources.getColor(R.color.red,null))
                    }
                }
                "1" ->{
                    binding.tvSfkp.apply {
                        text = "已开票"
                    }
                }
            }
            when{
                bean.Jyzt?.contains("未") == true -> {
                    binding.tvJyzt.apply {
                        setTextColor(resources.getColor(R.color.red,null))
                    }
                }
                bean.Jyzt?.contains("结束") == true ->{
                    binding.tvJyzt.apply {
                        setTextColor(resources.getColor(R.color.blue,null))
                    }
                }
            }

        }
        sharedViewModel.hostName.observe(fragment) { hostName ->

            when {
                hostName.equals(NavHostFragment.HOSTNAME_CHARGE) -> {
                    holder.itemView.setOnClickListener {
                        view ->
                         if (bean.Sfsf == "1"&& bean.Sfkp == "0"){
                            vehicleAllInfoViewModel.getVehicleAllInfo(bean.Ajlsh,bean.Hjlsh).observe(fragment){
                                if (it.isNotEmpty()){
                                    val bean005 = it.first()
                                    chargeViewModel.getChargeInfo(bean005.Ajlsh).observe(fragment){
                                            wbean004 ->
                                        val action =
                                            VehicleQueueFragmentDirections.actionVehicleQueueFragmentToInvoiceFragment(bean005,wbean004)
                                        view.findNavController().navigate(action)


                                    }
                                }
                            }
                        }else{
                            holder.itemView.setOnClickListener {

                                val action =
                                    VehicleQueueFragmentDirections.actionVehicleQueueFragmentToChargeItemFragment(
                                        bean)
                                it.findNavController().navigate(action)
                            }
                        }

                    }


                }
                hostName.equals(NavHostFragment.HOSTNAME_VEHICLE_INSPECTION) -> {
                    holder.itemView.setOnClickListener {

                        val action =
                            VehicleQueueFragmentDirections.actionVehicleQueueFragmentToInspectionItemFragment(
                                bean)
                        it.findNavController().navigate(action)
                    }
                }
                hostName.equals(NavHostFragment.HOSTNAME_REPLENISH) -> {
                    holder.itemView.setOnClickListener {
                        val action = VehicleQueueFragmentDirections.actionVehicleQueueFragmentToReplenishFragment(bean)
                        it.findNavController().navigate(action)
                    }
                }


            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        return position
    }




}

