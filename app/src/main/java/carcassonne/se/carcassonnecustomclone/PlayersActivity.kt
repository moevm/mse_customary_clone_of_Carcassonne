package carcassonne.se.carcassonnecustomclone

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_players.*
import java.util.*

class PlayersActivity : AppCompatActivity() {

    var players: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        addNewPlayer()
        addNewPlayer()
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
    }

    /*Добавление нового игрока*/
    private fun addNewPlayer() {
        playerArea.removeView(findViewById(R.id.addPlayerButton))
        addPlayerButton()
        setFullscreenMode()
        addAddButton()
    }

    /*Добавляет кружок игрока в список*/
    fun addPlayerButton() {
        val newPlayer = ImageButton(this)
        val params =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(10, 10, 10, 10)
        newPlayer.layoutParams = params
        newPlayer.setImageResource(R.drawable.ic_player)
        newPlayer.setBackgroundResource(R.drawable.circle)
        val rnd = Random() // TODO: брать не рандомные цвета
        (newPlayer.background as? GradientDrawable)?.setColor(
            Color.argb(
                255,
                rnd.nextInt(256),
                rnd.nextInt(256),
                rnd.nextInt(256)
            )
        )
        newPlayer.setOnClickListener {
            if (players.size > 2) {
                players.removeAt(players.lastIndex)
                playerArea.removeView(newPlayer)
                if (players.size == 5) {
                    addAddButton()
                }
            }
        }
        playerArea.addView(newPlayer)
        players.add("Player${players.size + 1}") // TODO: нормально собирать инфу и отправлять ее
    }


    /*Добавляет кнопку добавления нового игрока*/
    private fun addAddButton() {
        if (players.size < 6) {
            val newAddButton = ImageButton(this)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 10, 10, 10)
            newAddButton.layoutParams = params
            newAddButton.setImageResource(R.drawable.ic_add)
            newAddButton.setBackgroundResource(R.drawable.circle)
            newAddButton.id = R.id.addPlayerButton
            (newAddButton.background as? GradientDrawable)?.setColor(resources.getColor(R.color.colorMenuButton))
            newAddButton.setOnClickListener {
                addNewPlayer()
            }
            playerArea.addView(newAddButton)
        }
    }
}



