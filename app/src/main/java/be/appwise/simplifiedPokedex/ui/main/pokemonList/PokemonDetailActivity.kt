package be.appwise.simplifiedPokedex.ui.main.pokemonList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.extensions.replaceFragment
import com.afollestad.aesthetic.AestheticActivity

class PokemonDetailActivity : AestheticActivity() {
    companion object {
        const val POKEMON_ID = "pokemonID"

        fun newIntent(context: Context, pokemonId: Int): Intent {
            return Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra(POKEMON_ID, pokemonId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        // Show the Up button in the action bar.
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val pokemonId = intent.getIntExtra(POKEMON_ID, 1)
        replaceFragment(
            PokemonDetailFragment.newInstance(pokemonId),
            false,
            PokemonDetailFragment.TAG,
            R.id.pokemon_detail_container
        )
    }
}