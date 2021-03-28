package be.appwise.simplifiedPokedex.data.repository

import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.dao.BaseStatDao
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.network.RxPokeApiService

class BaseStatRepository(
    private val baseStatDao: BaseStatDao,
    private val service: RxPokeApiService
) {

    private suspend fun insertAllBaseStats(baseStats: List<BaseStat>) =
        baseStatDao.insertList(baseStats)

    suspend fun getAllBaseStats() {
        val baseStats = MyApplication.clientConfig.doCall(service.getAllBaseStats())

        insertAllBaseStats(baseStats)
    }

    suspend fun getBaseStatsByPokemonId(pokemonId: Int) = baseStatDao.getBaseStatById(pokemonId)
}