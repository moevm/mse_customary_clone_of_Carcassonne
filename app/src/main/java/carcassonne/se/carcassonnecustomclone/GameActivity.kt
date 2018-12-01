package carcassonne.se.carcassonnecustomclone


import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.math.abs
import kotlin.math.sqrt


class GameActivity : AppCompatActivity() {

    private var players: ArrayList<PlayerInfo>? = null

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI(window)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI(window)
        setContentView(R.layout.activity_game)
        val layout1 = findViewById(R.id.layout1) as ConstraintLayout
        val canvass = Canvass(this)
        layout1.addView(canvass)
        setButtonListeners()
        players = intent.getParcelableArrayListExtra("players")
        displayPlayers()
        okButton.visibility = View.INVISIBLE
        declineButton.visibility = View.INVISIBLE
        //TODO: нужно запилить методы для отображения этих кнопочек
    }

    /*Добавляет игроков на панель игроков слева*/
    private fun displayPlayers() {
        players?.forEach {
            addPlayer(it)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        //TODO: я вот совсем не уверен что так делать хорошо
        showPauseDialog()
    }

    private fun addPlayer(playerInfo: PlayerInfo) {
        val player = PlayerGameInfo(this, playerInfo)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val displayHeight = dm.heightPixels
        val margin = pxToDp((displayHeight - dpToPx(50) * 6) / 7)
        //TODO: ресурсы опять же
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 2 * margin, 0, 0)
        player.layoutParams = params
        playerInfoArea.addView(player)
    }


    private fun pxToDp(px: Int): Int {
        return (px / resources.displayMetrics.density + 0.5f).toInt()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }


    private fun showPauseDialog() {
        val pauseDialog = PauseDialog()
        pauseDialog.show(supportFragmentManager, "PauseDialog")
    }

    class Canvass : View {
        var side__ = 140f
        var hexagonesList = ArrayList<Hexagon>(0)
        var shouldInit = true
        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
            super.onLayout(changed, l, t, r, b)
            if (shouldInit) {
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
                var bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.wall1)
                var bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.wall2)
                var bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.wall3)
                var bitmap4 = BitmapFactory.decodeResource(resources, R.drawable.wall4)
                var bitmap5 = BitmapFactory.decodeResource(resources, R.drawable.wall5)
                var bitmap6 = BitmapFactory.decodeResource(resources, R.drawable.castle1)
                var bitmap7 = BitmapFactory.decodeResource(resources, R.drawable.castle2)
                var bitmap8 = BitmapFactory.decodeResource(resources, R.drawable.castle3)
                var bitmap9 = BitmapFactory.decodeResource(resources, R.drawable.castle4)
                var bitmap10 = BitmapFactory.decodeResource(resources, R.drawable.castle5)
                var bitmap11 = BitmapFactory.decodeResource(resources, R.drawable.cityblock1)
                var bitmap12 = BitmapFactory.decodeResource(resources, R.drawable.cityblock2)
                var bitmap13 = BitmapFactory.decodeResource(resources, R.drawable.cityblock3)
                while ((center.y + hexVertAlign) < alto) {
                    while ((center.x + hexHorizAlign) < ancho) {
                        var bitmap: Bitmap = when ((Math.random() * ((14 + 1) - 0) + 0).toInt()) {
                            0 -> bitmap1
                            1 -> bitmap2
                            2 -> bitmap3
                            3 -> bitmap4
                            4 -> bitmap5
                            5 -> bitmap6
                            6 -> bitmap7
                            7 -> bitmap8
                            8 -> bitmap9
                            9 -> bitmap10
                            10 -> bitmap11
                            11 -> bitmap12
                            12 -> bitmap13
                            else -> bitmap12
                        }
                        hexagonesList.add(
                            Hexagon(
                                center.x, center.y, size, Color.argb(
                                    255, (Math.random() * 255).toInt(),
                                    (Math.random() * 255).toInt(), (Math.random() * 255).toInt()
                                ), bitmap,
                                true
                            )
                        )
                        center.x += (hexHorizAlign * 2) + 4
                        println(center)
                    }
                    if (oddFlag) {
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

        constructor(context: Context) : super(context)

        fun getHexToPointDist(hexCenter: PointF, destPoint: PointF): Float {
            var deltaX = hexCenter.x - destPoint.x
            var deltaY = hexCenter.y - destPoint.y
            return (deltaX * deltaX) + (deltaY * deltaY)
        }

        fun getIndexHexOnTap(destPoint: PointF, radius: Float): Int {
            var bestIndex: Int = -1
            var bestDistance: Float = -1f
            for (i in 0..hexagonesList.size - 1) {
                if (((hexagonesList[i].center.x - destPoint.x) >= abs(radius)) || ((hexagonesList[i].center.y - destPoint.y) >= abs(
                        radius
                    ))
                )
                    continue

                var res = getHexToPointDist(hexagonesList[i].center, destPoint)
                if ((radius * radius >= res)) {
                    if (bestDistance == -1f) {
                        bestDistance = res
                        bestIndex = i
                    } else if (res < bestDistance) {
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
                    if (res != -1)
//                        if (!hexagonesList[res].isChosen())
                        hexagonesList[res].choose()
//                        else
//                            hexagonesList[res].cancel()
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

            canvas.drawRGB(0, 0, 0)
            val pincell = Paint()
            pincell.setStrokeWidth(4f)
            pincell.setARGB(
                255,
                (Math.random() * 255).toInt(),
                (Math.random() * 255).toInt(),
                (Math.random() * 255).toInt()
            )
            val roundPincell = Paint()
            roundPincell.setARGB(255, 255, 0, 0)
            roundPincell.setStyle(Paint.Style.STROKE);
            //center.set(center.x + hexHorizAlign, center.y + hexVertAlign)
            //var bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mygod)
            //canvas.drawBitmap(bitmap, 100f, 100f, pincell)
            //drawHex(center, size, pincell, canvas)

            for (elem in hexagonesList) {
                pincell.color = elem.getColor()
                elem.draw(canvas)
                //canvas.drawCircle(elem.center.x, elem.center.y, side__, roundPincell)
                println(elem.sideLen)
            }
        }
    }

    private fun setButtonListeners() {
        pauseButton.setOnClickListener {
            showPauseDialog()
        }
        okButton.setOnClickListener {

        }

        declineButton.setOnClickListener {

        }

        remainingTiles.setOnClickListener {
            val tilesDialog = TilesDialog()
            tilesDialog.show(supportFragmentManager, "TilesDialog")
        }
    }
}

