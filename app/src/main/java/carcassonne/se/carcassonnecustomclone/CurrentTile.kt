package carcassonne.se.carcassonnecustomclone

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.view_current_tile.view.*


class CurrentTile : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    init {
        View.inflate(context, R.layout.view_current_tile, this)
        this.setOnClickListener {
            //Заглушечка
        }
    }

    fun setTile(tileResourceId : Int) {
        tileImage.setImageResource(tileResourceId)
    }

}