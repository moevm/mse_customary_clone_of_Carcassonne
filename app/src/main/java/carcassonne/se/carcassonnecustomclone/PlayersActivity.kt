package carcassonne.se.carcassonnecustomclone

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.EditText

import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

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
        playerIcons.removeView(findViewById(R.id.addPlayerButton))
        addPlayerButton()
        setFullscreenMode()
        addAddButton()
        addPlayerName()
    }

    private fun addPlayerName() {
        val newPlayerName = TextView(this, null, R.style.PlayerName)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(10, 10, 10, 10)
        params.width = 300 //TODO: ширина
        newPlayerName.layoutParams = params
        newPlayerName.gravity = Gravity.CENTER_HORIZONTAL
        newPlayerName.text = players[players.size - 1]
        //TODO: сделать нормальный диалог
        newPlayerName.setOnClickListener {
            val editText = EditText(this)
            editText.setText("")
            val alert = AlertDialog.Builder(this)
            alert.setView(editText)
            alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->  })
            alert.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->  })
            alert.setTitle("Change player name")
            alert.show()
        }
        playerNames.addView(newPlayerName)
    }

    //TODO: пофиксить выбор цветов

    /*Добавляет иконку игрока в список*/
    fun addPlayerButton() {
        val newPlayerIcon = ImageButton(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(10, 10, 10, 10)
        newPlayerIcon.layoutParams = params
        newPlayerIcon.setImageResource(R.drawable.ic_player)
        newPlayerIcon.setBackgroundResource(R.drawable.circle)
        (newPlayerIcon.background as? GradientDrawable)?.setColor(Color.parseColor(resources.getStringArray(R.array.PlayerColors)[players.size]))
        newPlayerIcon.setOnClickListener {
            if (players.size > 2) {
                players.removeAt(players.lastIndex)
                playerIcons.removeView(newPlayerIcon)
                if (players.size == 5) {
                    addAddButton()
                }
            }
        }
        playerIcons.addView(newPlayerIcon)
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
            (newAddButton.background as? GradientDrawable)?.setColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorMenuButton
                )
            )
            newAddButton.setOnClickListener {
                addNewPlayer()
            }
            playerIcons.addView(newAddButton)
        }
    }
}



