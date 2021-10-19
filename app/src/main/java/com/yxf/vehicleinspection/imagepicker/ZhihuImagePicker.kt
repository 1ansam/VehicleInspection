package com.yxf.vehicleinspection.imagepicker

import android.content.Context
import com.qingmei2.rximagepicker.entity.Result
import com.qingmei2.rximagepicker.entity.sources.Camera
import com.qingmei2.rximagepicker.entity.sources.Gallery
import com.qingmei2.rximagepicker.ui.ICustomPickerConfiguration
import com.qingmei2.rximagepicker_extension_zhihu.ui.ZhihuImagePickerActivity
import io.reactivex.Observable

/**
 *   author:yxf
 *   time:2021/10/19
 */
interface ZhihuImagePicker {
    //正常样式
    @Gallery(componentClazz = ZhihuImagePickerActivity::class,openAsFragment = false)
    fun openGalleryAsNormal(context: Context, config: ICustomPickerConfiguration) : Observable<Result>

    //dracula style
    @Gallery(componentClazz = ZhihuImagePickerActivity::class,openAsFragment = false)
    fun openGalleryAsDracula(context: Context, config: ICustomPickerConfiguration): Observable<Result>

    //打开相机拍照
    @Camera
    fun openCamera(context: Context): Observable<Result>
}