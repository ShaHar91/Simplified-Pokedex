package be.appwise.simplifiedPokedex.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.appwise.simplifiedPokedex.data.model.BaseStat

@Dao
interface BaseStatDao {
    @Query("SELECT * from base_stat where _id = :id")
    fun getBaseStatById(id: Int): BaseStat

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(baseStat: BaseStat)

    @Query("DELETE from base_stat")
    fun deleteAll()

    @Transaction
    fun updateData(baseStats: List<BaseStat>) {
        insertAll(baseStats)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(baseStats: List<BaseStat>)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(baseStats: List<BaseStat>)

    @Query("SELECT * from base_stat where _id = :id")
    fun getBaseStatByIdLive(id: Int): LiveData<BaseStat>
}