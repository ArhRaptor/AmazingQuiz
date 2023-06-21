package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "region",
    foreignKeys = [ForeignKey(
        entity = Questions::class,
        parentColumns = ["id"],
        childColumns = ["question_id"],
        onDelete = ForeignKey.CASCADE
    )])
data class Regions(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("region")
    val region: String,
    @ColumnInfo("question_id")
    val questionId: Long
)