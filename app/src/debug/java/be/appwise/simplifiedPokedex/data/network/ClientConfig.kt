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

    override fun <T> getService(service: Class<T>): T {
        return retrofitConfig().create(service)
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
    },
    {
        "_id": "4",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "The flame on its tail indicates Charmander's life force. If it's healthy, the flame burns brightly.",
        "description_y": "From the time is born, a flame burns at the tip of its tail. Its life would end if the flame were to go out.",
        "egg_group1": "Monster",
        "egg_group2": "Dragon",
        "egg_steps": "5120",
        "ev_yield": "1 Speed Point(s)",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "0",
        "height": "2'00\"\n0,6 m",
        "is_alternate": 0,
        "is_mega": 0,
        "location_x": "Lumiose City - Professor Sycamore",
        "location_y": "Lumiose City - Professor Sycamore",
        "name": "Charmander",
        "nat_dex": "4",
        "notes": "",
        "species": "Lizard",
        "type1": "Fire",
        "type2": "",
        "weight": "18,7 lbs\n8,5 kg"
    },
    {
        "_id": "5",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "It lashes about with its tail to knock down its foe. It then tears up the fallen opponent with sharp claws. ",
        "description_y": "When it swings its burning tail, it elevates the air temperature to unbearably high levels.",
        "egg_group1": "Monster",
        "egg_group2": "Dragon",
        "egg_steps": "5120",
        "ev_yield": "1 Speed Point(s)\n1 Sp. Attack Point(s)",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "0",
        "height": "3'7\"\n1,1 m",
        "is_alternate": 0,
        "is_mega": 0,
        "location_x": "Friend Safari",
        "location_y": "Friend Safari",
        "name": "Charmeleon",
        "nat_dex": "5",
        "notes": "",
        "species": "Flame",
        "type1": "Fire",
        "type2": "",
        "weight": "41,9 lbs\n19,0 kg"
    },
    {
        "_id": "6",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "When expelling a blast of superhot fire, the red flame at the tip of its tail burns more intensely.",
        "description_y": "Its wings can carry this Pokémon close to an altitude of 4,600 feet. It blows out fire at very high temperatures.",
        "egg_group1": "Monster",
        "egg_group2": "Dragon",
        "egg_steps": "5120",
        "ev_yield": "3 Sp. Attack Point(s)",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "1",
        "height": "5'07\"\n1,7 m",
        "is_alternate": 0,
        "is_mega": 0,
        "location_x": "Evolve Charmeleon",
        "location_y": "Evolve Charmeleon",
        "name": "Charizard",
        "nat_dex": "6",
        "notes": "",
        "species": "Flame",
        "type1": "Fire",
        "type2": "Flying",
        "weight": "199,5 lbs\n90,5 kg"
    },
    {
        "_id": "10033",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "/",
        "description_y": "/",
        "egg_group1": "",
        "egg_group2": "",
        "egg_steps": "5120",
        "ev_yield": "/",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "0",
        "height": "7'10\"\n2,4 m",
        "is_alternate": 0,
        "is_mega": 1,
        "location_x": "Using Mega Stone",
        "location_y": "Using Mega Stone",
        "name": "Mega Venusaur",
        "nat_dex": "3",
        "notes": "",
        "species": "Seed",
        "type1": "Grass",
        "type2": "Poison",
        "weight": "342,8 lbs\n155,5 kg"
    },
    {
        "_id": "10034",
        "base_happiness": "70",
        "capture_rate": "45",
        "description_x": "/",
        "description_y": "/",
        "egg_group1": "",
        "egg_group2": "",
        "egg_steps": "5120",
        "ev_yield": "/",
        "gender_spread": "87,5% ♂\n12,5% ♀",
        "growth": "Medium Slow",
        "has_mega": "0",
        "height": "5'07\"\n1.7 m",
        "is_alternate": 0,
        "is_mega": 1,
        "location_x": "Using Mega Stone",
        "location_y": "Using Mega Stone",
        "name": "Mega Charizard X",
        "nat_dex": "6",
        "notes": "",
        "species": "Flame",
        "type1": "Fire",
        "type2": "Dragon",
        "weight": "243,6 lbs\n110,5 kg"
    }]""".trimMargin())
        }
    )
}