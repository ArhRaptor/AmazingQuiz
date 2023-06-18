package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.Questions

@Dao
interface QuestionsDao {
    @Insert
    suspend fun addQuestion(question: Questions): Long

    @Query("UPDATE questions SET user_answer = :userAnswer, score = :score WHERE question = :question AND quiz_id = :quizId")
    suspend fun setUserAnswer(userAnswer: String?, question: String?, score: Int?, quizId: Long)

    @Query("SELECT SUM(score) FROM questions WHERE quiz_id = :quizId")
    suspend fun getSumOfScores(quizId: Long): Int?

    @Query("SELECT COUNT(*) FROM questions WHERE quiz_id = :quizId")
    suspend fun getCountQuestion(quizId: Long): Int?

    @Query("SELECT COUNT(*) FROM questions WHERE correctAnswer = user_answer AND quiz_id = :quizId")
    suspend fun getCountCorrectAnswers(quizId: Long): Int?

    @Query("SELECT * FROM questions WHERE quiz_id = :quizId")
    suspend fun getQuestions(quizId: Long): List<Questions>

    @Query("SELECT id FROM questions WHERE quiz_id = :quizId AND question = :question")
    suspend fun getQuestionId(quizId: Long, question: String?): Long
}