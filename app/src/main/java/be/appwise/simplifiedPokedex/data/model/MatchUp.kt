package be.appwise.simplifiedPokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_up")
data class MatchUp(
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    val bug: String,
    val dark: String,
    val dragon: String,
    val electric: String,
    val fairy: String,
    val fight: String,
    val fire: String,
    val flying: String,
    val ghost: String,
    val grass: String,
    val ground: String,
    val ice: String,
    val normal: String,
    val poison: String,
    val psychic: String,
    val rock: String,
    val steel: String,
    val type1: String,
    val type2: String,
    val water: String
)