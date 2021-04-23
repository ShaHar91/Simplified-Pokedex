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
        getRetrofit.create(PokemonNetworkService::class.java)
    }

    val matchUpService: MatchUpNetworkService by lazy {
        getRetrofit.create(MatchUpNetworkService::class.java)
    }

    val baseStatService: BaseStatNetworkService by lazy {
        getRetrofit.create(BaseStatNetworkService::class.java)
    }
}