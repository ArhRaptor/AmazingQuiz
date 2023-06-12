package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import by.quizzes.amazingquiz.model.db.IncorrectAnswers

@Dao
interface IncorrectAnswersDao {
    @Insert
    suspend fun addIncorrectAnswers(incorrectAnswers: List<IncorrectAnswers>)
}