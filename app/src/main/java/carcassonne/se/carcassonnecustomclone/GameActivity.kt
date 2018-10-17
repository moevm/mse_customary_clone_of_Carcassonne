package carcassonne.se.carcassonnecustomclone

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun calcHexVertex(center: PointF, size: Int, i: Int) : PointF
{
    var angleDeg = 60 * i + 30
    var angleRad = PI /180 * angleDeg
    return PointF(
        (center.x + size * cos(angleRad)).toFloat(),
        (center.y + size * sin(angleRad).toFloat())
    )
}

fun drawHex(center: PointF, size: Int, pincell: Paint, canvas: Canvas) : Boolean
{
    var previousPoint = calcHexVertex(center, size, 0)
    var currentPoint : PointF
    for(i in 1 .. 6)
    {
        currentPoint = calcHexVertex(center, size, i)
        canvas.drawLine(previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y, pincell)

        previousPoint = currentPoint
    }
    return true
}

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val layout1 = findViewById(R.id.layout1) as android.support.constraint.ConstraintLayout
        val canvass = Canvass(this)
        layout1.addView(canvass)
    }

    class Canvass(context: Context) : View(context) {

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(0,0,0)
            val ancho = getWidth()
            val alto = getHeight()
            val pincell = Paint()
            pincell.setStrokeWidth(4f)


            var size = 100;
            var widthScreenAlign = 100f
            var heightScreenAlign = 120f
            var center = PointF(widthScreenAlign, heightScreenAlign)



            var hexVertAlign = 3f/2 * size
            var hexHorizAlign =  (sqrt(3f) /2) * size

            var currHexHorizAlign = hexHorizAlign
            var currHexVertAlign = hexVertAlign

            var oddFlag = true

            while((center.y + hexVertAlign) < alto)
            {
                while((center.x + hexHorizAlign) < ancho)
                {
                    pincell.setARGB(255, (Math.random() * 255).toInt(), (Math.random() * 255).toInt(), (Math.random() * 255).toInt())
                    drawHex(center, size, pincell, canvas)
                    center.x += (hexHorizAlign*2) + 4
                }
                if(oddFlag == true)
                {
                    oddFlag = false
                    center.x = widthScreenAlign + hexHorizAlign + 2
                }
                else
                {
                    oddFlag = true
                    center.x = widthScreenAlign
                }
                center.y += hexVertAlign + 4

            }


            //center.set(center.x + hexHorizAlign, center.y + hexVertAlign)

            //drawHex(center, size, pincell, canvas)


        }
    }
}

