package com.yxf.vehicleinspection.utils

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Message
import android.view.View
import com.yxf.vehicleinspection.MyApp

/**
 *   author:yxf
 *   time:2021/10/15
 */
class DialogUtil(context: Context) : ProgressDialog(context){
    val dialog : ProgressDialog = ProgressDialog(context)

//    fun getBuilder(context: Context,message: String): AlertDialog.Builder{
//        return Builder(context).apply {
//            setTitle("提示信息")
//            setMessage(message)
//
//        }

    fun showLoadingDialog(){
        dialog.apply {
            setMessage("请稍后")
            setTitle("提示")
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            show()
        }

    }
    fun dismissDialog(){
        dialog.dismiss()
    }
}