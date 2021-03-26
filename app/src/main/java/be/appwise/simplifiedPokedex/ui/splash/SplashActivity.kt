package be.appwise.simplifiedPokedex.ui.splash

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.extensions.isNetworkAvailable
import be.appwise.simplifiedPokedex.extensions.snackBar
import be.appwise.simplifiedPokedex.extensions.visible
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import com.afollestad.aesthetic.Aesthetic
import com.afollestad.aesthetic.AestheticActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AestheticActivity() {
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java).apply {
            setDefaultExceptionHandler(::onError)
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getString("preference_name", "")

        if (!name.isNullOrEmpty()) {
            tvWelcomeText.visible()
            tvWelcomeText.text = "Welcome back $name"
        }

        if (isNetworkAvailable()) {
            viewModel.getAllData {
                startActivity(MainActivity.newIntent(this))
                finish()
            }
        } else {
            startActivity(MainActivity.newIntent(this))
            finish()
        }

        // If we haven't set any defaults, do that now
        if (Aesthetic.isFirstTime) {
            Aesthetic.config {
                activityTheme(R.style.AppTheme)
                textColorPrimary(res = R.color.text_color_primary)
                textColorSecondary(res = R.color.text_color_secondary)
                colorPrimary(res = R.color.md_white)
                colorAccent(res = R.color.md_blue)
                colorStatusBarAuto()
                colorNavigationBarAuto()
                textColorPrimary(Color.BLACK)
            }
        }
    }

    private fun onError(throwable: Throwable) {
        snackBar(throwable.message ?: "Something went wrong")
        Log.e("SplashActivity", throwable.message ?: "", throwable)
    }
}