package be.appwise.simplifiedPokedex.ui.main.detail

import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.model.Pokemon
import kotlinx.coroutines.launch

class PokemonDetailViewModel : BaseViewModel() {
    val pokemon = MutableLiveData<Pokemon>()
    val pokemonBaseStat = MutableLiveData<BaseStat>()
    val pokemonMatchUp = MutableLiveData<MatchUp>()

    fun getPokemonDetails(pokemonId: Int) = vmScope.launch {
        pokemonBaseStat.postValue(MyApplication.baseStatRepository.getBaseStatsByPokemonId(pokemonId))
        pokemon.postValue(MyApplication.pokemonRepository.getPokemonById(pokemonId).apply {
            pokemonMatchUp.postValue(
                MyApplication.matchUpRepository.getMatchUpForTypesLive(
                    type1,
                    type2
                )
            )
        })
    }
}