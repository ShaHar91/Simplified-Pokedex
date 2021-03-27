package be.appwise.simplifiedPokedex.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.appwise.core.data.base.BaseEntity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "base_stat")
data class BaseStat(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("_id")
    @ColumnInfo(name = "_id")
    override val id: Int,
    val att: Int,
    val def: Int,
    val hp: Int,
    val sp_att: Int,
    val sp_def: Int,
    val speed: Int
) : BaseEntity()