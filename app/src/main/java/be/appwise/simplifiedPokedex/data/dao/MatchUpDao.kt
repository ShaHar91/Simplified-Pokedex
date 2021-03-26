package be.appwise.simplifiedPokedex.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.model.Pokemon

@Dao
interface MatchUpDao {
    @Query("SELECT * from match_up where type1 = :type1 and type2 = :type2")
    fun getMatchUpForTypes(type1: String?, type2: String?): MatchUp

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(matchUp: MatchUp)

    @Query("DELETE from match_up")
    fun deleteAll()

    @Transaction
    fun updateData(matchUps: List<MatchUp>) {
        insertAll(matchUps)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(matchUps: List<MatchUp>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(matchUps: List<MatchUp>)

    @Query("SELECT * from match_up where type1 = :type1 and type2 = :type2")
    fun getMatchUpForTypesLive(type1: String?, type2: String?): LiveData<MatchUp>
}