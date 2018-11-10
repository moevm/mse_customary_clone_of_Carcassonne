package carcassonne.se.carcassonnecustomclone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setFullscreenMode()
        setButtonListeners()
        configureBars()
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

    //TODO: Сделать нормально
    private fun configureBars() {
        effectsBar.max = 20
        musicBar.max = 20
        effectsBar.progress = 10
        musicBar.progress = 10
    }


    private fun setButtonListeners() {
        backButton.setOnClickListener {
            finish()
        }
        effectsMinus.setOnClickListener {
            effectsBar.progress--
        }
        effectsPlus.setOnClickListener {
            effectsBar.progress++
        }

        musicMinus.setOnClickListener {
            musicBar.progress--
        }
        musicPlus.setOnClickListener {
            musicBar.progress++
        }
    }
}

