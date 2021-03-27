package be.appwise.simplifiedPokedex.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import be.appwise.simplifiedPokedex.data.model.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * from pokemon where is_alternate = 0 and is_mega = 0")
    fun getAll(/*isAlternate: String = "0", isMega: String = "0"*/): List<Pokemon>

    @Query("SELECT * from pokemon where _id = :id")
    fun getPokemonById(id: Int): Pokemon

    @Insert(onConflict = REPLACE)
    fun insertAll(pokemons: List<Pokemon>)


    @Insert(onConflict = REPLACE)
    suspend fun insertList(pokemons: List<Pokemon>)

    @Query("SELECT * from pokemon where _id = :id")
    fun getPokemonByIdLive(id: Int): LiveData<Pokemon>

    @Query("SELECT * FROM pokemon where name LIKE '%' || :query || '%' and is_alternate = 0 and is_mega = 0")
    fun findPokemonsByQueryLive(query: String): LiveData<List<Pokemon>>
}