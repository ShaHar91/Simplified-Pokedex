package be.appwise.simplifiedPokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import be.appwise.simplifiedPokedex.data.model.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * from pokemon")
    fun getAll(): List<Pokemon>

    @Insert(onConflict = REPLACE)
    fun insert(pokemon: Pokemon)

    @Query("DELETE from pokemon")
    fun deleteAll()

    @Transaction
    fun updateData(pokemons: List<Pokemon>) {
        insertAll(pokemons)
    }

    @Insert(onConflict = REPLACE)
    fun insertAll(pokemons: List<Pokemon>)
}