package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    foreignKeys = [ForeignKey(
        entity = Settings::class,
        parentColumns = ["id"],
        childColumns = ["settings_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class QuizCategories(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("value")
    val value: String,
    @ColumnInfo("settings_id")
    var settingsId: Long? = null
)