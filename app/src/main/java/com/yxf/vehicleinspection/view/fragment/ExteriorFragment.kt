package com.yxf.vehicleinspection.view.fragment

import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Xml
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.ImageItemR017Response
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentExteriorBinding
import com.yxf.vehicleinspection.utils.*
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.InspectionItemImageAdapter
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.view.adapter.YcySpinnerAdapter
import com.yxf.vehicleinspection.viewModel.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    private lateinit var count: CountDownTimer
    private var AjJyjghb = ""
    private var HjJyjghb = ""
    private var AjJkxlh = ""
    private var beginTime = ""
    private var endTime = ""
    private var dczxllthwsd = false
    private var dcqtllthwsd = false
    private var gclthwsd = false
    private var zj = false
    private var Sfqssq = false
    private var Sfdzzc = false
    private var Sfkqxj = false
    lateinit var bean001: UserInfoR001Response
    private var ivImage: ImageView? = null
    lateinit var currentPhotoPath: String
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> {
        InspectionItemViewModelFactory(
            (requireActivity().application as MyApp).inspectionItemRepository,
            (requireActivity().application as MyApp).serverTimeRepository
        )
    }
    private val systemParamsViewModel by viewModels<SystemParamsViewModel> {
        SystemParamsViewModelFactory((requireActivity().application as MyApp).systemParamsRepository)
    }
    lateinit var inspectionItemImageAdapter: InspectionItemImageAdapter
    lateinit var inspectionItemAjSelectAdapter: InspectionItemSelectAdapter
    lateinit var inspectionItemHjSelectAdapter: InspectionItemSelectAdapter
    private val args: ExteriorFragmentArgs by navArgs()
    private var holder: RecyclerView.ViewHolder? = null

    override fun init() {
        if (args.bean006.Ajywlb == "-") {
            binding.llVehicleFeatures.visibility = View.GONE
        }
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        systemParamsViewModel.getJyjgbh("AJ").observe(this) {
            AjJyjghb = it
            systemParamsViewModel.getJyjgbh("HJ").observe(this) {
                HjJyjghb = it
                systemParamsViewModel.getWebPass("AJ").observe(this) {
                    AjJkxlh = it
                    inspectionItemViewModel.getServerTime().observe(this) {
                        beginTime = it.Sj
                        inspectionItemViewModel.postProjectStartW010(
                            ProjectStartW010Request(
                                AjJyjghb,
                                args.jcxh,
                                args.bean005.Hphm,
                                args.bean005.Hpzl,
                                args.bean005.Clsbdh,
                                args.bean006.Jcxm,
                                args.bean006.Jcxm,
                                beginTime,
                                args.bean006.Ajywlb,
                                args.bean006.Hjywlb,
                                AjJkxlh,
                                args.bean002.Ajlsh,
                                args.bean002.Hjlsh,
                                args.bean002.Ajjccs,
                                args.bean002.Hjjccs
                            )
                        ).observe(this) {
                            if (it) {
                                getImageData(
                                    args.bean006.Jcxm,
                                    args.bean006.Ajywlb,
                                    args.bean006.Hjywlb,
                                    args.bean002.Ajlsh,
                                    args.bean002.Hjlsh
                                )
                                getSelectData(
                                    args.bean006.Jcxm,
                                    args.bean006.Ajywlb, args.bean006.Hjywlb,
                                    args.bean002.Ajlsh, args.bean002.Hjlsh
                                )
                            } else {
                                Toast.makeText(MyApp.context, "写入项目开始失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
        inspectionItemViewModel.getLeastestTime(args.bean005.Ajcx, args.bean006.Jcxm)
            .observe(this) {
                count = object : CountDownTimer(it.Yqsc.toInt() * 1000L, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.includeTitle.textView.text = "外观查验${millisUntilFinished / 1000}"
                    }
                    override fun onFinish() {
                        Snackbar.make(this@ExteriorFragment.requireView(),"检验时间已到可以提交查验",Snackbar.LENGTH_SHORT).show()
                    }
                }.start()
            }

        inspectionItemImageAdapter = InspectionItemImageAdapter()
        binding.rvImage.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvImage.adapter = inspectionItemImageAdapter
        binding.rvAjSelect.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvHjSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemAjSelectAdapter = InspectionItemSelectAdapter(this)
        inspectionItemHjSelectAdapter = InspectionItemSelectAdapter(this)
        binding.rvAjSelect.adapter = inspectionItemAjSelectAdapter
        binding.rvHjSelect.adapter = inspectionItemHjSelectAdapter
        inspectionItemImageAdapter.onItemViewClickListener =
            object : BaseRvAdapter.OnItemViewClickListener<ImageItemR017Response> {
                override fun onItemClick(view: View, position: Int, bean: ImageItemR017Response) {
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
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                            }
                        }
                    }
                    holder = binding.rvImage.findViewHolderForAdapterPosition(position)
                    ivImage = holder?.itemView?.findViewById(R.id.ivImage)

                }


            }
        binding.includeTitle.btnSubmit.setOnClickListener {
            if (binding.includeTitle.textView.text.toString().substring(4) != "0") {
                Snackbar.make(this.requireView(),"检验时间未到",Snackbar.LENGTH_SHORT).show()
            } else {


                binding.pbExteriorSubmit.visibility = View.VISIBLE
                inspectionItemViewModel.getServerTime().observe(this) {
                    endTime = it.Sj
                    inspectionItemViewModel.postInspectionPhotoW007(
                        getPostPhotoData(
                            inspectionItemImageAdapter
                        )
                    )
                        .observe(this) {
                            if (it) {
                                inspectionItemViewModel.postSaveVideoW008(
                                    inspectionItemViewModel.getPostVideoData(
                                        args.jcxh,
                                        args.bean005.Hphm,
                                        args.bean005.Hpzl,
                                        args.bean006.Jcxm,
                                        EXTERIOR_FRONT,
                                        EXTERIOR_LEFT_FRONT_HJ,
                                        args.bean006.Ajywlb,
                                        args.bean006.Hjywlb,
                                        endTime.substring(0, 10),
                                        endTime.substring(11),
                                        string2String(
                                            beginTime,
                                            "yyyy-MM-dd HH:mm:ss",
                                            "yyyyMMddHHmmss"
                                        ),
                                        string2String(
                                            endTime,
                                            "yyyy-MM-dd HH:mm:ss",
                                            "yyyyMMddHHmmss"
                                        ),
                                        "",
                                        args.bean005.Clpp1,
                                        args.bean005.Syr,
                                        args.bean005.Hjdlsj,
                                        "",
                                        "0",
                                        args.bean002.Ajlsh,
                                        args.bean002.Hjlsh,
                                        args.bean002.Ajjccs,
                                        args.bean002.Hjjccs
                                    )
                                ).observe(this) {
                                    if (it) {
                                        inspectionItemViewModel.postSaveVideoW008(
                                            inspectionItemViewModel.getPostVideoData(
                                                args.jcxh,
                                                args.bean005.Hphm,
                                                args.bean005.Hpzl,
                                                args.bean006.Jcxm,
                                                EXTERIOR_BEHIDE,
                                                EXTERIOR_RIGHT_BEHIND_HJ,
                                                args.bean006.Ajywlb,
                                                args.bean006.Hjywlb,
                                                endTime.substring(0, 10),
                                                endTime.substring(11),
                                                string2String(
                                                    beginTime,
                                                    "yyyy-MM-dd HH:mm:ss",
                                                    "yyyyMMddHHmmss"
                                                ),
                                                string2String(
                                                    endTime,
                                                    "yyyy-MM-dd HH:mm:ss",
                                                    "yyyyMMddHHmmss"
                                                ),
                                                "",
                                                args.bean005.Clpp1,
                                                args.bean005.Syr,
                                                args.bean005.Hjdlsj,
                                                "",
                                                "0",
                                                args.bean002.Ajlsh,
                                                args.bean002.Hjlsh,
                                                args.bean002.Ajjccs,
                                                args.bean002.Hjjccs
                                            )
                                        ).observe(this) {
                                            if (it) {
                                                inspectionItemViewModel.postArtificialProjectW011(
                                                    getPostArtificialData(
                                                        inspectionItemAjSelectAdapter,
                                                        inspectionItemHjSelectAdapter
                                                    )
                                                ).observe(this) {
                                                    if (it) {
                                                        inspectionItemViewModel.postProjectEndW012(
                                                            inspectionItemViewModel.getPostProjectEndData(
                                                                AjJyjghb,
                                                                args.jcxh,
                                                                args.bean005.Hphm,
                                                                args.bean005.Hpzl,
                                                                args.bean005.Clsbdh,
                                                                args.bean006.Jcxm,
                                                                args.bean006.Jcxm,
                                                                endTime,
                                                                args.bean006.Ajywlb,
                                                                args.bean006.Hjywlb,
                                                                AjJkxlh,
                                                                args.bean002.Ajlsh,
                                                                args.bean002.Hjlsh,
                                                                args.bean002.Ajjccs,
                                                                args.bean002.Hjjccs
                                                            )
                                                        ).observe(this) {
                                                            if (it) {
                                                                Toast.makeText(
                                                                    this.context,
                                                                    "外观项目结束",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                                val action =
                                                                    ExteriorFragmentDirections.actionExteriorFragmentToSignatureFragment(
                                                                        args.bean005,
                                                                        args.jcxh, args.bean002,args.bean006.Jcxm,args.bean006.Ajywlb,args.bean006.Hjywlb
                                                                    )
                                                                findNavController().navigate(action)
                                                            } else {
                                                                Toast.makeText(
                                                                    this.context,
                                                                    "外观项目结束失败",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    } else {
                                                        binding.pbExteriorSubmit.visibility =
                                                            View.GONE
                                                        Toast.makeText(
                                                            this.context,
                                                            "人工检验信息上传失败",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }

                                            } else {
                                                Toast.makeText(
                                                    this.context,
                                                    "保存视频失败",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                binding.pbExteriorSubmit.visibility = View.GONE
                                            }
                                        }
                                    } else {
                                        Toast.makeText(this.context, "保存视频失败", Toast.LENGTH_SHORT)
                                            .show()
                                        binding.pbExteriorSubmit.visibility = View.GONE
                                    }
                                }
                            } else {
                                binding.pbExteriorSubmit.visibility = View.GONE
                                Toast.makeText(MyApp.context, "上传照片失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
        binding.tvZj.setOnClickListener {
            zj = !zj
            if (zj) {
                binding.llZj.visibility = View.VISIBLE
            } else {
                binding.llZj.visibility = View.GONE
            }
        }
        binding.tvDczxlhwsd.setOnClickListener {
            dczxllthwsd = !dczxllthwsd
            if (dczxllthwsd) {
                binding.llDczxlhwsd.visibility = View.VISIBLE
            } else {
                binding.llDczxlhwsd.visibility = View.GONE
            }
        }
        binding.tvDcqtlhwsd.setOnClickListener {
            dcqtllthwsd = !dcqtllthwsd
            if (dcqtllthwsd) {
                binding.llDcqtlhwsd.visibility = View.VISIBLE
            } else {
                binding.llDcqtlhwsd.visibility = View.GONE
            }
        }
        binding.tvGclthwsd.setOnClickListener {
            gclthwsd = !gclthwsd
            if (gclthwsd) {
                binding.llGclthwsd.visibility = View.VISIBLE
            } else {
                binding.llGclthwsd.visibility = View.GONE
            }
        }
        //是否全时/适时四驱
        binding.cbSfqssq.isChecked = Sfqssq
        if (binding.cbSfqssq.isChecked) {
            binding.cbSfqssq.text = "是"
        } else {
            binding.cbSfqssq.text = "否"
        }
        binding.cbSfqssq.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbSfqssq.text = "是"
            } else {
                binding.cbSfqssq.text = "否"
            }
        }
        //是否电子手刹
        if (args.bean005.Dzss == "0") {
            Sfdzzc = false
        } else if (args.bean005.Dzss == "1") {
            Sfdzzc = true
        }
        binding.cbSfdzzc.isChecked = Sfdzzc
        if (binding.cbSfdzzc.isChecked) {
            binding.cbSfdzzc.text = "是"
        } else {
            binding.cbSfdzzc.text = "否"
        }
        binding.cbSfdzzc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbSfdzzc.text = "是"
            } else {
                binding.cbSfdzzc.text = "否"
            }
        }
        //是否空气悬架
        binding.cbSfkqxj.isChecked = Sfkqxj
        binding.llKqxjz.visibility = View.GONE.takeIf { !Sfkqxj } ?: View.VISIBLE
        if (binding.cbSfkqxj.isChecked) {
            binding.cbSfkqxj.text = "是"
            binding.llKqxjz.visibility = View.VISIBLE
        } else {
            binding.cbSfkqxj.text = "否"
            binding.llKqxjz.visibility = View.GONE
        }
        binding.cbSfkqxj.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbSfkqxj.text = "是"
                binding.llKqxjz.visibility = View.VISIBLE
            } else {
                binding.cbSfkqxj.text = "否"
                binding.llKqxjz.visibility = View.GONE
            }
        }
        binding.includeBottomButton.btnLeftPhoto.setOnClickListener {
            binding.pbExteriorSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.postTakePhoto(
                TakePhotoW009Request(
                    0,
                    args.jcxh, args.bean005.Hphm, args.bean005.Hpzl, args.bean005.Clsbdh,
                    args.bean006.Jcxm, args.bean006.Ajywlb, args.bean006.Jcxm, "110",
                    args.bean002.Ajlsh, args.bean002.Hjlsh,
                    args.bean002.Ajjccs, args.bean002.Hjjccs
                )
            ).observe(this) {
                if (it) {
                    binding.pbExteriorSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "前上方拍照成功", Toast.LENGTH_SHORT).show()
                } else {
                    binding.pbExteriorSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "前上方拍照失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.includeBottomButton.btnRightPhoto.setOnClickListener {
            binding.pbExteriorSubmit.visibility = View.VISIBLE
            inspectionItemViewModel.postTakePhoto(
                TakePhotoW009Request(
                    0,
                    args.jcxh, args.bean005.Hphm, args.bean005.Hpzl, args.bean005.Clsbdh,
                    args.bean006.Jcxm, args.bean006.Ajywlb, args.bean006.Jcxm, "111",
                    args.bean002.Ajlsh, args.bean002.Hjlsh,
                    args.bean002.Ajjccs, args.bean002.Hjjccs
                )
            ).observe(this) {
                if (it) {
                    binding.pbExteriorSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "后上方拍照成功", Toast.LENGTH_SHORT).show()
                } else {
                    binding.pbExteriorSubmit.visibility = View.GONE
                    Toast.makeText(this.context, "后上方拍照失败", Toast.LENGTH_SHORT).show()
                }
            }
        }

        systemParamsViewModel.getJyjgbh("AJ").observe(this) {
            AjJyjghb = it
            systemParamsViewModel.getJyjgbh("HJ").observe(this) {
                HjJyjghb = it
                systemParamsViewModel.getWebPass("AJ").observe(this) {
                    AjJkxlh = it
                    inspectionItemViewModel.getServerTime().observe(this) {
                        beginTime = it.Sj
                        inspectionItemViewModel.postProjectStartW010(
                            ProjectStartW010Request(
                                AjJyjghb,
                                args.jcxh,
                                args.bean005.Hphm,
                                args.bean005.Hpzl,
                                args.bean005.Clsbdh,
                                args.bean006.Jcxm,
                                args.bean006.Jcxm,
                                beginTime,
                                args.bean006.Ajywlb,
                                args.bean006.Hjywlb,
                                AjJkxlh,
                                args.bean002.Ajlsh,
                                args.bean002.Hjlsh,
                                args.bean002.Ajjccs,
                                args.bean002.Hjjccs
                            )
                        ).observe(this) {
                            if (it) {
                                getImageData(
                                    args.bean006.Jcxm,
                                    args.bean006.Ajywlb,
                                    args.bean006.Hjywlb,
                                    args.bean002.Ajlsh,
                                    args.bean002.Hjlsh
                                )
                                getSelectData(
                                    args.bean006.Jcxm,
                                    args.bean006.Ajywlb, args.bean006.Hjywlb,
                                    args.bean002.Ajlsh, args.bean002.Hjlsh
                                )
                            } else {
                                Toast.makeText(MyApp.context, "写入项目开始失败", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }


    }


    private fun getImageData(
        Jyxm: String, Ajywlb: String, Hjywlb: String, Ajlsh: String,
        Hjlsh: String,
    ) {
        inspectionItemViewModel.getImageItemData(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh).observe(this) {
            inspectionItemImageAdapter.data = it
        }
    }

    private fun getSelectData(
        Jyxm: String,
        Ajywlb: String,
        Hjywlb: String,
        Ajlsh: String,
        Hjlsh: String
    ) {
        inspectionItemViewModel.getSelectItemData(Jyxm, Ajywlb, Hjywlb, Ajlsh, Hjlsh)
            .observe(this) {
                if (it.size == 1) {
                    inspectionItemAjSelectAdapter.data = it[0].Xmlb
                }
                if (it.size == 2) {
                    inspectionItemAjSelectAdapter.data = it[0].Xmlb
                    inspectionItemHjSelectAdapter.data = it[1].Xmlb
                }


            }
    }


    private fun getPostPhotoData(adapter: InspectionItemImageAdapter): List<InspectionPhotoW007Request> {
        val list = ArrayList<InspectionPhotoW007Request>()
        for (index in 0 until adapter.itemCount) {
            val holder = binding.rvImage.findViewHolderForAdapterPosition(index)
            val ivImage = holder?.itemView?.findViewById<ImageView>(R.id.ivImage)
            val drawable = ivImage?.drawable
            val ivTag = ivImage?.tag.toString()
            if (ivTag == "1" && ivTag != null) {
                val bitmap = getBitmapFromDrawable(drawable!!)
                val base64 = bitmap2Base64(bitmap)
                val model = InspectionPhotoW007Request(
                    index,
                    AjJyjghb,
                    HjJyjghb,
                    args.jcxh,
                    args.bean005.Hphm,
                    args.bean005.Hpzl,
                    args.bean005.Clsbdh,
                    base64,
                    string2String(
                        beginTime,
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyyMMddHHmmss"
                    ),
                    args.bean006.Jcxm,
                    adapter.data[index].Zpdm,
                    adapter.data[index].Zpmc,
                    adapter.data[index].Zpajdm,
                    adapter.data[index].Zphjdm,
                    adapter.data[index].Bcaj,
                    adapter.data[index].BcHj,
                    args.bean005.Hjdlsj,
                    args.bean002.Ajlsh,
                    args.bean002.Hjlsh,
                    args.bean002.Ajjccs,
                    args.bean002.Hjjccs
                )
                list.add(model)
            }


        }

        return list

    }

    private fun getPostArtificialData(
        ajAdapter: InspectionItemSelectAdapter,
        hjAdapter: InspectionItemSelectAdapter
    ): List<ArtificialProjectW011Request<ExteriorArtificialProjectRequest>> {
        val list = ArrayList<ArtificialProjectW011Request<ExteriorArtificialProjectRequest>>()
        val ajListXmlb = ArrayList<Xmlb>()
        val hjListXmlb = ArrayList<Xmlb>()
        for (index in 0 until ajAdapter.itemCount) {
            val holder = binding.rvAjSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(
                ajAdapter.data[index].Xmdm,
                ajAdapter.data[index].Xmms,
                ivTag,
                etBz?.text.toString()
            )
            ajListXmlb.add(xmlb)
        }
        for (index in 0 until hjAdapter.itemCount) {
            val holder = binding.rvHjSelect.findViewHolderForAdapterPosition(index)
            val ivSelected = holder?.itemView?.findViewById<ImageView>(R.id.ivSelected)
            val etBz = holder?.itemView?.findViewById<EditText>(R.id.etBz)
            val ivTag = ivSelected?.tag as String
            val xmlb = Xmlb(
                hjAdapter.data[index].Xmdm,
                hjAdapter.data[index].Xmms,
                ivTag,
                etBz?.text.toString()
            )
            hjListXmlb.add(xmlb)
        }
        val exteriorArtificialProjectRequest = ExteriorArtificialProjectRequest(
            AjJyjghb,
            args.jcxh,
            args.bean005.Hphm,
            args.bean005.Hpzl,
            args.bean005.Clsbdh,
            args.bean006.Jcxm,
            args.bean006.Ajywlb,
            args.bean006.Hjywlb,
            AjJkxlh, ajListXmlb, hjListXmlb,
            string2String(beginTime, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"),
            string2String(endTime, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss"),
            binding.etCwkc.text.toString(),
            binding.etCwkk.text.toString(),
            binding.etCwkg.text.toString(),
            getZj(),
            binding.etCxlbgd.text.toString(),
            getDczxlhwsd(),
            getDcqtlhwsd(),
            getGchwsd(),
            binding.etYzzgd.text.toString(), binding.etYzygd.text.toString(),
            "".takeIf {
                binding.etYzzgd.text.toString().isBlank() || binding.etYzygd.text.toString()
                    .isBlank()
            } ?: abs(
                (binding.etYzzgd.text.toString().toInt() - binding.etYzygd.text.toString().toInt())
            ).toString(),
            binding.etZhzzgd.text.toString(),
            binding.etZhzygd.text.toString(),
            "".takeIf {
                binding.etZhzzgd.text.toString().isBlank() || binding.etZhzygd.text.toString()
                    .isBlank()
            } ?: abs(
                (binding.etYzzgd.text.toString().toInt() - binding.etYzygd.text.toString().toInt())
            ).toString(),
            "1".takeIf { binding.cbSfqssq.isChecked } ?: "0",
            "1".takeIf { binding.cbSfdzzc.isChecked } ?: "0",
            "1".takeIf { binding.cbSfkqxj.isChecked } ?: "0",
            binding.etKqxjz.text.toString(),
            binding.etZxzsl.text.toString(),
            "",
            bean001.TrueName, bean001.ID, binding.etExteriorBz.text.toString(),
            args.bean002.Ajlsh, args.bean002.Hjlsh,
            args.bean002.Ajjccs, args.bean002.Hjjccs
        )
        list.add(ArtificialProjectW011Request(args.bean006.Jcxm, exteriorArtificialProjectRequest))
        Log.e("TAG", "getPostArtificialData: $list")
        return list

    }

    private fun getZj(): String {
        val oneZj = binding.etOneZj.text.toString()
        val twoZj = binding.etTwoZj.text.toString()
        val threeZj = binding.etThreeZj.text.toString()
        val fourZj = binding.etFourZj.text.toString()
        val fiveZj = binding.etFiveZj.text.toString()
        var str = ""
        if (oneZj.isNotBlank()) {
            str += oneZj
            if (twoZj.isNotBlank()) {
                str += "+$twoZj"
                if (threeZj.isNotBlank()) {
                    str += "+$threeZj"
                    if (fourZj.isNotBlank()) {
                        str += "+$fourZj"
                        if (fiveZj.isNotBlank()) {
                            str += "+$fiveZj"
                        }
                    }
                }
            }
        }
        return str
    }

    private fun getDczxlhwsd(): String {
        val A1 = binding.etTurnA1.text.toString()
        val A2 = binding.etTurnA2.text.toString()
        val A3 = binding.etTurnA3.text.toString()
        val A4 = binding.etTurnA4.text.toString()
        val B1 = binding.etTurnB1.text.toString()
        val B2 = binding.etTurnB2.text.toString()
        val B3 = binding.etTurnB3.text.toString()
        val B4 = binding.etTurnB4.text.toString()
        val A12 = "A1:$A1/A2:$A2"
        val A34 = "A3:$A3/A4:$A4"
        val B12 = "B1:$B1/B2:$B2"
        val B34 = "B1:$B3/B2:$B4"
        var str = ""
        if (A1.isNotBlank() && A2.isNotBlank()) {
            str += A12
            if (A3.isNotBlank() && A4.isNotBlank()) {
                str += "/$A34"
                if (B1.isNotBlank() && B2.isNotBlank()) {
                    str += "/$B12"
                    if (B3.isNotBlank() && B4.isNotBlank()) {
                        str += "/$B34"
                    }
                }
            } else if (B1.isNotBlank() && B2.isNotBlank()) {
                str += "/$B12"
                if (B3.isNotBlank() && B4.isNotBlank()) {
                    str += "/$B34"
                }
            }
        }
        return str
    }

    private fun getDcqtlhwsd(): String {
        val A1 = binding.etOtherA1.text.toString()
        val A2 = binding.etOtherA2.text.toString()
        val A3 = binding.etOtherA3.text.toString()
        val A4 = binding.etOtherA4.text.toString()
        val B1 = binding.etOtherB1.text.toString()
        val B2 = binding.etOtherB2.text.toString()
        val B3 = binding.etOtherB3.text.toString()
        val B4 = binding.etOtherB4.text.toString()
        val C1 = binding.etOtherC1.text.toString()
        val C2 = binding.etOtherC2.text.toString()
        val C3 = binding.etOtherC3.text.toString()
        val C4 = binding.etOtherC4.text.toString()
        val D1 = binding.etOtherD1.text.toString()
        val D2 = binding.etOtherD2.text.toString()
        val D3 = binding.etOtherD3.text.toString()
        val D4 = binding.etOtherD4.text.toString()
        val A12 = "A1:$A1/A2:$A2"
        val A34 = "A3:$A3/A4:$A4"
        val B12 = "B1:$B1/B2:$B2"
        val B34 = "B3:$B3/B4:$B4"
        val C12 = "C1:$C1/C2:$C2"
        val C34 = "C3:$C3/C4:$C4"
        val D12 = "D1:$D1/D2:$D2"
        val D34 = "D3:$D3/D4:$D4"
        var str = ""
        if (A1.isNotBlank() && A2.isNotBlank()) {
            str += A12
            if (A3.isNotBlank() && A4.isNotBlank()) {
                str += "/$A34"
                if (B1.isNotBlank() && B2.isNotBlank()) {
                    str += "/$B12"
                    if (B3.isNotBlank() && B4.isNotBlank()) {
                        str += "/$B34"
                        if (C1.isNotBlank() && C2.isNotBlank()) {
                            str += "/$C12"
                            if (C3.isNotBlank() && C4.isNotBlank()) {
                                str += "/$C34"
                                if (D1.isNotBlank() && D2.isNotBlank()) {
                                    str += "/$D12"
                                    if (D3.isNotBlank() && D4.isNotBlank()) {
                                        str += "/$D34"
                                    }
                                }
                            } else if (D1.isNotBlank() && D2.isNotBlank()) {
                                str += "/$D12"
                                if (D3.isNotBlank() && D4.isNotBlank()) {
                                    str += "/$D34"
                                }
                            }
                        }
                    } else if (C1.isNotBlank() && C2.isNotBlank()) {
                        str += "/$C12"
                        if (C3.isNotBlank() && C4.isNotBlank()) {
                            str += "/$C34"
                            if (D1.isNotBlank() && D2.isNotBlank()) {
                                str += "/$D12"
                                if (D3.isNotBlank() && D4.isNotBlank()) {
                                    str += "/$D34"
                                }
                            }
                        } else if (D1.isNotBlank() && D2.isNotBlank()) {
                            str += "/$D12"
                            if (D3.isNotBlank() && D4.isNotBlank()) {
                                str += "/$D34"
                            }
                        }
                    }
                }
            } else if (B1.isNotBlank() && B2.isNotBlank()) {
                str += "/$B12"
                if (B3.isNotBlank() && B4.isNotBlank()) {
                    str += "/$B34"
                    if (C1.isNotBlank() && C2.isNotBlank()) {
                        str += "/$C12"
                        if (C3.isNotBlank() && C4.isNotBlank()) {
                            str += "/$C34"
                            if (D1.isNotBlank() && D2.isNotBlank()) {
                                str += "/$D12"
                                if (D3.isNotBlank() && D4.isNotBlank()) {
                                    str += "/$D34"
                                }
                            }
                        }
                        if (D1.isNotBlank() && D2.isNotBlank()) {
                            str += "/$D12"
                            if (D3.isNotBlank() && D4.isNotBlank()) {
                                str += "/$D34"
                            }
                        }
                    }
                }
                if (C1.isNotBlank() && C2.isNotBlank()) {
                    str += "/$C12"
                    if (C3.isNotBlank() && C4.isNotBlank()) {
                        str += "/$C34"
                        if (D1.isNotBlank() && D2.isNotBlank()) {
                            str += "/$D12"
                            if (D3.isNotBlank() && D4.isNotBlank()) {
                                str += "/$D34"
                            }
                        }
                    }
                    if (D1.isNotBlank() && D2.isNotBlank()) {
                        str += "/$D12"
                        if (D3.isNotBlank() && D4.isNotBlank()) {
                            str += "/$D34"
                        }
                    }
                }
            }
        }
        return str
    }

    private fun getGchwsd(): String {
        val A1 = binding.etGcA1.text.toString()
        val A2 = binding.etGcA2.text.toString()
        val A3 = binding.etGcA3.text.toString()
        val A4 = binding.etGcA4.text.toString()
        val B1 = binding.etGcB1.text.toString()
        val B2 = binding.etGcB2.text.toString()
        val B3 = binding.etGcB3.text.toString()
        val B4 = binding.etGcB4.text.toString()
        val C1 = binding.etGcC1.text.toString()
        val C2 = binding.etGcC2.text.toString()
        val C3 = binding.etGcC3.text.toString()
        val C4 = binding.etGcC4.text.toString()
        val D1 = binding.etGcD1.text.toString()
        val D2 = binding.etGcD2.text.toString()
        val D3 = binding.etGcD3.text.toString()
        val D4 = binding.etGcD4.text.toString()
        val A12 = "A1:$A1/A2:$A2"
        val A34 = "A3:$A3/A4:$A4"
        val B12 = "B1:$B1/B2:$B2"
        val B34 = "B3:$B3/B4:$B4"
        val C12 = "C1:$C1/C2:$C2"
        val C34 = "C3:$C3/C4:$C4"
        val D12 = "D1:$D1/D2:$D2"
        val D34 = "D3:$D3/D4:$D4"
        var str = ""
        if (A1.isNotBlank() && A2.isNotBlank()) {
            str += A12
            if (A3.isNotBlank() && A4.isNotBlank()) {
                str += "/$A34"
                if (B1.isNotBlank() && B2.isNotBlank()) {
                    str += "/$B12"
                    if (B3.isNotBlank() && B4.isNotBlank()) {
                        str += "/$B34"
                        if (C1.isNotBlank() && C2.isNotBlank()) {
                            str += "/$C12"
                            if (C3.isNotBlank() && C4.isNotBlank()) {
                                str += "/$C34"
                                if (D1.isNotBlank() && D2.isNotBlank()) {
                                    str += "/$D12"
                                    if (D3.isNotBlank() && D4.isNotBlank()) {
                                        str += "/$D34"
                                    }
                                }
                            } else if (D1.isNotBlank() && D2.isNotBlank()) {
                                str += "/$D12"
                                if (D3.isNotBlank() && D4.isNotBlank()) {
                                    str += "/$D34"
                                }
                            }
                        }
                    } else if (C1.isNotBlank() && C2.isNotBlank()) {
                        str += "/$C12"
                        if (C3.isNotBlank() && C4.isNotBlank()) {
                            str += "/$C34"
                            if (D1.isNotBlank() && D2.isNotBlank()) {
                                str += "/$D12"
                                if (D3.isNotBlank() && D4.isNotBlank()) {
                                    str += "/$D34"
                                }
                            }
                        } else if (D1.isNotBlank() && D2.isNotBlank()) {
                            str += "/$D12"
                            if (D3.isNotBlank() && D4.isNotBlank()) {
                                str += "/$D34"
                            }
                        }
                    }
                }
            } else if (B1.isNotBlank() && B2.isNotBlank()) {
                str += "/$B12"
                if (B3.isNotBlank() && B4.isNotBlank()) {
                    str += "/$B34"
                    if (C1.isNotBlank() && C2.isNotBlank()) {
                        str += "/$C12"
                        if (C3.isNotBlank() && C4.isNotBlank()) {
                            str += "/$C34"
                            if (D1.isNotBlank() && D2.isNotBlank()) {
                                str += "/$D12"
                                if (D3.isNotBlank() && D4.isNotBlank()) {
                                    str += "/$D34"
                                }
                            }
                        }
                        if (D1.isNotBlank() && D2.isNotBlank()) {
                            str += "/$D12"
                            if (D3.isNotBlank() && D4.isNotBlank()) {
                                str += "/$D34"
                            }
                        }
                    }
                }
                if (C1.isNotBlank() && C2.isNotBlank()) {
                    str += "/$C12"
                    if (C3.isNotBlank() && C4.isNotBlank()) {
                        str += "/$C34"
                        if (D1.isNotBlank() && D2.isNotBlank()) {
                            str += "/$D12"
                            if (D3.isNotBlank() && D4.isNotBlank()) {
                                str += "/$D34"
                            }
                        }
                    }
                    if (D1.isNotBlank() && D2.isNotBlank()) {
                        str += "/$D12"
                        if (D3.isNotBlank() && D4.isNotBlank()) {
                            str += "/$D34"
                        }
                    }
                }
            }
        }
        return str
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

    override fun onStart() {

        super.onStart()
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        count.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val targetW: Int = ivImage!!.width
            val targetH: Int = ivImage!!.height

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true

                val photoW: Int = outWidth
                val photoH: Int = outHeight

                // Determine how much to scale down the image
                val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)
                inJustDecodeBounds = false
                inSampleSize = 4
                inPurgeable = true
            }
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                ivImage!!.setImageBitmap(bitmap)
                ivImage!!.tag = "1"
            }

        }

    }
    private fun galleryAddPic(file : File) {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            mediaScanIntent.data = Uri.fromFile(file)
            this.requireContext().sendBroadcast(mediaScanIntent)
        }
    }




}