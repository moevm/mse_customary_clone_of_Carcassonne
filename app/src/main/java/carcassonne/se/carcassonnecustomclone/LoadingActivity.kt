package carcassonne.se.carcassonnecustomclone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        hideSystemUI(window)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemUI(window)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            val openGameActivity = Intent(this, GameActivity::class.java)
            openGameActivity.putParcelableArrayListExtra("players", intent.getParcelableArrayListExtra("players"))
            startActivity(openGameActivity)
            //TODO: ну это страшно
        }, 50)
    }


}