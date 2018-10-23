package carcassonne.se.carcassonnecustomclone


import android.content.Context
import android.graphics.*

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.abs
import android.view.MotionEvent


class GameActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val layout1 = findViewById(R.id.layout1) as android.support.constraint.ConstraintLayout
        val canvass = Canvass(this)
        layout1.addView(canvass)
    }

    class Canvass : View{


        class Hexagon(X: Float, Y:Float, side: Float, roundColor: Int, choosed: Boolean)
        {
            var center: PointF
            var sideLen: Float
            var path = Path()
            private var isChoosed : Boolean = false
            private var color : Int = 0
            private var selfColor : Int

            init
            {
                center = PointF(X,Y)
                sideLen = side
                path = getHexagonPath()
                selfColor = roundColor
                if(choosed)
                    choose()
                else
                    unchoose()
            }

            fun isChoosed() : Boolean
            {
                return isChoosed
            }

            fun choose()
            {
                isChoosed = true
                color = Color.BLACK
            }

            fun unchoose()
            {
                isChoosed = false
                color = selfColor
            }

            fun setColor(newColor: Int)
            {
                color = newColor
                selfColor = color
            }

            fun getColor() : Int
            {
                return color
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
            private fun getHexagonPath() : Path
            {
                path.reset()
                path.moveTo(center.x, center.y + sideLen)
                for(i in 0 .. 5)
                {
                    var currVertex = calcHexVertex(i+1)
                    path.lineTo(currVertex.x, currVertex.y)
                }
                path.lineTo(center.x, center.y + sideLen)
                path.close()
                return path
            }


            fun draw(canvas: Canvas, pincell: Paint)
            {
                canvas.drawPath(path, pincell)
                //parent invalidate();
            }

        }
        var side__ = 112f
        var hexagonesList = ArrayList<Hexagon>(0)
        var shouldInit = true
        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
            super.onLayout(changed, l, t, r, b)
            if(shouldInit) {
                //Here you can get the size!
                val ancho = width
                val alto = height
                var oddFlag = true
                var size = side__
                var widthScreenAlign = side__
                var heightScreenAlign = side__ + side__ * 1 / 5
                var center = PointF(widthScreenAlign, heightScreenAlign)

                var hexVertAlign = 3f / 2 * size
                var hexHorizAlign = (sqrt(3f) / 2) * size
                while ((center.y + hexVertAlign) < alto) {
                    while ((center.x + hexHorizAlign) < ancho) {
                        hexagonesList.add(Hexagon(center.x, center.y, size, Color.argb(255, (Math.random() * 255).toInt(), (Math.random() * 255).toInt(), (Math.random() * 255).toInt()), true ))
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
                shouldInit = false
            }
            println("hello")
        }

        constructor(context: Context): super(context){


        }

        fun getHexToPointDist(hexCenter :PointF, destPoint: PointF) : Float
        {
            var deltaX = hexCenter.x - destPoint.x
            var deltaY = hexCenter.y - destPoint.y
            return (deltaX * deltaX) + (deltaY * deltaY)
        }

        fun getIndexHexOnTap(destPoint: PointF, radius: Float) : Int
        {
            var bestIndex: Int = -1
            var bestDistance: Float = -1f
            for (i in 0 .. hexagonesList.size-1)
            {
                if(((hexagonesList[i].center.x - destPoint.x) >= abs(radius)) || ((hexagonesList[i].center.y - destPoint.y) >= abs(radius)))
                    continue

                var res = getHexToPointDist(hexagonesList[i].center, destPoint)
                if((radius * radius >= res))
                {
                    if(bestDistance == -1f)
                    {
                        bestDistance = res
                        bestIndex = i
                    }
                    else if(res < bestDistance)
                    {
                        bestDistance = res
                        bestIndex = i
                        break
                    }

                }
            }
            return bestIndex
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
                    var res = getIndexHexOnTap(PointF(event.x, event.y), side__)
                    if(res != -1)
                        if(!hexagonesList[res].isChoosed())
                            hexagonesList[res].choose()
                        else
                            hexagonesList[res].unchoose()
                    invalidate()
                    //hexagonesList.add(Hexagon(event.x, event.y, side__, Color.MAGENTA))
                    //println(hexagonesList.last())
                    //invalidate()
                }
            }

            return true
        }
        var drawBackground = true
        override fun onDraw(canvas: Canvas) {

            canvas.drawRGB(0,0,0)
            val pincell = Paint()
            pincell.setStrokeWidth(4f)
            pincell.setARGB(255, (Math.random() * 255).toInt(), (Math.random() * 255).toInt(), (Math.random() * 255).toInt())
            val roundPincell = Paint()
            roundPincell.setARGB(255,255,0,0)
            roundPincell.setStyle(Paint.Style.STROKE);
            for(elem in hexagonesList) {
                pincell.color = elem.getColor()
                elem.draw(canvas, pincell)
                //canvas.drawCircle(elem.center.x, elem.center.y, side__, roundPincell)
                println(elem.sideLen)
            }

            //center.set(center.x + hexHorizAlign, center.y + hexVertAlign)

            //drawHex(center, size, pincell, canvas)


        }
    }
}

