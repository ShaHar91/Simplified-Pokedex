package be.appwise.simplifiedPokedex.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import be.appwise.simplifiedPokedex.data.dao.PokemonDao
import be.appwise.simplifiedPokedex.data.model.Pokemon

@Database(entities = arrayOf(Pokemon::class), version = 1)
abstract class SimplifiedPokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        private var INSTANCE: SimplifiedPokedexDatabase? = null
        fun getInstance(context: Context): SimplifiedPokedexDatabase? {
            if (INSTANCE == null) {
                synchronized(SimplifiedPokedexDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SimplifiedPokedexDatabase::class.java, "simplified_pokedex.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}