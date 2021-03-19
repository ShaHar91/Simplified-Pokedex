package be.appwise.simplifiedPokedex.data.repository

import be.appwise.simplifiedPokedex.data.network.RxPokeApiService

class RxPokeApiRepositoryImpl(private val service: RxPokeApiService) : RxPokeApiRepository {

    // region Pokemon
    override fun getPokemon() = service.getPokemon()

    override fun getBaseStat() = service.getBaseStat()

    override fun getMatchUps() = service.getMatchUps()
    // endregion Pokemon
}

