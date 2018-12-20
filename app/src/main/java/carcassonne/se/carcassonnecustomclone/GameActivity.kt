package carcassonne.se.carcassonnecustomclone


import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_game.*
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.sqrt

class GameActivity : AppCompatActivity() {

    private var players: ArrayList<PlayerInfo>? = null

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullscreenMode(window)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullscreenMode(window)
        setContentView(R.layout.activity_game)
        val layout1 = findViewById<ConstraintLayout>(R.id.layout1)
        val canvass = Canvass(this)
        layout1.addView(canvass)
        setButtonListeners()
        players = intent.getParcelableArrayListExtra("players")
        displayPlayers()
        okButton.visibility = View.INVISIBLE
        declineButton.visibility = View.INVISIBLE
    }

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
        //TODO: сделать чтобы растягивалось равномерно
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 15, 0, 15)
        player.layoutParams = params

        playerInfoArea.addView(player)
    }


    private fun showPauseDialog() {
        val pauseDialog = PauseDialog()
        pauseDialog.parentActivity = this
        pauseDialog.show(supportFragmentManager, "PauseDialog")
    }

    class Canvass : View {
        var side__ = 100f
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
                tiles.add(TileInfo(BitmapFactory.decodeResource(resources, tileDrawableId), tileSidesType))
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
                    if (res.x != -1)
                    {
                        if(currentTile == defaultTile || hexagonesList[res.y][res.x].isChosen())
                            return true
                        hexagonesList[res.y][res.x].placeOnMap(currentTile)
                        currentTile = getNextTile()
                        for(i in 0..5)
                        {
                            var tmp = getAdjacentHex(res, i)
                            if(checkCoordsOverflow(tmp))
                                continue
                            hexagonesList[tmp.y][tmp.x].show()
                        }
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

            return true
        }

        var drawBackground = true
        override fun onDraw(canvas: Canvas) {

            canvas.drawRGB(0, 0, 0)
            val pincell = Paint()
            pincell.strokeWidth = 4f
            pincell.setARGB(
                255,
                (Math.random() * 255).toInt(),
                (Math.random() * 255).toInt(),
                (Math.random() * 255).toInt()
            )
            val roundPincell = Paint()
            roundPincell.setARGB(255, 255, 0, 0)
            roundPincell.style = Paint.Style.STROKE
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
            tilesDialog.parentActivity = this
            tilesDialog.show(supportFragmentManager, "TilesDialog")
        }
    }
}

