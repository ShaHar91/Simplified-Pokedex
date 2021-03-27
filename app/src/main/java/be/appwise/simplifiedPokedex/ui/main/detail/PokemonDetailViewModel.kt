package be.appwise.simplifiedPokedex.ui.main.detail

import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.data.repository.BaseStatRepository
import be.appwise.simplifiedPokedex.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel : BaseViewModel() {
    val pokemon = MutableLiveData<Pokemon>()
    val pokemonBaseStat = MutableLiveData<BaseStat>()

    fun getPokemonDetails(pokemonId: Int) = vmScope.launch {
        pokemon.postValue(PokemonRepository.getPokemonById(pokemonId))
        pokemonBaseStat.postValue(BaseStatRepository.getBaseStatsByPokemonId(pokemonId))
    }
}