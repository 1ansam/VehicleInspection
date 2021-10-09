package com.yxf.vehicleinspection.view.activity

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.yxf.vehicleinspection.view.activity.PaintView
import android.view.View

/**
 * author:yxf
 * time:2021/10/9
 */
class PaintView(context: Context, private val screenWidth: Int, private val screenHeight: Int) :
    View(context) {
    private lateinit var mPaint: Paint
    lateinit var path: Path
        private set
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private var currentX = 0f
    private var currentY = 0f
    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true // 去除锯齿
        mPaint.strokeWidth = 5f
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.BLACK
        path = Path()
        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        //   mCanvas.drawColor(Color.WHITE);
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
        get() = resizeImage(mBitmap, 320, 480)

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