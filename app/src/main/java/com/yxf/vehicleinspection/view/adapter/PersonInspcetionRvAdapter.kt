package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.view.activity.InspectionInfoActivity

/**
 *   author:yxf
 *   time:2021/9/28
 */
class PersonInspcetionRvAdapter(
    private val context: Context,
    private val modelList: ArrayList<BaseInfo_Hand>,
) : RecyclerView.Adapter<PersonInspectionViewHolder>() {
    lateinit var binding: PersonInspectionItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonInspectionViewHolder {
        binding =
            PersonInspectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonInspectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonInspectionViewHolder, position: Int) {
        val vehicle: BaseInfo_Hand = modelList[position]
        holder.setData(vehicle)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, InspectionInfoActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("key", modelList[position])
            intent.putExtra("bundle", bundle)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return modelList.size
    }
}

class PersonInspectionViewHolder(private val binding: PersonInspectionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setData(model: BaseInfo_Hand) {
        binding.tvHphm.text = model.hphm
        binding.tvHpzl.text = model.hpzl
        binding.tvAjywlb.text = "安检:${model.ajywlb}"
        binding.tvHjjwlb.text = "环保：${model.hjywlb}"
        binding.tvLsh.text = model.Lsh
    }
}