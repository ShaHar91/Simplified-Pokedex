package be.appwise.simplifiedPokedex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.appwise.simplifiedPokedex.data.dao.BaseStatDao
import be.appwise.simplifiedPokedex.data.dao.MatchUpDao
import be.appwise.simplifiedPokedex.data.dao.PokemonDao
import be.appwise.simplifiedPokedex.data.model.BaseStat
import be.appwise.simplifiedPokedex.data.model.MatchUp
import be.appwise.simplifiedPokedex.data.model.Pokemon

@Database(entities = [Pokemon::class, BaseStat::class, MatchUp::class], version = 1)
abstract class SimplifiedPokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun baseStatDao(): BaseStatDao
    abstract fun matchUpDao(): MatchUpDao

    companion object {
        @Volatile
        private var INSTANCE: SimplifiedPokedexDatabase? = null

        fun getDatabase(context: Context): SimplifiedPokedexDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        SimplifiedPokedexDatabase::class.java, "simplified_pokedex.db"
                    ).build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}