package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.bean.Data
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.utils.TableJsCsCodeHelper
import com.yxf.vehicleinspection.view.activity.InspectionInfoActivity

/**
 *   author:yxf
 *   time:2021/9/28
 */
class PersonInspcetionRvAdapter(

    private val context: Context,
    private var modelList: ArrayList<Data>?,
) : RecyclerView.Adapter<PersonInspectionViewHolder>() {

    lateinit var binding: PersonInspectionItemBinding
    val tableJsCsCodeHelper = TableJsCsCodeHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonInspectionViewHolder {
        binding =
            PersonInspectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonInspectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonInspectionViewHolder, position: Int) {
        if (modelList != null){
            val vehicle = Data(tableJsCsCodeHelper.getMc("08",modelList!![position].ajywlb),
                tableJsCsCodeHelper.getMc("31",modelList!![position].hjywlb),
                modelList!![position].hphm,
                tableJsCsCodeHelper.getMc("09",modelList!![position].hpzl),
                modelList!![position].lsh,
                modelList!![position].time)
            holder.setData(vehicle)
            holder.itemView.setOnClickListener {
                val intent = Intent(context, InspectionInfoActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("key", modelList!![position])
                intent.putExtra("bundle", bundle)
                context.startActivity(intent)
            }
        }



    }
    override fun getItemCount(): Int {

        return modelList?.size ?:0
    }
    fun setModel(modelList : ArrayList<Data>){
        this.modelList = modelList
        notifyDataSetChanged()
    }

}

class PersonInspectionViewHolder(private val binding: PersonInspectionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(model: Data) {
        binding.tvHphm.text = model.hphm
        binding.tvHpzl.text = model.hpzl
        binding.tvAjywlb.text = "安检:${model.ajywlb}"
        binding.tvHjjwlb.text = "环保：${model.hjywlb}"
        binding.tvLsh.text = model.lsh
        binding.tvTime.text = model.time
    }
}