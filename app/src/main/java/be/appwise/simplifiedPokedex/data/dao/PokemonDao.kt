package be.appwise.simplifiedPokedex.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
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
    fun updateData(pokemons: List<Pokemon>){
        insertAll(pokemons)
    }

    @Insert(onConflict = REPLACE)
    fun insertAll(pokemons: List<Pokemon>)
}