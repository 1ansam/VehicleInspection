package com.yxf.vehicleinspection.view.fragment

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
import com.yxf.vehicleinspection.utils.date2String
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
        binding.rvLeftList.layoutManager = GridLayoutManager(this.requireContext(),2)
        val textAdapter = RegisterListAdapter()
        textAdapter.data = registerViewModel.textMap.values.toList()
        binding.rvLeftList.adapter = textAdapter
        binding.rvLeftList.setHasFixedSize(true)

        binding.rvLeftSpinner.layoutManager = GridLayoutManager(this.requireContext(),2)
        val spinnerAdapter = RegisterSpinnerAdapter(this.requireContext())
        spinnerAdapter.data = registerViewModel.spinnerMap.values.toList()

        dataDictionaryViewModel.getDataDictionaryFromDb().observe(this){
            val listspinnerData = ArrayList<List<String>>()
            val listCllx = mutableListOf<String>("")
            val listClyt =mutableListOf<String>("")
            val listYtsx =mutableListOf<String>("")
            val listWgchx = mutableListOf<String>("")
            val listGcjk = mutableListOf<String>("")
            val listQdxs = mutableListOf<String>("")
            val listZdly = mutableListOf<String>("")
            val listRlzl1 = mutableListOf<String>("")
            val listRlzl2 = mutableListOf<String>("")
            val listRygg = mutableListOf<String>("")
            val listQzdz = mutableListOf<String>("")
            val listJcxh = mutableListOf<String>("")
            val listJdcsslb = mutableListOf<String>("")
            val listGyfs = mutableListOf<String>("")
            val listJqfs = mutableListOf<String>("")
            val listBsxs = mutableListOf<String>("")
            val listDws = mutableListOf<String>("")
            val listCcs = mutableListOf<String>("")
            val listHclfs = mutableListOf<String>("")
            for (element in it){
                when(element.Fl){
                    "11" -> listCllx.add(element.Mc)
                    "21" -> listClyt.add(element.Mc)
                    "22" -> listYtsx.add(element.Mc)
                    "60" -> listWgchx.add(element.Mc)
                    "gc" -> listGcjk.add(element.Mc)
                    "17" -> listQdxs.add(element.Mc)
                    "23" -> listZdly.add(element.Mc)
                    "02" -> {
                        listRlzl1.add(element.Mc)
                        listRlzl2.add(element.Mc)
                    }
                    "27" -> listRygg.add(element.Mc)
                    "03" -> listQzdz.add(element.Mc)
                    "hx" -> listJcxh.add(element.Mc)
                    "50" -> listJdcsslb.add(element.Mc)
                    "gy" -> listGyfs.add(element.Mc)
                    "jq" -> listJqfs.add(element.Mc)
                    "bs" -> listBsxs.add(element.Mc)
                    "dw" -> listDws.add(element.Mc)
                    "13" -> listCcs.add(element.Mc)
                    "25" -> listHclfs.add(element.Mc)
                }
            }

            listspinnerData.add(listCllx)
            listspinnerData.add(listClyt)
            listspinnerData.add(listYtsx)
            listspinnerData.add(listWgchx)
            listspinnerData.add(listGcjk)
            listspinnerData.add(listQdxs)
            listspinnerData.add(listZdly)
            listspinnerData.add(listRlzl1)
            listspinnerData.add(listRlzl2)
            listspinnerData.add(listRygg)
            listspinnerData.add(listQzdz)
            listspinnerData.add(listJcxh)
            listspinnerData.add(listJdcsslb)
            listspinnerData.add(listGyfs)
            listspinnerData.add(listJqfs)
            listspinnerData.add(listBsxs)
            listspinnerData.add(listDws)
            listspinnerData.add(listCcs)
            listspinnerData.add(listHclfs)
            spinnerAdapter.spinnerData = listspinnerData
        }

        binding.rvLeftSpinner.adapter = spinnerAdapter
        binding.rvLeftSpinner.setHasFixedSize(true)
        binding.btnGetVehicleInfo.setOnClickListener{
            dataDictionaryViewModel.getDm("09",binding.spHpzl.selectedItem.toString()).observe(this){
                hpzl ->
                vehicleAllInfoViewModel.getVehicleAllInfo("${binding.spHphm.selectedItem.toString()}${binding.etHphm.text.toString()}",hpzl,binding.etClsbdh.text.toString(),"","","").observe(this){
                    if (it.isNotEmpty()){
                        val bean005 = it[0]
                        with(binding){
                            etClsbdh.setText(bean005.Clsbdh)
                            etCwkc.setText(bean005.Cwkc)
                            etCwkk.setText(bean005.Cwkk)
                            etCwkg.setText(bean005.Cwkg)
                            etHxnbcd.setText(bean005.Hxnbcd)
                            etHxnbkd.setText(bean005.Hxnbkd)
                            etHxnbgd.setText(bean005.Hxnbgd)
                            cbSfdlxj.isChecked = true.takeIf { bean005.Zxzxjxs == "1" }?:false
                            cbSfdzzc.isChecked = true.takeIf { bean005.Dzss == "1" }?:false
                            cbSfcyc.isChecked = true.takeIf { bean005.Cyc == "1" }?:false
                            cbSfmz.isChecked = true.takeIf { bean005.Sfmz == "1" }?:false
                        }

                    }
                }
            }

            val string = "小客"
            val viewHolder = binding.rvLeftSpinner.findViewHolderForAdapterPosition(0)
            val spinner = viewHolder?.itemView?.findViewById<Spinner>(R.id.tvSpinnerValue)
            spinner?.setText(string)
        }
        binding.btnNewLsh.setOnClickListener {
            val randomLsh = "${date2String(Date(),"yyyyMMddHHmmss")}${(0..9).random()}"
            binding.tvLsh.text = randomLsh

        }

