package be.appwise.simplifiedPokedex.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientConfig : IClientConfig {
    override var getBaseUrl = "https://www.google.com".toHttpUrl()

    override fun okHttpConfig() = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)

    override fun retrofitConfig(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().apply {
                        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    }.create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpConfig().build())
            .build()
    }
}