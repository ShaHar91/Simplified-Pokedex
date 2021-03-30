package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {

    @GET("pokemon.json")
    fun getAllPokemons(): Call<List<Pokemon>>

    @GET("base_stat.json")
    fun getAllBaseStats(): Call<List<BaseStat>>

    @GET("match_up.json")
    fun getAllMatchUps(): Call<List<MatchUp>>
}
