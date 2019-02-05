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
//                    bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
//                    bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
//                    swipeRefreshLayoutColorsRes(R.color.md_purple)
//                    attribute(R.attr.my_custom_attr, res = R.color.md_amber)

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Red" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_red)
                    colorAccent(res = R.color.md_amber)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()
//                    bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
//                    bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
//                    swipeRefreshLayoutColorsRes(
//                        R.color.md_red,
//                        R.color.md_amber
//                    )
//                    attribute(R.attr.my_custom_attr, res = R.color.md_blue)

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Purple" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_purple)
                    colorAccent(res = R.color.md_lime)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()
//                    bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
//                    bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
//                    swipeRefreshLayoutColorsRes(
//                        R.color.md_purple,
//                        R.color.md_lime
//                    )
//                    attribute(R.attr.my_custom_attr, res = R.color.md_green)

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Blue" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_blue)
                    colorAccent(res = R.color.md_pink)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()
//                    bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
//                    bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
//                    swipeRefreshLayoutColorsRes(
//                        R.color.md_blue,
//                        R.color.md_pink
//                    )
//                    attribute(R.attr.my_custom_attr, res = R.color.md_purple)

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "Green" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_green)
                    colorAccent(res = R.color.md_blue_grey)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()
//                    bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
//                    bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
//                    swipeRefreshLayoutColorsRes(
//                        R.color.md_green,
//                        R.color.md_blue_grey
//                    )
//                    attribute(R.attr.my_custom_attr, res = R.color.md_pink)

                    snackbarBackgroundColorDefault()
                    snackbarTextColorDefault()
                }
                "White" -> Aesthetic.config {
                    colorPrimary(res = R.color.md_white)
                    colorAccent(res = R.color.md_blue)
                    colorStatusBarAuto()
                    colorNavigationBarAuto()
//                    bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
//                    bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
//                    swipeRefreshLayoutColorsRes(R.color.md_blue)
//                    attribute(R.attr.my_custom_attr, res = R.color.md_lime)

                    snackbarBackgroundColor(res = R.color.md_white)
                    snackbarTextColor(res = android.R.color.black)
                }
            }
//            when (newValue?.toString()) {
//                "Blue" -> {
//                    Aesthetic.config {
//                        activityTheme(R.style.BlueTheme)
//                    }
////                    activity?.setTheme(R.style.BlueTheme)
////                    activity?.recreate()
//                }
//                else -> {
//                    Aesthetic.config {
//                        activityTheme(R.style.AppTheme)
//                    }
//
////                    activity?.setTheme(R.style.AppTheme)
////                    activity?.recreate()
//                }
//            }
            return@setOnPreferenceChangeListener true
        }
    }
}
