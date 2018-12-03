package carcassonne.se.carcassonnecustomclone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_players.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        setButtonListeners()
        hideSystemUI(window)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI(window)
    }


    private fun setButtonListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }
}
