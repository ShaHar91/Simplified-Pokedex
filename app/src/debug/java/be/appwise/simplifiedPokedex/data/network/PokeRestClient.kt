package be.appwise.simplifiedPokedex.data.network

import be.appwise.core.networking.base.BaseSimpleRestClient

object PokeRestClient : BaseSimpleRestClient() {
    override val protectedClient = false
    override fun enableBagelInterceptor() = true

    override fun getBaseUrl() =
        "https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/"

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
        getRetrofit.create(BaseStatNetworkService::class.java)
    }
}