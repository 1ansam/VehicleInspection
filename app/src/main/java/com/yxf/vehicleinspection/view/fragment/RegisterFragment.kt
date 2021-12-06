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
import com.yxf.vehicleinspection.utils.date2String
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

        binding.rvLeftSpinner.layoutManager = GridLayoutManager(this.requireContext(), 2)
        val spinnerAdapter = RegisterSpinnerAdapter(this.requireContext())
        spinnerAdapter.data = registerViewModel.spinnerMap.values.toList()
        binding.tvCcrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager,"ccrq")
        }
        binding.tvDjrq.setOnClickListener {
            DatePickerFragment().show(this.childFragmentManager,"djrq")
        }
        dataDictionaryViewModel.getDataDictionaryFromDb().observe(this) {
            val listspinnerData = ArrayList<List<String>>()
            val listCllx = mutableListOf<String>("")
            val listClyt = mutableListOf<String>("")
            val listYtsx = mutableListOf<String>("")
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
            for (element in it) {
                when (element.Fl) {
                    "07" -> listCllx.add(element.Mc)
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
                            with(binding) {
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
                            with(dataDictionaryViewModel) {
                                getMc("26", bean005.Hpys).observe(this@RegisterFragment) {
                                    binding.spHpys.setText(it)
                                }
                                getMc("08", bean005.Ajywlb).observe(this@RegisterFragment) {
                                    binding.spAjywlb.setText(it)
                                }
                                getMc("31", bean005.Hjywlb).observe(this@RegisterFragment) {
                                    binding.spHjywlb.setText(it)
                                }
                                getMc("30", bean005.Zjywlb).observe(this@RegisterFragment) {
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
                            dataDictionaryViewModel.getMc("07", bean005.Cllx).observe(this) {
                                val spinnerValueList = ArrayList<String>()
                                spinnerValueList.add(it)
                                dataDictionaryViewModel.getMc("21", bean005.Clyt).observe(this) {

                                    spinnerValueList.add(it)
                                    dataDictionaryViewModel.getMc("22", bean005.Ytsx)
                                        .observe(this) {
                                            spinnerValueList.add(it)
                                            dataDictionaryViewModel.getMc("60", bean005.Wgchx)
                                                .observe(this) {
                                                    spinnerValueList.add(it)
                                                    dataDictionaryViewModel.getMc(
                                                        "gc",
                                                        bean005.Gcjk
                                                    ).observe(this) {
                                                        spinnerValueList.add(it)
                                                        dataDictionaryViewModel.getMc(
                                                            "17",
                                                            bean005.Qdxs
                                                        ).observe(this) {
                                                            spinnerValueList.add(it)
                                                            dataDictionaryViewModel.getMc(
                                                                "23",
                                                                bean005.Zdly
                                                            ).observe(this) {
                                                                spinnerValueList.add(it)
                                                                if (bean005.Rlzl.length == 1) {
                                                                    dataDictionaryViewModel.getMc(
                                                                        "02",
                                                                        bean005.Rlzl
                                                                    ).observe(this) {
                                                                        spinnerValueList.add(it)
                                                                        spinnerValueList.add("")
                                                                        dataDictionaryViewModel.getMc(
                                                                            "27",
                                                                            bean005.Rygg
                                                                        ).observe(this) {
                                                                            spinnerValueList.add(it)
                                                                            dataDictionaryViewModel.getMc(
                                                                                "03",
                                                                                bean005.Qzdz
                                                                            ).observe(this) {
                                                                                spinnerValueList.add(
                                                                                    it
                                                                                )
                                                                                spinnerValueList.add(
                                                                                    ""
                                                                                )
                                                                                dataDictionaryViewModel.getMc(
                                                                                    "50",
                                                                                    bean005.Jdcsslb
                                                                                ).observe(this) {
                                                                                    spinnerValueList.add(
                                                                                        it
                                                                                    )
                                                                                    spinnerValueList.add(
                                                                                        ""
                                                                                    )
                                                                                    spinnerValueList.add(
                                                                                       ""
                                                                                    )

//                                                                                    dataDictionaryViewModel.getMc(
//                                                                                        "gy",
//                                                                                        bean005.Gyfs
//                                                                                    ).observe(
//                                                                                        this
//                                                                                    ) {
//                                                                                        spinnerValueList.add(
//                                                                                            it
//                                                                                        )
//                                                                                        dataDictionaryViewModel.getMc(
//                                                                                            "jq",
//                                                                                            bean005.Jqfs
//                                                                                        ).observe(
//                                                                                            this
//                                                                                        ) {
//                                                                                            spinnerValueList.add(
//                                                                                                it
//                                                                                            )
                                                                                            dataDictionaryViewModel.getMc(
                                                                                                "bs",
                                                                                                bean005.Bsxs
                                                                                            )
                                                                                                .observe(
                                                                                                    this
                                                                                                ) {
                                                                                                    spinnerValueList.add(
                                                                                                        it
                                                                                                    )
                                                                                                    spinnerValueList.add("")
//                                                                                                    dataDictionaryViewModel.getMc(
//                                                                                                        "dw",
//                                                                                                        bean005.Dws
//                                                                                                    )
//                                                                                                        .observe(
//                                                                                                            this
//                                                                                                        ) {
//                                                                                                            spinnerValueList.add(
//                                                                                                                it
//                                                                                                            )
                                                                                                            spinnerValueList.add(
                                                                                                                ""
                                                                                                            )
                                                                                                            spinnerValueList.add(
                                                                                                                ""
                                                                                                            )
                                                                                                            for (index in 0 until spinnerValueList.size){
                                                                                                                val viewHolder = binding.rvLeftSpinner.findViewHolderForAdapterPosition(index)
                                                                                                                val spinner = viewHolder?.itemView?.findViewById<Spinner>(R.id.tvSpinnerValue)
                                                                                                                spinner?.setText(spinnerValueList[index])
                                                                                                            }


                                                                                                        }
                                                                                                }
                                                                                        }
                                                                                    }
                                                                                }



                                                                } else if (bean005.Rlzl.length == 2) {
                                                                    dataDictionaryViewModel.getMc(
                                                                        "02",
                                                                        bean005.Rlzl.substring(0, 1)
                                                                    ).observe(this) {
                                                                        spinnerValueList.add(it)
                                                                        dataDictionaryViewModel.getMc(
                                                                            "02",
                                                                            bean005.Rlzl.substring(
                                                                                1,
                                                                                2
                                                                            )
                                                                        ).observe(this) {
                                                                            spinnerValueList.add(it)
                                                                            dataDictionaryViewModel.getMc(
                                                                                "27",
                                                                                bean005.Rygg
                                                                            ).observe(this) {
                                                                                spinnerValueList.add(
                                                                                    it
                                                                                )
                                                                                dataDictionaryViewModel.getMc(
                                                                                    "03",
                                                                                    bean005.Qzdz
                                                                                ).observe(this) {
                                                                                    spinnerValueList.add(
                                                                                        it
                                                                                    )
                                                                                    spinnerValueList.add(
                                                                                        ""
                                                                                    )
                                                                                    dataDictionaryViewModel.getMc(
                                                                                        "50",
                                                                                        bean005.Jdcsslb
                                                                                    ).observe(
                                                                                        this
                                                                                    ) {
                                                                                        spinnerValueList.add(
                                                                                            it
                                                                                        )
                                                                                        dataDictionaryViewModel.getMc(
                                                                                            "gy",
                                                                                            bean005.Gyfs
                                                                                        ).observe(
                                                                                            this
                                                                                        ) {
                                                                                            spinnerValueList.add(
                                                                                                it
                                                                                            )
                                                                                            dataDictionaryViewModel.getMc(
                                                                                                "jq",
                                                                                                bean005.Jqfs
                                                                                            )
                                                                                                .observe(
                                                                                                    this
                                                                                                ) {
                                                                                                    spinnerValueList.add(
                                                                                                        it
                                                                                                    )
                                                                                                    dataDictionaryViewModel.getMc(
                                                                                                        "bs",
                                                                                                        bean005.Bsxs
                                                                                                    )
                                                                                                        .observe(
                                                                                                            this
                                                                                                        ) {
                                                                                                            spinnerValueList.add(
                                                                                                                it
                                                                                                            )
                                                                                                            dataDictionaryViewModel.getMc(
                                                                                                                "dw",
                                                                                                                bean005.Dws
                                                                                                            )
                                                                                                                .observe(
                                                                                                                    this
                                                                                                                ) {
                                                                                                                    spinnerValueList.add(
                                                                                                                        it
                                                                                                                    )
                                                                                                                    spinnerValueList.add(
                                                                                                                        ""
                                                                                                                    )
                                                                                                                    spinnerValueList.add(
                                                                                                                        ""
                                                                                                                    )
                                                                                                                    for (index in 0 until spinnerValueList.size){
                                                                                                                        val viewHolder = binding.rvLeftSpinner.findViewHolderForAdapterPosition(index)
                                                                                                                        val spinner = viewHolder?.itemView?.findViewById<Spinner>(R.id.tvSpinnerValue)
                                                                                                                        spinner?.setText(spinnerValueList[index])
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
        binding.btnNewLsh.setOnClickListener {
            val randomLsh = "${date2String(Date(), "yyyyMMddHHmmss")}${
                String.format(
                    "%03d",
                    (1..999).random()
                )
            }"
            binding.tvLsh.text = randomLsh

        }

//        binding.btnRegister.setOnClickListener {
//            val action = RegisterFragmentDirections.actionRegisterFragmentToSignatureFragment()
//            findNavController().navigate(action)
//        }

    }

    fun initView() {
        dataDictionaryViewModel.getMcListFromFl("06").observe(this) {
            binding.spHphm.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl("09").observe(this) {
            binding.spHpzl.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl("10").observe(this) {
            binding.spHpys.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl("08").observe(this) {
            binding.spAjywlb.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl("31").observe(this) {
            binding.spHjywlb.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
        }
        dataDictionaryViewModel.getMcListFromFl("30").observe(this) {
            binding.spZjywlb.adapter =
                ArrayAdapter(this.requireContext(), R.layout.textview_spinner, it)
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

    fun getSpinnerValue(adapter: RegisterSpinnerAdapter): List<String> {
        val list = ArrayList<String>()
        for (index in 0 until adapter.itemCount) {
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