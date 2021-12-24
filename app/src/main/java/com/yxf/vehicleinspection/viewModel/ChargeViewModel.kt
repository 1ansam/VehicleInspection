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
    fun getChargeStatus(oid : String) : LiveData<Boolean> {
        return chargeRepository.getChargeStatus(oid)
    }
    fun getChargeInfo(ajlsh: String) : LiveData<SaveChargeInfoW004Request>{
        return chargeRepository.getChargeInfo(ajlsh)
    }
    fun postChargePayment(saveChargeInfoW004Request: SaveChargeInfoW004Request): LiveData<Boolean> {
        return chargeRepository.postChargePayment(saveChargeInfoW004Request)
    }
    fun getInvoiceParams() : LiveData<InvoiceParamsR025Response>{
        return chargeRepository.getInvoiceParams()
    }
    fun postInvoice(saveInvoiceW005Request: SaveInvoiceW005Request) : LiveData<Boolean> {
        return chargeRepository.postInvoice(saveInvoiceW005Request)
    }
    fun getBuyerParams(buyerParamsR026Request: BuyerParamsR026Request): LiveData<BuyerParamsR026Response> {
        return chargeRepository.getBuyerParams(buyerParamsR026Request)
    }
}
class ChargeViewModelFactory(private val chargeRepository: ChargeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChargeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChargeViewModel(chargeRepository) as T
        }
        throw IllegalArgumentException("未知ViewModel")
    }
}