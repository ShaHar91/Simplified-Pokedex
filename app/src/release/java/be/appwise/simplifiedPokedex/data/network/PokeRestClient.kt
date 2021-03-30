package be.appwise.simplifiedPokedex.data.network

import be.appwise.core.networking.base.BaseRestClient

object PokeRestClient : BaseRestClient<NetworkService>(){
    override val apiService = NetworkService::class.java
    override val protectedClient = false
    override fun enableBagelInterceptor() = true

    override fun getBaseUrl() = "https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/"
}