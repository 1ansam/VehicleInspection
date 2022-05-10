package com.yxf.vehicleinspection.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yxf.vehicleinspection.bean.request.*
import com.yxf.vehicleinspection.bean.response.BuyerParamsR026Response
import com.yxf.vehicleinspection.bean.response.InvoiceParamsR025Response
import com.yxf.vehicleinspection.repository.ChargeRepository
import java.lang.IllegalArgumentException

/**
 *   author:yxf
 *   time:2021/12/22
 */
class ChargeViewModel(private val chargeRepository: ChargeRepository) : ViewModel() {
    /**
     * 从服务器获取收费状态
     * @param oid 订单编号
     */
    fun getChargeStatus(oid : String) : LiveData<Boolean> {
        return chargeRepository.getChargeStatus(oid)
    }
    /**
     * 获取收费信息
     * @param ajlsh 安检流水号
     */
    fun getChargeInfo(ajlsh: String) : LiveData<SaveChargeInfoW004Request>{
        return chargeRepository.getChargeInfo(ajlsh)
    }
    /**
     * 上传收费信息
     */
    fun postChargePayment(saveChargeInfoW004Request: SaveChargeInfoW004Request): LiveData<Boolean> {
        return chargeRepository.postChargePayment(saveChargeInfoW004Request)
    }
    /**
     * 从服务器获取开票信息
     */
    fun getInvoiceParams() : LiveData<InvoiceParamsR025Response>{
        return chargeRepository.getInvoiceParams()
    }
    /**
     * 上传开票信息
     */
    fun postInvoice(saveInvoiceW005Request: SaveInvoiceW005Request) : LiveData<Boolean> {
        return chargeRepository.postInvoice(saveInvoiceW005Request)
    }
    /**
     * 获取客户信息
     */
    fun getBuyerParams(buyerParamsR026Request: BuyerParamsR026Request): LiveData<BuyerParamsR026Response> {
        return chargeRepository.getBuyerParams(buyerParamsR026Request)
    }
}
class ChargeViewModelFactory(private val chargeRepository: ChargeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChargeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChargeViewModel(chargeRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}