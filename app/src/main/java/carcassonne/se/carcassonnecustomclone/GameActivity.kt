package carcassonne.se.carcassonnecustomclone


import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import carcassonne.se.carcassonnecustomclone.zoom.ZoomLayout
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.view.*
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.sqrt

class GameActivity : AppCompatActivity() {

    private var players: ArrayList<PlayerInfo>? = null
    private var currentPlayerIndex: Int = -1

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI(window)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI(window)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val canvass = Canvass(this)
        canvass.layoutParams = ViewGroup.LayoutParams(4000, 4000)
        zoomLayout.addView(canvass)
        setButtonListeners()
        players = intent.getParcelableArrayListExtra("players")
        displayPlayers()
        currentTile.setTile(R.drawable.castle1)
        nextPlayer()
        hideOkButton()
        hideDeclineButton()
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
        val margin = pxToDp((displayHeight - resources.getDimension(R.dimen.menu_button_height).toInt() * 6) / 7)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, (margin * 1.5).toInt(), 0, 0)
        player.layoutParams = params
        playerInfoArea.addView(player)
    }


    private fun pxToDp(px: Int): Int {
        return (px / resources.displayMetrics.density + 0.5f).toInt()
    }



    private fun showPauseDialog() {
        val pauseDialog = PauseDialog()
        pauseDialog.show(supportFragmentManager, "PauseDialog")
    }

    class Canvass : View {
        var side__ = 180f
        private var zoomContainer: ZoomLayout? = null
        var hexagonesList = ArrayList<ArrayList<Hexagon>>(0)
        var shouldInit = true
        var tiles: ArrayList<TileInfo> = ArrayList()
        var defaultTile: TileInfo
        var currentTile: TileInfo
        var xTilesMax: Int = 0
        var yTilesMax: Int = 0

        constructor(context: Context) : super(context) {
            val tileResources = resources.getStringArray(R.array.TilesInfo)

            for (i in 0 until tileResources.size) {
                var tileDrawableId: Int = 0
                try {
                    tileDrawableId = resources.getIdentifier("tile${i + 1}", "drawable", context.packageName)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val tileSidesType = ArrayList<sideType>()
                for (j in 0..5) {
                    tileSidesType.add(sideType.values()[tileResources[i][j].toString().toInt()])
                }
                val bitmap = BitmapFactory.decodeResource(resources, tileDrawableId)
                for (j in 0 until tileResources[i][6].toString().toInt()) {
                    tiles.add(TileInfo(bitmap, tileSidesType))
                }
            }

            val defaultSides = ArrayList<sideType>()
            for (i in 0..5) {
                defaultSides.add(sideType.EMPTY)
            }
            defaultTile = TileInfo(
                    BitmapFactory.decodeResource(resources, R.drawable.default_tile),
                    defaultSides
            )

            currentTile = getNextTile()
        }

        fun getNextTile(): TileInfo
        {
            if(tiles.size == 0)
                return defaultTile
            val rand = (Math.random() * tiles.size).toInt()
            val result = tiles[rand]
            tiles.removeAt(rand)
            return result
        }

        override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
            super.onLayout(changed, l, t, r, b)
            zoomContainer = parent as ZoomLayout
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




                while ((center.y + hexVertAlign) < alto) {
                    hexagonesList.add(ArrayList(0))
                    while ((center.x + hexHorizAlign) < ancho) {

                        hexagonesList.last().add(
                            Hexagon(
                                center.x,
                                center.y,
                                size,
                                Color.argb(
                                    255, (Math.random() * 255).toInt(),
                                    (Math.random() * 255).toInt(), (Math.random() * 255).toInt()
                                ),
                                defaultTile.bitmap,
                                defaultTile.sides,
                                false
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
            xTilesMax = hexagonesList[0].size-1
            yTilesMax = hexagonesList.size-1
        }

        // ШТО ДЕЛОТЬ ИЛИ ТЫ ЗАБЫЛ ИЛИ ТЫ НЕ ЗНАЛ?
        // 1. Берём рандомный битмап и выставляем его на карту, кнопки поворота опционально или шото вроде
        // 2. Проверку на возможность установить гекс
        // 3. Поле шире чем экран
        // 4. Придётся прихуярить нормальную структуру почти везде,но ок
        // 5. Ебать копать пиздец дарова


        fun getAdjacentHex(hexIndex: Point, sideId: Int): Point
        {
            var isOdd = false
            var result = Point()
            if(hexIndex.y % 2 == 0)
            {
                isOdd = true
            }

            if(sideId == 0)
            {
                result.x = hexIndex.x
                result.y = hexIndex.y - 1
            }
            else if(sideId == 1)
            {
                result.x = hexIndex.x + 1
                result.y = hexIndex.y
                return result
            }
            else if(sideId == 2)
            {
                result.x = hexIndex.x
                result.y = hexIndex.y + 1
            }
            else if(sideId == 3)
            {
                result.x = hexIndex.x - 1
                result.y = hexIndex.y + 1
            }
            else if(sideId == 4)
            {
                result.x = hexIndex.x - 1
                result.y = hexIndex.y
                return result
            }
            else if(sideId == 5)
            {
                result.x = hexIndex.x - 1
                result.y = hexIndex.y - 1
            }
            if(!isOdd)
            {
                result.x += 1
            }
            return result
        }

        fun checkCoordsOverflow(coords: Point) : Boolean
        {
            var tmp = coords.x
            if(coords.y % 2 != 0)
            {
                tmp+=1
            }

            if(coords.x < 0 || coords.y < 0)
                return true


            if(tmp > xTilesMax || coords.y > yTilesMax)
                return true

            return false
        }

        fun getHexToPointDist(hexCenter: PointF, destPoint: PointF): Float {
            var deltaX = hexCenter.x - destPoint.x
            var deltaY = hexCenter.y - destPoint.y
            return (deltaX * deltaX) + (deltaY * deltaY)
        }

        fun getIndexHexOnTap(destPoint: PointF, radius: Float): Point {
            var result = Point(-1,-1)
            var bestDistance: Float = -1f
            for (i in 0..hexagonesList.size - 1) {
                for(j in 0..hexagonesList[i].size-1){

                    if (((hexagonesList[i][j].center.x - destPoint.x) >= abs(radius)) || ((hexagonesList[i][j].center.y - destPoint.y) >= abs(
                            radius
                        ))
                    )
                        continue

                    var res = getHexToPointDist(hexagonesList[i][j].center, destPoint)
                    if ((radius * radius >= res)) {
                        if (bestDistance == -1f) {
                            bestDistance = res
                            result.x = j
                            result.y = i
                        } else if (res < bestDistance) {
                            bestDistance = res
                            result.x = j
                            result.y = i
                            return result
                        }

                    }
                }
            }
            return result
        }

        val MAX_CLICK_DISTANCE = 20
        var pressedX = 0f
        var pressedY = 0f
        private val MAX_CLICK_DURATION = 300
        private var timeDown = 0L

        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    timeDown = System.currentTimeMillis()
                }
                MotionEvent.ACTION_UP -> {
                    if(System.currentTimeMillis() - timeDown < MAX_CLICK_DURATION) {
                        val shiftX = (zoomContainer?.panX ?: 0f)
                    val shiftY = (zoomContainer?.panY ?: 0f)
                    val zoom = (zoomContainer?.realZoom ?: 1f)
                    var res = getIndexHexOnTap(PointF(event.x / zoom - shiftX, event.y / zoom - shiftY), side__ )
                    if (res.x != -1)
                    {
                        if(currentTile == defaultTile || hexagonesList[res.y][res.x].isChosen())
                            return true
                        for(i in 0..5)
                        {
                            var tmp = getAdjacentHex(res, i)
                            if(checkCoordsOverflow(tmp))
                                continue

                            var currSide: sideType = currentTile.sides[i]
                            var adjacentSide: sideType = hexagonesList[tmp.y][tmp.x].sides[(i+3)%6]
                            if((currSide != adjacentSide) && (adjacentSide != sideType.EMPTY))
                                return true
                        }
                        hexagonesList[res.y][res.x].placeOnMap(currentTile)
                        currentTile = getNextTile()
                    }

//                        if (!hexagonesList[res].isChosen())
                            //hexagonesList[res].choose()
//                        else
//                            hexagonesList[res].cancel()
                    invalidate()
                    //hexagonesList.add(Hexagon(event.x, event.y, side__, Color.MAGENTA))
                    //println(hexagonesList.last())
                    //invalidate()
                    }

                    

                }

            }
            return true
        }



        var drawBackground = true
        override fun onDraw(canvas: Canvas) {
            canvas.drawColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
//            val pincell = Paint()
//            pincell.strokeWidth = 4f
//            pincell.setARGB(
//                255,
//                (Math.random() * 255).toInt(),
//                (Math.random() * 255).toInt(),
//                (Math.random() * 255).toInt()
//            )
//            val roundPincell = Paint()
//            roundPincell.setARGB(255, 255, 0, 0)
//            roundPincell.style = Paint.Style.STROKE
            //center.set(center.x + hexHorizAlign, center.y + hexVertAlign)
            //var bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mygod)
            //canvas.drawBitmap(bitmap, 100f, 100f, pincell)
            //drawHex(center, size, pincell, canvas)

            for (hexagonesString in hexagonesList) {
                for (elem in hexagonesString)
                {
                    pincell.color = elem.getColor()
                    elem.draw(canvas)
                    //canvas.drawCircle(elem.center.x, elem.center.y, side__, roundPincell)
                    println(elem.sideLen)

                }
            }


        }

    }

    private fun nextPlayer() {
        currentPlayerInfo()?.setCurrent(false)
        if(currentPlayerIndex != players?.size?.minus(1)) {
            currentPlayerIndex++
        } else {
            currentPlayerIndex = 0
        }
        currentPlayerInfo()?.setCurrent(true)
    }

    private fun currentPlayerInfo(): PlayerGameInfo? {
        return if(currentPlayerIndex in 0..(players?.size ?: 0)) {
            (playerInfoArea.getChildAt(currentPlayerIndex) as PlayerGameInfo)
        } else {
            null
        }
    }


    private fun showOkButton() {
        okButton.visibility = View.VISIBLE
    }

    private fun showDeclineButton() {
        declineButton.visibility = View.VISIBLE
    }

    private fun hideOkButton() {
        okButton.visibility = View.INVISIBLE
    }

    private fun hideDeclineButton() {
        declineButton.visibility = View.INVISIBLE
    }




    private fun setButtonListeners() {
        currentTileImage.setOnClickListener{

        }


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

