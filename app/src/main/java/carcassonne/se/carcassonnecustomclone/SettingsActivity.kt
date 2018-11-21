package carcassonne.se.carcassonnecustomclone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setButtonListeners()
        configureBars()
        setFullscreenMode(window)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setFullscreenMode(window)
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

