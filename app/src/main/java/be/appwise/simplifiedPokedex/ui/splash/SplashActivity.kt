package be.appwise.simplifiedPokedex.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import be.appwise.simplifiedPokedex.Constants
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.data.network.RxPokeApiClient
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*

//TODO: use this json-minded 'api' https://github.com/jalyna/oakdex-pokedex/tree/master/data

//TODO: https://github.com/veekun/pokedex/blob/master/pokedex/data/csv/pokemon.csv
class SplashActivity : AppCompatActivity() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val pokemonList: ArrayList<Pokemon> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!Hawk.get<Boolean>(Constants.DATABASE_BEEN_SYNCED, false)) {
            getAllSeparatePokemons()
        } else {
            startActivity(MainActivity.newIntent(this))
            finish()
        }
    }

    private fun getAllSeparatePokemons() {
        val mDb = SimplifiedPokedexDatabase.getInstance(this)

        val rxPoke = RxPokeApiClient()
//
//        val pokeList = rxPoke.getPokemonList(20, 20)
//
//        val disposable = pokeList
//            .subscribeOn(Schedulers.io())
//            .switchMap {
//                initializeProgressBars(it.results.size)
//
//                return@switchMap Observable.fromIterable(it.results)
//            }
//            .switchMap {
//                return@switchMap rxPoke.getPokemon(it.id)
//            }
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                pokemonList.add(it)
////                    mDb?.pokemonDao()?.insert(it)
//
//                addStepToProgress()
//            }, {
//                it.printStackTrace()
//            }, {
//                mDb?.pokemonDao()?.updateData(pokemonList)
//
//                Hawk.put(Constants.DATABASE_BEEN_SYNCED, true)
//
//                startActivity(MainActivity.newIntent(this))
//                finish()
//            })

//        compositeDisposable.add(disposable)
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