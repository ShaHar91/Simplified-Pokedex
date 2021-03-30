package be.appwise.simplifiedPokedex.ui.splash

import be.appwise.core.ui.base.BaseViewModel
import be.appwise.simplifiedPokedex.MyApplication
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {
    fun getAllData(onStep:(Int) -> Unit, onSuccess: () -> Unit) = vmScope.launch {
        MyApplication.pokemonRepository.getAllPokemons()
        onStep(1)
        MyApplication.baseStatRepository.getAllBaseStats()
        onStep(2)
        MyApplication.matchUpRepository.getAllMatchUps()
        onStep(3)

        onSuccess()
    }
}