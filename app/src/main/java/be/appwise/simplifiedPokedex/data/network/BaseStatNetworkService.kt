package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.BaseStat
import retrofit2.Call
import retrofit2.http.GET

interface BaseStatNetworkService {
    @GET("base_stat.json")
    fun getAllBaseStats(): Call<List<BaseStat>>
}