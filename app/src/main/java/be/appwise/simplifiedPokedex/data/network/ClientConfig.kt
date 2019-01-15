package be.appwise.simplifiedPokedex.data.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class ClientConfig(
    val rootUrl: HttpUrl = HttpUrl.parse("https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/")!!,
    val okHttpConfig: OkHttpClient.Builder.() -> OkHttpClient.Builder = {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        interceptors().add(logging)

        retryOnConnectionFailure(false)
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }
)