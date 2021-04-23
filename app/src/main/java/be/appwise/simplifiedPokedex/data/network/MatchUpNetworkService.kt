package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.MatchUp
import retrofit2.Call
import retrofit2.http.GET

interface MatchUpNetworkService {
    @GET("match_up.json")
    fun getAllMatchUps(): Call<List<MatchUp>>
}