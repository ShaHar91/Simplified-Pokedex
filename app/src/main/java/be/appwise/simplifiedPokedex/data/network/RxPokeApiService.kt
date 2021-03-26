package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RxPokeApiService {
    // region Pokemon
    @GET("pokemon.json")
    fun getPokemon(): Observable<List<Pokemon>>

    @GET("base_stat.json")
    fun getBaseStat(): Observable<List<BaseStat>>

    @GET("match_up.json")
    fun getMatchUps(): Observable<List<MatchUp>>
    // endregion Pokemon


    @GET("pokemon.json")
    fun getAllPokemons(): Call<List<Pokemon>>

    @GET("base_stat.json")
    fun getAllBaseStats(): Call<List<BaseStat>>

    @GET("match_up.json")
    fun getAllMatchUps(): Call<List<MatchUp>>
}
