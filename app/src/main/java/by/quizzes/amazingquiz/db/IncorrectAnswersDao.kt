package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.IncorrectAnswers

@Dao
interface IncorrectAnswersDao {
    @Insert
    suspend fun addIncorrectAnswers(incorrectAnswers: List<IncorrectAnswers>)
    @Query("SELECT incorrect_answer FROM incorrect_answers WHERE question_id = :questionId")
    suspend fun getIncorrectAnswers(questionId: Long?): List<String>
}