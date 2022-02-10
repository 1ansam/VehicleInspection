package com.yxf.vehicleinspection.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemR006Response
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.databinding.RvItemInspectionItemBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.fragment.InspectionItemFragmentDirections
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel

/**
 *   author:yxf
 *   time:2021/10/19
 */
class InspectionItemAdapter(
    val fragment: Fragment,
    private val dataDictionaryViewModel: DataDictionaryViewModel,
    val bean002 : VehicleQueueR002Response
) : BaseRvAdapter<VehicleInspectionItemR006Response, RvItemInspectionItemBinding>() {
    var bean005: VehicleAllInfoR005Response? = null
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<RvItemInspectionItemBinding> {
        return BaseRvViewHolder(RvItemInspectionItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<RvItemInspectionItemBinding>,
        position: Int,
        binding: RvItemInspectionItemBinding,
        bean: VehicleInspectionItemR006Response,
    ) {

        holder.apply {
            binding.tvJyxm.text =   "检测项目：${bean.Xmmc}"
            binding.tvJyzt.text =   "检测状态：${bean.Jczt}"
            binding.tvJcry1.text =  "检测人员 1：${bean.Jcry_01}"
            binding.tvJcry2.text =  "检测人员 2：${bean.Jcry_02}"
            binding.tvJckssj.text = "检测开始时间：${bean.Jckssj}"
            binding.tvJcjssj.text = "检测开始时间：${bean.Jcjssj}"
            if (bean.Jczt?.contains("未") == true){
                binding.tvJyzt.apply {
                    setTextColor(resources.getColor(R.color.red,null))
                }
                binding.tvJyxm.apply {
                    setTextColor(resources.getColor(R.color.red,null))
                }
            }
            dataDictionaryViewModel.getMc(FL_AJYWLB,bean.Ajywlb).observe(fragment){
                binding.tvAjywlb.text = "安检业务类别：$it"
            }
            dataDictionaryViewModel.getMc(FL_HJYWLB,bean.Hjywlb).observe(fragment){
                binding.tvHjywlb.text = "环检业务类别：$it"
            }
            Log.e("TAG", "onBindViewHolder: $bean005", )
        if (bean005 != null){
            when (bean.Jcxm) {
                "F1" -> dataDictionaryViewModel.getListFromFl(FL_F1XH).observe(fragment) {
                    binding.lvLineNumber.adapter =
                        LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                    binding.lvLineNumber.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            fragment.findNavController()
                                .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToExteriorFragment(
                                    bean,
                                    bean005!!,it[position].Dm,bean002))
                        }
                }
                "C1" -> dataDictionaryViewModel.getListFromFl(FL_C1XH).observe(fragment) {
                    binding.lvLineNumber.adapter =
                        LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                    binding.lvLineNumber.onItemClickListener =
                        AdapterView.OnItemClickListener{
                                _, _, position, _ ->
                            fragment.findNavController()
                                .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToChassisFragment(bean,
                                    bean005!!,it[position].Dm,bean002))
                        }
                }
                "DC" -> dataDictionaryViewModel.getListFromFl(FL_DCXH).observe(fragment) {
                    binding.lvLineNumber.adapter =
                        LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                    binding.lvLineNumber.onItemClickListener =
                        AdapterView.OnItemClickListener{
                                _, _, position, _ ->
                            fragment.findNavController()
                                .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToDynamicFragment(bean,
                                    bean005!!,it[position].Dm,bean002))
                        }
                }
                "NQ" -> dataDictionaryViewModel.getListFromFl(FL_NQXH).observe(fragment) {
                    binding.lvLineNumber.adapter =
                        LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                    binding.lvLineNumber.onItemClickListener =
                        AdapterView.OnItemClickListener{
                                _, _, position, _ ->
                            fragment.findNavController()
                                .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToNetworkQueryFragment(bean,
                                    bean005!!,it[position].Dm,bean002))
                        }
                }
                "UC" -> dataDictionaryViewModel.getListFromFl(FL_UCXH).observe(fragment) {
                    binding.lvLineNumber.adapter =
                        LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                    binding.lvLineNumber.onItemClickListener =
                        AdapterView.OnItemClickListener{
                                _, _, position, _ ->
                            fragment.findNavController()
                                .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToUniqueFragment(bean,
                                    bean005!!,it[position].Dm,bean002))
                        }
                }
                "YQ" -> dataDictionaryViewModel.getListFromFl(FL_JCXH).observe(fragment){
                    binding.lvLineNumber.adapter =
                        LineNumberAdapter(fragment.requireContext(),R.layout.item_line_number,it)
                    binding.lvLineNumber.onItemClickListener =
                        AdapterView.OnItemClickListener{
                                _,_,position,_ ->
                            fragment.findNavController().navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToOnlineFragment(bean,bean005!!,it[position].Dm,bean002))
                        }
                }
            }
        }
        if (bean.Jczt == "完成")
            holder.binding.lvLineNumber.visibility = View.GONE
            holder.itemView.setOnClickListener {
        }
        }

    }


}