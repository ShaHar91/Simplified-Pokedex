package be.appwise.simplifiedPokedex.data.repository

import be.appwise.core.data.base.BaseRepository
import be.appwise.simplifiedPokedex.MyApplication
import be.appwise.simplifiedPokedex.data.dao.MatchUpDao
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.network.NetworkService

class MatchUpRepository(
    private val matchUpDao: MatchUpDao,
    private val service: NetworkService
) : BaseRepository() {

    private suspend fun insertAllMatchUps(matchUps: List<MatchUp>) = matchUpDao.insertList(matchUps)

    suspend fun getAllMatchUps() {
        val matchUps = doCall(service.getAllMatchUps())

        insertAllMatchUps(matchUps)
    }

    suspend fun getMatchUpForTypesLive(type1: String?, type2: String?) =
        matchUpDao.getMatchUpForTypesLive(type1, type2)
}