package be.appwise.simplifiedPokedex.extensions

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import be.appwise.simplifiedPokedex.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

fun baseSnackBar(parentView: View, message: String, textColor: Int) = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).apply {
    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(ContextCompat.getColor(context, textColor))
}

fun baseReplaceFragment(fragmentManager: FragmentManager, fragment: Fragment, addToBackstack: Boolean, TAG: String, id: Int = R.id.container, animation: Boolean = false) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    if (animation) {
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right, // this parameter is for the new fragment's animation when replacing
                R.anim.slide_out_left,  // this parameter is for the old fragment's animation when replacing
                R.anim.slide_in_left,  // this parameter is for the new fragment's animation when popping the backstack
                R.anim.slide_out_right) // this parameter is for the old fragment's animation when popping the backstack
    }
    fragmentTransaction.replace(id, fragment, TAG)
    if (addToBackstack) {
        fragmentTransaction.addToBackStack(TAG)
    }
    try {
        fragmentTransaction.commit()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

//<editor-fold desc="Listen for tapping done">
fun EditText.listenForTappingDone(callbackFunctionForResult: () -> Unit) {
    listenForUserInteractionWithKeyboard(this) {
        callbackFunctionForResult()
    }
}

fun TextInputEditText.listenForTappingDone(callbackFunctionForResult: () -> Unit) {
    listenForUserInteractionWithKeyboard(this) {
        callbackFunctionForResult()
    }
}

private fun listenForUserInteractionWithKeyboard(editText: EditText, callback: () -> Unit) {
    editText.setOnEditorActionListener { _, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            callback()
        }
        return@setOnEditorActionListener false
    }
}
//</editor-fold>

//<editor-fold desc="visibility shizzle">
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
//</editor-fold>


fun TextView.typeBackground(type: String?) {
    if (!type.isNullOrEmpty()) {
        text = type
        visibility = View.VISIBLE

        background = when (type.toLowerCase()) {
            "bug" -> resources.getDrawable(R.drawable.button_border_bug, null)
            "dark" -> resources.getDrawable(R.drawable.button_border_dark, null)
            "dragon" -> resources.getDrawable(R.drawable.button_border_dragon, null)
            "electric" -> resources.getDrawable(R.drawable.button_border_electric, null)
            "fairy" -> resources.getDrawable(R.drawable.button_border_fairy, null)
            "fighting" -> resources.getDrawable(R.drawable.button_border_fight, null)
            "fire" -> resources.getDrawable(R.drawable.button_border_fire, null)
            "flying" -> resources.getDrawable(R.drawable.button_border_flying, null)
            "ghost" -> resources.getDrawable(R.drawable.button_border_ghost, null)
            "grass" -> resources.getDrawable(R.drawable.button_border_grass, null)
            "ground" -> resources.getDrawable(R.drawable.button_border_ground, null)
            "ice" -> resources.getDrawable(R.drawable.button_border_ice, null)
            "normal" -> resources.getDrawable(R.drawable.button_border_normal, null)
            "poison" -> resources.getDrawable(R.drawable.button_border_poison, null)
            "psychic" -> resources.getDrawable(R.drawable.button_border_psychic, null)
            "rock" -> resources.getDrawable(R.drawable.button_border_rock, null)
            "steel" -> resources.getDrawable(R.drawable.button_border_steel, null)
            else -> resources.getDrawable(R.drawable.button_border_water, null)
        }
    } else {
        visibility = View.INVISIBLE
    }
}