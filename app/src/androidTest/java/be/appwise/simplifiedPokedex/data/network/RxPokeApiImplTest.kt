package be.appwise.simplifiedPokedex.data.network

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import be.appwise.simplifiedPokedex.data.SimplifiedPokedexDatabase
import be.appwise.simplifiedPokedex.data.dao.PokemonDao
import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.data.repository.RxPokeApiRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.nio.charset.StandardCharsets
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class RxPokeApiImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()

    private val config = ClientConfig.apply {
        this.getBaseUrl = mockWebServer.url("/")
    }
    private val retrofit = config.retrofitConfig()

    private val sut = RxPokeApiRepositoryImpl(retrofit.create(RxPokeApiService::class.java))

    private lateinit var database: SimplifiedPokedexDatabase
    private lateinit var pokemonDao: PokemonDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SimplifiedPokedexDatabase::class.java
        ).allowMainThreadQueries().build()

        pokemonDao = database.pokemonDao()
    }

    @Test
    fun shouldFetchPokemons() {
        mockWebServer.enqueueResponse("pokemons-200.json", 200)

        runBlocking {
            val actual = sut.getPokemon()

            runBlockingTest {
                val list = actual.blockingFirst()
                pokemonDao.insertAll(list)

                val allPokemons = pokemonDao.getAll()

                assertEquals(allPokemons, list)
            }
        }
    }

    internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}

/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}