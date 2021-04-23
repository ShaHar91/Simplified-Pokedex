package be.appwise.simplifiedPokedex.data.repository

import be.appwise.core.data.base.BaseRepository
import be.appwise.simplifiedPokedex.data.dao.BaseStatDao
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.network.BaseStatNetworkService

class BaseStatRepository(
    private val baseStatDao: BaseStatDao,
    private val service: BaseStatNetworkService
): BaseRepository() {

    private suspend fun insertAllBaseStats(baseStats: List<BaseStat>) =
        baseStatDao.insertList(baseStats)

    suspend fun getAllBaseStats() {
        val baseStats = doCall(service.getAllBaseStats())

        insertAllBaseStats(baseStats)
    }

    suspend fun getBaseStatsByPokemonId(pokemonId: Int) = baseStatDao.getBaseStatById(pokemonId)
}