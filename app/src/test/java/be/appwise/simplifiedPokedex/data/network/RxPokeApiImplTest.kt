package be.appwise.simplifiedPokedex.data.network

import be.appwise.simplifiedPokedex.data.model.Pokemon
import be.appwise.simplifiedPokedex.data.repository.RxPokeApiRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Test
import java.nio.charset.StandardCharsets
import org.junit.Assert.assertEquals
import retrofit2.converter.gson.GsonConverterFactory

class RxPokeApiImplTest {
    private val mockWebServer = MockWebServer()

    private val config = ClientConfig.apply {
        this.getBaseUrl = mockWebServer.url("/")
    }
    private val retrofit = config.retrofitConfig()

    private val sut = RxPokeApiRepositoryImpl(retrofit.create(RxPokeApiService::class.java))

    @Test
    fun shouldFetchPokemons() {
        mockWebServer.enqueueResponse("pokemons-200.json", 200)

        runBlocking {
            val actual = sut.getPokemon()

            val expected = listOf(
                Pokemon(
                    1,
                    "Bulbasaur",
                    1,
                    "Seed",
                    "Grass",
                    "Poison",
                    "Monster",
                    "Grass",
                    "2'04\"\n0,7 m",
                    "15,2 lbs\n6,9 kg",
                    "87,5% ♂\n12,5% ♀",
                    "1 Sp. Attack Point(s)",
                    "5120",
                    "70",
                    "45",
                    "Medium Slow",
                    "A strange seed was planted on its back at birth. The plant sprouts and grows with his Pokémon.",
                    "For some time after its birth, it grows by gaining nourishment from the seed on its back.",
                    "Lumiose City - Professor Sycamore",
                    "Lumiose City - Professor Sycamore",
                    0,
                    0,
                    "",
                    0
                )
            )

            assertEquals(expected, actual.blockingFirst())
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