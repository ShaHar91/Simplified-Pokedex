package be.appwise.simplifiedPokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "base_stat")
data class BaseStat(
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    val att: Int,
    val def: Int,
    val hp: Int,
    val sp_att: Int,
    val sp_def: Int,
    val speed: Int
)