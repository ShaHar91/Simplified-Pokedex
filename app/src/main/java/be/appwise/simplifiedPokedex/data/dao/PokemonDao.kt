package be.appwise.simplifiedPokedex.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import be.appwise.core.data.base.BaseRoomDao
import be.appwise.simplifiedPokedex.data.model.Pokemon

@Dao
abstract class PokemonDao : BaseRoomDao<Pokemon>("pokemon") {
    @Query("SELECT * from pokemon where _id = :id")
    abstract suspend fun getPokemonById(id: Int): Pokemon

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertList(pokemons: List<Pokemon>)

    @Query("SELECT * FROM pokemon where name LIKE '%' || :query || '%' and is_alternate = 0 and is_mega = 0")
    abstract fun findPokemonsByQueryLive(query: String): LiveData<List<Pokemon>>
}