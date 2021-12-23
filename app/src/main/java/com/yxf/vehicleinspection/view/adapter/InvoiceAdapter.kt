package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.request.ChargeDetail
import com.yxf.vehicleinspection.databinding.ItemInvoiceBinding

/**
 *   author:yxf
 *   time:2021/12/23
 */
class InvoiceAdapter : BaseRvAdapter<ChargeDetail,ItemInvoiceBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRvViewHolder<ItemInvoiceBinding> {
        return BaseRvViewHolder(ItemInvoiceBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemInvoiceBinding>,
        position: Int,
        binding: ItemInvoiceBinding,
        bean: ChargeDetail
    ) {
        binding.apply {
            tvSpmc.text = bean.Goodsname
            tvNum.text = bean.Num
            tvAmt.text = bean.Zje
        }
    }
}