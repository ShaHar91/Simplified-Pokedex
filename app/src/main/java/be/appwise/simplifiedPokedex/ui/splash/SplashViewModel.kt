package be.appwise.simplifiedPokedex.ui.splash

import be.appwise.core.ui.base.BaseViewModel
import be.appwise.simplifiedPokedex.MyApplication
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {
    fun getAllData(onSuccess: () -> Unit) = vmScope.launch {
        MyApplication.pokemonRepository.getAllPokemons()
        MyApplication.baseStatRepository.getAllBaseStats()
        MyApplication.matchUpRepository.getAllMatchUps()

        onSuccess()
    }
}