package be.appwise.simplifiedPokedex.data.network

import be.appwise.core.networking.base.BaseSimpleRestClient
import com.appham.mockinizer.RequestFilter
import com.appham.mockinizer.mockinize
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import java.util.concurrent.TimeUnit

object PokeRestClient: BaseSimpleRestClient() {
    override val protectedClient = false
    override fun enableBagelInterceptor() = true

    override fun getBaseUrl() = "https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/"

    val pokemonService: PokemonNetworkService by lazy {
        // Use this function ('createRetrofit()') when you wish to use a different 'baseUrl' with your service
        createRetrofit("https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/")
            .create(PokemonNetworkService::class.java)
    }

    val matchUpService: MatchUpNetworkService by lazy {
        // Use this when you just want to use a different service
        getRetrofit.create(MatchUpNetworkService::class.java)
    }

    val baseStatService: BaseStatNetworkService by lazy {
        // This behaves the same as using 'createRetrofit(baseUrl)',
        // but offers more options by using the builder itself.
        getRetrofit.newBuilder().baseUrl("").build()
            .create(BaseStatNetworkService::class.java)
    }
}