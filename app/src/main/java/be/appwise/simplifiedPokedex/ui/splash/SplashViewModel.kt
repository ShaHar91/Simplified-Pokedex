package be.appwise.simplifiedPokedex.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.appwise.simplifiedPokedex.data.repository.BaseStatRepository
import be.appwise.simplifiedPokedex.data.repository.MatchUpRepository
import be.appwise.simplifiedPokedex.data.repository.PokemonRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class SplashViewModel : ViewModel() {
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

    fun getAllData(onSuccess: () -> Unit) = vmScope.launch {
        PokemonRepository.getAllPokemons()
        BaseStatRepository.getAllBaseStats()
        MatchUpRepository.getAllMatchUps()

        onSuccess()
    }
}