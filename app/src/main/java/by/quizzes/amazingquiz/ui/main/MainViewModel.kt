package by.quizzes.amazingquiz.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.repository.QuizRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val quizRepository: QuizRepository
) : ViewModel() {

    val totalScore = MutableLiveData<Int>()
    val rank = MutableLiveData<Pair<String, Int>>()

    fun getTotalScore() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepository.getUserId(userPreferencesRepository.getUserUid())
            totalScore.postValue(quizRepository.getSumOfScore(userId))
        }
    }

    fun setRank(rating: (star: Float) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = userRepository.getUserId(userPreferencesRepository.getUserUid())

            when (val score = quizRepository.getSumOfScore(userId) ?: 0) {
                in 0 until 300 -> {
                    rank.postValue(Pair("BABY", (300 - score)))
                    rating(0f)
                }

                in 300 until 1000 -> {
                    rank.postValue(Pair("BEGINNER", (1000 - score)))
                    rating(1f)
                }

                in 1000 until 2000 -> {
                    rank.postValue(Pair("STUDENT", 2000 - score))
                    rating(2f)
                }

                in 2000 until 4000 -> {
                    rank.postValue(Pair("TEACHER", 4000 - score))
                    rating(3f)
                }

                in 4000 until 7000 -> {
                    rank.postValue(Pair("MASTER", 7000 - score))
                    rating(4f)
                }

                else -> {
                    rank.postValue(Pair("GENIUS", 0))
                    rating(5f)
                }
            }
        }
    }
}