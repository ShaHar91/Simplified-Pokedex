package be.appwise.simplifiedPokedex.ui.splash

import android.graphics.Color
import android.os.Bundle
import androidx.preference.PreferenceManager
import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.extensions.isNetworkAvailable
import be.appwise.simplifiedPokedex.extensions.visible
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import com.afollestad.aesthetic.Aesthetic
import com.afollestad.aesthetic.AestheticActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AestheticActivity() {
    companion object {
        private const val COUNT_OF_STEPS = 3
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    //TODO: if no internet available, go on to the MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getString("preference_name", "")

        if (!name.isNullOrEmpty()) {
            tvWelcomeText.visible()
            tvWelcomeText.text = "Welcome back $name"
        }

        if (isNetworkAvailable()){
            getAllSeparatePokemons()
        }else{
            startActivity(MainActivity.newIntent(this))
            finish()
        }

        initializeProgressBars(COUNT_OF_STEPS)

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

    private fun getAllSeparatePokemons() {
        val mDb = SimplifiedPokedexDatabase.getInstance(this)

        val rxPoke = MyApplication.rxPokeApiImpl

        val disposable = rxPoke.getPokemon()
            .subscribeOn(Schedulers.io())
            .switchMap {
                mDb?.pokemonDao()?.insertAll(it)

                addStepToProgress()

                return@switchMap rxPoke.getBaseStat()
            }
            .subscribeOn(Schedulers.io())
            .switchMap {
                mDb?.baseStatDao()?.insertAll(it)

                addStepToProgress()

                return@switchMap rxPoke.getMatchUps()
            }
            .subscribeOn(Schedulers.io())
            .subscribe({
                mDb?.matchUpDao()?.insertAll(it)

                addStepToProgress()
            }, {
                it.printStackTrace()
            }, {
                startActivity(MainActivity.newIntent(this))
                finish()
            })

        compositeDisposable.add(disposable)
    }

    private fun addStepToProgress() {
        runOnUiThread {
            progressBar.progress = progressBar.progress.plus(1)
        }
    }

    private fun initializeProgressBars(size: Int) {
        runOnUiThread {
            progressBar.max = size
        }
    }

    override fun onDestroy() {
        SimplifiedPokedexDatabase.destroyInstance()
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onDestroy()
    }
}