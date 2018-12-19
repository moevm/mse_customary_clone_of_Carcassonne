package carcassonne.se.carcassonnecustomclone

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setButtonListeners()
        configureBars()
        setSeekbarListeners()
        loadPreferences()
    }



    private fun saveEffectsVolume(progress: Int) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putInt("effectsVolume", progress)
        editor.apply()
    }

    private fun saveMusicVolume(progress: Int) {
        val editor = PreferenceManager.getDefaultSharedPreferences(this).edit()
        editor.putInt("musicVolume", progress)
        editor.apply()
    }

    private fun loadPreferences() {
        effectsBar.progress = PreferenceManager.getDefaultSharedPreferences(this).getInt("effectsVolume", 10)
        musicBar.progress = PreferenceManager.getDefaultSharedPreferences(this).getInt("musicVolume", 10)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI(window)
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI(window)
    }


    private fun configureBars() {
        effectsBar.max = 20
        musicBar.max = 20
    }

    private fun setSeekbarListeners() {
        effectsBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                saveEffectsVolume(i)
                //TODO: неплохо было бы издавать какой-нибудь звук чтобы было понятно что за громкость
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        musicBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                saveMusicVolume(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
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

