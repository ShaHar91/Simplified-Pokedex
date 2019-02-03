package be.appwise.simplifiedPokedex.ui.settings


import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import be.appwise.simplifiedPokedex.R


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


            when (newValue?.toString()) {
                "Blue" -> {
                    activity?.setTheme(R.style.BlueTheme)
                    activity?.recreate()
                }
                else -> {
                    activity?.setTheme(R.style.AppTheme)
                    activity?.recreate()
                }
            }
            return@setOnPreferenceChangeListener true
        }
    }
}
