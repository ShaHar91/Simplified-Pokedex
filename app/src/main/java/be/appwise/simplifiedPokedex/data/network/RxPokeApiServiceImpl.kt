package be.appwise.simplifiedPokedex.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal class RxPokeApiServiceImpl(
    private val config: ClientConfig
) : RxPokeApiService by Retrofit.Builder()
    .baseUrl(config.rootUrl)
    .addConverterFactory(
        GsonConverterFactory.create(
            GsonBuilder().apply {
                setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            }.create()
        )
    )
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(OkHttpClient.Builder().(config.okHttpConfig)().build())
    .build()
    .create(RxPokeApiService::class.java)