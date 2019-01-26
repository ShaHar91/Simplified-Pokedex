package be.appwise.simplifiedPokedex.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.extensions.replaceFragment
import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokemonDetailFragment
import be.appwise.simplifiedPokedex.ui.main.pokemonList.PokemonListFragment

class MainActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(PokemonListFragment.newInstance(), false, PokemonListFragment.TAG)
    }

    fun replaceAFragment(pokemonId: Int) {
        replaceFragment(PokemonDetailFragment.newInstance(pokemonId), true, PokemonDetailFragment.TAG)
    }
}
