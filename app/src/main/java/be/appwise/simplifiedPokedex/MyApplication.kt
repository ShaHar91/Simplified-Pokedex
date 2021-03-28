package be.appwise.simplifiedPokedex

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.network.ClientConfig
import be.appwise.simplifiedPokedex.data.network.IClientConfig
import be.appwise.simplifiedPokedex.data.network.RxPokeApiService
import be.appwise.simplifiedPokedex.data.repository.PokemonRepository

class MyApplication : MultiDexApplication() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context

        fun getContext(): Context {
            return mContext
        }

        val clientConfig: IClientConfig by lazy { ClientConfig }
        val service: RxPokeApiService by lazy { clientConfig.getService(RxPokeApiService::class.java) }
        val pokedexDatabase: SimplifiedPokedexDatabase by lazy {
            SimplifiedPokedexDatabase.getDatabase(getContext())
        }

        val pokemonRepository: PokemonRepository by lazy {
            PokemonRepository(pokedexDatabase.pokemonDao(), service)
        }
    }

    override fun onCreate() {
        super.onCreate()

        mContext = this

//        CoreApp.init(this)
//            .initializeErrorActivity(BuildConfig.DEBUG)
//            .initializeLogger()
//            .build()
    }
}