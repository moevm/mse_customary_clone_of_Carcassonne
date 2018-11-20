package carcassonne.se.carcassonnecustomclone

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_pause.*

class PauseDialog : DialogFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        resumeButton.setOnClickListener {
            dismiss()
        }

        rulesButton.setOnClickListener {

        }
        
        exitButton.setOnClickListener {

        }

        settingsButton.setOnClickListener {

        }

        infoButton.setOnClickListener {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_pause, container)
    }
}