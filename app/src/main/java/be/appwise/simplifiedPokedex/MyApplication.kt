package be.appwise.simplifiedPokedex

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import be.appwise.core.core.CoreApp
import be.appwise.core.networking.Networking
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.network.PokeRestClient
import be.appwise.simplifiedPokedex.data.repository.BaseStatRepository
import be.appwise.simplifiedPokedex.data.repository.MatchUpRepository
import be.appwise.simplifiedPokedex.data.repository.PokemonRepository

class MyApplication : MultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context

        fun getContext(): Context {
            return mContext
        }

//        private val service: NetworkService by lazy { PokeRestClient.getService }
        private val pokedexDatabase: SimplifiedPokedexDatabase by lazy {
            SimplifiedPokedexDatabase.getDatabase(getContext())
        }

        val pokemonRepository: PokemonRepository by lazy {
            PokemonRepository(pokedexDatabase.pokemonDao(), PokeRestClient.pokemonService)
        }

        val matchUpRepository: MatchUpRepository by lazy {
            MatchUpRepository(pokedexDatabase.matchUpDao(), PokeRestClient.matchUpService)
        }

        val baseStatRepository: BaseStatRepository by lazy {
            BaseStatRepository(pokedexDatabase.baseStatDao(), PokeRestClient.baseStatService)
        }
    }

    override fun onCreate() {
        super.onCreate()

        mContext = this

        Networking.Builder()
            .registerBagelService(this)
            .setPackageName(packageName)
            .setAppName(getString(R.string.app_name))
            .setVersionCode(BuildConfig.VERSION_CODE.toString())
            .setVersionName(BuildConfig.VERSION_NAME)
            .build()

        CoreApp.init(this)
            .initializeLogger("SimplifiedPokedex", BuildConfig.DEBUG)
            .build()
    }
}