package com.yxf.vehicleinspection.view.fragment

import android.app.DatePickerDialog
import android.content.pm.ActivityInfo
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentRegisterBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.DatePickerFragment
import com.yxf.vehicleinspection.view.adapter.RegisterListAdapter
import com.yxf.vehicleinspection.view.adapter.RegisterSpinnerAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log
import kotlin.random.Random

class RegisterFragment : BaseBindingFragment<FragmentRegisterBinding>() {
    private val registerViewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory((requireActivity().application as MyApp).registerRepository)
    }
    private val dataDictionaryViewModel by viewModels<DataDictionaryViewModel> {
        DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository)
    }
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    private val vehicleAllInfoViewModel by viewModels<VehicleAllInfoViewModel> {
        VehicleAllInfoViewModelFactory((requireActivity().application as MyApp).vehicleAllInfoRepository)
    }

    override fun init() {
        initView()
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding.rvLeftList.layoutManager = GridLayoutManager(this.requireContext(), 2)
        val textAdapter = RegisterListAdapter()
        textAdapter.data = registerViewModel.textMap.values.toList()
        binding.rvLeftList.adapter = textAdapter
        binding.rvLeftList.setHasFixedSize(true)

        binding.tvCcrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager,"ccrq")
        }
        binding.tvDjrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager,"djrq")
        }

        binding.btnGetVehicleInfo.setOnClickListener {
            dataDictionaryViewModel.getDm("09", binding.spHpzl.selectedItem.toString())
                .observe(this) { hpzl ->
                    vehicleAllInfoViewModel.getVehicleAllInfo(
                        "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
                        hpzl,
                        binding.etClsbdh.text.toString(),
                        "",
                        "",
                        ""
                    ).observe(this) {
                        if (it.isNotEmpty()) {
                            val bean005 = it[0]
                            binding.apply {
                                etClsbdh.setText(bean005.Clsbdh)
                                etCwkc.setText(bean005.Cwkc)
                                etCwkk.setText(bean005.Cwkk)
                                etCwkg.setText(bean005.Cwkg)
                                etHxnbcd.setText(bean005.Hxnbcd)
                                etHxnbkd.setText(bean005.Hxnbkd)
                                etHxnbgd.setText(bean005.Hxnbgd)
                                cbSfdlxj.isChecked = true.takeIf { bean005.Zxzxjxs == "1" } ?: false
                                cbSfdzzc.isChecked = true.takeIf { bean005.Dzss == "1" } ?: false
                                cbSfcyc.isChecked = true.takeIf { bean005.Cyc == "1" } ?: false
                                cbSfmz.isChecked = true.takeIf { bean005.Sfmz == "1" } ?: false
                                cbAjywlb.isChecked = false.takeIf { bean005.Ajywlb == "-" } ?: true
                                cbHjywlb.isChecked = false.takeIf { bean005.Hjywlb == "-" } ?: true
                                cbZjywlb.isChecked = false.takeIf { bean005.Zjywlb == "-" } ?: true
                            }

                            dataDictionaryViewModel.apply {
                                getMc(FL_HPYS, bean005.Hpys).observe(this@RegisterFragment) {
                                    binding.spHpys.setText(it)
                                }
                                getMc(FL_AJYWLB, bean005.Ajywlb).observe(this@RegisterFragment) {
                                    binding.spAjywlb.setText(it)
                                }
                                getMc(FL_HJYWLB, bean005.Hjywlb).observe(this@RegisterFragment) {
                                    binding.spHjywlb.setText(it)
                                }
                                getMc(FL_ZJYWLB, bean005.Zjywlb).observe(this@RegisterFragment) {
                                    binding.spZjywlb.setText(it)
                                }

                            }

                            val textValueList = arrayListOf(
                                bean005.Xszbh, bean005.Clpp1,
                                bean005.Clxh, bean005.Fdjh,
                                bean005.Fdjxh, bean005.Syr,
                                bean005.Lxdh, bean005.Zt,
                                bean005.Zzcmc, bean005.Qdzs,
                                bean005.Qdzw, bean005.Zczs,
                                bean005.Zs, bean005.Zj,
                                bean005.Zzs, bean005.Qzs,
                                bean005.Qlj, bean005.Hlj,
                                bean005.Zzl, bean005.Zbzl,
                                bean005.Kqxjzw, bean005.Zxzs,
                                bean005.Bzzw, bean005.Hdzk,
                                bean005.Hdzzl, bean005.Zqyzl,
                                bean005.Zdsjcs, bean005.Gl,
                                bean005.Pl, bean005.Pqgs,
                                bean005.Lcbds, bean005.Edzs,
                                bean005.Ltgg, bean005.Qgs,
                                bean005.Lxdz, bean005.Sjr,
                                bean005.Sjrdh, bean005.Sjrsfzh,
                                "", ""
                            )
                            textAdapter.value = textValueList
                        }
                    }
                }



        }
        binding.btnNewLsh.setOnClickListener {
            val randomLsh = "${date2String(Date(), "yyyyMMddHHmmss")}${
                String.format(
                    "%03d",
                    (1..999).random()
                )
            }"
            binding.tvLsh.text = randomLsh

        }

    }

    fun initView() {
        dataDictionaryViewModel.getMcListFromFl(FL_CSJC).observe(this) {
            binding.spHphm.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_HPZL).observe(this) {
            binding.spHpzl.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_HPYS).observe(this) {
            binding.spHpys.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_AJYWLB).observe(this) {
            binding.spAjywlb.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_HJYWLB).observe(this) {
            binding.spHjywlb.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_ZJYWLB).observe(this) {
            binding.spZjywlb.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_CLLX).observe(this){
            binding.spCllx.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_CLYT).observe(this){
            binding.spClyt.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_YTSX).observe(this){
            binding.spYtsx.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_PZCX).observe(this){
            binding.spWgcx.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_GCJK).observe(this){
            binding.spGcjk.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_QDFS).observe(this){
            binding.spQdxs.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_ZDLY).observe(this){
            binding.spZdly.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_RYGG).observe(this){
            binding.spRygg.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_RYLB).observe(this){
            binding.spRyzl1.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
            binding.spRyzl2.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_QZDZ).observe(this){
            binding.spQzdz.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_HBXH).observe(this){
            binding.spJcxh.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_CLSSLB).observe(this){
            binding.spSslb.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_GYFS).observe(this){
            binding.spGyfs.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_JQFS).observe(this){
            binding.spJqfs.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_BSX).observe(this){
            binding.spBsxs.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_DW).observe(this){
            binding.spDws.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_CC).observe(this){
            binding.spCcs.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl(FL_HCLZL).observe(this){
            binding.spHclfs.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }

        systemParamsViewModel.getLshSzm().observe(this) {
            binding.etLshSzm.setText(it)
        }
    }

    fun Spinner.setText(text: String) {
        for (i in 0 until this.adapter.count) {
            if (this.adapter.getItem(i).toString().contains(text)) {
                this.setSelection(i)
            }
        }
    }

    fun getKey(listAdapter: RegisterListAdapter): List<String> {
        val list = ArrayList<String>()
        for (index in 0 until listAdapter.itemCount) {
            list.add(listAdapter.data[index])
        }
        return list
    }

    fun getTextValue(adapter: RegisterListAdapter): List<String> {
        val list = ArrayList<String>()

        for (index in 0 until adapter.itemCount) {
            val holder = binding.rvLeftList.findViewHolderForAdapterPosition(index)
            val clearEditText = holder?.itemView?.findViewById<EditText>(R.id.value)
            list.add(clearEditText?.text.toString())
        }
        return list
    }

    override fun onResume() {
        super.onResume()

    }

}