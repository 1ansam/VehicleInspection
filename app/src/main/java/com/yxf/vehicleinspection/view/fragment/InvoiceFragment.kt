package com.yxf.vehicleinspection.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.CollectMoney
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.InvoiceParamsR025Response
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.databinding.FragmentInvoiceBinding
import com.yxf.vehicleinspection.utils.FL_HPZL
import com.yxf.vehicleinspection.utils.FL_QYLX
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.utils.setText
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InvoiceAdapter
import com.yxf.vehicleinspection.viewModel.ChargeViewModel
import com.yxf.vehicleinspection.viewModel.ChargeViewModelFactory
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModel
import com.yxf.vehicleinspection.viewModel.DataDictionaryViewModelFactory
import java.util.*
import kotlin.collections.ArrayList


class InvoiceFragment : BaseBindingFragment<FragmentInvoiceBinding>() {
    lateinit var bean001 : UserInfoR001Response
    private val args : InvoiceFragmentArgs by navArgs()
    private val invoiceAdapter = InvoiceAdapter()
    private val chargeViewModel by
    viewModels<ChargeViewModel> { ChargeViewModelFactory(
        (requireActivity().application as MyApp).chargeRepository)
    }
    private val dataDictionaryViewModel: DataDictionaryViewModel by viewModels {
        DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository)
    }
    override fun init() {
        activity?.onBackPressedDispatcher?.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = InvoiceFragmentDirections.actionInvoiceFragmentToVehicleQueueFragment()
                findNavController().navigate(action)
            }
        })
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        dataDictionaryViewModel.getMcListFromFl(FL_QYLX).observe(this){
            binding.spQylx.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
            chargeViewModel.getBuyerParams(BuyerParamsR026Request(args.bean005.Hphm)).observe(this){
                binding.apply {
                    etAddress.setText(it.Address)
                    etAccount.setText(it.Account)
                    etKhh.setText(it.OpenBank)
                    etMobile.setText(it.Mobile)
                    etTaxNumber.setText(it.Taxnum)
                    spQylx.setText(it.Type)
                }
            }
        }
        with(binding) { etBuyerName.setText(args.bean005.Syr) }
        binding.tvBuyerHphm.text = args.bean005.Hphm
        binding.tvLsh.text = args.bean005.Ajlsh
        dataDictionaryViewModel.getMc(FL_HPZL,args.bean005.Hpzl).observe(this){
            binding.tvHpzl.text = it
        }


        binding.btnInvoice.setOnClickListener {
            chargeViewModel.getInvoiceParams().observe(this){
                chargeViewModel.postInvoice(getInvoiceW005Request(args.bean005,args.wbean004,it)).observe(this){
                    if (it){
                        Snackbar.make(this.requireView(),"开票成功",Snackbar.LENGTH_SHORT).show()
                        val action = InvoiceFragmentDirections.actionInvoiceFragmentToVehicleQueueFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
        invoiceAdapter.data = args.wbean004.chargeDetails
        binding.rvInvoiceItem.apply {
            adapter = invoiceAdapter
            layoutManager = LinearLayoutManager(this@InvoiceFragment.requireContext())
            setHasFixedSize(true)
        }

    }
    fun getInvoiceW005Request(bean005 : VehicleAllInfoR005Response, wbean004 : SaveChargeInfoW004Request , bean025: InvoiceParamsR025Response): SaveInvoiceW005Request {
        val list = ArrayList<InvoiceDetail>()
        for (index in 0 until wbean004.chargeDetails.size){
            list.add(InvoiceDetail(
                wbean004.chargeDetails[index].Orderno,
                wbean004.chargeDetails[index].Detailno,
                wbean004.chargeDetails[index].Goodsname,
                wbean004.chargeDetails[index].Num,
                wbean004.chargeDetails[index].Price,
                bean025.Sl,
                bean025.Spbm
                ))
        }
        return SaveInvoiceW005Request(InvoiceOrder(
            binding.tvLsh.text.toString(),
            bean005.Hpzl,
            binding.tvBuyerHphm.text.toString(),
            bean005.Cllx,
            bean005.Syr,
            binding.etMobile.text.toString(),
            wbean004.chargeOrder.Amt,
            binding.etBuyerName.text.toString().takeIf { binding.spQylx.selectedItem != "个人" }?:binding.tvBuyerHphm.text.toString(),
            binding.etTaxNumber.text.toString().takeIf { binding.spQylx.selectedItem != "个人" }?:"",
            binding.etMobile.text.toString(),
            binding.etAddress.text.toString(),
            "${ binding.etKhh.text.toString()} ${binding.etAccount.text.toString()}".takeIf { binding.spQylx.selectedItem != "个人" }?:"",
            wbean004.chargeOrder.Oid,
            bean001.TrueName,
            bean001.ID,
            bean001.TrueName,
            bean025.Fpjksfsbm,
            date2String(Date(),"yyyy-MM-dd HH:mm:ss"),
            bean025.Khh,
            bean025.Dh,
            bean025.Dz,
            bean025.Skdwsbh,
            bean025.Shr,
            binding.spQylx.selectedItem.toString()
        ),list)
    }

    override fun onStop() {
        super.onStop()

    }
}