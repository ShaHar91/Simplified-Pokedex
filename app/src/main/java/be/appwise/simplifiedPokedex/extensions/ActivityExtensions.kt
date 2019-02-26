package be.appwise.simplifiedPokedex.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import be.appwise.simplifiedPokedex.R

/**
 * @message the message to show
 * @parentView the view which should hold the baseSnackBar
 * @textColor the color of the text (i.e. R.color.red)
 */
fun Activity.snackBar(message: String, parentView: View = findViewById(android.R.id.content),
                          textColor: Int = android.R.color.white) = baseSnackBar(parentView, message, textColor).show()

fun Activity.openKeyBoard(edittext: EditText) {
    edittext.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Activity.hideKeyboard() {

    // Check if no view has focus:
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addToBackstack: Boolean, TAG: String, id: Int = R.id.container, animation: Boolean = false) {
    val fragmentManager = supportFragmentManager

    baseReplaceFragment(fragmentManager, fragment, addToBackstack, TAG, id, animation)
}

fun Activity.screenWidth(): Int {
    val metrics: DisplayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
}

fun Activity.screenHeight(): Int {
    val metrics: DisplayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.heightPixels
}

fun Activity.color(resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

/**
 * check if there is an internet connection or not
 *
 * @return the a connection state
 */
fun Activity.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}