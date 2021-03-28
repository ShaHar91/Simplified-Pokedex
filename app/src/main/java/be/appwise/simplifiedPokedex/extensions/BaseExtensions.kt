package be.appwise.simplifiedPokedex.extensions

import android.view.View
import android.widget.TextView
import be.appwise.simplifiedPokedex.R

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