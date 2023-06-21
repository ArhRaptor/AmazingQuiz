package by.quizzes.amazingquiz.ui.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.model.api.Quiz
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizPresencesRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import by.quizzes.amazingquiz.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(
    private val quizRepository: QuizRepository,
    private val settingsRepository: SettingsRepository,
    private val questionsRepository: QuestionsRepository,
    private val quizPresencesRepository: QuizPresencesRepository
) : ViewModel() {

    val quiz = MutableLiveData<Quiz>()
    val isTimer = MutableLiveData<Boolean?>()
    var openResultFragment: (() -> Unit)? = null
    private val arrayQuiz = arrayListOf<Quiz>()

    private var currentQuizNumber = 0

    fun getQuiz() {
        viewModelScope.launch(Dispatchers.IO) {
            val settings = settingsRepository.getSettings()
            isTimer.postValue(settings.timer)

            val questionList = questionsRepository.getQuestionsForQuiz(quizPresencesRepository.getQuizId())

            for (question in questionList) {
                arrayQuiz.add(
                    Quiz(
                        question = question.question,
                        correctAnswer = question.correctAnswer,
                        difficulty = question.difficulty,
                        incorrectAnswers = questionsRepository.getIncorrectAnswers(question.id),
                        tags = questionsRepository.getTagsByQuestionId(question.id),
                        regions = questionsRepository.getRegionsByQuestionId(question.id)
                    )
                )
            }
            setQuiz()

        }
    }

    fun getNextQuiz() {
        if (currentQuizNumber < arrayQuiz.size - 1) {
            currentQuizNumber += 1
            setQuiz()
        } else {
            openResultFragment?.invoke()
        }
    }

    fun setUserAnswer(
        question: String?,
        userAnswer: String?,
        correctAnswer: String,
        currentDifficulty: String?,
        isTimer: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            var score = 2
            if (userAnswer == correctAnswer) {
                when (currentDifficulty) {
                    "medium" -> {
                        score += 2
                    }

                    "hard" -> {
                        score += 3
                    }
                }
            } else {
                score = 0
            }
            if (isTimer){
                score *= 2
            }


            val quizId = quizPresencesRepository.getQuizId()

            questionsRepository.apply {
                setUserAnswer(userAnswer, question, score, quizId)
                val questionId = getQuestionId(quizId, question)
                if (score > 0) {
                    updateTags(questionId = questionId, isCorrect = true)
                } else {
                    updateTags(questionId = questionId, isCorrect = false)
                }
            }
            quizRepository.setTotalScore(quizId, questionsRepository.getSumOfScores(quizId) ?: 0)
        }
    }

    private fun setQuiz() {
        quiz.postValue(arrayQuiz[currentQuizNumber])
    }
}