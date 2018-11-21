package carcassonne.se.carcassonnecustomclone

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        setButtonListeners()
        setFullscreenMode(window)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        //TODO: я вот совсем не уверен что так делать хорошо
        showExitDialog()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullscreenMode(window)
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

        exitButton.setOnClickListener {
            showExitDialog()
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


    private fun showExitDialog() {
        val exitDialog = ExitDialog()
        exitDialog.parentActivity = this
        exitDialog.show(supportFragmentManager, "ExitDialog")
    }
}




