package be.appwise.simplifiedPokedex.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

//TODO: look at @Embedded
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    val name: String,
    val nat_dex: Int,
    val species: String,
    val type1: String,
    val type2: String?,
    val egg_group1: String?,
    val egg_group2: String?,
    val height: String?,
    val weight: String?,
    val gender_spread: String?,
    val evYield: String?,
    val egg_steps: String,
    val base_happiness: String?,
    val capture_rate: String?,
    val growth: String?,
    val description_x: String?,
    val description_y: String?,
    val location_x: String?,
    val location_y: String?,
    val isMega: Int,
    val isAlternate: Int,
    val notes: String?,
    val hasMega: Int
)