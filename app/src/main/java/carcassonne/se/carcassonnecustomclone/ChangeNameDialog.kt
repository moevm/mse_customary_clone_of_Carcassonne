package carcassonne.se.carcassonnecustomclone

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.change_name_dialog.*

class ChangeNameDialog : DialogFragment() {
    var nameField: TextView? = null
    var player: PlayerInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        okButton.setOnClickListener {
            if(newName.text.isNotEmpty()) {
                nameField?.text = newName.text
                player?.name = newName.text.toString()
            }
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.change_name_dialog, container)
    }

    override fun onStart() {
        super.onStart()
        newName.setText(nameField?.text)
    }
}