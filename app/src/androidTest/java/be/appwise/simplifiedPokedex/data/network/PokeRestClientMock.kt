package be.appwise.simplifiedPokedex.data.network

import be.appwise.core.networking.base.BaseRestClient

object PokeRestClientMock: BaseRestClient() {
    var mockBaseUrl = "/"

    val pokemonService: PokemonNetworkService by lazy {
        getRetrofit.create(PokemonNetworkService::class.java)
    }

    override val protectedClient = false

    // http://localhost:57705/
    override fun getBaseUrl() = mockBaseUrl
}