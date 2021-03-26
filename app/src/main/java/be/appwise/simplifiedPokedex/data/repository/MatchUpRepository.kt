package be.appwise.simplifiedPokedex.data.repository

import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.network.RxPokeApiService

object MatchUpRepository {
    private val matchUpDao = MyApplication.pokedexDatabase.matchUpDao()
    private val service: RxPokeApiService = MyApplication.service

    private suspend fun insertAllMatchUps(matchUps: List<MatchUp>) = matchUpDao.insertList(matchUps)

    suspend fun getAllMatchUps() {
        val matchUps = MyApplication.clientConfig.doCall(service.getAllMatchUps())

        insertAllMatchUps(matchUps)
    }
}