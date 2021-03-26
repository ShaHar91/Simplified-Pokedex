package be.appwise.simplifiedPokedex.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import java.net.UnknownHostException

interface IClientConfig {
    var getBaseUrl: HttpUrl

    fun okHttpConfig(): OkHttpClient.Builder

    fun retrofitConfig(): Retrofit

    fun <T> getService(service: Class<T>): T

    /**
     * Wrap your network call with this function to have a centralized way to handle response errors
     *
     * @param call Retrofit call
     * @return Type returned by the network call
     */
    suspend fun <T : Any> doCall(call: Call<T>): T {
        return try {
            withContext(Dispatchers.IO) {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    throw Exception("Issue!!!")
                }
            }
        } catch (ex: UnknownHostException) {
            throw Exception("Big Issue!!!")
        }
    }
}