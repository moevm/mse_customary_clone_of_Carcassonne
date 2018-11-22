package carcassonne.se.carcassonnecustomclone

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_tiles.*

class TilesDialog : DialogFragment() {

    var parentActivity: AppCompatActivity? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        cancelButton.setOnClickListener {
            dismiss()
        }

        for (i in 1..10) {
            val tileRow = TableRow(parentActivity)
            val params = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
            tileRow.layoutParams = params
            val countRow = TableRow(parentActivity)
            countRow.layoutParams = params
            for(j in 1..4) {
                val tile = ImageView(parentActivity)
                tile.setImageResource(R.drawable.castle1)
                val imageParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
                imageParams.width = dpToPx(100)
                imageParams.height = dpToPx(100)
                tile.layoutParams = imageParams
                tileRow.addView(tile)
                val count = TextView(parentActivity, null, 0, R.style.PlayerName)
                count.text = "2/3"
                count.gravity = Gravity.CENTER_HORIZONTAL
                val countParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
                countParams.width = dpToPx(100)
                countRow.addView(count)
            }
            tiles.addView(tileRow, params)
            tiles.addView(countRow, params)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density + 0.5f).toInt()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_tiles, container)
    }
}