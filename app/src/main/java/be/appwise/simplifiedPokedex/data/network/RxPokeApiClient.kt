package be.appwise.simplifiedPokedex.data.network

class RxPokeApiClient(
        private val clientConfig: ClientConfig = ClientConfig()
) : RxPokeApi {
    private val service = RxPokeApiServiceImpl(clientConfig)

    // region Pokemon

    override fun getPokemon(id: Int) = service.getPokemon(id)

    // endregion Pokemon
}
