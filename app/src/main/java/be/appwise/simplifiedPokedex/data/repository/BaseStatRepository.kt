package be.appwise.simplifiedPokedex.data.repository

import be.appwise.core.data.base.BaseRepository
import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.dao.BaseStatDao
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.network.NetworkService

class BaseStatRepository(
    private val baseStatDao: BaseStatDao,
    private val service: NetworkService
): BaseRepository() {

    private suspend fun insertAllBaseStats(baseStats: List<BaseStat>) =
        baseStatDao.insertList(baseStats)

    suspend fun getAllBaseStats() {
        val baseStats = doCall(service.getAllBaseStats())

        insertAllBaseStats(baseStats)
    }

    suspend fun getBaseStatsByPokemonId(pokemonId: Int) = baseStatDao.getBaseStatById(pokemonId)
}