//        binding.btnRegister.setOnClickListener {
//            val action = RegisterFragmentDirections.actionRegisterFragmentToSignatureFragment()
//            findNavController().navigate(action)
//        }

    }
    fun initView(){
        dataDictionaryViewModel.getMcListFromFl("06").observe(this){
            binding.spHphm.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl("09").observe(this){
            binding.spHpzl.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl("10").observe(this){
            binding.spHpys.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl("08").observe(this){
            binding.spAjywlb.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl("31").observe(this){
            binding.spHjywlb.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        dataDictionaryViewModel.getMcListFromFl("30").observe(this){
            binding.spZjywlb.adapter = ArrayAdapter(this.requireContext(),R.layout.textview_spinner,it)
        }
        systemParamsViewModel.getLshSzm().observe(this){
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
    fun getKey(listAdapter : RegisterListAdapter): List<String> {
        val list = ArrayList<String>()
        for (index in 0 until listAdapter.itemCount){
            list.add(listAdapter.data[index])
        }
        return list
    }
    fun getTextValue(adapter: RegisterListAdapter) : List<String>{
        val list = ArrayList<String>()

        for (index in 0 until adapter.itemCount){
            val holder = binding.rvLeftList.findViewHolderForAdapterPosition(index)
            val clearEditText = holder?.itemView?.findViewById<EditText>(R.id.value)
            list.add(clearEditText?.text.toString())
        }
        return list
    }
    fun getSpinnerValue(adapter : RegisterSpinnerAdapter) : List<String>{
        val list = ArrayList<String>()
        for (index in 0 until adapter.itemCount){
            val holder = binding.rvLeftSpinner.findViewHolderForAdapterPosition(index)
            val spinner = holder?.itemView?.findViewById<Spinner>(R.id.tvSpinnerValue)
            list.add(spinner?.selectedItem.toString())
        }
        return list
    }



    override fun onResume() {
        super.onResume()

    }

}