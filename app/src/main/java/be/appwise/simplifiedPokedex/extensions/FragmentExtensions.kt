package be.appwise.simplifiedPokedex.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import be.appwise.simplifiedPokedex.R

/**
 * @message the message to show
 * @parentView the view which should hold the baseSnackBar
 * @textColor the color of the text (i.e. R.color.red)
 */
fun Fragment.snackBar(message: String, parentView: View = activity!!.findViewById(android.R.id.content),
                      textColor: Int = android.R.color.white) = baseSnackBar(parentView, message, textColor).show()

fun Fragment.openKeyBoard(edittext: EditText) {
    edittext.requestFocus()
    val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment.hideKeyboard() {

    // Check if no view has focus:
    val view = activity!!.currentFocus
    if (view != null) {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.replaceFragment(fragment: Fragment, addToBackstack: Boolean, TAG: String, id: Int = R.id.container, animation: Boolean = false) {
    val fragmentManager = childFragmentManager

    baseReplaceFragment(fragmentManager, fragment, addToBackstack, TAG, id, animation)
}