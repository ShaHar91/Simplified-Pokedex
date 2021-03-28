package be.appwise.simplifiedPokedex.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.appwise.core.data.base.BaseRoomDao
import be.appwise.simplifiedPokedex.data.model.BaseStat

@Dao
abstract class BaseStatDao : BaseRoomDao<BaseStat>("base_stat") {
    @Query("SELECT * from base_stat where _id = :id")
    abstract suspend fun getBaseStatById(id: Int): BaseStat

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertList(baseStats: List<BaseStat>)
}