package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "questions",
    foreignKeys = [ForeignKey(
        entity = DbQuiz::class,
        parentColumns = ["id"],
        childColumns = ["quiz_id"],
        onDelete = ForeignKey.CASCADE
    )])
data class Questions(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("question")
    val question: String,
    @ColumnInfo("correctAnswer")
    val correctAnswer: String,
    @ColumnInfo("user_answer")
    var userAnswer: String? = null,
    @ColumnInfo("difficulty")
    val difficulty: String,
    @ColumnInfo("score")
    var score: Int? = null,
    @ColumnInfo("quiz_id")
    var quizId: Long
)
