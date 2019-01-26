package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.model.MatchUp
import io.reactivex.Observable

class RxPokeApiClient(
        private val clientConfig: ClientConfig = ClientConfig()
) : RxPokeApi {
    private val service = RxPokeApiServiceImpl(clientConfig)

    // region Pokemon

    override fun getPokemon() = service.getPokemon()

    override fun getBaseStat() = service.getBaseStat()

    override fun getMatchUps() = service.getMatchUps()
    // endregion Pokemon
}

