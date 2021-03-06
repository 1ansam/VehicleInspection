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
                    "????????????" -> binding.spHpys.setText("??????")
                    "????????????" -> binding.spHpys.setText("??????")
                    "??????" -> binding.spHpys.setText("??????")
                    "????????????" -> binding.spHpys.setText("??????")
                    "????????????" -> binding.spHpys.setText("??????")
                    "????????????" -> binding.spHpys.setText("??????")
                    "????????????" -> binding.spHpys.setText("??????")
                    "?????????????????????" -> binding.spHpys.setText("??????")
                    "?????????????????????" -> binding.spHpys.setText("??????")
                    "???????????????" -> binding.spHpys.setText("??????")
                    "???????????????" -> binding.spHpys.setText("??????")
                    "???????????????" -> binding.spHpys.setText("??????")
                    "???????????????" -> binding.spHpys.setText("??????")
                    "???????????????" -> binding.spHpys.setText("??????")
                    "???????????????" -> binding.spHpys.setText("??????")
                    "???????????????" -> binding.spHpys.setText("??????")

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
                                    map.get("???????????????")?.set(1, bean?.Xszbh ?: "")
                                    map.get("????????????")?.set(1, bean?.Clpp1 ?: "")
                                    map.get("????????????")?.set(1, bean?.Clxh ?: "")
                                    map.get("???????????????")?.set(1, bean?.Fdjxh ?: "")
                                    map.get("????????????")?.set(1, bean?.Fdjh ?: "")
                                    map.get("?????????")?.set(1, bean?.Syr ?: "")
                                    map.get("????????????")?.set(1, bean?.Lxdh ?: "")
                                    map.get("???????????????")?.set(1, ztMcString)
                                    map.get("???????????????")?.set(1, bean?.Zzcmc ?: "")
                                    map.get("????????????")?.set(1, bean?.Qdzs ?: "")
                                    map.get("????????????")?.set(1, bean?.Qdzw ?: "")
                                    map.get("????????????")?.set(1, bean?.Zczs ?: "")
                                    map.get("????????????")?.set(1, bean?.Zczw ?: "")
                                    map.get("??????")?.set(1, bean?.Zs ?: "")
                                    map.get("??????")?.set(1, bean?.Zj ?: "")
                                    map.get("?????????")?.set(1, bean?.Zzs ?: "")
                                    map.get("?????????")?.set(1, bean?.Qzs ?: "")
                                    map.get("?????????")?.set(1, bean?.Qlj ?: "")
                                    map.get("?????????")?.set(1, bean?.Hlj ?: "")
                                    map.get("?????????")?.set(1, bean?.Zzl ?: "")
                                    map.get("????????????")?.set(1, bean?.Zbzl ?: "")
                                    map.get("??????????????????")?.set(1, bean?.Kqxjzw ?: "")
                                    map.get("????????????")?.set(1, bean?.Zxzs ?: "")
                                    map.get("????????????")?.set(1, bean?.Bzzw ?: "")
                                    map.get("????????????")?.set(1, bean?.Hdzk ?: "")
                                    map.get("???????????????")?.set(1, bean?.Hdzzl ?: "")
                                    map.get("???????????????")?.set(1, bean?.Zqyzl ?: "")
                                    map.get("??????????????????")?.set(1, bean?.Zdsjcs ?: "")
                                    map.get("??????")?.set(1, bean?.Gl ?: "")
                                    map.get("??????")?.set(1, bean?.Pl ?: "")
                                    map.get("????????????")?.set(1, bean?.Pqgs ?: "")
                                    map.get("???????????????")?.set(1, bean?.Lcbds ?: "")
                                    map.get("????????????")?.set(1, bean?.Edzs ?: "")
                                    map.get("????????????")?.set(1, bean?.Ltgg ?: "")
                                    map.get("?????????")?.set(1, bean?.Qgs ?: "")
                                    map.get("????????????")?.set(1, bean?.Lxdz ?: "")
                                    map.get("???????????????")?.set(1, args.bean010.sjr)
                                    map.get("???????????????")?.set(1, args.bean010.sjrdh)
                                    map.get("?????????????????????")?.set(1, args.bean010.sjrsfzh)
                                    map.get("SCR????????????")?.set(1, bean?.SCR ?: "")
                                    map.get("DPF????????????")?.set(1, bean?.DPF ?: "")
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
                Snackbar.make(it, "????????????????????????", Snackbar.LENGTH_SHORT)
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
                                registerViewModel.getAjJyxm(binding.rvAjJyxm)
                            ).observe(this) { jyxmList ->

                                val valueMap = registerViewModel.getValueListMap(
                                    binding.rvTextList
                                )

                                for (element in dataDictionaryList) {
                                    if (element.Fl == FL_HPZL && element.Mc == binding.spHpzl.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("hpzl", element.Dm)
                                    }
                                    if (element.Fl == FL_HPYS && element.Mc == binding.spHpys.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("hpys", element.Dm)
                                    }
                                    if (element.Fl == FL_ZXZFS && element.Mc == (binding.cbSfdlxj.text.toString()
                                            .takeIf { binding.cbSfdlxj.isChecked } ?: "???????????????")
                                    ) {
                                        valueMap["????????????"] = mutableListOf("zxxs", element.Dm)
                                    }
                                    if (element.Fl == FL_AJYWLB && element.Mc == binding.spAjywlb.selectedItem.toString()) {
                                        valueMap["??????????????????"] = mutableListOf("ajywlb", element.Dm)
                                    }
                                    if (element.Fl == FL_HJYWLB && element.Mc == binding.spHjywlb.selectedItem.toString()) {
                                        valueMap["??????????????????"] = mutableListOf("hjywlb", element.Dm)
                                    }
                                    if (element.Fl == FL_ZJYWLB && element.Mc == binding.spZjywlb.selectedItem.toString()) {
                                        valueMap["??????????????????"] = mutableListOf("zjywlb", element.Dm)
                                    }
                                    if (element.Fl == FL_CLLX && element.Mc == binding.spCllx.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("cllx", element.Dm)
                                    }
                                    if (element.Fl == FL_CLYT && element.Mc == binding.spClyt.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("clyt", element.Dm)
                                    }
                                    if (element.Fl == FL_YTSX && element.Mc == binding.spYtsx.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("ytsx", element.Dm)
                                    }
                                    if (element.Fl == FL_PZCX && element.Mc == binding.spWgcx.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("wgcx", element.Dm)
                                    }
                                    if (element.Fl == FL_GCJK && element.Mc == binding.spGcjk.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("gcjk", element.Dm)
                                    }
                                    if (element.Fl == FL_QDFS && element.Mc == binding.spQdxs.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("qdxs", element.Dm)
                                    }
                                    if (element.Fl == FL_ZDLY && element.Mc == binding.spZdly.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("zdly", element.Dm)
                                    }
                                    if (element.Fl == FL_RLZL && element.Mc == binding.spRlzl1.selectedItem.toString()) {
                                        valueMap["????????????1"] = mutableListOf("rlzl1", element.Dm)
                                    }
                                    if (element.Fl == FL_RLZL && element.Mc == binding.spRlzl2.selectedItem.toString()) {
                                        valueMap["????????????2"] = mutableListOf("rlzl2", element.Dm)
                                    }
                                    if (element.Fl == FL_RYGG && element.Mc == binding.spRygg.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("rygg", element.Dm)
                                    }
                                    if (element.Fl == FL_QZDZ && element.Mc == binding.spQzdz.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("qzdz", element.Dm)
                                    }
                                    if (element.Fl == FL_HBXH && element.Mc == binding.spJcxh.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("jcxh", element.Dm)
                                    }
                                    if (element.Fl == FL_SSLB && element.Mc == binding.spSslb.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("sslb", element.Dm)
                                    }
                                    if (element.Fl == FL_GYFS && element.Mc == binding.spGyfs.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("gyfs", element.Dm)
                                    }
                                    if (element.Fl == FL_JQFS && element.Mc == binding.spJqfs.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("jqfs", element.Dm)
                                    }
                                    if (element.Fl == FL_BSX && element.Mc == binding.spBsxs.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("bsxs", element.Dm)
                                    }
                                    if (element.Fl == FL_DW && element.Mc == binding.spDws.selectedItem.toString()) {
                                        valueMap["?????????"] = mutableListOf("dws", element.Dm)
                                    }
                                    if (element.Fl == FL_CC && element.Mc == binding.spCcs.selectedItem.toString()) {
                                        valueMap["?????????"] = mutableListOf("ccs", element.Dm)
                                    }
                                    if (element.Fl == FL_HCLZL && element.Mc == binding.spHclfs.selectedItem.toString()) {
                                        valueMap["???????????????"] = mutableListOf("hclfs", element.Dm)
                                    }

                                    if (element.Fl == FL_CLSYXZ && element.Mc == binding.spSyxz.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("syxz", element.Dm)
                                    }
                                    if (element.Fl == FL_CSYS && element.Mc == binding.spCsys1.selectedItem.toString()) {
                                        valueMap["????????????1"] = mutableListOf("csys", element.Dm)
                                    }
                                    if (element.Fl == FL_CSYS && element.Mc == binding.spCsys2.selectedItem.toString()) {
                                        valueMap["????????????2"] = mutableListOf("csys2", element.Dm)
                                    }
                                    if (element.Fl == FL_CSYS && element.Mc == binding.spCsys3.selectedItem.toString()) {
                                        valueMap["????????????3"] = mutableListOf("csys3", element.Dm)
                                    }
                                    if (element.Fl == FL_AJCX && element.Mc == binding.spAjcx.selectedItem.toString()) {
                                        valueMap["????????????"] = mutableListOf("ajcx", element.Dm)
                                    }
                                    if (element.Fl == FL_HBJCFS && element.Mc == binding.spHbjcfs.selectedItem.toString()) {
                                        valueMap["??????????????????"] = mutableListOf("hbjcfs", element.Dm)
                                    }
                                }
                                valueMap["????????????"] = mutableListOf(
                                    "rlzl",
                                    "${valueMap["????????????1"]?.get(1)}${valueMap["????????????2"]?.get(1)}"
                                )
                                valueMap["????????????"] = mutableListOf(
                                    "csys",
                                    "${valueMap["????????????1"]?.get(1)}${valueMap["????????????2"]?.get(1)}${ valueMap["????????????3"]?.get(1)}"
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
                                    valueMap["??????????????????"] =
                                        mutableListOf("ajJyxmString", ajJyxmString.substring(1))
                                }
                                var hjJyxmString = registerViewModel.getHjJyxmString(
                                    binding.rvHjJyxm
                                )
                                if (hjJyxmString.isNotEmpty()) {
                                    valueMap["??????????????????"] =
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
                                                                                        "?????????????????????",
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
                                                                                                        "??????????????????",
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
                                                                                                                "????????????",
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
                                                                                                    "????????????",
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
                                                                                            "??????????????????",
                                                                                            "0201",
                                                                                            "100101"
                                                                                        )
                                                                                    ).observe(
                                                                                        this
                                                                                    ) { postXszSuccess ->
                                                                                        if (postXszSuccess) {
                                                                                            Toast.makeText(
                                                                                                this.requireContext(),
                                                                                                "????????????",
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
                                                                                        "????????????",
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
                                                        "???????????????????????????",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    }
                                    else -> Snackbar.make(
                                        this.requireView(),
                                        "????????????????????????",
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
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
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
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            binding.etClsbdh.text.toString(),
            binding.etCwkc.text.toString(),
            binding.etCwkk.text.toString(),
            binding.etCwkg.text.toString(),
            binding.etHxnbcd.text.toString(),
            binding.etHxnbkd.text.toString(),
            binding.etHxnbgd.text.toString(),
            valueMap["????????????"]?.get(1) ?: "",
            "1".takeIf { binding.cbSfdzzc.isChecked } ?: "0",
            "1".takeIf { binding.cbSfcyc.isChecked } ?: "0",
            "1".takeIf { binding.cbSfmz.isChecked } ?: "0",
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["??????"]?.get(1) ?: "",
            valueMap["??????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["??????"]?.get(1) ?: "",
            valueMap["??????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["?????????????????????"]?.get(1) ?: "",
            valueMap["SCR????????????"]?.get(1) ?: "",
            valueMap["DPF????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["?????????"]?.get(1) ?: "",
            valueMap["???????????????"]?.get(1) ?: "",
            valueMap["????????????"]?.get(1) ?: "",
            binding.tvCcrq.text.toString(),
            binding.tvCcdjrq.text.toString(),
            binding.tvDjrq.text.toString(),
            bean022?.Clpp2,
            bean022?.Zzg ?: "",
            valueMap["????????????"]?.get(1)?: "",
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
            valueMap["??????????????????"]?.get(1) ?: "",
            bean022?.Hjxm ?: "",
            valueMap["????????????"]?.get(1) ?: "",
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
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
            valueMap["??????????????????"]?.get(1) ?: "",
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
            valueMap["????????????"]?.get(1) ?: "",
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
                binding.spHpys.setText("??????")
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