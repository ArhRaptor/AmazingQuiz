package by.quizzes.amazingquiz.ui.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.model.QuestionResult
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizPresencesRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel(
    private val questionsRepository: QuestionsRepository,
    private val quizRepository: QuizRepository,
    private val quizPresencesRepository: QuizPresencesRepository
) : ViewModel() {

    val totalQuestions = MutableLiveData<Int>()
    val totalCorrectAnswers = MutableLiveData<Int>()
    val totalScore = MutableLiveData<Int>()
    val questionsResult = MutableLiveData<List<QuestionResult>>()

    fun setQuizComplete(isComplete: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            quizRepository.setQuizComplete(quizPresencesRepository.getQuizId(), isComplete)
        }
    }

    fun getTotalScore() {
        viewModelScope.launch(Dispatchers.IO) {
            totalScore.postValue(questionsRepository.getSumOfScores(quizPresencesRepository.getQuizId()))
        }
    }

    fun getCountQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            totalQuestions.postValue(questionsRepository.getCountQuestion(quizPresencesRepository.getQuizId()))
        }
    }

    fun getCountCorrectAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            totalCorrectAnswers.postValue(
                questionsRepository.getCountCorrectAnswers(
                    quizPresencesRepository.getQuizId()
                )
            )
        }
    }

    fun getQuestionsResult() {
        viewModelScope.launch(Dispatchers.IO) {
            val questionList = arrayListOf<QuestionResult>()
            for (question in questionsRepository.getQuestions(quizPresencesRepository.getQuizId())) {
                questionList.add(
                    QuestionResult(
                        question = question.question,
                        difficulty = question.difficulty,
                        correctAnswer = question.correctAnswer,
                        myAnswer = question.userAnswer,
                        score = question.score
                    )
                )
            }
            questionsResult.postValue(questionList)
        }
    }

}