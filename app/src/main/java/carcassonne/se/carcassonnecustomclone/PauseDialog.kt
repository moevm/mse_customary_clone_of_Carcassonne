package carcassonne.se.carcassonnecustomclone

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_pause.*

class PauseDialog : DialogFragment() {

    var parentActivity: AppCompatActivity? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        resumeButton.setOnClickListener {
            dismiss()
        }

        rulesButton.setOnClickListener {
            val openRulesActivity = Intent(parentActivity, RulesActivity::class.java)
            startActivity(openRulesActivity)
        }

        exitButton.setOnClickListener {
            val openMainMenuActivity = Intent(parentActivity, MainMenuActivity::class.java)
            openMainMenuActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            openMainMenuActivity.putExtra("EXIT", true)
            startActivity(openMainMenuActivity)
            //TODO: возможно тут же стоит закрывать окно игры, но я не уверен что это правильно
            //TODO: подтверждение выхода
        }

        settingsButton.setOnClickListener {
            val openSettingsActivity = Intent(parentActivity, SettingsActivity::class.java)
            startActivity(openSettingsActivity)
        }

        infoButton.setOnClickListener {
            val openInfoActivity = Intent(parentActivity, InfoActivity::class.java)
            startActivity(openInfoActivity)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_pause, container)
    }
}