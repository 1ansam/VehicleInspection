package com.yxf.vehicleinspection.imagepicker

import android.content.Context
import com.qingmei2.rximagepicker.entity.Result
import com.qingmei2.rximagepicker.entity.sources.Camera
import com.qingmei2.rximagepicker.entity.sources.Gallery
import io.reactivex.Observable

/**
 *   author:yxf
 *   time:2021/10/19
 *   implement:
 *          RxImagePicker
 *               .create(@clazz interface)
 *               .openGallery(@context)
 *               .subscribe(Consumer {
 *                   Glide.with(@fragmentActivity)
 *                       .load(it.uri)
 *                       .into(@imageView)
 *               })
 *
 *           RxImagePicker
 *               .create(@clazz interface)
 *               .openCamera(@context)
 *               .subscribe(Consumer {
 *                   Glide.with(@fragmentActivity)
 *                       .load(it.uri)
 *                       .into(@imageView)
 *               })
 */
interface DefaultImagePicker {
    //打开相册选择图片
    @Gallery
    fun openGallery(context: Context) : Observable<Result>
    // 打开相机拍照
    @Camera
    fun openCamera(context: Context): Observable<Result>
}