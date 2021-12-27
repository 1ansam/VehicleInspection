package com.yxf.vehicleinspection.view

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

/**
 *   author:yxf
 *   time:2021/12/27
 */
class MyVideoView : VideoView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context, attrs: AttributeSet, defStyle: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyle,
        defStyleRes
    )
    init {
        isFocusable = false
        isFocusableInTouchMode = false
        clearFocus()
    }
}