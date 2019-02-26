package be.appwise.simplifiedPokedex.ui.settings

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import be.appwise.simplifiedPokedex.R
import com.afollestad.aesthetic.Aesthetic

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_simplified_pokedex)

        val sharedPreferences = preferenceManager.sharedPreferences

        val editTextPreference = findPreference("preference_name") as EditTextPreference
        editTextPreference.summary = sharedPreferences.getString("preference_name", "")
        editTextPreference.setOnPreferenceChangeListener { preference, newValue ->
            sharedPreferences?.edit()?.putString("preference_name", newValue.toString())?.apply()
            editTextPreference.summary = newValue.toString()

            return@setOnPreferenceChangeListener true
        }

        val themeSelectionPreference = findPreference("preference_theme") as ListPreference
        themeSelectionPreference.summary = sharedPreferences.getString("preference_theme", "")
        themeSelectionPreference.setOnPreferenceChangeListener { preference, newValue ->
            sharedPreferences?.edit()?.putString("preference_theme", newValue.toString())?.apply()
            themeSelectionPreference.summary = newValue.toString()

            when (newValue.toString()) {
                "Black" -> Aesthetic.config {
                    colorPrimary(res = R.color.text_color_primary)
                    colorAccent(res = R.color.md_purple)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Red" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_red)
                    colorAccent(res = R.color.md_amber)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Purple" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_purple)
                    colorAccent(res = R.color.md_lime)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Blue" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_blue)
                    colorAccent(res = R.color.md_pink)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Green" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_green)
                    colorAccent(res = R.color.md_blue_grey)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "White" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_white)
                    colorAccent(res = R.color.md_blue)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()

                    snackbarBackgroundColor(res = R.color.md_white)
                    snackbarTextColor(res = android.R.color.black)
                }
            }
            return@setOnPreferenceChangeListener true
        }
    }
}
