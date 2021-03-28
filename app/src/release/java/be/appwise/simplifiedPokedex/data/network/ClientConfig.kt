package be.appwise.simplifiedPokedex.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientConfig: IClientConfig {
    override var getBaseUrl: HttpUrl =
        "https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/".toHttpUrl()

    override fun okHttpConfig(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .retryOnConnectionFailure(false)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

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
            .client(okHttpConfig().build())
            .build()
    }
}