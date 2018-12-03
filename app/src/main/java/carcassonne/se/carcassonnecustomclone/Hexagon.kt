package carcassonne.se.carcassonnecustomclone

import android.graphics.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.R.attr.angle



class Hexagon(X: Float, Y: Float, side: Float, roundColor: Int, patternBitmap: Bitmap, choosed: Boolean) {
    var center: PointF
    var sideLen: Float
    private var mPath = Path()
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isChoosed: Boolean = false
    private var color: Int = 0
    private var selfColor: Int
    private var bitmap: Bitmap
    private var tapCounter: Int = 0
    private var realBitmap: Bitmap

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
        realBitmap = bitmap

    }

    fun isChosen(): Boolean {
        return isChoosed
    }

    fun choose() {
        if(tapCounter == 0)
        {
            cancel()
            tapCounter++
        }
        else if(tapCounter < 7)
        {
            rotate()
            tapCounter++
        }
        else
        {
            tapCounter = 0
            isChoosed = true
            mPaint.color = Color.TRANSPARENT
        }
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
        realBitmap = Bitmap.createScaledBitmap(bitmap, (sideLen * sqrt(3f)).toInt(), (sideLen * 2).toInt(), false)
        bitmap = realBitmap
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
        //canvas.drawCircle( center.x - (realBitmap.width / 2), center.y - (realBitmap.height / 2), sideLen/10,mPaint)
        //parent invalidate();

    }

    fun rotate()
    {
        val angle:Float = (60*tapCounter).toFloat()
        val matrix = Matrix()
        matrix.postRotate(angle)
        //matrix.postScale(0.7f, 0.7f)
        val rotatedBitmap = Bitmap.createBitmap(this.realBitmap, 0, 0,
            this.realBitmap.width, this.realBitmap.height, matrix, true)
        //val x_Offset = (sideLen/2) * cos(30f)
        //val y_Offset = (sideLen/2) * cos(60f) //TODO сделать нормально, а не ущербно
        bitmap = rotatedBitmap
    }

}