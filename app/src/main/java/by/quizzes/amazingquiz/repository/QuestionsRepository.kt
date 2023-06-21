package by.quizzes.amazingquiz.repository

import by.quizzes.amazingquiz.db.IncorrectAnswersDao
import by.quizzes.amazingquiz.db.QuestionsDao
import by.quizzes.amazingquiz.db.RegionsDao
import by.quizzes.amazingquiz.db.TagsDao
import by.quizzes.amazingquiz.model.db.IncorrectAnswers
import by.quizzes.amazingquiz.model.db.Questions
import by.quizzes.amazingquiz.model.db.Regions
import by.quizzes.amazingquiz.model.db.Tags
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
    private val questionsDao: QuestionsDao,
    private val incorrectAnswersDao: IncorrectAnswersDao,
    private val tagsDao: TagsDao,
    private val regionsDao: RegionsDao
) {
    suspend fun addQuestion(question: Questions) = questionsDao.addQuestion(question)
    suspend fun addIncorrectAnswers(incorrectAnswers: List<IncorrectAnswers>) = incorrectAnswersDao.addIncorrectAnswers(incorrectAnswers)
    suspend fun getIncorrectAnswers(questionId: Long?) = incorrectAnswersDao.getIncorrectAnswers(questionId)
    suspend fun addRegions(regions: List<Regions>) = regionsDao.addRegions(regions)
    suspend fun getRegionsByQuestionId(questionId: Long?) = regionsDao.getRegionsByQuestionId(questionId)
    suspend fun addTags(tags: List<Tags>) = tagsDao.addTags(tags)
    suspend fun setUserAnswer(userAnswer: String?, question: String?, score: Int?, quizId: Long) = questionsDao.setUserAnswer(userAnswer, question, score, quizId)
    suspend fun getSumOfScores(quizId: Long) = questionsDao.getSumOfScores(quizId)
    suspend fun getCountQuestion(quizId: Long) = questionsDao.getCountQuestion(quizId)
    suspend fun getCountCorrectAnswers(quizId: Long) = questionsDao.getCountCorrectAnswers(quizId)
    suspend fun getQuestions(quizId: Long) = questionsDao.getQuestions(quizId)
    suspend fun getQuestionsForQuiz(quizId: Long) = questionsDao.getQuestionsForQuiz(quizId)
    suspend fun updateTags(questionId:Long, isCorrect: Boolean) = tagsDao.updateTags(questionId, isCorrect)
    suspend fun getTagsByQuestionId(questionId: Long?) = tagsDao.getTagsByQuestionId(questionId)
    suspend fun getQuestionId(quizId: Long, question: String?) = questionsDao.getQuestionId(quizId, question)
    suspend fun selectUniqueTag(userId: Long) = tagsDao.selectUniqueTags(userId)
    suspend fun getCountTag(tag: String) = tagsDao.getCountTag(tag)
}