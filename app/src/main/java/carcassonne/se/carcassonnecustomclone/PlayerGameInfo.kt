package carcassonne.se.carcassonnecustomclone

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.constraint.ConstraintLayout
import android.view.View
import kotlinx.android.synthetic.main.view_player_game_info.view.*

class PlayerGameInfo(context: Context, playerInfo: PlayerInfo) : ConstraintLayout(context) {


    var coinCount: Int = 0
        set(value) {
        field = value
        coinCounter.text = value.toString()
    }

    var figurineCount: Int = 7
        set(value) {
        field = value
        figurineCounter.text = value.toString()
    }

    init {
        View.inflate(context, R.layout.view_player_game_info, this)
        (playerIcon.background as? GradientDrawable)?.setColor(playerInfo.color)
        playerName.text = playerInfo.name
        coinCount = 0
        figurineCount = 7
    }







}