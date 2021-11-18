package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemR006Response
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
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.rvVehicleInformation.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvInspectionItem.layoutManager = LinearLayoutManager(this.requireContext())
        vehicleInformationAdapter = VehicleAllInfoAdapter(this,dataDictionaryViewModel)
        inspectionItemAdapter = InspectionItemAdapter(this,dataDictionaryViewModel)

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
            ArrayList<VehicleAllInfoR005Response>()
        val inspectionItemList = ArrayList<VehicleInspectionItemR006Response>()

        vehicleAllInfoViewModel.getVehicleAllInfo(Lsh, Hphm, Hpzl, Clsbdh, Xszbh)
            .observe(this) {
                for (element in it) {
                    vehicleInformationList.add(element)
//                    inspectionItemAdapter.vehicleAllInfoResponse = element
                }
                if (it.isNotEmpty()){
                    inspectionItemAdapter.beanR005 = it[0]
                }
                vehicleInformationAdapter.data = vehicleInformationList
            }
        vehicleInspectionItemViewModel.getVehicleInspectionItem(Lsh).observe(this) {
            for (element in it) {
                inspectionItemList.add(element)
            }
            inspectionItemAdapter.data = inspectionItemList
        }


    }


    override fun onResume() {
        super.onResume()
        getData(args.bean002.Lsh, args.bean002.Hphm, args.bean002.Hpzl, "", "")
    }


}