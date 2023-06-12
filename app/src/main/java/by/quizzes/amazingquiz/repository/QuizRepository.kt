package by.quizzes.amazingquiz.repository

import by.quizzes.amazingquiz.db.QuizDao
import by.quizzes.amazingquiz.model.db.DbQuiz
import by.quizzes.amazingquiz.network.QuizApi
import javax.inject.Inject

class QuizRepository @Inject constructor(private val api: QuizApi, private val quizDao: QuizDao) {

    suspend fun getCategories() = api.getCategories()
    suspend fun getQuiz(
        limit: Int, difficulty: String?, categories: String?, region: String?, tags: String?
    ) = api.getQuiz(limit, difficulty, categories, region, tags)

    suspend fun addQuizToDb(quiz: DbQuiz) = quizDao.addQuiz(quiz)
    suspend fun setTotalScore(id:Long, totalScore: Int) = quizDao.setTotalScore(id, totalScore)
    suspend fun setQuizComplete(id: Long, isComplete: Boolean) = quizDao.setQuizComplete(id, isComplete)
    suspend fun getSumOfScore(userId: Long) = quizDao.getSumOfScores(userId)
}