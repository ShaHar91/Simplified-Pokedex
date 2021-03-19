package be.appwise.simplifiedPokedex.data.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface IClientConfig {
    var getBaseUrl: HttpUrl

    fun okHttpConfig(): OkHttpClient.Builder

    fun retrofitConfig(): Retrofit
}