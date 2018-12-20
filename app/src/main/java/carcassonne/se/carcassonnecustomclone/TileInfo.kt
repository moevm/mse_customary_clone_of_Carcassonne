package carcassonne.se.carcassonnecustomclone

import android.graphics.Bitmap

class TileInfo(patternBitmap: Bitmap, sideTypes: ArrayList<sideType>)
{
    var bitmap: Bitmap
    var sides: ArrayList<sideType>

    init
    {
        bitmap = patternBitmap
        sides = sideTypes
    }

}