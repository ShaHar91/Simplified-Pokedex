package be.appwise.simplifiedPokedex.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.appwise.core.data.base.BaseEntity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "match_up")
data class MatchUp(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("_id")
    @ColumnInfo(name = "_id")
    override val id: Int,
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
): BaseEntity()