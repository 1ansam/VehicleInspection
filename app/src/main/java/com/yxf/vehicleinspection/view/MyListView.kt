package com.yxf.vehicleinspection.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

/**
 *   author:yxf
 *   time:2021/11/10
 */
class MyListView : ListView{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context,attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle : Int) : super(context, attrs,defStyle)
    constructor(context: Context, attrs: AttributeSet, defStyle : Int, defStyleRes : Int) : super(context, attrs,defStyle,defStyleRes)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2,MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
