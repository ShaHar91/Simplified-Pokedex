package be.appwise.simplifiedPokedex.data.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ClientConfig(
        val rootUrl: HttpUrl = HttpUrl.parse("https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/")!!,
        val okHttpConfig: OkHttpClient.Builder.() -> OkHttpClient.Builder = {
            retryOnConnectionFailure(false)
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }
)