package com.yxf.vehicleinspection.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.ListView
import androidx.appcompat.widget.AppCompatSpinner

/**
 *   author:yxf
 *   time:2021/12/9
 */
class MySpinner : AppCompatSpinner {
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

    override fun performClick(): Boolean {
        removeFocus()
        return super.performClick()
    }

    private fun removeFocus() {
        val view = (context as Activity).currentFocus
        view?.clearFocus()
    }
}