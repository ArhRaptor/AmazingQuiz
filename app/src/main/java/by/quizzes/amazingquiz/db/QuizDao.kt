package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("SELECT SUM(total_score) FROM quiz WHERE user_id = :userId AND is_complete = 1")
    suspend fun getSumOfScores(userId: Long): Int?

    @Query("SELECT * FROM quiz WHERE user_id = :userId AND is_complete = 0")
    suspend fun getIncompleteQuiz(userId: Long): List<DbQuiz>

    @Delete
    suspend fun deleteQuiz(quiz: DbQuiz)
}