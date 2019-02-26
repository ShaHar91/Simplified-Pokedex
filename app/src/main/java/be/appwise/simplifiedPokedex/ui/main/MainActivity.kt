package be.appwise.simplifiedPokedex.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.extensions.replaceFragment
import be.appwise.simplifiedPokedex.services.NotificationIntentService
import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokedexRecyclerView
import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokemonDetailActivity
import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokemonDetailFragment
import be.appwise.simplifiedPokedex.ui.settings.SettingsActivity
import com.afollestad.aesthetic.AestheticActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import java.util.*

class MainActivity : AestheticActivity() {
    companion object {
        const val SELECTED_KEY = "selected_position"
        const val SELECTED_ID = "selected_id"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private var twoPane: Boolean = false
    private var pokemonList: List<Pokemon> = emptyList()
    private var compositeDisposable = CompositeDisposable()
    private var mPosition = ListView.INVALID_POSITION
    private var mSelectedId = 1
    private var timer = Timer()
    private lateinit var mAdapter: PokedexRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        if (intent?.hasExtra("rating") == true) {
            MaterialDialog(this).show{
                customView(R.layout.custom_dialog_view)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY)
            mSelectedId = savedInstanceState.getInt(SELECTED_ID)
        }

        if (pokemon_detail_container != null) {
            twoPane = true

            if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_ID)) {
                replaceFragment(
                    PokemonDetailFragment.newInstance(savedInstanceState.getInt(SELECTED_ID, 1)),
                    false,
                    PokemonDetailFragment.TAG,
                    R.id.pokemon_detail_container
                )
            }
        }

        svPokemonList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mAdapter.filter.filter(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            mAdapter.filter.filter(newText)
                        }
                    }
                }, 500)
                return true
            }
        })

        fetchDataFromDb()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(SELECTED_KEY, mPosition)
        outState?.putInt(SELECTED_ID, mSelectedId)
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
                pokemonList = it ?: emptyList()
            }, {
                it.printStackTrace()
            }, {
                fillInAdapter()
            })

        compositeDisposable.add(disposable)
    }

    private fun fillInAdapter() {
        mAdapter = PokedexRecyclerView(pokemonList) { pokemon, position, _ ->
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
            mSelectedId = pokemon._id
        }

        pokemon_list.apply {
            adapter =
                AlphaInAnimationAdapter(mAdapter).apply {
                    setFirstOnly(false)
                    setDuration(100)
                }

            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            if (mPosition != ListView.INVALID_POSITION) {
                scrollToPosition(mPosition)
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
        val comp = ComponentName(this.packageName, MainActivity::class.java.name)
        NotificationIntentService.enqueueWork(this, (intent.setComponent(comp)))

        compositeDisposable.dispose()
        compositeDisposable.clear()
        SimplifiedPokedexDatabase.destroyInstance()
        super.onDestroy()
    }
}