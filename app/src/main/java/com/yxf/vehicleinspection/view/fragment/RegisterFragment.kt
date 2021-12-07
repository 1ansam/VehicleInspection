package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.*
import androidx.compose.material.CheckboxColors
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.SaveVehicleInfoW003Request
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR022Response
import com.yxf.vehicleinspection.databinding.FragmentRegisterBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.DatePickerFragment
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.RegisterJyxmAdapter
import com.yxf.vehicleinspection.view.adapter.RegisterListAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.util.*
import kotlin.collections.ArrayList

class RegisterFragment : BaseBindingFragment<FragmentRegisterBinding>() {
    lateinit var bean001 : UserInfoR001Response
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
    private val registerJyxmAdapter = RegisterJyxmAdapter()

    private var bean : VehicleAllInfoR022Response? = null

    override fun init() {
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        initView()
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding.rvLeftList.layoutManager = GridLayoutManager(this.requireContext(), 2)
        val textAdapter = RegisterListAdapter()
        textAdapter.data = registerViewModel.textMap.values.toList()
        binding.rvLeftList.adapter = textAdapter
        binding.rvLeftList.setHasFixedSize(true)
        binding.rvJyxm.layoutManager = GridLayoutManager(this.requireContext(),4)
        binding.rvJyxm.adapter = registerJyxmAdapter
        dataDictionaryViewModel.getMcListFromFl(FL_JYXM).observe(this){
            registerJyxmAdapter.data = it
        }
        binding.spHpzl.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(binding.spHpzl.selectedItem.toString()){
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
            DatePickerFragment().show(this.childFragmentManager,"ccrq")
        }
        binding.tvDjrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager,"djrq")
        }
        binding.tvCcdjrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager,"ccdjrq")
        }

        binding.btnGetVehicleInfo.setOnClickListener {
            dataDictionaryViewModel.getDm(FL_HPZL, binding.spHpzl.selectedItem.toString())
                .observe(this) { hpzl ->
                    dataDictionaryViewModel.getDm(FL_AJYWLB,binding.spAjywlb.selectedItem.toString()).observe(this){
                        ajywlb ->
                        dataDictionaryViewModel.getDm(FL_HJYWLB,binding.spHjywlb.selectedItem.toString()).observe(this){
                            hjywkb ->
                            systemParamsViewModel.getJyjgbh("AJ").observe(this){
                                jyjgbh ->
                                registerViewModel.getVehicleInfo(
                                    "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
                                    hpzl,
                                    binding.etClsbdh.text.toString(),
                                    ajywlb,
                                    hjywkb,
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
                                        cbSfdlxj.isChecked = true.takeIf { bean?.Zxzxjxs == "1" } ?: false
                                        cbSfdzzc.isChecked = true.takeIf { bean?.Dzss == "1" } ?: false
                                        cbSfcyc.isChecked = true.takeIf { bean?.Cyc == "1" } ?: false
                                        cbSfmz.isChecked = true.takeIf { bean?.Sfmz == "1" } ?: false
                                        cbAjywlb.isChecked = false.takeIf { binding.spAjywlb.selectedItem.toString() == "-"} ?: true
                                        cbHjywlb.isChecked = false.takeIf { binding.spHjywlb.selectedItem.toString() == "-" } ?: true
                                        cbZjywlb.isChecked = false.takeIf { binding.spZjywlb.selectedItem.toString() == "-" } ?: true
                                        tvCcrq.text = bean?.Ccrq
                                        tvCcdjrq.text = bean?.Ccdjrq
                                        tvDjrq.text = bean?.Djrq
                                    }

                                    dataDictionaryViewModel.apply {
                                        bean?.let {
                                            getMc(FL_CLSYXZ,it.Syxz).observe(this@RegisterFragment){
                                                binding.spSyxz.setText(it)
                                            }
                                            getMc(FL_HPZL,it.Hpzl).observe(this@RegisterFragment){
                                                binding.spHpzl.setText(it)
                                            }
                                        }


//                                        getMc(FL_HPYS, bean.Hpys).observe(this@RegisterFragment) {
//                                            binding.spHpys.setText(it)
//                                        }
//                                        getMc(FL_ZJYWLB, bean.Zjywlb).observe(this@RegisterFragment) {
//                                            binding.spZjywlb.setText(it)
//                                        }

                                    }

                                    val textValueList = arrayListOf(
                                        bean?.Xszbh, bean?.Clpp1,
                                        bean?.Clxh, bean?.Fdjh,
                                        bean?.Fdjxh, bean?.Syr,
                                        bean?.Lxdh, bean?.Zt,
                                        bean?.Zzcmc, bean?.Qdzs,
                                        bean?.Qdzw, bean?.Zczs,
                                        bean?.Zs, bean?.Zj,
                                        bean?.Zzs, bean?.Qzs,
                                        bean?.Qlj, bean?.Hlj,
                                        bean?.Zzl, bean?.Zbzl,
                                        bean?.Kqxjzw, bean?.Zxzs,
                                        bean?.Bzzw, bean?.Hdzk,
                                        bean?.Hdzzl, bean?.Zqyzl,
                                        bean?.Zdsjcs, bean?.Gl,
                                        bean?.Pl, bean?.Pqgs,
                                        bean?.Lcbds, bean?.Edzs,
                                        bean?.Ltgg, bean?.Qgs,
                                        bean?.Lxdz, bean?.Sjr,
                                        bean?.Sjrdh, bean?.Sjrsfzh,
                                        bean?.SCR, bean?.DPF
                                    )
                                    textAdapter.value = textValueList

                                }
                            }
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

            val hdzkHolder = binding.rvLeftList.findViewHolderForAdapterPosition(24)
            val zsHolder = binding.rvLeftList.findViewHolderForAdapterPosition(12)
            val hdzkValue = hdzkHolder?.itemView?.findViewById<EditText>(R.id.value)
            val zsValue = zsHolder?.itemView?.findViewById<EditText>(R.id.value)
            for (index in 0 until registerJyxmAdapter.itemCount){
                val holder = binding.rvJyxm.findViewHolderForAdapterPosition(index)
                setJyxm(binding.spAjywlb.selectedItem.toString()
                    ,binding.spCllx.selectedItem.toString()
                    ,binding.spSyxz.selectedItem.toString()
                    ,0.takeIf { hdzkValue?.text.isNullOrBlank() }?:hdzkValue?.text.toString().toInt()
                    ,holder?.itemView?.findViewById(R.id.cbJyxm)
                    ,0.takeIf{zsValue?.text.toString().isBlank()}?:zsValue?.text.toString().toInt())
            }

        }
        binding.btnSaveVehicleInfo.setOnClickListener {
            val textValueList = getTextValue(textAdapter)
            if (bean!=null){
                bean?.let {
                    dataDictionaryViewModel.getDm(FL_HPZL,binding.spHpzl.selectedItem.toString()).observe(this){
                            hpzl ->
                        dataDictionaryViewModel.getDm(FL_GCJK,binding.spGcjk.selectedItem.toString()).observe(this){
                                jkgc ->
                            dataDictionaryViewModel.getDm(FL_CLLX,binding.spCllx.selectedItem.toString()).observe(this){
                                    cllx ->
                                dataDictionaryViewModel.getDm(FL_CLSYXZ,binding.spSyxz.selectedItem.toString()).observe(this){
                                        syxz ->
                                    dataDictionaryViewModel.getDm(FL_RYLB,binding.spRyzl1.selectedItem.toString()).observe(this){
                                            rlzl ->
                                        dataDictionaryViewModel.getDm(FL_ZXZFS,binding.cbSfdlxj.toString().takeIf { binding.cbSfdlxj.isChecked }?:"非独立悬架").observe(this){
                                                zxxs ->
                                            dataDictionaryViewModel.getDm(FL_CLYT,binding.spClyt.selectedItem.toString()).observe(this){
                                                    clyt ->
                                                dataDictionaryViewModel.getDm(FL_YTSX,binding.spYtsx.selectedItem.toString()).observe(this){
                                                        ytsx ->
                                                    dataDictionaryViewModel.getDm(FL_AJYWLB,binding.spAjywlb.selectedItem.toString()).observe(this){
                                                            ajywlb ->
                                                        dataDictionaryViewModel.getDm(FL_HJYWLB,binding.spHjywlb.selectedItem.toString()).observe(this){
                                                                hjywlb ->
                                                            dataDictionaryViewModel.getDm(FL_ZJYWLB,binding.spZjywlb.selectedItem.toString()).observe(this){
                                                                    zjywlb ->
                                                                dataDictionaryViewModel.getDm(FL_PZCX,binding.spWgcx.selectedItem.toString()).observe(this){
                                                                        wgcxh ->
                                                                    dataDictionaryViewModel.getDm(FL_QDFS,binding.spQdxs.selectedItem.toString()).observe(this){
                                                                            qdxs ->
                                                                        dataDictionaryViewModel.getDm(
                                                                            FL_QZDZ,binding.spQzdz.selectedItem.toString()).observe(this){
                                                                                qzdz ->
                                                                            dataDictionaryViewModel.getDm(
                                                                                FL_DW,binding.spDws.selectedItem.toString()).observe(this){
                                                                                    dws ->
                                                                                dataDictionaryViewModel.getDm(
                                                                                    FL_BSX,binding.spBsxs.selectedItem.toString()).observe(this){
                                                                                        bsxs ->
                                                                                    dataDictionaryViewModel.getDm(
                                                                                        FL_ZDLY,binding.spZdly.selectedItem.toString()).observe(this){
                                                                                            zdly ->
                                                                                        dataDictionaryViewModel.getDm(
                                                                                            FL_RYGG,binding.spRygg.selectedItem.toString()).observe(this){
                                                                                                rygg ->
                                                                                            dataDictionaryViewModel.getDm(
                                                                                                FL_HPYS,binding.spHpys.selectedItem.toString()).observe(this){
                                                                                                    hpys ->
                                                                                                dataDictionaryViewModel.getDm(
                                                                                                    FL_CLSSLB,binding.spSslb.selectedItem.toString()).observe(this){
                                                                                                        jdcsslb ->
                                                                                                    dataDictionaryViewModel.getDm(
                                                                                                        FL_JQFS,binding.spJqfs.selectedItem.toString()).observe(this){
                                                                                                            jqfs ->
                                                                                                        systemParamsViewModel.getJyjgbh("AJ").observe(this){
                                                                                                            jyjgbhAj ->
                                                                                                            dataDictionaryViewModel.getDm(
                                                                                                                FL_HBXH,binding.spJcxh.selectedItem.toString()).observe(this){
                                                                                                                    hbxh ->
                                                                                                                dataDictionaryViewModel.getDm(
                                                                                                                    FL_GYFS,binding.spGyfs.selectedItem.toString()).observe(this){
                                                                                                                        gyfs ->
                                                                                                                    dataDictionaryViewModel.getDm(
                                                                                                                        FL_CC,binding.spCcs.selectedItem.toString()).observe(this){
                                                                                                                            ccs ->
                                                                                                                        dataDictionaryViewModel.getDm(
                                                                                                                            FL_HCLZL,binding.spHclfs.selectedItem.toString()).observe(this){
                                                                                                                                hclfs ->
                                                                                                                            dataDictionaryViewModel.getDmList(
                                                                                                                                FL_JYXM,getJyxm(registerJyxmAdapter)).observe(this){
                                                                                                                                    jyxmList ->
                                                                                                                                var jyxmString = ""
                                                                                                                                for(element in jyxmList){
                                                                                                                                    jyxmString += element
                                                                                                                                    jyxmString +=","
                                                                                                                                }
                                                                                                                                jyxmString = jyxmString.substring(0,jyxmString.length-1)
                                                                                                                                registerViewModel.postSaveVehicleInfo(SaveVehicleInfoW003Request(
                                                                                                                                    0,
                                                                                                                                    "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
                                                                                                                                    hpzl,
                                                                                                                                    binding.etClsbdh.text.toString(),
                                                                                                                                    textValueList[1],
                                                                                                                                    textValueList[2],
                                                                                                                                    it.Clpp2,
                                                                                                                                    jkgc,
                                                                                                                                    it.Zzg,
                                                                                                                                    textValueList[8],
                                                                                                                                    textValueList[4],
                                                                                                                                    cllx,
                                                                                                                                    it.Csys,
                                                                                                                                    syxz,
                                                                                                                                    it.Sfzmhm,
                                                                                                                                    it.Sfzmmc,
                                                                                                                                    textValueList[5],
                                                                                                                                    binding.tvCcdjrq.text.toString(),
                                                                                                                                    binding.tvDjrq.text.toString(),
                                                                                                                                    it.Yxqz,
                                                                                                                                    it.Qzbfqz,
                                                                                                                                    it.Fzjg,
                                                                                                                                    it.Glbm,
                                                                                                                                    it.Bxzzrq,
                                                                                                                                    textValueList[7],
                                                                                                                                    it.Dybj,
                                                                                                                                    textValueList[3],
                                                                                                                                    rlzl,
                                                                                                                                    textValueList[28],
                                                                                                                                    textValueList[27],
                                                                                                                                    zxxs,
                                                                                                                                    binding.etCwkc.text.toString(),
                                                                                                                                    binding.etCwkk.text.toString(),
                                                                                                                                    binding.etCwkg.text.toString(),
                                                                                                                                    binding.etHxnbcd.text.toString(),
                                                                                                                                    binding.etHxnbkd.text.toString(),
                                                                                                                                    binding.etHxnbgd.text.toString(),
                                                                                                                                    it.Gbthps,
                                                                                                                                    textValueList[12],
                                                                                                                                    textValueList[13],
                                                                                                                                    textValueList[16],
                                                                                                                                    textValueList[17],
                                                                                                                                    textValueList[32],
                                                                                                                                    it.Lts,
                                                                                                                                    textValueList[18],
                                                                                                                                    textValueList[19],
                                                                                                                                    textValueList[24],
                                                                                                                                    textValueList[23],
                                                                                                                                    textValueList[25],
                                                                                                                                    it.Qpzk,
                                                                                                                                    it.Hpzk,
                                                                                                                                    binding.tvCcrq.text.toString(),
                                                                                                                                    clyt,
                                                                                                                                    ytsx,
                                                                                                                                    textValueList[0],
                                                                                                                                    it.Jyhgbzbh,
                                                                                                                                    it.Xzqh,
                                                                                                                                    it.Zsxzqh,
                                                                                                                                    it.Zzxzqh,
                                                                                                                                    it.Sfmj,
                                                                                                                                    "1".takeIf { binding.cbSfcyc.isChecked}?:"0",
                                                                                                                                    ajywlb,
                                                                                                                                    zjywlb,
                                                                                                                                    hjywlb,
                                                                                                                                    it.Hjxm,
                                                                                                                                    it.Ajcx,
                                                                                                                                    it.Zjcx,
                                                                                                                                    wgcxh,
                                                                                                                                    it.Hbdbqk,
                                                                                                                                    "",
                                                                                                                                    "",
                                                                                                                                    qdxs,
                                                                                                                                    textValueList[11],
                                                                                                                                    "",
                                                                                                                                    textValueList[14],
                                                                                                                                    qzdz,
                                                                                                                                    it.Ygdltz,
                                                                                                                                    it.Zxzxjxs,
                                                                                                                                    textValueList[30],
                                                                                                                                    textValueList[9],
                                                                                                                                    textValueList[10],
                                                                                                                                    "1".takeIf{ binding.cbSfmz.isChecked}?:"0",
                                                                                                                                    textValueList[26],
                                                                                                                                    it.Zjdw,
                                                                                                                                    bsxs,
                                                                                                                                    zdly,
                                                                                                                                    textValueList[33],
                                                                                                                                    textValueList[32],
                                                                                                                                    textValueList[6],
                                                                                                                                    textValueList[34],
                                                                                                                                    hpys,
                                                                                                                                    textValueList[31],
                                                                                                                                    textValueList[22],
                                                                                                                                    jdcsslb,
                                                                                                                                    textValueList[15],
                                                                                                                                    textValueList[21],
                                                                                                                                    zjywlb,
                                                                                                                                    "1".takeIf{ binding.cbSfdzzc.isChecked}?:"0",
                                                                                                                                    "",
                                                                                                                                    it.Pfjd,
                                                                                                                                    textValueList[29],
                                                                                                                                    jqfs,
                                                                                                                                    dws,
                                                                                                                                    gyfs,
                                                                                                                                    it.Hjdlsj,
                                                                                                                                    "${binding.etLshSzm.text.toString()}${binding.tvLsh.text.toString()}".takeIf{ binding.cbAjywlb.isChecked}?:"",
                                                                                                                                    "${binding.etLshSzm.text.toString()}${binding.tvLsh.text.toString()}".takeIf { binding.cbHjywlb.isChecked }?:"",
                                                                                                                                    jyjgbhAj,
                                                                                                                                    hbxh,
                                                                                                                                    "",
                                                                                                                                    jyxmString,
                                                                                                                                    "",
                                                                                                                                    "",
                                                                                                                                    "",
                                                                                                                                    "",
                                                                                                                                    date2String(Date(),"yyyyMMdd HH:mm:ss"),
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
                                                                                                                                    "",
                                                                                                                                    textValueList[35],
                                                                                                                                    textValueList[37],
                                                                                                                                    textValueList[36],
                                                                                                                                    "",
                                                                                                                                    textValueList[38],
                                                                                                                                    textValueList[39],
                                                                                                                                    ccs,
                                                                                                                                    hclfs,

                                                                                                                                    )).observe(this){
                                                                                                                                    if (it){
                                                                                                                                        Toast.makeText(
                                                                                                                                            this.requireContext(),
                                                                                                                                            "登记成功",
                                                                                                                                            Toast.LENGTH_SHORT
                                                                                                                                        ).show()
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }


                                                                                                                }

                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    }

                                                }

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }
                }


            }else{
                dataDictionaryViewModel.getDm(FL_HPZL,binding.spHpzl.selectedItem.toString()).observe(this){
                        hpzl ->
                    dataDictionaryViewModel.getDm(FL_GCJK,binding.spGcjk.selectedItem.toString()).observe(this){
                            jkgc ->
                        dataDictionaryViewModel.getDm(FL_CLLX,binding.spCllx.selectedItem.toString()).observe(this){
                                cllx ->
                            dataDictionaryViewModel.getDm(FL_CLSYXZ,binding.spSyxz.selectedItem.toString()).observe(this){
                                    syxz ->
                                dataDictionaryViewModel.getDm(FL_RYLB,binding.spRyzl1.selectedItem.toString()).observe(this){
                                        rlzl ->
                                    dataDictionaryViewModel.getDm(FL_ZXZFS,binding.cbSfdlxj.toString().takeIf { binding.cbSfdlxj.isChecked }?:"非独立悬架").observe(this){
                                            zxxs ->
                                        dataDictionaryViewModel.getDm(FL_CLYT,binding.spClyt.selectedItem.toString()).observe(this){
                                                clyt ->
                                            dataDictionaryViewModel.getDm(FL_YTSX,binding.spYtsx.selectedItem.toString()).observe(this){
                                                    ytsx ->
                                                dataDictionaryViewModel.getDm(FL_AJYWLB,binding.spAjywlb.selectedItem.toString()).observe(this){
                                                        ajywlb ->
                                                    dataDictionaryViewModel.getDm(FL_HJYWLB,binding.spHjywlb.selectedItem.toString()).observe(this){
                                                            hjywlb ->
                                                        dataDictionaryViewModel.getDm(FL_ZJYWLB,binding.spZjywlb.selectedItem.toString()).observe(this){
                                                                zjywlb ->
                                                            dataDictionaryViewModel.getDm(FL_PZCX,binding.spWgcx.selectedItem.toString()).observe(this){
                                                                    wgcxh ->
                                                                dataDictionaryViewModel.getDm(FL_QDFS,binding.spQdxs.selectedItem.toString()).observe(this){
                                                                        qdxs ->
                                                                    dataDictionaryViewModel.getDm(
                                                                        FL_QZDZ,binding.spQzdz.selectedItem.toString()).observe(this){
                                                                            qzdz ->
                                                                        dataDictionaryViewModel.getDm(
                                                                            FL_DW,binding.spDws.selectedItem.toString()).observe(this){
                                                                                dws ->
                                                                            dataDictionaryViewModel.getDm(
                                                                                FL_BSX,binding.spBsxs.selectedItem.toString()).observe(this){
                                                                                    bsxs ->
                                                                                dataDictionaryViewModel.getDm(
                                                                                    FL_ZDLY,binding.spZdly.selectedItem.toString()).observe(this){
                                                                                        zdly ->
                                                                                    dataDictionaryViewModel.getDm(
                                                                                        FL_RYGG,binding.spRygg.selectedItem.toString()).observe(this){
                                                                                            rygg ->
                                                                                        dataDictionaryViewModel.getDm(
                                                                                            FL_HPYS,binding.spHpys.selectedItem.toString()).observe(this){
                                                                                                hpys ->
                                                                                            dataDictionaryViewModel.getDm(
                                                                                                FL_CLSSLB,binding.spSslb.selectedItem.toString()).observe(this){
                                                                                                    jdcsslb ->
                                                                                                dataDictionaryViewModel.getDm(
                                                                                                    FL_JQFS,binding.spJqfs.selectedItem.toString()).observe(this){
                                                                                                        jqfs ->
                                                                                                    systemParamsViewModel.getJyjgbh("AJ").observe(this){
                                                                                                            jyjgbhAj ->
                                                                                                        dataDictionaryViewModel.getDm(
                                                                                                            FL_HBXH,binding.spJcxh.selectedItem.toString()).observe(this){
                                                                                                                hbxh ->
                                                                                                            dataDictionaryViewModel.getDm(
                                                                                                                FL_GYFS,binding.spGyfs.selectedItem.toString()).observe(this){
                                                                                                                    gyfs ->
                                                                                                                dataDictionaryViewModel.getDm(
                                                                                                                    FL_CC,binding.spCcs.selectedItem.toString()).observe(this){
                                                                                                                        ccs ->
                                                                                                                    dataDictionaryViewModel.getDm(
                                                                                                                        FL_HCLZL,binding.spHclfs.selectedItem.toString()).observe(this){
                                                                                                                            hclfs ->
                                                                                                                        dataDictionaryViewModel.getDmList(
                                                                                                                            FL_JYXM,getJyxm(registerJyxmAdapter)).observe(this){
                                                                                                                                jyxmList ->
                                                                                                                            var jyxmString = ""
                                                                                                                            for(element in jyxmList){
                                                                                                                                jyxmString += element
                                                                                                                                jyxmString +=","
                                                                                                                            }
                                                                                                                            jyxmString = jyxmString.substring(0,jyxmString.length-1)
                                                                                                                            registerViewModel.postSaveVehicleInfo(SaveVehicleInfoW003Request(
                                                                                                                                0,
                                                                                                                                "${binding.spHphm.selectedItem}${binding.etHphm.text.toString()}",
                                                                                                                                hpzl,
                                                                                                                                binding.etClsbdh.text.toString(),
                                                                                                                                textValueList[1],
                                                                                                                                textValueList[2],
                                                                                                                                "",
                                                                                                                                jkgc,
                                                                                                                                "",
                                                                                                                                textValueList[8],
                                                                                                                                textValueList[4],
                                                                                                                                cllx,
                                                                                                                                "",
                                                                                                                                syxz,
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                textValueList[5],
                                                                                                                                binding.tvCcdjrq.text.toString(),
                                                                                                                                binding.tvDjrq.text.toString(),
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                textValueList[7],
                                                                                                                                "",
                                                                                                                                textValueList[3],
                                                                                                                                rlzl,
                                                                                                                                textValueList[28],
                                                                                                                                textValueList[27],
                                                                                                                                zxxs,
                                                                                                                                binding.etCwkc.text.toString(),
                                                                                                                                binding.etCwkk.text.toString(),
                                                                                                                                binding.etCwkg.text.toString(),
                                                                                                                                binding.etHxnbcd.text.toString(),
                                                                                                                                binding.etHxnbkd.text.toString(),
                                                                                                                                binding.etHxnbgd.text.toString(),
                                                                                                                                "",
                                                                                                                                textValueList[12],
                                                                                                                                textValueList[13],
                                                                                                                                textValueList[16],
                                                                                                                                textValueList[17],
                                                                                                                                textValueList[32],
                                                                                                                                "",
                                                                                                                                textValueList[18],
                                                                                                                                textValueList[19],
                                                                                                                                textValueList[24],
                                                                                                                                textValueList[23],
                                                                                                                                textValueList[25],
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                binding.tvCcrq.text.toString(),
                                                                                                                                clyt,
                                                                                                                                ytsx,
                                                                                                                                textValueList[0],
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "1".takeIf { binding.cbSfcyc.isChecked}?:"0",
                                                                                                                                ajywlb,
                                                                                                                                zjywlb,
                                                                                                                                hjywlb,
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                wgcxh,
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                qdxs,
                                                                                                                                textValueList[11],
                                                                                                                                "",
                                                                                                                                textValueList[14],
                                                                                                                                qzdz,
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                textValueList[30],
                                                                                                                                textValueList[9],
                                                                                                                                textValueList[10],
                                                                                                                                "1".takeIf{ binding.cbSfmz.isChecked}?:"0",
                                                                                                                                textValueList[26],
                                                                                                                                "",
                                                                                                                                bsxs,
                                                                                                                                zdly,
                                                                                                                                textValueList[33],
                                                                                                                                textValueList[32],
                                                                                                                                textValueList[6],
                                                                                                                                textValueList[34],
                                                                                                                                hpys,
                                                                                                                                textValueList[31],
                                                                                                                                textValueList[22],
                                                                                                                                jdcsslb,
                                                                                                                                textValueList[15],
                                                                                                                                textValueList[21],
                                                                                                                                zjywlb,
                                                                                                                                "1".takeIf{ binding.cbSfdzzc.isChecked}?:"0",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                textValueList[29],
                                                                                                                                jqfs,
                                                                                                                                dws,
                                                                                                                                gyfs,
                                                                                                                                "",
                                                                                                                                "${binding.etLshSzm.text.toString()}${binding.tvLsh.text.toString()}".takeIf{ binding.cbAjywlb.isChecked}?:"",
                                                                                                                                "${binding.etLshSzm.text.toString()}${binding.tvLsh.text.toString()}".takeIf { binding.cbHjywlb.isChecked }?:"",
                                                                                                                                jyjgbhAj,
                                                                                                                                hbxh,
                                                                                                                                "",
                                                                                                                                jyxmString,
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                date2String(Date(),"yyyyMMdd HH:mm:ss"),
                                                                                                                                1,
                                                                                                                                1,
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                bean001.TrueName,
                                                                                                                                bean001.ID,
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                "",
                                                                                                                                textValueList[35],
                                                                                                                                textValueList[37],
                                                                                                                                textValueList[36],
                                                                                                                                "",
                                                                                                                                textValueList[38],
                                                                                                                                textValueList[39],
                                                                                                                                ccs,
                                                                                                                                hclfs,

                                                                                                                                )).observe(this){
                                                                                                                                if (it){
                                                                                                                                    Toast.makeText(
                                                                                                                                        this.requireContext(),
                                                                                                                                        "登记成功",
                                                                                                                                        Toast.LENGTH_SHORT
                                                                                                                                    ).show()
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }


                                                                                                            }

                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }
            }

        }
            }


    fun initView() {
        dataDictionaryViewModel.apply {
            getMcListFromFl(FL_CSJC).observe(this@RegisterFragment) {
                binding.spHphm.adapter =
                    ArrayAdapter(this@RegisterFragment.requireContext(), R.layout.textview_spinner, it)
            }
            getMcListFromFl(FL_HPZL).observe(this@RegisterFragment) {
                binding.spHpzl.adapter =
                    ArrayAdapter(this@RegisterFragment.requireContext(), R.layout.textview_spinner, it)
            }
            getMcListFromFl(FL_HPYS).observe(this@RegisterFragment) {
                binding.spHpys.adapter =
                    ArrayAdapter(this@RegisterFragment.requireContext(), R.layout.textview_spinner, it)
                binding.spHpys.setText("蓝色")
            }
            getMcListFromFl(FL_AJYWLB).observe(this@RegisterFragment) {
                binding.spAjywlb.adapter =
                    ArrayAdapter(this@RegisterFragment.requireContext(), R.layout.textview_spinner, it)
                binding.spAjywlb.setText("-")
            }
            getMcListFromFl(FL_HJYWLB).observe(this@RegisterFragment) {
                binding.spHjywlb.adapter =
                    ArrayAdapter(this@RegisterFragment.requireContext(), R.layout.textview_spinner, it)
                binding.spHjywlb.setText("-")
            }
            getMcListFromFl(FL_ZJYWLB).observe(this@RegisterFragment) {
                binding.spZjywlb.adapter =
                    ArrayAdapter(this@RegisterFragment.requireContext(), R.layout.textview_spinner, it)
                binding.spZjywlb.setText("-")
            }
            getMcListFromFl(FL_CLLX).observe(this@RegisterFragment){
                binding.spCllx.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_CLYT).observe(this@RegisterFragment){
                binding.spClyt.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_YTSX).observe(this@RegisterFragment){
                binding.spYtsx.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_PZCX).observe(this@RegisterFragment){
                binding.spWgcx.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_GCJK).observe(this@RegisterFragment){
                binding.spGcjk.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_QDFS).observe(this@RegisterFragment){
                binding.spQdxs.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_ZDLY).observe(this@RegisterFragment){
                binding.spZdly.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_RYGG).observe(this@RegisterFragment){
                binding.spRygg.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_RYLB).observe(this@RegisterFragment){
                binding.spRyzl1.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
                binding.spRyzl2.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_QZDZ).observe(this@RegisterFragment){
                binding.spQzdz.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_HBXH).observe(this@RegisterFragment){
                binding.spJcxh.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_CLSSLB).observe(this@RegisterFragment){
                binding.spSslb.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_GYFS).observe(this@RegisterFragment){
                binding.spGyfs.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_JQFS).observe(this@RegisterFragment){
                binding.spJqfs.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_BSX).observe(this@RegisterFragment){
                binding.spBsxs.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_DW).observe(this@RegisterFragment){
                binding.spDws.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_CC).observe(this@RegisterFragment){
                binding.spCcs.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_HCLZL).observe(this@RegisterFragment){
                binding.spHclfs.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_HYYT).observe(this@RegisterFragment){
                binding.spHyyt.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
            getMcListFromFl(FL_CLSYXZ).observe(this@RegisterFragment){
                binding.spSyxz.adapter = ArrayAdapter(this@RegisterFragment.requireContext(),R.layout.textview_spinner,it)
            }
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
    fun getJyxm(adapter : RegisterJyxmAdapter): List<String>{
        val list = ArrayList<String>()
        for (index in 0 until adapter.itemCount){
            val holder = binding.rvJyxm.findViewHolderForAdapterPosition(index)
            val checkbox = holder?.itemView?.findViewById<CheckBox>(R.id.cbJyxm)
            if (checkbox != null && checkbox.isChecked){
                list.add(checkbox.text.toString())
            }
        }
        return list
    }

    fun setJyxm(ajywlb : String, cllx : String, syxz : String, hdzk : Int, Jyxm: CheckBox?, zs : Int){
//        val list = ArrayList<CheckBox>()
//        for (index in 0 until registerJyxmAdapter.itemCount){
//            val view = binding.rvJyxm.layoutManager?.findViewByPosition(index) as CheckBox
//            list.add(view)
//        }
        if (Jyxm?.text.toString() == "外观"){
            Jyxm?.isChecked = true
        }
        if (Jyxm?.text.toString() == "唯一性检查"){
            Jyxm?.isChecked = true
        }
        if (Jyxm?.text.toString() == "联网查询"){
            Jyxm?.isChecked = true
        }
        if (ajywlb.contains("注册")){

            if(isFyyxwzk(cllx,syxz)||cllx.contains("二轮摩托车")||cllx.contains("侧三轮摩托车")){
                if (Jyxm?.text.toString() == "外廓尺寸"){
                    Jyxm?.isChecked = false
                }
            }else{
                if (Jyxm?.text.toString() == "外廓尺寸"){
                    Jyxm?.isChecked = true
                }
//                for (element in list){
//                    if (element.text.toString() == "外廓尺寸"){
//                        element.isChecked = true
//                    }
//                }
            }
            if (isGc(cllx)||!(isZk(cllx)&&(cllx.contains("面包")||hdzk >= 7))){
                if (Jyxm?.text.toString() == "底盘动态"){
                    Jyxm?.isChecked = false
                }
            }else{
                if (Jyxm?.text.toString() == "底盘动态"){
                    Jyxm?.isChecked = true
                }
            }
            if (isMtc(cllx)||!(isZk(cllx)&&(cllx.contains("面包")||hdzk >= 7))){
                if (Jyxm?.text.toString() == "底盘动态"){
                    Jyxm?.isChecked = false
                }
            }else{
                if (Jyxm?.text.toString() == "底盘"){
                    Jyxm?.isChecked = true
                }
            }
            if (isZk(cllx)||isMtc(cllx)){
                if (Jyxm?.text.toString() == "整备质量"){
                    Jyxm?.isChecked = false
                }
            }else{
                if (Jyxm?.text.toString() == "整备质量"){
                    Jyxm?.isChecked = true
                }
            }
            if (isGc(cllx)){
                if (Jyxm?.text.toString() == "左外灯"){
                    Jyxm?.isChecked = false
                }
                if (Jyxm?.text.toString() == "右外灯"){
                    Jyxm?.isChecked = false
                }
            }else{
                if (Jyxm?.text.toString() == "左外灯"){
                    Jyxm?.isChecked = true
                }
                if (Jyxm?.text.toString() == "右外灯"){
                    Jyxm?.isChecked = true
                }
            }
            if (binding.cbSfdzzc.isChecked){
                if (Jyxm?.text.toString() == "驻车制动"){
                    Jyxm?.isChecked = false
                }
            }else{
                if (Jyxm?.text.toString() == "驻车制动"){
                    Jyxm?.isChecked = true
                }
            }
            if (binding.cbSfdlxj.isChecked){
                if (Jyxm?.text.toString() == "侧滑"){
                    Jyxm?.isChecked = true
                }
            }else{
                if (Jyxm?.text.toString() == "侧滑")
                Jyxm?.isChecked = false
            }
            when(zs){
                1 -> {
                    if (Jyxm?.text.toString() == "一轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "二轴制动"){
                        Jyxm?.isChecked = false
                    }
                    if (Jyxm?.text.toString() == "三轴制动"){
                        Jyxm?.isChecked = false
                    }
                    if (Jyxm?.text.toString() == "四轴制动"){
                        Jyxm?.isChecked = false
                    }
                    if (Jyxm?.text.toString() == "五轴制动"){
                        Jyxm?.isChecked = false
                    }
                }
                2 -> {
                    if (Jyxm?.text.toString() == "一轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "二轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "三轴制动"){
                        Jyxm?.isChecked = false
                    }
                    if (Jyxm?.text.toString() == "四轴制动"){
                        Jyxm?.isChecked = false
                    }
                    if (Jyxm?.text.toString() == "五轴制动"){
                        Jyxm?.isChecked = false
                    }
                }
                3 -> {
                    if (Jyxm?.text.toString() == "一轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "二轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "三轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "四轴制动"){
                        Jyxm?.isChecked = false
                    }
                    if (Jyxm?.text.toString() == "五轴制动"){
                        Jyxm?.isChecked = false
                    }
                }
                4 -> {
                    if (Jyxm?.text.toString() == "一轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "二轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "三轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "四轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "五轴制动"){
                        Jyxm?.isChecked = false
                    }
                }
                5 -> {
                    if (Jyxm?.text.toString() == "一轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "二轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "三轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "四轴制动"){
                        Jyxm?.isChecked = true
                    }
                    if (Jyxm?.text.toString() == "五轴制动"){
                        Jyxm?.isChecked = true
                    }
                }
            }

        }else if (ajywlb.contains("在用车")){
            if (syxz.contains("非营运")){

            }else{

            }
            if (cllx.contains("非营运")){

            }else{

            }

        }
    }
    fun isFyy(syxz : String): Boolean {
        return syxz == "非营运"
    }

    fun isZk(cllx: String) : Boolean{
        return cllx.contains("客车")
                ||cllx.contains("校车")
                ||cllx.contains("轿车")
                ||cllx.contains("面包")
                ||(cllx.contains("旅居")&&!cllx.contains("挂"))
    }
    fun isFyyxwzk(cllx: String, syxz: String): Boolean {
        return (isZk(cllx)&&(cllx.contains("小")||cllx.contains("微")))&&isFyy(syxz)
    }
    fun isHc(cllx: String) : Boolean{
        return cllx.contains("货车")
                ||cllx.contains("运输车")
                ||cllx.contains("专项作业车")
                ||cllx.contains("牵引")
    }
    fun isGc(cllx: String) : Boolean{
        return (cllx.contains("挂")&&!cllx.contains("牵引"))
    }
    fun isSlqc(cllx: String) : Boolean{
        return cllx == "三轮汽车"
    }
    fun isMtc(cllx: String) : Boolean{
        return cllx.contains("摩托")
    }


    override fun onResume() {
        super.onResume()

    }

}