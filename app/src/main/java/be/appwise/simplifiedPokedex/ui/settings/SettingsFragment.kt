package be.appwise.simplifiedPokedex.ui.settings

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import be.appwise.simplifiedPokedex.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_simplified_pokedex)

        val sharedPreferences = preferenceManager.sharedPreferences

        val editTextPreference = findPreference("preference_name") as EditTextPreference
        editTextPreference.summary = sharedPreferences.getString("preference_name", "")
        editTextPreference.setOnPreferenceChangeListener { _, newValue ->
            sharedPreferences?.edit()?.putString("preference_name", newValue.toString())?.apply()
            editTextPreference.summary = newValue.toString()

            return@setOnPreferenceChangeListener true
        }
    }
}
