package by.quizzes.amazingquiz.ui.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.model.api.Quiz
import by.quizzes.amazingquiz.model.api.Quizlet
import by.quizzes.amazingquiz.model.db.DbQuiz
import by.quizzes.amazingquiz.model.db.IncorrectAnswers
import by.quizzes.amazingquiz.model.db.Questions
import by.quizzes.amazingquiz.model.db.Regions
import by.quizzes.amazingquiz.model.db.Tags
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizPresencesRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import by.quizzes.amazingquiz.repository.SettingsRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class QuizViewModel(
    private val quizRepository: QuizRepository,
    private val settingsRepository: SettingsRepository,
    private val questionsRepository: QuestionsRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: UserPreferencesRepository,
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

            val response =
                quizRepository.getQuiz(settings.limit, settings.difficulty, null, null, null)
            if (response.isSuccessful) {
                addQuizToDb(response.body())
                arrayQuiz.addAll(response.body() ?: arrayListOf())
                setQuiz()
            }
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

    fun setUserAnswer(question: String?, score:Int?, userAnswer:String){
        viewModelScope.launch(Dispatchers.IO) {
            val quizId = quizPresencesRepository.getQuizId()

            questionsRepository.setUserAnswer(userAnswer, question, score, quizId)
            quizRepository.setTotalScore(quizId, questionsRepository.getSumOfScores(quizId)?: 0)
        }
    }

    private fun setQuiz() {
        quiz.postValue(arrayQuiz[currentQuizNumber])
    }

    private suspend fun addQuizToDb(quizlet: Quizlet?) {
        val quizId = quizRepository.addQuizToDb(
            DbQuiz(
                userId = userRepository.getUserId(preferencesRepository.getUserUid()),
                date = Date()
            )
        )
        quizPresencesRepository.run {
            clearPreferences()
            addQuizId(quizId)
        }
        if (quizlet != null) {
            for (quiz in quizlet) {
                val questionId = questionsRepository.addQuestion(
                    Questions(
                        question = quiz.question,
                        correctAnswer = quiz.correctAnswer,
                        difficulty = quiz.difficulty,
                        type = quiz.type,
                        quizId = quizId
                    )
                )
                val incorrectAnswersList = arrayListOf<IncorrectAnswers>()
                for (incorrectAnswer in quiz.incorrectAnswers){
                    incorrectAnswersList.add(IncorrectAnswers(incorrectAnswer = incorrectAnswer, questionId = questionId))
                }
                questionsRepository.addIncorrectAnswers(incorrectAnswersList)

                val regionList = arrayListOf<Regions>()
                for (region in quiz.regions){
                    regionList.add(Regions(region = region, questionId = questionId))
                }
                questionsRepository.addRegions(regionList)

                val tagList = arrayListOf<Tags>()
                for (tag in quiz.tags){
                    tagList.add(Tags(tag = tag, questionId = questionId))
                }
                questionsRepository.addTags(tagList)
            }
        }
    }
}