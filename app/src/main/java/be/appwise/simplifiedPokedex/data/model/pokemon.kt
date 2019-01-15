package be.appwise.simplifiedPokedex.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

//TODO: look at @Embedded
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    val base_happiness: Int,
    val capture_rate: Int,
    val description_x: String,
    val description_y: String,
    val egg_group1: String?,
    val egg_group2: String?,
    val egg_steps: String,
    val evYield: String?,
    val gender_spread: String,
    val growth: String,
    val height: String,
    val isMega: Int,
    val location_x: String,
    val location_y: String,
    val name: String,
    val nat_dex: Int,
    val no_order: Int,
    val species: String,
    val type1: String,
    val type2: String?,
    val weight: String
)