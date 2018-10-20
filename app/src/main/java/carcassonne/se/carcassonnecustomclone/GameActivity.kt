package carcassonne.se.carcassonnecustomclone

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.opengl.ETC1
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MotionEventCompat
import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_POINTER_DOWN
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.ViewTreeObserver
import android.opengl.ETC1.getHeight







class GameActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val layout1 = findViewById(R.id.layout1) as android.support.constraint.ConstraintLayout
        val canvass = Canvass(this)
        layout1.addView(canvass)
    }



    class Canvass : View{


        class Hexagon(X: Float, Y:Float, side: Float)
        {
            var center: PointF
            var sideLen: Float

            init
            {
                center = PointF(X,Y)
                sideLen = side
            }

            private fun calcHexVertex(number: Int) : PointF
            {
                var angleDeg = 60 * number + 30
                var angleRad = PI /180 * angleDeg
                return PointF(
                    (center.x + sideLen * cos(angleRad)).toFloat(),
                    (center.y + sideLen * sin(angleRad).toFloat())
                )
            }

            private fun getVerticesCoords() : List<PointF>
            {
                var result = Array(6, {PointF(0f,0f)})
                for(i in 0 .. 5)
                {
                    result[i] = calcHexVertex(i)
                }
                return result.toList()
            }

            fun draw(canvas: Canvas, pincell: Paint)
            {
                var vertices = getVerticesCoords()
                for (i in 1..5)
                    canvas.drawLine(vertices[i - 1].x, vertices[i - 1].y, vertices[i].x, vertices[i].y, pincell)
                canvas.drawLine(vertices[5].x, vertices[5].y, vertices[0].x, vertices[0].y, pincell)
                //parent invalidate();
            }

        }
        var hexagonesList = ArrayList<Hexagon>(0)
        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
            super.onLayout(changed, l, t, r, b)

            //Here you can get the size!
            val ancho = width
            val alto = height
            var oddFlag = true
            var size = 100fЛ
            var widthScreenAlign = 100f
            var heightScreenAlign = 120f
            var center = PointF(widthScreenAlign, heightScreenAlign)

            var hexVertAlign = 3f/2 * size
            var hexHorizAlign =  (sqrt(3f) /2) * size
            while ((center.y + hexVertAlign) < alto) {
                while ((center.x + hexHorizAlign) < ancho) {
                    hexagonesList.add(Hexagon(center.x, center.y, 100f))
                    center.x += (hexHorizAlign * 2) + 4
                    println(center)
                }
                if (oddFlag == true) {
                    oddFlag = false
                    center.x = widthScreenAlign + hexHorizAlign + 2
                } else {
                    oddFlag = true
                    center.x = widthScreenAlign
                }
                center.y += hexVertAlign + 4

            }
        }

        constructor(context: Context): super(context){


        }


        override fun onTouchEvent(event: MotionEvent): Boolean {
            // MotionEvent reports input details from the touch screen
            // and other input controls. In this case, you are only
            // interested in events where the touch position  changed.

            val x: Float = event.x
            val y: Float = event.y

            println(x)
            println(y)
            MotionEvent.ACTION_DOWN
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    hexagonesList.add(Hexagon(event.x, event.y, 100f))
                    println(hexagonesList.last())
                    invalidate()
                }
            }

            return true
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawRGB(0,0,0)
            val pincell = Paint()
            pincell.setStrokeWidth(4f)
            pincell.setARGB(255, (Math.random() * 255).toInt(), (Math.random() * 255).toInt(), (Math.random() * 255).toInt())
            for(elem in hexagonesList) {
                elem.draw(canvas, pincell)
                println(elem.sideLen)
            }

            //center.set(center.x + hexHorizAlign, center.y + hexVertAlign)

            //drawHex(center, size, pincell, canvas)


        }
    }
}

