package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "quiz",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DbQuiz(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("date")
    val date: Date,
    @ColumnInfo("total_score")
    var totalScore: Int? = null,
    @ColumnInfo("is_complete")
    var isComplete: Boolean = false,
    @ColumnInfo("user_id")
    val userId: Long
)
