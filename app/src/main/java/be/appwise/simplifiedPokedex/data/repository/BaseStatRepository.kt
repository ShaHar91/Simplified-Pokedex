package be.appwise.simplifiedPokedex.data.repository

import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.network.RxPokeApiService

object BaseStatRepository {
    private val baseStatDao = MyApplication.pokedexDatabase.baseStatDao()
    private val service: RxPokeApiService = MyApplication.service

    private suspend fun insertAllBaseStats(baseStats: List<BaseStat>) =
        baseStatDao.insertList(baseStats)

    suspend fun getAllBaseStats() {
        val baseStats = MyApplication.clientConfig.doCall(service.getAllBaseStats())

        insertAllBaseStats(baseStats)
    }
}