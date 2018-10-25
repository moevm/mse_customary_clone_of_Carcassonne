package carcassonne.se.carcassonnecustomclone

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
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
            val openPlayersActivity = Intent(this, PlayersActivity::class.java)
            startActivity(openPlayersActivity)
        }

        rulesButton.setOnClickListener {
            val openRulesActivity = Intent(this, RulesActivity::class.java)
            startActivity(openRulesActivity)
        }

        //TODO добавить всплывающее окно с подтверждением выхода
        exitButton.setOnClickListener {
            finish()
        }

        settingsButton.setOnClickListener {
            val openSettingsActivity = Intent(this, SettingsActivity::class.java)
            startActivity(openSettingsActivity)
        }

        infoButton.setOnClickListener {
            val openInfoActivity = Intent(this, InfoActivity::class.java)
            startActivity(openInfoActivity)
        }


    }
}




