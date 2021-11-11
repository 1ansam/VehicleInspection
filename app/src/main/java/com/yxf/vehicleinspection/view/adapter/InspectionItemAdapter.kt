package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleAllInfo005Response
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemR006Response
import com.yxf.vehicleinspection.databinding.RvItemInspectionItemBinding
import com.yxf.vehicleinspection.view.fragment.InspectionItemFragmentDirections
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel

/**
 *   author:yxf
 *   time:2021/10/19
 */
class InspectionItemAdapter(
    val fragment: Fragment,
    private val dataDictionaryViewModel: DataDictionaryViewModel,
) : BaseRvAdapter<VehicleInspectionItemR006Response, RvItemInspectionItemBinding>() {
    var bean005: VehicleAllInfo005Response? = null
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
            binding.tvJyxm.text = bean.Xmmc
            binding.tvJyzt.text = bean.Jczt
        }
//        if (binding.tvJyzt.text.equals("完成"))
//            holder.binding.lvLineNumber.visibility = View.GONE
        holder.itemView.setOnClickListener {

        }
        when (bean.Xmbh) {
            "F1" -> dataDictionaryViewModel.getListFromFl("wx").observe(fragment) {
                binding.lvLineNumber.adapter =
                    LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                binding.lvLineNumber.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        fragment.findNavController()
                            .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToExteriorFragment(
                                bean,
                                bean005!!,it[position].Dm))
                    }
            }
            "C1" -> dataDictionaryViewModel.getListFromFl("dx").observe(fragment) {
                binding.lvLineNumber.adapter =
                    LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                binding.lvLineNumber.onItemClickListener =
                    AdapterView.OnItemClickListener{
                            _, _, position, _ ->
                        fragment.findNavController()
                    .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToChassisFragment(bean,
                        bean005!!,it[position].Dm))
                    }
            }
            "DC" -> dataDictionaryViewModel.getListFromFl("tx").observe(fragment) {
                binding.lvLineNumber.adapter =
                    LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                binding.lvLineNumber.onItemClickListener =
                    AdapterView.OnItemClickListener{
                            _, _, position, _ ->
                        fragment.findNavController()
                            .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToDynamicFragment(bean,
                                bean005!!,it[position].Dm))
                    }
            }
            "NQ" -> dataDictionaryViewModel.getListFromFl("nx").observe(fragment) {
                binding.lvLineNumber.adapter =
                    LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                binding.lvLineNumber.onItemClickListener =
                    AdapterView.OnItemClickListener{
                            _, _, position, _ ->
                        fragment.findNavController()
                            .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToNetworkQueryFragment(bean,
                                bean005!!,it[position].Dm))
                    }
            }
            "UC" -> dataDictionaryViewModel.getListFromFl("ux").observe(fragment) {
                binding.lvLineNumber.adapter =
                    LineNumberAdapter(fragment.requireContext(), R.layout.item_line_number, it)
                binding.lvLineNumber.onItemClickListener =
                    AdapterView.OnItemClickListener{
                            _, _, position, _ ->
                        fragment.findNavController()
                            .navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToUniqueFragment(bean,
                                bean005!!,it[position].Dm))
                    }
            }
        }

    }


}