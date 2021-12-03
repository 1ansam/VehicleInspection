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
import com.yxf.vehicleinspection.view.adapter.RegisterListAdapter
import com.yxf.vehicleinspection.view.adapter.RegisterSpinnerAdapter
import com.yxf.vehicleinspection.viewModel.RegisterViewModel
import com.yxf.vehicleinspection.viewModel.RegisterViewModelFactory

class RegisterFragment : BaseBindingFragment<FragmentRegisterBinding>() {
    private val registerViewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory((requireActivity().application as MyApp).registerRepository,
            (requireActivity().application as MyApp).dataDictionaryRepository)
    }
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding.rvLeftList.layoutManager = GridLayoutManager(this.requireContext(),2)
        val textAdapter = RegisterListAdapter()
        textAdapter.data = registerViewModel.textMap.values.toList()
        binding.rvLeftList.adapter = textAdapter
        binding.rvLeftList.setHasFixedSize(true)

        binding.rvLeftSpinner.layoutManager = GridLayoutManager(this.requireContext(),2)
        val spinnerAdapter = RegisterSpinnerAdapter(this.requireContext())
        spinnerAdapter.data = registerViewModel.spinnerMap.values.toList()
        getSpinnerList(spinnerAdapter).observe(this){
            spinnerAdapter.spinnerData = it
        }
//        spinnerAdapter.spinnerData = getSpinnerList(spinnerAdapter)

        binding.rvLeftSpinner.adapter = spinnerAdapter
        binding.rvLeftSpinner.setHasFixedSize(true)
        binding.btnNewLsh.setOnClickListener {
            getSpinnerValue(spinnerAdapter)
        }

//        binding.btnRegister.setOnClickListener {
//            val action = RegisterFragmentDirections.actionRegisterFragmentToSignatureFragment()
//            findNavController().navigate(action)
//        }

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
    fun getSpinnerList(adapter: RegisterSpinnerAdapter): LiveData<ArrayList<List<String>>> {
        val livedata = MutableLiveData<ArrayList<List<String>>>()
        for (element in registerViewModel.getSpinnerList()){
            element.observe(this){
                livedata.value?.add(it)
            }
        }
        return livedata

    }


    override fun onResume() {
        super.onResume()

    }

}