package be.appwise.simplifiedPokedex.ui.main.pokemonList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.extensions.replaceFragment
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pokemon_list.*

class PokemonListFragment : Fragment() {
    companion object {
        const val SELECTED_KEY = "selected_position"
        const val TAG = "PokemonListFragment"

        fun newInstance(): PokemonListFragment {
            val fragment = PokemonListFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private var pokemonList: List<Pokemon>? = null
    private var compositeDisposable = CompositeDisposable()
    private lateinit var parentActivity: MainActivity
    private var mPosition = ListView.INVALID_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentActivity = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY)
        }

        return inflater.inflate(R.layout.activity_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pokemon_detail_container != null) {
            // The detail container view will be present only in landscape.
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        tbPokemonList.inflateMenu(R.menu.main)

        fetchDataFromDb()

        if (mPosition != ListView.INVALID_POSITION) {
            pokemon_list.smoothScrollToPosition(mPosition)
        }
    }

    private fun fetchDataFromDb() {
        val disposable = Observable.fromCallable {
            val mDb = SimplifiedPokedexDatabase.getInstance(parentActivity)
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
                initializeFirstDetail()
            })

        compositeDisposable.add(disposable)
    }

    private fun initializeFirstDetail() {
        if (twoPane) {
            replaceFragment(
                PokemonDetailFragment.newInstance(1),
                false,
                PokemonDetailFragment.TAG,
                R.id.pokemon_detail_container
            )
        }
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
                                parentActivity.replaceAFragment(pokemon._id!!)
                            }
                            mPosition = position
                        }

                layoutManager = LinearLayoutManager(parentActivity)
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

                itemAnimator = null
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        SimplifiedPokedexDatabase.destroyInstance()
        super.onDestroy()
    }
}