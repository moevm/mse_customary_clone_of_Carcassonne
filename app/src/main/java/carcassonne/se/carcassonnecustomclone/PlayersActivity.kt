package carcassonne.se.carcassonnecustomclone

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_players.*

class PlayersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        setButtonListeners()
    }

    override fun onResume() {
        super.onResume()
        setFullscreenMode()
    }


    /*Set fullscreen mode*/
    private fun setFullscreenMode() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


    /*Устанавливает слушатели на кнопки меню*/
    private fun setButtonListeners() {
        playButton.setOnClickListener {
            val play = Intent(this, GameActivity::class.java)
            startActivity(play)
        }

        backButton.setOnClickListener {
            finish()
        }

        addPlayerButton.setOnClickListener {
            addPlayer()
        }
    }

    /*Добавление нового игрока*/
    private fun addPlayer() {
        val newPlayer = ImageButton(this)
        newPlayer.setImageResource(R.drawable.ic_player)
        newPlayer.setBackgroundResource(R.drawable.button_round)
        playerArea.addView(newPlayer)

    }
}



