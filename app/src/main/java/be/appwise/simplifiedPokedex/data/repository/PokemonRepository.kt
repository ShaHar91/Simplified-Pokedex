package be.appwise.simplifiedPokedex.data.repository

import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.data.network.RxPokeApiService

object PokemonRepository {
    private val pokemonDao = MyApplication.pokedexDatabase.pokemonDao()
    private val service: RxPokeApiService = MyApplication.service

    private suspend fun insertAllPokemons(pokemons: List<Pokemon>) = pokemonDao.insertList(pokemons)

    suspend fun getAllPokemons() {
        val pokemons = MyApplication.clientConfig.doCall(service.getAllPokemons())

        insertAllPokemons(pokemons)
    }
}