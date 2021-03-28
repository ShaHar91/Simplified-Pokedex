package be.appwise.simplifiedPokedex.data.repository

import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.dao.PokemonDao
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.data.network.RxPokeApiService

class PokemonRepository(private val pokemonDao: PokemonDao, private val service: RxPokeApiService) {

    private suspend fun insertAllPokemons(pokemons: List<Pokemon>) = pokemonDao.insertList(pokemons)

    suspend fun getAllPokemons() {
        val pokemons = MyApplication.clientConfig.doCall(service.getAllPokemons())

        insertAllPokemons(pokemons)
    }

    fun getPokemonsByQuery(query: String) = pokemonDao.findPokemonsByQueryLive(query)

    suspend fun getPokemonById(pokemonId: Int) = pokemonDao.getPokemonById(pokemonId)
}