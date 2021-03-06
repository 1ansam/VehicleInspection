package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.databinding.FragmentInspectionItemBinding
import com.yxf.vehicleinspection.view.adapter.InspectionItemAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleAllInfoAdapter
import com.yxf.vehicleinspection.viewModel.*

class InspectionItemFragment : BaseBindingFragment<FragmentInspectionItemBinding>() {
    private lateinit var vehicleInformationAdapter: VehicleAllInfoAdapter
    private lateinit var inspectionItemAdapter: InspectionItemAdapter
    private val vehicleAllInfoViewModel by viewModels<VehicleAllInfoViewModel> {
        VehicleAllInfoViewModelFactory((requireActivity().application as MyApp).vehicleAllInfoRepository)
    }
    private val dataDictionaryViewModel by viewModels<DataDictionaryViewModel> { DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository) }

    private val vehicleInspectionItemViewModel by viewModels<VehicleInspectionItemViewModel> {
        VehicleInspectionItemViewModelFactory((requireActivity().application as MyApp).vehicleInspectionItemRepository)
    }
    private val args: InspectionItemFragmentArgs by navArgs()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        binding.rvVehicleInformation.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvInspectionItem.layoutManager = LinearLayoutManager(this.requireContext())
        vehicleInformationAdapter = VehicleAllInfoAdapter(this,dataDictionaryViewModel)
        inspectionItemAdapter = InspectionItemAdapter(this,dataDictionaryViewModel,args.bean002)

        binding.rvVehicleInformation.adapter = vehicleInformationAdapter
        binding.rvInspectionItem.adapter = inspectionItemAdapter
        binding.rvVehicleInformation.setHasFixedSize(true)
        binding.rvInspectionItem.setHasFixedSize(true)


    }

    /**
     *   @param Lsh ????????? VARCHAR(20) ?????????????????????????????????????????????????????????
     *   @param Hphm ???????????? ????????????/????????????????????????????????????????????????
     *   @param Hpzl ???????????? ????????????/????????????????????????????????????????????????
     *   @param Clsbdh ?????????????????? ????????????/????????????????????????????????????????????????
     *   @param Xszbh ??????????????? ???????????????????????????????????????????????????????????????
     */
    private fun getData(Ajlsh : String, Hjlsh : String) {
        val vehicleInformationList =
            ArrayList<VehicleAllInfoR005Response>()

        vehicleAllInfoViewModel.getVehicleAllInfo(Ajlsh,Hjlsh)
            .observe(this) {
                for (element in it) {
                    vehicleInformationList.add(element)
//                    inspectionItemAdapter.vehicleAllInfoResponse = element
                }

                vehicleInformationAdapter.data = vehicleInformationList
                if (it.isNotEmpty()){
                    inspectionItemAdapter.bean005 = it.first()
                    vehicleInspectionItemViewModel.getVehicleInspectionItem(args.bean002.Ajlsh,
                        args.bean002.Hjlsh,args.bean002.Ajywlb,args.bean002.Hjywlb).observe(this) {
//                        it.forEach {
//                            inspectionItemList.add(it)
//                        }
//                        for (element in it) {
//                            inspectionItemList.add(element)
//                        }
                        inspectionItemAdapter.data = it
                    }
                }
            }



    }


    override fun onResume() {
        super.onResume()
        getData(args.bean002.Ajlsh,args.bean002.Hjlsh)
    }


}