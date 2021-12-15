package com.yxf.vehicleinspection.view.adapter

import android.graphics.Color
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.res.colorResource
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.databinding.ItemRegisterBinding

/**
 *   author:yxf
 *   time:2021/12/3
 */
class RegisterListAdapter() : BaseRvAdapter<String,ItemRegisterBinding>() {
    var value : ArrayList<String?> = ArrayList<String?>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemRegisterBinding> {
        return BaseRvViewHolder(ItemRegisterBinding.inflate(LayoutInflater.from(parent.context,),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemRegisterBinding>,
        position: Int,
        binding: ItemRegisterBinding,
        bean: String,
    ) {
        binding.key.text = bean
//        when{
//            binding.key.text.toString() == "里程表读数" -> binding.key.setTextColor(Color.parseColor("#FF0000"))
//        }
        setTextInput(binding)
        if (!value.isNullOrEmpty()){
            binding.value.setText(value[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
    private fun setTextInput(binding: ItemRegisterBinding,){
        when(binding.key.text.toString()){
            "联系电话" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(11))
                }
            }
            "驱动轴数" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(1))
                }
            }
            "驻车轴数" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(1))
                }
            }
            "轴数" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(1))
                }
            }
            "轴距" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(5))
                }
            }
            "主轴数" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(1))
                }
            }
            "前轮距" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(4))
                }
            }
            "后轮距" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(4))
                }
            }
            "总质量" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(8))
                }
            }
            "整备质量" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(8))
                }
            }
            "核定载质量" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(8))
                }
            }
            "核定载客" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(3))
                }
            }
            "准牵引质量" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(8))
                }
            }
            "核定载客" -> {
                binding.value.apply {
                    inputType = InputType.TYPE_CLASS_NUMBER
                    filters = arrayOf(InputFilter.LengthFilter(3))
                }
            }

        }
    }
}