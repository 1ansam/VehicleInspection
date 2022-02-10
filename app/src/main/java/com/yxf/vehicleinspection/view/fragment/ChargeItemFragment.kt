package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.CollectMoney
import com.yxf.vehicleinspection.bean.request.ChargeDetail
import com.yxf.vehicleinspection.bean.request.ChargeOrder
import com.yxf.vehicleinspection.bean.request.SaveChargeInfoW004Request
import com.yxf.vehicleinspection.bean.response.ChargeItemR004Response
import com.yxf.vehicleinspection.bean.response.SystemParamsR015Response
import com.yxf.vehicleinspection.databinding.FragmentChargeItemBinding
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.view.adapter.ChargeItemAdapter
import com.yxf.vehicleinspection.viewModel.ChargeItemViewModel
import com.yxf.vehicleinspection.viewModel.ChargeItemViewModelFactory
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModel
import com.yxf.vehicleinspection.viewModel.SystemParamsViewModelFactory
import org.w3c.dom.Text
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class ChargeItemFragment : BaseBindingFragment<FragmentChargeItemBinding>() {
    val chargeItemAdapter = ChargeItemAdapter()
    private val chargeItemViewModel: ChargeItemViewModel by viewModels {
        ChargeItemViewModelFactory((requireActivity().application as MyApp).chargeItemRepository)
    }
    private val systemParamsViewModel: SystemParamsViewModel by viewModels {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    private val args : ChargeItemFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding.includeTitle.Alltitle.text = "选择收费项目"
        binding.rvChargeItem.apply {
            layoutManager = GridLayoutManager(this@ChargeItemFragment.requireContext(),4)
            adapter = chargeItemAdapter
            setHasFixedSize(true)
        }
        chargeItemViewModel.getChargeItemFromDb().observe(this){
            chargeItemAdapter.data = it
        }
        binding.btnCharge.setOnClickListener {
            if (checkItemSelected(chargeItemAdapter)){
                systemParamsViewModel.getSystemParamsDataFromDb("AJ").observe(this){
                        bean015 ->
                    chargeItemViewModel.getChargeItemFromDb().observe(this){
                            bean004 ->
                        val oid = getOid()
                        val wbean004 = SaveChargeInfoW004Request(ChargeOrder(args.bean002.Ajlsh,bean015.Appid,bean015.C,oid,(getAmt(chargeItemAdapter)*100).toString(),"05|Q1#"+args.bean002.Hphm+"|X#其他备注信息","",bean015.Md5key),
                            getChargeDetails(chargeItemAdapter,bean004,oid))
                        val collectMoney = CollectMoney(bean015.Appid,bean015.C,oid,getAmt(chargeItemAdapter)*100,"05|Q1#"+args.bean002.Hphm+"|X#其他备注信息","",bean015.Md5key)
                        val action = ChargeItemFragmentDirections.actionChargeItemFragmentToChargeFragment(args.bean002,collectMoney,bean015.C1,wbean004)
                        findNavController().navigate(action)
                    }

                }
            }else{
                Snackbar.make(this.requireView(),"请选择一个项目",Snackbar.LENGTH_SHORT).show()
            }



        }
    }
    fun getChargeDetails(chargeItemAdapter: ChargeItemAdapter, chargeItems : List<ChargeItemR004Response>, oid : String) : List<ChargeDetail>{
        val list = ArrayList<ChargeDetail>()
        for (index in 0 until chargeItemAdapter.itemCount){
            var detailNo = 0
            val holder = binding.rvChargeItem.findViewHolderForAdapterPosition(index)
            val tvSpbh = holder?.itemView?.findViewById<TextView>(R.id.tvSpbh)
            val cbSpmc = holder?.itemView?.findViewById<CheckBox>(R.id.cbSpmc)
            val tvSpdj = holder?.itemView?.findViewById<TextView>(R.id.tvSpdj)
                if (cbSpmc != null && cbSpmc.isChecked){
                    detailNo += 1
                    list.add(ChargeDetail(oid,
                        detailNo.toString(),
                        tvSpbh?.text.toString(),
                        cbSpmc.text.toString(),
                        tvSpdj?.text.toString(),
                        tvSpdj?.text.toString()
                        ))
                }
        }
        return list
    }
    fun getAmt(chargeItemAdapter : ChargeItemAdapter) : Int{
        var amt = 0
        for (index in 0 until chargeItemAdapter.itemCount){
            val holder = binding.rvChargeItem.findViewHolderForAdapterPosition(index)
            val cbSpmc = holder?.itemView?.findViewById<CheckBox>(R.id.cbSpmc)
            val tvSpdj = holder?.itemView?.findViewById<TextView>(R.id.tvSpdj)
            if (tvSpdj != null) {
                if (cbSpmc != null && cbSpmc.isChecked){
                    amt += tvSpdj.text.toString().toInt()
                }
            }
        }
        return amt
    }

    fun getOid() : String{
        val string = StringBuilder()
        val random = "${date2String(Date(), "yyyyMMddHHmmss")}${
            String.format(
                "%03d",
                (1..99).random()
            )
        }"
        return string.append("01").append(random).toString()
    }

    private fun checkItemSelected(chargeItemAdapter: ChargeItemAdapter) : Boolean{
        var boolean = false
        for (index in 0 until chargeItemAdapter.itemCount){
            val holder = binding.rvChargeItem.findViewHolderForAdapterPosition(index)
            val cbSpmc = holder?.itemView?.findViewById<CheckBox>(R.id.cbSpmc)
            if (cbSpmc != null){
                boolean = cbSpmc.isChecked
                if (boolean){
                    return boolean
                }
            }

        }
        return boolean

    }

}