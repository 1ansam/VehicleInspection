package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.InspectionItem
import com.yxf.vehicleinspection.bean.VehicleInformation
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoResponse
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemResponse
import com.yxf.vehicleinspection.databinding.FragmentInspectionItemBinding
import com.yxf.vehicleinspection.repository.VehicleAllInfoRepository
import com.yxf.vehicleinspection.repository.VehicleInspectionItemRepository
import com.yxf.vehicleinspection.view.adapter.InspectionItemAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleAllInfoAdapter
import com.yxf.vehicleinspection.viewModel.*

class InspectionItemFragment : BaseBindingFragment<FragmentInspectionItemBinding>() {
    private lateinit var vehicleInformationAdapter: VehicleAllInfoAdapter
    private lateinit var inspectionItemAdapter: InspectionItemAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var vehicleAllInfoViewModel: VehicleAllInfoViewModel
    private lateinit var vehicleInspectionItemViewModel : VehicleInspectionItemViewModel
    private val args: DispatchFragmentArgs by navArgs()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        vehicleAllInfoViewModel = ViewModelProvider(this, VehicleAllInfoViewModelFactory(
            VehicleAllInfoRepository())).get(VehicleAllInfoViewModel::class.java)
        vehicleInspectionItemViewModel = ViewModelProvider(this,VehicleInspectionItemViewModelFactory(
            VehicleInspectionItemRepository())).get(VehicleInspectionItemViewModel::class.java)
        binding.rvVehicleInformation.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvInspectionItem.layoutManager = LinearLayoutManager(this.requireContext())
        vehicleInformationAdapter = VehicleAllInfoAdapter()

        inspectionItemAdapter = InspectionItemAdapter()

        binding.rvVehicleInformation.adapter = vehicleInformationAdapter
        binding.rvInspectionItem.adapter = inspectionItemAdapter
        binding.rvVehicleInformation.setHasFixedSize(true)
        binding.rvInspectionItem.setHasFixedSize(true)


    }

    /**
     *   @param Lsh 流水号 VARCHAR(20) 按流水号查询时其他参数可空（空字符串）
     *   @param Hphm 号牌号码 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Hpzl 号牌种类 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Clsbdh 车辆识别代号 用流水号/行驶证编号查询时可空（空字符串）
     *   @param Xszbh 行驶证编号 按行驶证编号查询时其他参数可空（空字符串）
     */
    private fun getData(Lsh: String, Hphm: String, Hpzl: String, Clsbdh: String, Xszbh: String) {
        val vehicleInformationList =
            ArrayList<VehicleAllInfoResponse>()
        val inspectionItemList = ArrayList<VehicleInspectionItemResponse>()

        vehicleAllInfoViewModel.getVehicleAllInfo(Lsh, Hphm, Hpzl, Clsbdh, Xszbh)
            .observe(this) {
                for (element in it) {
                    vehicleInformationList.add(element)
//                    inspectionItemAdapter.vehicleAllInfoResponse = element
                }

                    vehicleInformationAdapter.data = vehicleInformationList
            }
        vehicleInspectionItemViewModel.getVehicleInspectionItem(Lsh).observe(this){
            for (element in it){
                inspectionItemList.add(element)
            }
            inspectionItemAdapter.data = inspectionItemList
        }



    }


    override fun onResume() {
        super.onResume()
        getData(args.bean.Lsh, args.bean.Hphm, args.bean.Hpzl, "", "")
        Log.e("TAG", "onResume: InspectionItemFragment")
    }


}