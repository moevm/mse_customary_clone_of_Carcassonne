package carcassonne.se.carcassonnecustomclone

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.change_name_dialog.*

class ChangeNameDialog : DialogFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        okButton.setOnClickListener {
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.change_name_dialog, container)
    }


}