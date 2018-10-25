package carcassonne.se.carcassonnecustomclone

import android.graphics.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Hexagon(X: Float, Y: Float, side: Float, roundColor: Int, patternBitmap: Bitmap, choosed: Boolean) {
    var center: PointF
    var sideLen: Float
    private var mPath = Path()
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isChoosed: Boolean = false
    private var color: Int = 0
    private var selfColor: Int
    private var bitmap: Bitmap

    init {
        center = PointF(X, Y)
        sideLen = side
        selfColor = roundColor
        if (choosed)
            choose()
        else
            cancel()
        bitmap = patternBitmap
        mPath = getHexagonPath()

    }

    fun isChosen(): Boolean {
        return isChoosed
    }

    fun choose() {
        isChoosed = true
        mPaint.color = Color.TRANSPARENT
    }

    fun cancel() {
        isChoosed = false
        mPaint.color = selfColor
    }

    fun setColor(newColor: Int) {
        mPaint.color = color
        selfColor = color

    }

    fun getColor(): Int {
        return mPaint.color
    }

    fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    private fun calcHexVertex(number: Int): PointF {
        var angleDeg = 60 * number + 30
        var angleRad = PI / 180 * angleDeg
        return PointF(
            (center.x + sideLen * cos(angleRad)).toFloat(),
            (center.y + sideLen * sin(angleRad).toFloat())
        )
    }

    fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    private fun getHexagonPath(): Path {
        mPath.reset()
        mPath.moveTo(center.x, center.y + sideLen)
        for (i in 0..5) {
            var currVertex = calcHexVertex(i + 1)
            mPath.lineTo(currVertex.x, currVertex.y)
        }
        mPath.lineTo(center.x, center.y + sideLen)
        mPath.close()
        bitmap = Bitmap.createScaledBitmap(bitmap, (sideLen * sqrt(3f)).toInt(), (sideLen * 2).toInt(), false)
        return mPath
    }

    fun onBoundsChange(bounds: Rect?) {

    }


    fun setAlpha(alpha: Int) {
        mPaint.setAlpha(alpha)
    }


    fun draw(canvas: Canvas) {
        canvas.drawPath(mPath, mPaint)
        canvas.drawBitmap(bitmap, center.x - (bitmap.width / 2), center.y - (bitmap.height / 2), mPaint)
        //parent invalidate();

    }

}