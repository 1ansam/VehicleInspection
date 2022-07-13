package com.yxf.vehicleinspection.view.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.CCbean
import com.yxf.vehicleinspection.bean.request.InspectionPhotoW007Request
import com.yxf.vehicleinspection.bean.request.SaveSignatureW006Request
import com.yxf.vehicleinspection.bean.request.SaveVehicleInfoW003Request
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR022Response
import com.yxf.vehicleinspection.databinding.FragmentRegisterBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.AdministrativePicker
import com.yxf.vehicleinspection.view.DatePickerFragment
import com.yxf.vehicleinspection.view.SignaturePicker
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.RegisterJyxmAdapter
import com.yxf.vehicleinspection.view.adapter.RegisterListAdapter
import com.yxf.vehicleinspection.view.adapter.RegisterListMapAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

class RegisterFragment : BaseBindingFragment<FragmentRegisterBinding>() {
    private lateinit var bean001: UserInfoR001Response
    private val registerViewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory((requireActivity().application as MyApp).registerRepository)
    }
    private val dataDictionaryViewModel by viewModels<DataDictionaryViewModel> {
        DataDictionaryViewModelFactory((requireActivity().application as MyApp).dataDictionaryRepository)
    }
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    private val signatureViewModel by viewModels<SignatureViewModel> {
        SignatureViewModelFactory((requireActivity().application as MyApp).signatureRepository)
    }
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> {
        InspectionItemViewModelFactory(
            (requireActivity().application as MyApp).inspectionItemRepository,
            (requireActivity().application as MyApp).serverTimeRepository
        )
    }
    private val registerListAdapter = RegisterListAdapter()
    private val registerListMapAdapter = RegisterListMapAdapter()
    private val registerAjJyxmAdapter = RegisterJyxmAdapter()
    private val registerHjJyxmAdapter = RegisterJyxmAdapter()

    private var bean: VehicleAllInfoR022Response? = null
    private val args: RegisterFragmentArgs by navArgs()
    private lateinit var currentPhotoPath: String
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        initView()
        binding.rvTextList.layoutManager = GridLayoutManager(this.requireContext(), 2)
        Log.e("TAG", "init: RegisterFragment")
        val map = registerViewModel.textListMap
        map.get("sjr")?.set(1, args.bean010.sjr)
        map.get("sjrdh")?.set(1, args.bean010.sjrdh)
        map.get("sjrsfzh")?.set(1, args.bean010.sjrsfzh)
        registerListMapAdapter.data = map

        binding.rvTextList.adapter = registerListMapAdapter
        binding.rvTextList.setHasFixedSize(true)
        binding.rvAjJyxm.layoutManager = GridLayoutManager(this.requireContext(), 4)
        binding.rvAjJyxm.adapter = registerAjJyxmAdapter
        dataDictionaryViewModel.getMcListFromFl(FL_JYXM).observe(this) {
            registerAjJyxmAdapter.data = it
        }
        binding.rvHjJyxm.layoutManager = GridLayoutManager(this.requireContext(), 4)
        binding.rvHjJyxm.adapter = registerHjJyxmAdapter
        registerHjJyxmAdapter.data = registerViewModel.hjJyxmList

        binding.spHpzl.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.spHpzl.selectedItem.toString()) {
                    "大型汽车" -> binding.spHpys.setText("黄色")
                    "教练汽车" -> binding.spHpys.setText("黄色")
                    "挂车" -> binding.spHpys.setText("黄色")
                    "公安警车" -> binding.spHpys.setText("白色")
                    "使馆汽车" -> binding.spHpys.setText("黑色")
                    "领馆汽车" -> binding.spHpys.setText("黑色")
                    "小型汽车" -> binding.spHpys.setText("蓝色")
                    "大型新能源汽车" -> binding.spHpys.setText("绿色")
                    "小型新能源汽车" -> binding.spHpys.setText("绿色")
                    "普通摩托车" -> binding.spHpys.setText("黄色")
                    "轻便摩托车" -> binding.spHpys.setText("蓝色")
                    "使馆摩托车" -> binding.spHpys.setText("黑色")
                    "领馆摩托车" -> binding.spHpys.setText("黑色")
                    "教练摩托车" -> binding.spHpys.setText("黄色")
                    "警用摩托车" -> binding.spHpys.setText("白色")
                    "农用运输车" -> binding.spHpys.setText("绿色")

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.tvCcrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager, "ccrq")
        }
        binding.tvDjrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager, "djrq")
        }
        binding.tvCcdjrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager, "ccdjrq")
        }
        binding.tvXzqhdm.setOnClickListener {
            AdministrativePicker().show(this.childFragmentManager, "xzqhdm")
        }
        binding.ivCzqm.setOnClickListener {
            SignaturePicker().show(this.childFragmentManager, "czqm")
        }
        binding.ivDlyqm.setOnClickListener {
            SignaturePicker().show(this.childFragmentManager, "dlyqm")
        }
        binding.ivSfzzp.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        null
                    }
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            FILE_PROVIDER,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_SFZZP_CAPTURE)
                    }
                }
            }
        }
        binding.ivXszzp.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        null
                    }
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            FILE_PROVIDER,
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_XSZZP_CAPTURE)
                    }
                }
            }
        }

        binding.btnGetVehicleInfo.setOnClickListener {
            dataDictionaryViewModel.getDm(FL_HPZL, binding.spHpzl.selectedItem.toString())
                .observe(this) { hpzl ->
                    dataDictionaryViewModel.getDm(
                        FL_AJYWLB,
                        binding.spAjywlb.selectedItem.toString()
                    ).observe(this) { ajywlb ->
                        dataDictionaryViewModel.getDm(
                            FL_HJYWLB,
                            binding.spHjywlb.selectedItem.toString()
                        ).observe(this) { hjywlb ->
                            systemParamsViewModel.getJyjgbh("AJ").observe(this) { jyjgbh ->
                                registerViewModel.getVehicleInfo(
                                    "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
                                    hpzl,
                                    binding.etClsbdh.text.toString(),
                                    ajywlb,
                                    hjywlb,
                                    jyjgbh
                                ).observe(this) {
                                    bean = it
                                    binding.apply {
                                        etClsbdh.setText(bean?.Clsbdh)
                                        etCwkc.setText(bean?.Cwkc)
                                        etCwkk.setText(bean?.Cwkk)
                                        etCwkg.setText(bean?.Cwkg)
                                        etHxnbcd.setText(bean?.Hxnbcd)
                                        etHxnbkd.setText(bean?.Hxnbkd)
                                        etHxnbgd.setText(bean?.Hxnbgd)
                                        cbSfdlxj.isChecked =
                                            true.takeIf { bean?.Zxzxjxs == "1" } ?: false
                                        cbSfdzzc.isChecked =
                                            true.takeIf { bean?.Dzss == "1" } ?: false
                                        cbSfcyc.isChecked =
                                            true.takeIf { bean?.Cyc == "1" } ?: false
                                        cbSfmz.isChecked =
                                            true.takeIf { bean?.Sfmz == "1" } ?: false
                                        cbAjywlb.isChecked =
                                            false.takeIf { binding.spAjywlb.selectedItem.toString() == "-" }
                                                ?: true
                                        cbHjywlb.isChecked =
                                            false.takeIf { binding.spHjywlb.selectedItem.toString() == "-" }
                                                ?: true
                                        cbZjywlb.isChecked =
                                            false.takeIf { binding.spZjywlb.selectedItem.toString() == "-" }
                                                ?: true
                                        tvCcrq.text = bean?.Ccrq
                                        tvCcdjrq.text = bean?.Ccdjrq
                                        tvDjrq.text = bean?.Djrq
                                    }

                                    dataDictionaryViewModel.apply {
                                        bean?.let { bean022 ->
                                            getMc(
                                                FL_CLSYXZ,
                                                bean022.Syxz
                                            ).observe(this@RegisterFragment) { syzx ->
                                                binding.spSyxz.setText(syzx)
                                            }
                                            getMc(
                                                FL_HPZL,
                                                bean022.Hpzl
                                            ).observe(this@RegisterFragment) { hpzl ->
                                                binding.spHpzl.setText(hpzl)
                                            }
                                            getMc(
                                                FL_CLLX,
                                                bean022.Cllx
                                            ).observe(this@RegisterFragment) { cllx ->
                                                binding.spCllx.setText(cllx)
                                            }
                                            getMc(
                                                FL_CLYT,
                                                bean022.Clyt
                                            ).observe(this@RegisterFragment) { clyt ->
                                                binding.spClyt.setText(clyt)
                                            }
                                            getMc(
                                                FL_YTSX,
                                                bean022.Ytsx
                                            ).observe(this@RegisterFragment) { ytsx ->
                                                binding.spYtsx.setText(ytsx)
                                            }
                                            getMc(
                                                FL_GCJK,
                                                bean022.Gcjk
                                            ).observe(this@RegisterFragment) { gcjk ->
                                                binding.spGcjk.setText(gcjk)
                                            }
                                            if (bean022.Rlzl.length == 1) {
                                                getMc(
                                                    FL_RLZL,
                                                    it.Rlzl
                                                ).observe(this@RegisterFragment) { rlzl ->
                                                    binding.spRlzl1.setText(rlzl)
                                                }
                                                binding.spRlzl2.setText("-")
                                            } else if (bean022.Rlzl.length == 2) {
                                                getMc(
                                                    FL_RLZL,
                                                    bean022.Rlzl.substring(0, 1)
                                                ).observe(this@RegisterFragment) { rlzl ->
                                                    binding.spRlzl1.setText(rlzl)
                                                }
                                                getMc(
                                                    FL_RLZL,
                                                    bean022.Rlzl.substring(1, 2)
                                                ).observe(this@RegisterFragment) { rlzl ->
                                                    binding.spRlzl2.setText(rlzl)
                                                }
                                            }
                                            getMc(
                                                FL_CLSYXZ,
                                                bean022.Syxz
                                            ).observe(this@RegisterFragment) { syxz ->
                                                binding.spSyxz.setText(syxz)
                                            }
                                            when (bean022.Csys.length) {
                                                1 -> {
                                                    getMc(
                                                        FL_CSYS,
                                                        bean022.Csys
                                                    ).observe(this@RegisterFragment) { csys ->
                                                        binding.spCsys1.setText(csys)
                                                    }
                                                    binding.spCsys2.setText("-")
                                                    binding.spCsys3.setText("-")
                                                }
                                                2 -> {
                                                    getMc(
                                                        FL_CSYS,
                                                        bean022.Csys.substring(0, 1)
                                                    ).observe(this@RegisterFragment) { csys ->
                                                        binding.spCsys1.setText(csys)
                                                    }
                                                    getMc(
                                                        FL_CSYS,
                                                        bean022.Csys.substring(1, 2)
                                                    ).observe(this@RegisterFragment) { csys ->
                                                        binding.spCsys2.setText(csys)
                                                    }
                                                    binding.spCsys3.setText("-")
                                                }
                                                3 -> {
                                                    getMc(
                                                        FL_CSYS,
                                                        bean022.Csys.substring(0, 1)
                                                    ).observe(this@RegisterFragment) { csys ->
                                                        binding.spCsys1.setText(csys)
                                                    }
                                                    getMc(
                                                        FL_CSYS,
                                                        bean022.Csys.substring(1, 2)
                                                    ).observe(this@RegisterFragment) { csys ->
                                                        binding.spCsys2.setText(csys)
                                                    }
                                                    getMc(
                                                        FL_CSYS,
                                                        bean022.Csys.substring(2, 3)
                                                    ).observe(this@RegisterFragment) { csys ->
                                                        binding.spCsys3.setText(csys)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    val ztDm = bean?.Zt
                                    val ztDmList = ArrayList<String>()
                                    var ztMcString = ""
                                    if (ztDm != null) {

                                        for (index in ztDm.indices - 1) {
                                            val string = ztDm.substring(index, index + 1)
                                            ztDmList.add(string)
                                        }
                                        ztDmList.forEach {
                                            ztMcString += ","
                                            ztMcString += registerViewModel.ztDmMap[it]
                                        }
//                                        for (element in ztDmList){
//                                            ztMcString += ","
//                                            ztMcString += registerViewModel.ztDmMap[element]
//                                        }
                                        if (ztMcString.isNotEmpty()) {
                                            ztMcString = ztMcString.substring(1)
                                        }
                                    }


//
                                    val map = registerViewModel.textListMap
                                    map.get("行驶证编号")?.set(1, bean?.Xszbh ?: "")
                                    map.get("车辆品牌")?.set(1, bean?.Clpp1 ?: "")
                                    map.get("车辆型号")?.set(1, bean?.Clxh ?: "")
                                    map.get("发动机型号")?.set(1, bean?.Fdjxh ?: "")
                                    map.get("发动机号")?.set(1, bean?.Fdjh ?: "")
                                    map.get("所有人")?.set(1, bean?.Syr ?: "")
                                    map.get("联系电话")?.set(1, bean?.Lxdh ?: "")
                                    map.get("机动车状态")?.set(1, ztMcString)
                                    map.get("制造厂名称")?.set(1, bean?.Zzcmc ?: "")
                                    map.get("驱动轴数")?.set(1, bean?.Qdzs ?: "")
                                    map.get("驱动轴位")?.set(1, bean?.Qdzw ?: "")
                                    map.get("驻车轴数")?.set(1, bean?.Zczs ?: "")
                                    map.get("驻车轴位")?.set(1, bean?.Zczw ?: "")
                                    map.get("轴数")?.set(1, bean?.Zs ?: "")
                                    map.get("轴距")?.set(1, bean?.Zj ?: "")
                                    map.get("主轴数")?.set(1, bean?.Zzs ?: "")
                                    map.get("前轴数")?.set(1, bean?.Qzs ?: "")
                                    map.get("前轮距")?.set(1, bean?.Qlj ?: "")
                                    map.get("后轮距")?.set(1, bean?.Hlj ?: "")
                                    map.get("总质量")?.set(1, bean?.Zzl ?: "")
                                    map.get("整备质量")?.set(1, bean?.Zbzl ?: "")
                                    map.get("空气悬架轴位")?.set(1, bean?.Kqxjzw ?: "")
                                    map.get("转向轴数")?.set(1, bean?.Zxzs ?: "")
                                    map.get("并装轴位")?.set(1, bean?.Bzzw ?: "")
                                    map.get("核定载客")?.set(1, bean?.Hdzk ?: "")
                                    map.get("核定载质量")?.set(1, bean?.Hdzzl ?: "")
                                    map.get("准牵引质量")?.set(1, bean?.Zqyzl ?: "")
                                    map.get("最大设计车速")?.set(1, bean?.Zdsjcs ?: "")
                                    map.get("功率")?.set(1, bean?.Gl ?: "")
                                    map.get("排量")?.set(1, bean?.Pl ?: "")
                                    map.get("排气管数")?.set(1, bean?.Pqgs ?: "")
                                    map.get("里程表读数")?.set(1, bean?.Lcbds ?: "")
                                    map.get("额定转速")?.set(1, bean?.Edzs ?: "")
                                    map.get("轮胎规格")?.set(1, bean?.Ltgg ?: "")
                                    map.get("气缸数")?.set(1, bean?.Qgs ?: "")
                                    map.get("联系地址")?.set(1, bean?.Lxdz ?: "")
                                    map.get("送检人姓名")?.set(1, args.bean010.sjr)
                                    map.get("送检人电话")?.set(1, args.bean010.sjrdh)
                                    map.get("送检人身份证号")?.set(1, args.bean010.sjrsfzh)
                                    map.get("SCR（柴油）")?.set(1, bean?.SCR ?: "")
                                    map.get("DPF（柴油）")?.set(1, bean?.DPF ?: "")
                                    registerListMapAdapter.data = map

                                }
                            }
                        }
                    }
                }
        }



        binding.btnNewLsh.setOnClickListener {
            if (binding.tvCcrq.text.isNotBlank()) {
                val randomLsh = "${date2String(Date(), "yyyyMMddHHmmss")}${
                    String.format(
                        "%03d",
                        (1..999).random()
                    )
                }"
                binding.tvLsh.text = randomLsh
                val hdzkHolder = binding.rvTextList.findViewHolderForAdapterPosition(24)
                val zsHolder = binding.rvTextList.findViewHolderForAdapterPosition(13)
                val zzlHolder = binding.rvTextList.findViewHolderForAdapterPosition(19)
                val hdzkValue = hdzkHolder?.itemView?.findViewById<EditText>(R.id.value)
                val zsValue = zsHolder?.itemView?.findViewById<EditText>(R.id.value)
                val zzlValue = zzlHolder?.itemView?.findViewById<EditText>(R.id.value)
                for (index in 0 until registerAjJyxmAdapter.itemCount) {
                    val holder = binding.rvAjJyxm.findViewHolderForAdapterPosition(index)
                    registerViewModel.setAjJyxm(binding.spAjywlb.selectedItem.toString(),
                        binding.spCllx.selectedItem.toString(),
                        binding.spSyxz.selectedItem.toString(),
                        0.takeIf { hdzkValue?.text.isNullOrBlank() } ?: hdzkValue?.text.toString()
                            .toInt(),
                        binding.cbSfdzzc,
                        binding.cbSfdlxj,
                        holder?.itemView?.findViewById(R.id.cbJyxm),
                        0.takeIf { zsValue?.text.toString().isBlank() } ?: zsValue?.text.toString()
                            .toInt(),
                        binding.tvCcrq.text.toString()
                    )
                }
                for (index in 0 until registerHjJyxmAdapter.itemCount) {
                    val holder = binding.rvHjJyxm.findViewHolderForAdapterPosition(index)
                    registerViewModel.setHjJyxm(
                        binding.spHjywlb.selectedItem.toString(),
                        holder?.itemView?.findViewById(R.id.cbJyxm),
                        binding.tvCcrq.text.toString(),
                        zzlValue?.text.toString().toInt()
                    )
                }
            } else {
                Snackbar.make(it, "出厂日期不可为空", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btnRegister.setOnClickListener {
            systemParamsViewModel.getJyjgbh("AJ").observe(this) { jyjgbhAJ ->
                systemParamsViewModel.getJyjgbh("HJ").observe(this) { jyjgbhHJ ->
                    dataDictionaryViewModel.getDataDictionaryFromDb()
                        .observe(this) { dataDictionaryList ->
                            dataDictionaryViewModel.getDmList(
                                FL_JYXM,
                                registerViewModel.getAjJyxm(binding.rvAjJyxm, registerAjJyxmAdapter)
                            ).observe(this) { jyxmList ->

                                val valueMap = registerViewModel.getValueListMap(
                                    binding.rvTextList
                                )

                                for (element in dataDictionaryList) {
                                    if (element.Fl == FL_HPZL && element.Mc == binding.spHpzl.selectedItem.toString()) {
                                        valueMap["号牌种类"] = mutableListOf("hpzl", element.Dm)
                                    }
                                    if (element.Fl == FL_HPYS && element.Mc == binding.spHpys.selectedItem.toString()) {
                                        valueMap["号牌颜色"] = mutableListOf("hpys", element.Dm)
                                    }
                                    if (element.Fl == FL_ZXZFS && element.Mc == (binding.cbSfdlxj.text.toString()
                                            .takeIf { binding.cbSfdlxj.isChecked } ?: "非独立悬架")
                                    ) {
                                        valueMap["转向形式"] = mutableListOf("zxxs", element.Dm)
                                    }
                                    if (element.Fl == FL_AJYWLB && element.Mc == binding.spAjywlb.selectedItem.toString()) {
                                        valueMap["安检业务类别"] = mutableListOf("ajywlb", element.Dm)
                                    }
                                    if (element.Fl == FL_HJYWLB && element.Mc == binding.spHjywlb.selectedItem.toString()) {
                                        valueMap["环检业务类别"] = mutableListOf("hjywlb", element.Dm)
                                    }
                                    if (element.Fl == FL_ZJYWLB && element.Mc == binding.spZjywlb.selectedItem.toString()) {
                                        valueMap["综检业务类别"] = mutableListOf("zjywlb", element.Dm)
                                    }
                                    if (element.Fl == FL_CLLX && element.Mc == binding.spCllx.selectedItem.toString()) {
                                        valueMap["车辆类型"] = mutableListOf("cllx", element.Dm)
                                    }
                                    if (element.Fl == FL_CLYT && element.Mc == binding.spClyt.selectedItem.toString()) {
                                        valueMap["车辆用途"] = mutableListOf("clyt", element.Dm)
                                    }
                                    if (element.Fl == FL_YTSX && element.Mc == binding.spYtsx.selectedItem.toString()) {
                                        valueMap["用途属性"] = mutableListOf("ytsx", element.Dm)
                                    }
                                    if (element.Fl == FL_PZCX && element.Mc == binding.spWgcx.selectedItem.toString()) {
                                        valueMap["外观车型"] = mutableListOf("wgcx", element.Dm)
                                    }
                                    if (element.Fl == FL_GCJK && element.Mc == binding.spGcjk.selectedItem.toString()) {
                                        valueMap["国产进口"] = mutableListOf("gcjk", element.Dm)
                                    }
                                    if (element.Fl == FL_QDFS && element.Mc == binding.spQdxs.selectedItem.toString()) {
                                        valueMap["驱动形式"] = mutableListOf("qdxs", element.Dm)
                                    }
                                    if (element.Fl == FL_ZDLY && element.Mc == binding.spZdly.selectedItem.toString()) {
                                        valueMap["制动力源"] = mutableListOf("zdly", element.Dm)
                                    }
                                    if (element.Fl == FL_RLZL && element.Mc == binding.spRlzl1.selectedItem.toString()) {
                                        valueMap["燃料种类1"] = mutableListOf("rlzl1", element.Dm)
                                    }
                                    if (element.Fl == FL_RLZL && element.Mc == binding.spRlzl2.selectedItem.toString()) {
                                        valueMap["燃料种类2"] = mutableListOf("rlzl2", element.Dm)
                                    }
                                    if (element.Fl == FL_RYGG && element.Mc == binding.spRygg.selectedItem.toString()) {
                                        valueMap["燃油规格"] = mutableListOf("rygg", element.Dm)
                                    }
                                    if (element.Fl == FL_QZDZ && element.Mc == binding.spQzdz.selectedItem.toString()) {
                                        valueMap["前照灯制"] = mutableListOf("qzdz", element.Dm)
                                    }
                                    if (element.Fl == FL_HBXH && element.Mc == binding.spJcxh.selectedItem.toString()) {
                                        valueMap["检测线号"] = mutableListOf("jcxh", element.Dm)
                                    }
                                    if (element.Fl == FL_SSLB && element.Mc == binding.spSslb.selectedItem.toString()) {
                                        valueMap["所属类别"] = mutableListOf("sslb", element.Dm)
                                    }
                                    if (element.Fl == FL_GYFS && element.Mc == binding.spGyfs.selectedItem.toString()) {
                                        valueMap["供油方式"] = mutableListOf("gyfs", element.Dm)
                                    }
                                    if (element.Fl == FL_JQFS && element.Mc == binding.spJqfs.selectedItem.toString()) {
                                        valueMap["进气方式"] = mutableListOf("jqfs", element.Dm)
                                    }
                                    if (element.Fl == FL_BSX && element.Mc == binding.spBsxs.selectedItem.toString()) {
                                        valueMap["变速形式"] = mutableListOf("bsxs", element.Dm)
                                    }
                                    if (element.Fl == FL_DW && element.Mc == binding.spDws.selectedItem.toString()) {
                                        valueMap["档位数"] = mutableListOf("dws", element.Dm)
                                    }
                                    if (element.Fl == FL_CC && element.Mc == binding.spCcs.selectedItem.toString()) {
                                        valueMap["冲程数"] = mutableListOf("ccs", element.Dm)
                                    }
                                    if (element.Fl == FL_HCLZL && element.Mc == binding.spHclfs.selectedItem.toString()) {
                                        valueMap["后处理方式"] = mutableListOf("hclfs", element.Dm)
                                    }

                                    if (element.Fl == FL_CLSYXZ && element.Mc == binding.spSyxz.selectedItem.toString()) {
                                        valueMap["使用性质"] = mutableListOf("syxz", element.Dm)
                                    }
                                    if (element.Fl == FL_CSYS && element.Mc == binding.spCsys1.selectedItem.toString()) {
                                        valueMap["车身颜色1"] = mutableListOf("csys", element.Dm)
                                    }
                                    if (element.Fl == FL_CSYS && element.Mc == binding.spCsys2.selectedItem.toString()) {
                                        valueMap["车身颜色2"] = mutableListOf("csys2", element.Dm)
                                    }
                                    if (element.Fl == FL_CSYS && element.Mc == binding.spCsys3.selectedItem.toString()) {
                                        valueMap["车身颜色3"] = mutableListOf("csys3", element.Dm)
                                    }
                                    if (element.Fl == FL_AJCX && element.Mc == binding.spAjcx.selectedItem.toString()) {
                                        valueMap["安检车型"] = mutableListOf("ajcx", element.Dm)
                                    }
                                    if (element.Fl == FL_HBJCFS && element.Mc == binding.spHbjcfs.selectedItem.toString()) {
                                        valueMap["环保检测方式"] = mutableListOf("hbjcfs", element.Dm)
                                    }
                                }
                                valueMap["燃料种类"] = mutableListOf(
                                    "rlzl",
                                    "${valueMap["燃料种类1"]?.get(1)}${valueMap["燃料种类2"]?.get(1)}"
                                )
                                valueMap["车身颜色"] = mutableListOf(
                                    "csys",
                                    "${valueMap["车身颜色1"]?.get(1)}${valueMap["车身颜色2"]?.get(1)}${ valueMap["车身颜色3"]?.get(1)}"
                                )
                                var ajJyxmString = ""
                                jyxmList.forEach {
                                    ajJyxmString += ","
                                    ajJyxmString += it
                                }
//                                for (element in jyxmList) {
//                                    ajJyxmString += ","
//                                    ajJyxmString += element
//                                }
                                if (ajJyxmString.isNotEmpty()) {
                                    valueMap["安检检验项目"] =
                                        mutableListOf("ajJyxmString", ajJyxmString.substring(1))
                                }
                                var hjJyxmString = registerViewModel.getHjJyxmString(
                                    binding.rvHjJyxm,
                                    registerHjJyxmAdapter
                                )
                                if (hjJyxmString.isNotEmpty()) {
                                    valueMap["环检检验项目"] =
                                        mutableListOf("hjJyxmString", hjJyxmString.substring(1))
                                }

                                when (binding.ivCzqm.tag) {
                                    "1" -> {
                                        signatureViewModel.postSignature(
                                            getSignature(
                                                bitmapDrawable2Base64(binding.ivCzqm.drawable),
                                                "CZ",
                                                valueMap
                                            )
                                        ).observe(this) { postCzqmSuccess ->
                                            if (postCzqmSuccess) {
                                                when (binding.ivDlyqm.tag) {
                                                    "1" -> {
                                                        signatureViewModel.postSignature(
                                                            getSignature(
                                                                bitmapDrawable2Base64(binding.ivDlyqm.drawable),
                                                                "DLY",
                                                                valueMap
                                                            )
                                                        ).observe(this) { postDlyqmSuccess ->
                                                            if (postDlyqmSuccess) {
                                                                registerViewModel.postSaveVehicleInfo(
                                                                    getVehicleInfo(
                                                                        valueMap,
                                                                        bean,
                                                                        jyjgbhAJ
                                                                    )
                                                                )
                                                                    .observe(this) { postVehicleInfoSuccess ->
                                                                        if (postVehicleInfoSuccess) {
                                                                            if (binding.ivSfzzp.tag == "1") {
                                                                                inspectionItemViewModel.postInspectionPhotoW007(
                                                                                    getImage(
                                                                                        jyjgbhAJ,
                                                                                        jyjgbhHJ,
                                                                                        valueMap,
                                                                                        "F1",
                                                                                        "202",
                                                                                        "送检人身份证明",
                                                                                        "A202",
                                                                                        "100104"
                                                                                    )
                                                                                )
                                                                                    .observe(this) { postSfzSuccess ->
                                                                                        if (postSfzSuccess) {
                                                                                            if (binding.ivXszzp.tag == "1") {
                                                                                                inspectionItemViewModel.postInspectionPhotoW007(
                                                                                                    getImage(
                                                                                                        jyjgbhAJ,
                                                                                                        jyjgbhHJ,
                                                                                                        valueMap,
                                                                                                        "F1",
                                                                                                        "01",
                                                                                                        "机动车行驶证",
                                                                                                        "0201",
                                                                                                        "100101"
                                                                                                    )
                                                                                                )
                                                                                                    .observe(
                                                                                                        this
                                                                                                    ) { postXszSuccess ->
                                                                                                        if (postXszSuccess) {
                                                                                                            Toast.makeText(
                                                                                                                this.requireContext(),
                                                                                                                "登记成功",
                                                                                                                Toast.LENGTH_SHORT
                                                                                                            )
                                                                                                                .show()
                                                                                                            val action =
                                                                                                                RegisterFragmentDirections.actionRegisterFragmentPopIncludingAppointmentAjFragment()
                                                                                                            findNavController().navigate(
                                                                                                                action
                                                                                                            )
                                                                                                        }
                                                                                                    }
                                                                                            } else {
                                                                                                Toast.makeText(
                                                                                                    this.requireContext(),
                                                                                                    "登记成功",
                                                                                                    Toast.LENGTH_SHORT
                                                                                                )
                                                                                                    .show()
                                                                                                val action =
                                                                                                    RegisterFragmentDirections.actionRegisterFragmentPopIncludingAppointmentAjFragment()
                                                                                                findNavController().navigate(
                                                                                                    action
                                                                                                )
                                                                                            }
                                                                                        }
                                                                                    }

//
                                                                            } else {
                                                                                if (binding.ivXszzp.tag == "1") {
                                                                                    inspectionItemViewModel.postInspectionPhotoW007(
                                                                                        getImage(
                                                                                            jyjgbhAJ,
                                                                                            jyjgbhHJ,
                                                                                            valueMap,
                                                                                            "F1",
                                                                                            "01",
                                                                                            "机动车行驶证",
                                                                                            "0201",
                                                                                            "100101"
                                                                                        )
                                                                                    ).observe(
                                                                                        this
                                                                                    ) { postXszSuccess ->
                                                                                        if (postXszSuccess) {
                                                                                            Toast.makeText(
                                                                                                this.requireContext(),
                                                                                                "登记成功",
                                                                                                Toast.LENGTH_SHORT
                                                                                            ).show()
                                                                                            val action =
                                                                                                RegisterFragmentDirections.actionRegisterFragmentPopIncludingAppointmentAjFragment()
                                                                                            findNavController().navigate(
                                                                                                action
                                                                                            )
                                                                                        }
                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(
                                                                                        this.requireContext(),
                                                                                        "登记成功",
                                                                                        Toast.LENGTH_SHORT
                                                                                    ).show()
                                                                                    val action =
                                                                                        RegisterFragmentDirections.actionRegisterFragmentPopIncludingAppointmentAjFragment()
                                                                                    findNavController().navigate(
                                                                                        action
                                                                                    )
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                            }
                                                        }
                                                    }
                                                    else -> Snackbar.make(
                                                        this.requireView(),
                                                        "登录员签名不能为空",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                    else -> Snackbar.make(
                                        this.requireView(),
                                        "车主签名不能为空",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }
            }
        }
    }

    private fun getSignature(
        base64: String,
        Jcxm: String,
        valueMap: LinkedHashMap<String, MutableList<String>>
    ): SaveSignatureW006Request {
        return SaveSignatureW006Request(
            0,
            "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
            base64,
            date2String(Date(), "yyyy-MM-dd HH:mm:ss"),
            Jcxm,
            valueMap["送检人姓名"]?.get(1) ?: "",
            valueMap["安检业务类别"]?.get(1) ?: "",
            valueMap["环检业务类别"]?.get(1) ?: "",
            "${binding.etLshSzm.text.toString()}${binding.tvLsh.text}".takeIf { binding.cbAjywlb.isChecked }
                ?: "",
            "${binding.etLshSzm.text.toString()}${binding.tvLsh.text}".takeIf { binding.cbHjywlb.isChecked }
                ?: "",
            1,
            1
        )
    }

    private fun getVehicleInfo(
        valueMap: Map<String, MutableList<String>>,
        bean022: VehicleAllInfoR022Response?,
        jyjgbhAj: String
    ): SaveVehicleInfoW003Request {
        val saveVehicleInfoW003Request = SaveVehicleInfoW003Request(
            0,
            "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
            valueMap["号牌种类"]?.get(1) ?: "",
            valueMap["号牌颜色"]?.get(1) ?: "",
            binding.etClsbdh.text.toString(),
            binding.etCwkc.text.toString(),
            binding.etCwkk.text.toString(),
            binding.etCwkg.text.toString(),
            binding.etHxnbcd.text.toString(),
            binding.etHxnbkd.text.toString(),
            binding.etHxnbgd.text.toString(),
            valueMap["转向形式"]?.get(1) ?: "",
            "1".takeIf { binding.cbSfdzzc.isChecked } ?: "0",
            "1".takeIf { binding.cbSfcyc.isChecked } ?: "0",
            "1".takeIf { binding.cbSfmz.isChecked } ?: "0",
            valueMap["安检业务类别"]?.get(1) ?: "",
            valueMap["环检业务类别"]?.get(1) ?: "",
            valueMap["综检业务类别"]?.get(1) ?: "",
            valueMap["行驶证编号"]?.get(1) ?: "",
            valueMap["车辆品牌"]?.get(1) ?: "",
            valueMap["车辆型号"]?.get(1) ?: "",
            valueMap["发动机型号"]?.get(1) ?: "",
            valueMap["发动机号"]?.get(1) ?: "",
            valueMap["所有人"]?.get(1) ?: "",
            valueMap["联系电话"]?.get(1) ?: "",
            valueMap["机动车状态"]?.get(1) ?: "",
            valueMap["制造厂名称"]?.get(1) ?: "",
            valueMap["驱动轴数"]?.get(1) ?: "",
            valueMap["驱动轴位"]?.get(1) ?: "",
            valueMap["驻车轴数"]?.get(1) ?: "",
            valueMap["驻车轴位"]?.get(1) ?: "",
            valueMap["轴数"]?.get(1) ?: "",
            valueMap["轴距"]?.get(1) ?: "",
            valueMap["主轴数"]?.get(1) ?: "",
            valueMap["前轴数"]?.get(1) ?: "",
            valueMap["前轮距"]?.get(1) ?: "",
            valueMap["后轮距"]?.get(1) ?: "",
            valueMap["总质量"]?.get(1) ?: "",
            valueMap["整备质量"]?.get(1) ?: "",
            valueMap["空气悬架轴位"]?.get(1) ?: "",
            valueMap["转向轴数"]?.get(1) ?: "",
            valueMap["并装轴位"]?.get(1) ?: "",
            valueMap["核定载客"]?.get(1) ?: "",
            valueMap["核定载质量"]?.get(1) ?: "",
            valueMap["准牵引质量"]?.get(1) ?: "",
            valueMap["最大设计车速"]?.get(1) ?: "",
            valueMap["功率"]?.get(1) ?: "",
            valueMap["排量"]?.get(1) ?: "",
            valueMap["排气管数"]?.get(1) ?: "",
            valueMap["里程表读数"]?.get(1) ?: "",
            valueMap["额定转速"]?.get(1) ?: "",
            valueMap["轮胎规格"]?.get(1) ?: "",
            valueMap["气缸数"]?.get(1) ?: "",
            valueMap["联系地址"]?.get(1) ?: "",
            valueMap["送检人姓名"]?.get(1) ?: "",
            valueMap["送检人电话"]?.get(1) ?: "",
            valueMap["送检人身份证号"]?.get(1) ?: "",
            valueMap["SCR（柴油）"]?.get(1) ?: "",
            valueMap["DPF（柴油）"]?.get(1) ?: "",
            valueMap["车辆类型"]?.get(1) ?: "",
            valueMap["车辆用途"]?.get(1) ?: "",
            valueMap["用途属性"]?.get(1) ?: "",
            valueMap["外观车型"]?.get(1) ?: "",
            valueMap["国产进口"]?.get(1) ?: "",
            valueMap["驱动形式"]?.get(1) ?: "",
            valueMap["制动力源"]?.get(1) ?: "",
            valueMap["燃料种类"]?.get(1) ?: "",
            valueMap["燃油规格"]?.get(1) ?: "",
            valueMap["前照灯制"]?.get(1) ?: "",
            valueMap["检测线号"]?.get(1) ?: "",
            valueMap["所属类别"]?.get(1) ?: "",
            valueMap["供油方式"]?.get(1) ?: "",
            valueMap["进气方式"]?.get(1) ?: "",
            valueMap["变速形式"]?.get(1) ?: "",
            valueMap["档位数"]?.get(1) ?: "",
            valueMap["冲程数"]?.get(1) ?: "",
            valueMap["后处理方式"]?.get(1) ?: "",
            valueMap["使用性质"]?.get(1) ?: "",
            binding.tvCcrq.text.toString(),
            binding.tvCcdjrq.text.toString(),
            binding.tvDjrq.text.toString(),
            bean022?.Clpp2,
            bean022?.Zzg ?: "",
            valueMap["车身颜色"]?.get(1)?: "",
            bean022?.Sfzmhm ?: "",
            bean022?.Sfzmmc ?: "",
            bean022?.Yxqz ?: "",
            bean022?.Qzbfqz ?: "",
            bean022?.Fzjg ?: "",
            bean022?.Glbm ?: "",
            bean022?.Bxzzrq ?: "",
            bean022?.Dybj ?: "",
            bean022?.Gbthps ?: "",
            bean022?.Lts ?: "",
            bean022?.Qpzk ?: "",
            bean022?.Hpzk ?: "",
            bean022?.Jyhgbzbh ?: "",
            binding.tvXzqhdm.text.toString(),
            bean022?.Zsxzqh ?: "",
            bean022?.Zzxzqh ?: "",
            bean022?.Sfmj ?: "",
            valueMap["综检业务类别"]?.get(1) ?: "",
            bean022?.Hjxm ?: "",
            valueMap["安检车型"]?.get(1) ?: "",
            bean022?.Zjcx ?: "",
            bean022?.Hbdbqk ?: "",
            date2String(Date(), "yyyy-MM-dd"),
            date2String(Date(), "HH:mm:ss"),
            bean022?.Ygdltz ?: "",
            bean022?.Zxzxjxs ?: "",
            bean022?.Zjdw ?: "",
            bean022?.Pfjd ?: "",
            bean022?.Hjdlsj ?: "",
            "${binding.etLshSzm.text.toString()}${binding.tvLsh.text}".takeIf { binding.cbAjywlb.isChecked }
                ?: "",
            "${binding.etLshSzm.text.toString()}${binding.tvLsh.text}".takeIf { binding.cbHjywlb.isChecked }
                ?: "",
            jyjgbhAj,
            "",
            valueMap["安检检验项目"]?.get(1) ?: "",
            valueMap["环检检验项目"]?.get(1) ?: "",
            valueMap["环保检测方式"]?.get(1) ?: "",
            "",
            "",
            date2String(Date(), "yyyyMMdd HH:mm:ss"),
            1,
            1,
            bean001.TrueName,
            bean001.ID,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            bean022?.Jcxlb ?: "",
            bean022?.Yyzh ?: "",
        )
        return saveVehicleInfoW003Request
    }

    private fun getImage(
        jyjgbhAj: String,
        jyjgbhHj: String,
        valueMap: Map<String, MutableList<String>>,
        Jyxm: String,
        Zpdm: String,
        Zpmc: String,
        Zpajdm: String,
        Zphjdm: String
    ): InspectionPhotoW007Request {
        val inspectionPhotoW007Request = InspectionPhotoW007Request(
            0,
            jyjgbhAj,
            jyjgbhHj,
            "1",
            "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
            valueMap["号牌种类"]?.get(1) ?: "",
            binding.etClsbdh.text.toString(),
            bitmap2Base64(getBitmapFromDrawable(binding.ivSfzzp.drawable)),
            date2String(Date(), "yyyyMMddHHmmss"),
            Jyxm,
            Zpdm,
            Zpmc,
            Zpajdm,
            Zphjdm,
            "1", "1",
            date2String(Date(), "yyyyMMddHHmmss"),
            "${binding.etLshSzm.text.toString()}${binding.tvLsh.text}".takeIf { binding.cbAjywlb.isChecked }
                ?: "",
            "${binding.etLshSzm.text.toString()}${binding.tvLsh.text}".takeIf { binding.cbHjywlb.isChecked }
                ?: "",
            1,
            1
        )
        return inspectionPhotoW007Request
    }

    private fun initView() {
        binding.nestedScrollView2.isNestedScrollingEnabled = true
        dataDictionaryViewModel.apply {
            getMcListFromFl(FL_CSJC).observe(this@RegisterFragment) { hphm ->
                binding.spHphm.adapter =
                    ArrayAdapter(
                        this@RegisterFragment.requireContext(),
                        R.layout.textview_spinner,
                        hphm
                    )
                getMcListFromFl(FL_HPZL).observe(this@RegisterFragment) { hpzl ->
                    binding.spHpzl.adapter =
                        ArrayAdapter(
                            this@RegisterFragment.requireContext(),
                            R.layout.textview_spinner,
                            hpzl
                        )
                    if (args.bean010.hphm.isNotBlank()) {
                        binding.spHphm.setText(args.bean010.hphm.substring(0, 1))
                        binding.etHphm.setText(args.bean010.hphm.substring(1))
                        dataDictionaryViewModel.getMc(FL_HPZL, args.bean010.hpzl)
                            .observe(this@RegisterFragment) {
                                binding.spHpzl.setText(it)
                            }
                        binding.etClsbdh.setText(args.bean010.clsbdh)
                    }
                }
            }

            getMcListFromFl(FL_HPYS).observe(this@RegisterFragment) {
                binding.spHpys.adapter =
                    ArrayAdapter(
                        this@RegisterFragment.requireContext(),
                        R.layout.textview_spinner,
                        it
                    )
                binding.spHpys.setText("蓝色")
            }
            getMcListFromFl(FL_AJYWLB).observe(this@RegisterFragment) {
                binding.spAjywlb.adapter =
                    ArrayAdapter(
                        this@RegisterFragment.requireContext(),
                        R.layout.textview_spinner,
                        it
                    )
                binding.cbAjywlb.isChecked = true
                binding.spAjywlb.setText("-")
            }
            getMcListFromFl(FL_HJYWLB).observe(this@RegisterFragment) {
                binding.spHjywlb.adapter =
                    ArrayAdapter(
                        this@RegisterFragment.requireContext(),
                        R.layout.textview_spinner,
                        it
                    )
                binding.cbHjywlb.isChecked = true
                binding.spHjywlb.setText("-")
            }
            getMcListFromFl(FL_ZJYWLB).observe(this@RegisterFragment) {
                binding.spZjywlb.adapter =
                    ArrayAdapter(
                        this@RegisterFragment.requireContext(),
                        R.layout.textview_spinner,
                        it
                    )
                binding.spZjywlb.setText("-")
            }
            getMcListFromFl(FL_CLLX).observe(this@RegisterFragment) {
                binding.spCllx.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_CLYT).observe(this@RegisterFragment) {
                binding.spClyt.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_YTSX).observe(this@RegisterFragment) {
                binding.spYtsx.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_PZCX).observe(this@RegisterFragment) {
                binding.spWgcx.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_GCJK).observe(this@RegisterFragment) {
                binding.spGcjk.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_QDFS).observe(this@RegisterFragment) {
                binding.spQdxs.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_ZDLY).observe(this@RegisterFragment) {
                binding.spZdly.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_RYGG).observe(this@RegisterFragment) {
                binding.spRygg.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_RLZL).observe(this@RegisterFragment) {
                binding.spRlzl1.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
                binding.spRlzl2.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
                binding.spRlzl2.setSelection(it.lastIndex)
            }
            getMcListFromFl(FL_QZDZ).observe(this@RegisterFragment) {
                binding.spQzdz.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_HBXH).observe(this@RegisterFragment) {
                binding.spJcxh.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_SSLB).observe(this@RegisterFragment) {
                binding.spSslb.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_GYFS).observe(this@RegisterFragment) {
                binding.spGyfs.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_JQFS).observe(this@RegisterFragment) {
                binding.spJqfs.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_BSX).observe(this@RegisterFragment) {
                binding.spBsxs.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_DW).observe(this@RegisterFragment) {
                binding.spDws.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_CC).observe(this@RegisterFragment) {
                binding.spCcs.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_HCLZL).observe(this@RegisterFragment) {
                binding.spHclfs.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_HYYT).observe(this@RegisterFragment) {
                binding.spHyyt.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_CLSYXZ).observe(this@RegisterFragment) {
                binding.spSyxz.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_CSYS).observe(this@RegisterFragment) {
                binding.spCsys1.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
                binding.spCsys2.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
                binding.spCsys3.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_AJCX).observe(this@RegisterFragment) {
                binding.spAjcx.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
            getMcListFromFl(FL_HBJCFS).observe(this@RegisterFragment) {
                binding.spHbjcfs.adapter = ArrayAdapter(
                    this@RegisterFragment.requireContext(),
                    R.layout.textview_spinner,
                    it
                )
            }
        }
        systemParamsViewModel.getLshSzm().observe(this) {
            binding.etLshSzm.setText(it)
        }


    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SFZZP_CAPTURE && resultCode == Activity.RESULT_OK) {

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true


                // Determine how much to scale down the image
                inJustDecodeBounds = false
                inSampleSize = 4
                inPurgeable = true
            }
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                binding.ivSfzzp.setImageBitmap(bitmap)
                binding.ivSfzzp.tag = "1"
            }

        } else if (requestCode == REQUEST_XSZZP_CAPTURE && resultCode == Activity.RESULT_OK) {

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true


                // Determine how much to scale down the image
                inJustDecodeBounds = false
                inSampleSize = 4
                inPurgeable = true
            }
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                binding.ivXszzp.setImageBitmap(bitmap)
                binding.ivXszzp.tag = "1"
            }
        }

    }


}