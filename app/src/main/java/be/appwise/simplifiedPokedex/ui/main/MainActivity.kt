package be.appwise.simplifiedPokedex.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.extensions.replaceFragment
import be.appwise.simplifiedPokedex.ui.base.BaseActivity
import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokemonDetailActivity

import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokedexRecyclerView
import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokemonDetailFragment
import be.appwise.simplifiedPokedex.ui.settings.SettingsActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pokemon_list.*

class MainActivity : BaseActivity() {
    companion object {
        const val SELECTED_KEY = "selected_position"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private var twoPane: Boolean = false
    private var pokemonList: List<Pokemon>? = null
    private var compositeDisposable = CompositeDisposable()
    private var mPosition = ListView.INVALID_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY)
        }

        if (pokemon_detail_container != null) {
            twoPane = true
        }

        fetchDataFromDb()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(SELECTED_KEY, mPosition)
        super.onSaveInstanceState(outState)
    }

    private fun fetchDataFromDb() {
        val disposable = Observable.fromCallable {
            val mDb = SimplifiedPokedexDatabase.getInstance(this)
            mDb?.pokemonDao()?.getAll()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pokemonList = it
            }, {
                it.printStackTrace()
            }, {
                fillInAdapter()
            })

        compositeDisposable.add(disposable)
    }

    private fun fillInAdapter() {
        if (pokemonList != null) {
            pokemon_list.apply {
                adapter =
                        PokedexRecyclerView(pokemonList as List<Pokemon>) { pokemon, position, _ ->
                            if (twoPane) {
                                replaceFragment(
                                    PokemonDetailFragment.newInstance(pokemon._id!!),
                                    false,
                                    PokemonDetailFragment.TAG,
                                    R.id.pokemon_detail_container
                                )
                            } else {
                                startActivity(
                                    PokemonDetailActivity.newIntent(
                                        this@MainActivity,
                                        pokemon._id!!
                                    )
                                )
                            }
                            mPosition = position
                        }

                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

                itemAnimator = null

                if (mPosition != ListView.INVALID_POSITION) {
                    scrollToPosition(mPosition)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(SettingsActivity.newIntent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        SimplifiedPokedexDatabase.destroyInstance()
        super.onDestroy()
    }
}