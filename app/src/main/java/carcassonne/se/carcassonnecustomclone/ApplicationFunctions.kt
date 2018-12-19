package carcassonne.se.carcassonnecustomclone

import android.app.Dialog
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.Window
import android.view.WindowManager

/*Set fullscreen mode*/
fun hideSystemUI(window: Window?) {
    window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}


//Скрывает элементы управления при открытии диалога
fun hideDialogSystemUI(dialog: Dialog?, activity: FragmentActivity?) {
    val window = dialog?.window
    window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    hideSystemUI(window)
    //dialogWindow?.decorView?.systemUiVisibility = activity?.window?.decorView?.systemUiVisibility ?: 0
    dialog?.setOnShowListener {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        (activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).updateViewLayout(window?.decorView, window?.attributes)
    }
}




