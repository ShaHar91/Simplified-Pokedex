package be.appwise.simplifiedPokedex

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.network.ClientConfig
import be.appwise.simplifiedPokedex.data.network.IClientConfig
import be.appwise.simplifiedPokedex.data.network.RxPokeApiService
import be.appwise.simplifiedPokedex.data.repository.RxPokeApiRepositoryImpl
import com.orhanobut.hawk.Hawk
import retrofit2.Retrofit

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
    }

    override fun onCreate() {
        super.onCreate()

        mContext = this

        // Initialize Hawk
        Hawk.init(this).build()
    }

}