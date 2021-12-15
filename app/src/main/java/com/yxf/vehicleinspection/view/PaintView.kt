package com.yxf.vehicleinspection.view

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import com.yxf.vehicleinspection.utils.bitmap2Base64

/**
 * author:yxf
 * time:2021/10/9
 */
class PaintView(context: Context, screenWidth: Int, screenHeight: Int) :
    View(context) {
    private var mPaint: Paint = Paint()
    var path: Path
    private var mBitmap: Bitmap
    private var mCanvas: Canvas
    private var currentX = 0f
    private var currentY = 0f
    init {
        mPaint.isAntiAlias = true // 去除锯齿
        mPaint.strokeWidth = 30f //画笔宽度
        mPaint.style = Paint.Style.STROKE //style = 签字笔
        mPaint.color = Color.BLACK //画笔颜色
        mPaint.strokeCap = Paint.Cap.ROUND //线帽
        path = Path()
        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        mCanvas.drawColor(Color.WHITE);
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap, 0f, 0f, null)
        canvas.drawPath(path, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentX = x
                currentY = y
                path.moveTo(currentX, currentY)
            }
            MotionEvent.ACTION_MOVE -> {
                currentX = x
                currentY = y
                path.quadTo(currentX, currentY, x, y) // 画线
            }
            MotionEvent.ACTION_UP -> mCanvas.drawPath(path, mPaint)
        }
        invalidate()
        return true
    }

    val paintBitmap: Bitmap
        get() = resizeImage(mBitmap, 480, 320)

    val base64 : String
        get() = bitmap2Base64(paintBitmap)


    //清除画板
    fun clear() {
        path.reset()
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    companion object {
        // 缩放
        fun resizeImage(bitmap: Bitmap?, width: Int, height: Int): Bitmap {
            val originWidth = bitmap!!.width
            val originHeight = bitmap.height
            val scaleWidth = width.toFloat() / originWidth
            val scaleHeight = height.toFloat() / originHeight
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)
            return Bitmap.createBitmap(bitmap, 0, 0, originWidth,
                originHeight, matrix, true)
        }
    }


}
