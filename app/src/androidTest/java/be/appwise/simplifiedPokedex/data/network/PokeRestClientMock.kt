package be.appwise.simplifiedPokedex.data.network

import be.appwise.core.networking.base.BaseRestClient
import be.appwise.core.networking.base.BaseSimpleRestClient

object PokeRestClientMock: BaseSimpleRestClient() {
    var mockBaseUrl = "/"

    val pokemonService: PokemonNetworkService by lazy {
        getRetrofit.create(PokemonNetworkService::class.java)
    }

    override val protectedClient = false

    // http://localhost:57705/
    override fun getBaseUrl() = mockBaseUrl
}