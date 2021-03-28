package be.appwise.simplifiedPokedex.ui.splash

import android.os.Bundle
import androidx.preference.PreferenceManager
import be.appwise.core.extensions.activity.isNetworkAvailable
import be.appwise.core.extensions.view.show
import be.appwise.core.ui.base.BaseVMActivity
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseVMActivity<SplashViewModel>() {
    override fun getViewModel() = SplashViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getString("preference_name", "")

        if (!name.isNullOrEmpty()) {
            tvWelcomeText.show()
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
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)

        throwable.printStackTrace()
    }
}