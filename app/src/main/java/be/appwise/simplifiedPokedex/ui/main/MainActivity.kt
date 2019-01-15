package be.appwise.simplifiedPokedex.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.model.Pokemon
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var pokemonList: List<Pokemon>? = null
    private var compositeDisposable = CompositeDisposable()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchDataFromDb()
    }

    private fun fetchDataFromDb() {
        val dispo = Observable.fromCallable {
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

        compositeDisposable.add(dispo)
    }

    private fun fillInAdapter() {
        if (pokemonList != null) {
            rvPokedexList.apply {
                adapter = PokedexRecyclerView(pokemonList as List<Pokemon>)

                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

                itemAnimator = null
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        SimplifiedPokedexDatabase.destroyInstance()
        super.onDestroy()
    }
}
