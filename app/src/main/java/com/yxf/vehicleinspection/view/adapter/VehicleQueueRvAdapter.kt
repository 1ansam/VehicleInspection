package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.utils.TableJsCsCodeHelper
import com.yxf.vehicleinspection.view.activity.VehicleImageVideoActivity
import com.yxf.vehicleinspection.view.fragment.VehicleImageVideoFragment

/**
 *   author:yxf
 *   time:2021/9/28
 */
class VehicleQueueRvAdapter(

    private val context: Context,
    private var modelList: List<VehicleQueueResponse>?,
) : RecyclerView.Adapter<VehicleQueueViewHolder>() {

    lateinit var binding: PersonInspectionItemBinding
    val tableJsCsCodeHelper = TableJsCsCodeHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleQueueViewHolder {
        binding =
            PersonInspectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleQueueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleQueueViewHolder, position: Int) {
        if (modelList != null){
            val model = modelList!![position]

            holder.setData(model)
            holder.itemView.setOnClickListener {
//                val intent = Intent(context, VehicleImageVideoActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("key", model)
//                intent.putExtra("bundle", bundle)
//                context.startActivity(intent)

                it.findNavController().navigate(R.id.vehicleImageVideoFragment,bundle)
            }
        }



    }
    override fun getItemCount(): Int {

        return modelList?.size ?:0
    }
    fun setModel(modelList : List<VehicleQueueResponse>){
        this.modelList = modelList
        notifyDataSetChanged()
    }

}

class VehicleQueueViewHolder(private val binding: PersonInspectionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(model: VehicleQueueResponse) {
        binding.tvHphm.text = model.Hphm
        binding.tvHpzl.text = model.HpzlCc
        binding.tvAjywlb.text = "安检：${model.AjywlbCc}"
        binding.tvHjjwlb.text = "环保：${model.HjywlbCc}"
        binding.tvLsh.text = model.Lsh
        binding.tvTime.text = model.Djrq?.substring(0,16)
        binding.tvInspectionState.text = model.Jyzt
    }
}