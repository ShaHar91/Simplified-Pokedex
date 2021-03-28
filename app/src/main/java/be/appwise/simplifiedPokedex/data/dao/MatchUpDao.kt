package be.appwise.simplifiedPokedex.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.appwise.core.data.base.BaseRoomDao
import be.appwise.simplifiedPokedex.data.model.MatchUp

@Dao
abstract class MatchUpDao: BaseRoomDao<MatchUp>("match_up") {
    @Query("SELECT * from match_up where type1 = :type1 and type2 = :type2")
    abstract fun getMatchUpForTypes(type1: String?, type2: String?): MatchUp

    @Query("DELETE from match_up")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(matchUps: List<MatchUp>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertList(matchUps: List<MatchUp>)

    @Query("SELECT * from match_up where type1 = :type1 and type2 = :type2")
    abstract suspend fun getMatchUpForTypesLive(type1: String?, type2: String?): MatchUp
}