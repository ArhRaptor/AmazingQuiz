package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.DbQuiz

@Dao
interface QuizDao {

    @Insert
    suspend fun addQuiz(quiz: DbQuiz): Long

    @Query("UPDATE quiz SET total_score = :totalScore WHERE id = :id")
    suspend fun setTotalScore(id: Long, totalScore: Int)

    @Query("UPDATE quiz SET is_complete = :isComplete WHERE id = :id")
    suspend fun setQuizComplete(id: Long, isComplete: Boolean)

    @Query("SELECT SUM(total_score) FROM quiz WHERE user_id = :userId")
    suspend fun getSumOfScores(userId: Long): Int?
}