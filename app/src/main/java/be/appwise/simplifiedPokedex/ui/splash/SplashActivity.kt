package be.appwise.simplifiedPokedex.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import be.appwise.simplifiedPokedex.Constants
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.network.RxPokeApiClient
import be.appwise.simplifiedPokedex.extensions.visible
import be.appwise.simplifiedPokedex.ui.base.BaseActivity
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import com.orhanobut.hawk.Hawk
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    companion object {
        private const val COUNT_OF_STEPS = 3
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getString("preference_name", "")

        if (!name.isNullOrEmpty()) {
            tvWelcomeText.visible()
            tvWelcomeText.text = "Welcome back $name"
        }

        getAllSeparatePokemons()

        initializeProgressBars(COUNT_OF_STEPS)
    }

    private fun getAllSeparatePokemons() {
        val mDb = SimplifiedPokedexDatabase.getInstance(this)

        val rxPoke = RxPokeApiClient()

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
                Hawk.put(Constants.DATABASE_BEEN_SYNCED, true)

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