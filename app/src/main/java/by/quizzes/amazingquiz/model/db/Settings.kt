package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("setting")
data class Settings(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    @ColumnInfo("limit")
    val limit: Int,
    @ColumnInfo("difficulty")
    var difficulty: String? = null,
    @ColumnInfo("timer")
    var timer: Boolean
)