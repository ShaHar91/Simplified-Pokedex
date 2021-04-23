package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET

interface PokemonNetworkService {
    @GET("pokemon.json")
    fun getAllPokemons(): Call<List<Pokemon>>
}