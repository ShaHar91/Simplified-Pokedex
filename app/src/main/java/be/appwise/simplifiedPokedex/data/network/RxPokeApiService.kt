package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RxPokeApiService {
    // region Pokemon
    @GET("pokemon/{id}/")
    fun getPokemon(@Path("id") id: Int): Observable<Pokemon>
    // endregion Pokemon
}
