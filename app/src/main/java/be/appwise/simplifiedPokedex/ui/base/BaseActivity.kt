package be.appwise.simplifiedPokedex.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import be.appwise.simplifiedPokedex.R

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themeName = sharedPreferences.getString("preference_theme", "")

        when (themeName) {
            "Blue" -> {
                setTheme(R.style.BlueTheme)
            }
            else -> {
                setTheme(R.style.AppTheme)
            }
        }

        super.onCreate(savedInstanceState)
    }
}