package be.appwise.simplifiedPokedex.data.network

import com.appham.mockinizer.RequestFilter
import com.appham.mockinizer.mockinize
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClientConfig: IClientConfig{
    override var getBaseUrl: HttpUrl =
        "https://raw.githubusercontent.com/ShaHar91/Simplified-Pokedex/master/json/".toHttpUrl()

    override fun okHttpConfig(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .mockinize(mocks)
            .retryOnConnectionFailure(false)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    override fun retrofitConfig(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().apply {
                        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    }.create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpConfig().build())
            .build()
    }

    private val mocks: Map<RequestFilter, MockResponse> = mapOf(
        RequestFilter("/ShaHar91/Simplified-Pokedex/master/json/pokemon.json") to MockResponse().apply {
            setResponseCode(200)
            setBody("""[{
        "_id": "1",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "A strange seed was planted on its back at birth. The plant sprouts and grows with his Pokémon.",
        "description_y": "For some time after its birth, it grows by gaining nourishment from the seed on its back.",
        "egg_group1": "Monster",
        "egg_group2": "Grass",
        "egg_steps": "5120",
        "ev_yield": "1 Sp. Attack Point(s)",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "0",
        "height": "2'04\"\n0,7 m",
        "is_alternate": 0,
        "is_mega": 0,
        "location_x": "Lumiose City - Professor Sycamore",
        "location_y": "Lumiose City - Professor Sycamore",
        "name": "Bulbasaur",
        "nat_dex": "1",
        "notes": "",
        "species": "Seed",
        "type1": "Grass",
        "type2": "Poison",
        "weight": "15,2 lbs\n6,9 kg"
    },
    {
        "_id": "2",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "There is a plant bulb on its back. When it absorbs nutrients, the bulb is said to blossom into a large flower.",
        "description_y": "When the bud on its back starts swelling, a sweet aroma wafts to indicate the flower's coming bloom.",
        "egg_group1": "Monster",
        "egg_group2": "Grass",
        "egg_steps": "5120",
        "ev_yield": "1 Sp. Attack Point(s)\n1 Sp. Defense Point(s)",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "0",
        "height": "3'03\"\n1,0 m",
        "is_alternate": 0,
        "is_mega": 0,
        "location_x": "Friend Safari",
        "location_y": "Friend Safari",
        "name": "Ivysaur",
        "nat_dex": "2",
        "notes": "",
        "species": "Seed",
        "type1": "Grass",
        "type2": "Poison",
        "weight": "28,7 lbs\n13,0 kg"
    },
    {
        "_id": "3",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "By spreading the broad petals of its flower and catching the sun's rays, it fills its body with power.",
        "description_y": "After a rainy day, the flower on its back smells stronger. The scent attracts other Pokémon.",
        "egg_group1": "Monster",
        "egg_group2": "Grass",
        "egg_steps": "5120",
        "ev_yield": "2 Sp. Attack Point(s)\n1 Sp. Defense Point(s)",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "1",
        "height": "6'07\"\n2,0 m",
        "is_alternate": 0,
        "is_mega": 0,
        "location_x": "Evolve Ivysaur",
        "location_y": "Evolve Ivysaur",
        "name": "Venusaur",
        "nat_dex": "3",
        "notes": "",
        "species": "Seed",
        "type1": "Grass",
        "type2": "Poison",
        "weight": "220,5 lbs\n100,0 kg"
    }]""".trimMargin())
        }
    )
}