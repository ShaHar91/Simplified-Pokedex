package be.appwise.simplifiedPokedex.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import be.appwise.core.extensions.activity.replaceFragment
import be.appwise.core.extensions.view.setupRecyclerView
import be.appwise.core.ui.base.BaseVMActivity
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.services.NotificationIntentService
import be.appwise.simplifiedPokedex.ui.main.adapter.PokedexRecyclerView
import be.appwise.simplifiedPokedex.ui.main.detail.PokemonDetailActivity
import be.appwise.simplifiedPokedex.ui.main.detail.PokemonDetailFragment
import be.appwise.simplifiedPokedex.ui.settings.SettingsActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import java.util.*

class MainActivity : BaseVMActivity() {
    companion object {
        const val SELECTED_KEY = "selected_position"
        const val SELECTED_ID = "selected_id"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override val mViewModel: MainViewModel by viewModels()

    private var twoPane: Boolean = false
    private var mPosition = ListView.INVALID_POSITION
    private var mSelectedId = 1

    private var mAdapter: PokedexRecyclerView = PokedexRecyclerView { pokemon, position, _ ->
        if (twoPane) {
            openDetailFragment(pokemon.id)
        } else {
            startActivity(
                PokemonDetailActivity.newIntent(
                    this@MainActivity,
                    pokemon.id
                )
            )
        }
        mPosition = position
        mSelectedId = pokemon.id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        showRatingDialog(intent)

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY)
            mSelectedId = savedInstanceState.getInt(SELECTED_ID)
        }

        if (pokemon_detail_container != null) {
            twoPane = true

            if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_ID)) {
                openDetailFragment(savedInstanceState.getInt(SELECTED_ID, 1))
            }
        }

        initializeAdapter()
        initializeListeners()
    }

    private fun openDetailFragment(pokemonId: Int) {
        replaceFragment(
            PokemonDetailFragment.newInstance(pokemonId),
            false,
            PokemonDetailFragment.TAG,
            R.id.pokemon_detail_container
        )
    }

    private fun initializeListeners() {
        svPokemonList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mViewModel.setQuery(newText)
                return true
            }
        })

        mViewModel.pokemons.observe(this, {
            mAdapter.submitList(it)
        })
    }

    private fun initializeAdapter() {
        pokemon_list.apply {
            setupRecyclerView()
            adapter = AlphaInAnimationAdapter(mAdapter).apply {
                setFirstOnly(false)
                setDuration(100)
            }

            if (mPosition != ListView.INVALID_POSITION) {
                scrollToPosition(mPosition)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(SettingsActivity.newIntent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRatingDialog(intent: Intent?) {
        if (intent?.hasExtra("rating") == true) {
            MaterialDialog(this).show {
                customView(R.layout.custom_dialog_view)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val dialogOk = findViewById<TextView>(R.id.tvDialogOk)
                val dialogCancel = findViewById<TextView>(R.id.tvDialogCancel)

                dialogOk.setOnClickListener {
                    intent.removeExtra("rating")
                    dismiss()
                }

                dialogCancel.setOnClickListener {
                    intent.removeExtra("rating")
                    dismiss()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_KEY, mPosition)
        outState.putInt(SELECTED_ID, mSelectedId)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        val comp = ComponentName(this.packageName, MainActivity::class.java.name)
        NotificationIntentService.enqueueWork(this, (intent.setComponent(comp)))

        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        showRatingDialog(intent)
    }
}