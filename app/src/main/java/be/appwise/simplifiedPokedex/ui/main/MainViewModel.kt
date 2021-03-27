package be.appwise.simplifiedPokedex.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.appwise.simplifiedPokedex.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

class MainViewModel : ViewModel() {
    @Suppress("MemberVisibilityCanBePrivate")
    var vmScope = viewModelScope

    fun setDefaultExceptionHandler(onError: (error: Throwable) -> Unit = {}) {
        vmScope = vmScopeWithCustomExceptionHandler(onError)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun vmScopeWithCustomExceptionHandler(onError: (error: Throwable) -> Unit = {}) =
        (viewModelScope + CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        })

    private val _searchQuery = MutableLiveData<String>().apply { value = "" }
    fun setQuery(query: String) = _searchQuery.postValue(query)
    val pokemons =
        Transformations.switchMap(_searchQuery) { PokemonRepository.getPokemonsByQuery(it) }
}