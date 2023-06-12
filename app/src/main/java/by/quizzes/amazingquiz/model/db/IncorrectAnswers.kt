package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "incorrect_answers",
foreignKeys = [ForeignKey(
    entity = Questions::class,
    parentColumns = ["id"],
    childColumns = ["question_id"],
    onDelete = ForeignKey.CASCADE
)])
data class IncorrectAnswers(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("incorrect_answer")
    val incorrectAnswer: String,
    @ColumnInfo("question_id")
    val questionId: Long
)
