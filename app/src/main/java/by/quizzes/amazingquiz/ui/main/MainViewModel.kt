package by.quizzes.amazingquiz.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.model.CorrectTags
import by.quizzes.amazingquiz.model.db.User
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository,
    private val quizRepository: QuizRepository,
    private val questionsRepository: QuestionsRepository
) : ViewModel() {

    val totalScore = MutableLiveData<Int>()
    val rank = MutableLiveData<Pair<String, Int>>()
    val statistics = MutableLiveData<ArrayList<CorrectTags>>()
    val isContinue = MutableLiveData<Boolean>()
    val incompleteScore = MutableLiveData<Int?>()
    val user = MutableLiveData<User>()

    fun getTotalScore() {
        viewModelScope.launch(Dispatchers.IO) {
            totalScore.postValue(quizRepository.getSumOfScore(getUserId()))
        }
    }

    fun setRank(rating: (star: Float) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val score = quizRepository.getSumOfScore(getUserId()) ?: 0) {
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

    fun getStatistic() {
        viewModelScope.launch(Dispatchers.IO) {
            val statisticList = arrayListOf<CorrectTags>()

            val uniqueTags = questionsRepository.selectUniqueTag(getUserId())
            for (tag in uniqueTags) {
                statisticList.add(CorrectTags(tag, questionsRepository.getCountTag(tag)))
            }

            statistics.postValue(statisticList)
        }
    }

    fun getIncompleteQuiz() {
        viewModelScope.launch(Dispatchers.IO) {
            isContinue.postValue(quizRepository.getIncompleteQuiz(getUserId()).isNotEmpty())
        }
    }

    fun getIncompleteQuizScore() {
        viewModelScope.launch(Dispatchers.IO) {
            if (quizRepository.getIncompleteQuiz(getUserId()).isNotEmpty()){
                incompleteScore.postValue(quizRepository.getIncompleteQuiz(getUserId())[0].totalScore?: 0)
            }else{
                incompleteScore.postValue(null)
            }
        }
    }

    fun deleteQuiz(){
        viewModelScope.launch(Dispatchers.IO) {
            quizRepository.deleteQuiz(quizRepository.getIncompleteQuiz(getUserId())[0])
        }
    }

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO) {
            user.postValue(userRepository.getUser(userPreferencesRepository.getUserUid()))
        }
    }

    fun updateUser(name: String, surname: String){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(name, surname, userPreferencesRepository.getUserUid())
        }
    }

    fun addPhotoToDb(url: String?){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(url ,userPreferencesRepository.getUserUid())
            getUser()
        }
    }

    private suspend fun getUserId(): Long {
        return userRepository.getUserId(userPreferencesRepository.getUserUid())
    }
}