package be.appwise.simplifiedPokedex.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.simplifiedPokedex.MyApplication

class MainViewModel : BaseViewModel() {
    private val _searchQuery = MutableLiveData<String>().apply { value = "" }
    fun setQuery(query: String) = _searchQuery.postValue(query)
    val pokemons =
        Transformations.switchMap(_searchQuery) { MyApplication.pokemonRepository.getPokemonsByQuery(it) }
}