package be.appwise.simplifiedPokedex.data.network

import be.appwise.core.networking.base.BaseRestClient

object PokeRestClientMock: BaseRestClient<NetworkService>() {
    var mockBaseUrl = "/"

    override val apiService = NetworkService::class.java
    override val protectedClient = false

    // http://localhost:57705/
    override fun getBaseUrl() = mockBaseUrl
}