package be.appwise.simplifiedPokedex.ui.splash

import be.appwise.core.ui.base.BaseViewModel
import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.repository.BaseStatRepository
import be.appwise.simplifiedPokedex.data.repository.MatchUpRepository
import be.appwise.simplifiedPokedex.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {
    fun getAllData(onSuccess: () -> Unit) = vmScope.launch {
        MyApplication.pokemonRepository.getAllPokemons()
        BaseStatRepository.getAllBaseStats()
        MatchUpRepository.getAllMatchUps()

        onSuccess()
    }
}