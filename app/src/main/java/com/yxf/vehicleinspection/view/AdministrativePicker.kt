package com.yxf.vehicleinspection.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.bean.response.AdministrativeR023Response
import com.yxf.vehicleinspection.utils.getScreenHeight
import com.yxf.vehicleinspection.utils.getScreenWidth
import com.yxf.vehicleinspection.view.adapter.AdministrativeAdapter
import com.yxf.vehicleinspection.viewModel.AdministrativeViewModel
import com.yxf.vehicleinspection.viewModel.AdministrativeViewModelFactory

/**
 *   author:yxf
 *   time:2021/12/14
 */
class AdministrativePicker : DialogFragment() {
    lateinit var svAdministrative : SearchView
    lateinit var rvAdministrative : RecyclerView
    val adapter = AdministrativeAdapter()
    private val administrativeViewModel: AdministrativeViewModel by viewModels {
        AdministrativeViewModelFactory((requireContext().applicationContext as MyApp).administrativeRepository)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_administrative, container,false)
    }

    override fun onResume() {
        super.onResume()
        svAdministrative = requireView().findViewById(R.id.svAdministrative)
        rvAdministrative = requireView().findViewById(R.id.rvAdministrative)
        rvAdministrative.layoutManager = LinearLayoutManager(this.requireContext())
        rvAdministrative.adapter = adapter
        administrativeViewModel.getAdministrativeListFromMc("").observe(this){
            adapter.data = it
        }
        adapter.onItemViewClickListener = object  : BaseRvAdapter.OnItemViewClickListener<AdministrativeR023Response>{
            override fun onItemClick(view: View, position: Int,bean : AdministrativeR023Response) {
                val xzqhdm = parentFragment?.view?.findViewById<TextView>(R.id.tvXzqhdm)
                xzqhdm?.text = bean.Xzqhdm
                dismiss()
            }
        }
        svAdministrative.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    administrativeViewModel.getAdministrativeListFromMc(newText).observe(this@AdministrativePicker){
                        adapter.data = it
                    }
                    return true
                }else{
                    return false
                }

            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    administrativeViewModel.getAdministrativeListFromMc(query).observe(this@AdministrativePicker){
                        adapter.data = it
                    }
                    return true
                }else{
                    return false
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val layoutParams = window?.attributes
        layoutParams?.height = (25 * getScreenHeight(this.requireContext()) / 32)
        layoutParams?.width = (8 * getScreenWidth(this.requireContext()) / 9)
        window?.setLayout(layoutParams?.width!!, layoutParams?.height!!)
    }

}