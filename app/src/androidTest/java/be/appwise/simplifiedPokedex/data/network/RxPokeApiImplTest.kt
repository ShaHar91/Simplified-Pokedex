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
import be.appwise.simplifiedPokedex.data.repository.PokemonRepository
import be.appwise.simplifiedPokedex.data.testingUtils.enqueueResponse
import be.appwise.simplifiedPokedex.data.testingUtils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
class RxPokeApiImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()

    private val config = ClientConfig.apply { this.getBaseUrl = mockWebServer.url("/") }
    private val service = config.getService(RxPokeApiService::class.java)

    private lateinit var database: SimplifiedPokedexDatabase
    private lateinit var pokemonDao: PokemonDao
    private lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SimplifiedPokedexDatabase::class.java
        ).allowMainThreadQueries().build()

        pokemonDao = database.pokemonDao()
        pokemonRepository = PokemonRepository(pokemonDao, service)
    }

    @Test
    fun shouldFetchPokemons() {
        mockWebServer.enqueueResponse("pokemons-200.json", 200)

        runBlocking {
            pokemonRepository.getAllPokemons()

            val allPokemons = pokemonRepository.getPokemonsByQuery("").getOrAwaitValue()

            assertEquals(1, allPokemons.size)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}