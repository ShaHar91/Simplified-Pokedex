package be.appwise.simplifiedPokedex.data.typeConverters

import android.arch.persistence.room.TypeConverter
import be.appwise.simplifiedPokedex.data.model.PokemonType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun fromTimestamp(data: String?): List<PokemonType>? {

        if (data == null){
            return Collections.emptyList()
        }
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<PokemonType>?): String? {
        return gson.toJson(someObjects)
    }
}