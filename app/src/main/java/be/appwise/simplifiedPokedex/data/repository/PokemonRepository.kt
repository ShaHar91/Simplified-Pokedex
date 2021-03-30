package be.appwise.simplifiedPokedex.data.repository

import be.appwise.core.data.base.BaseRepository
import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.dao.PokemonDao
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.data.network.NetworkService

class PokemonRepository(
    private val pokemonDao: PokemonDao,
    private val service: NetworkService
) : BaseRepository() {

    private suspend fun insertAllPokemons(pokemons: List<Pokemon>) = pokemonDao.insertList(pokemons)

    suspend fun getAllPokemons() {
        val pokemons = doCall(service.getAllPokemons())

        insertAllPokemons(pokemons)
    }

    fun getPokemonsByQuery(query: String) = pokemonDao.findPokemonsByQueryLive(query)

    suspend fun getPokemonById(pokemonId: Int) = pokemonDao.getPokemonById(pokemonId)
}