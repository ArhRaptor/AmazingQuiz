package by.quizzes.amazingquiz.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.model.api.Quizlet
import by.quizzes.amazingquiz.model.db.DbQuiz
import by.quizzes.amazingquiz.model.db.IncorrectAnswers
import by.quizzes.amazingquiz.model.db.Questions
import by.quizzes.amazingquiz.model.db.QuizCategories
import by.quizzes.amazingquiz.model.db.Regions
import by.quizzes.amazingquiz.model.db.Settings
import by.quizzes.amazingquiz.model.db.Tags
import by.quizzes.amazingquiz.repository.CategoriesRepository
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizPresencesRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import by.quizzes.amazingquiz.repository.SettingsRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class SettingViewModel(
    private val settingsRepository: SettingsRepository,
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: UserPreferencesRepository,
    private val quizPresencesRepository: QuizPresencesRepository,
    private val questionsRepository: QuestionsRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    var openQuizFragment = MutableLiveData<Boolean>()
    var errorMessage: ((message:String) -> Unit)? = null

    fun addSettings(settings: Settings, categories: List<QuizCategories>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (settingsRepository.isEmptySettings()) {
                settingsRepository.addSettings(settings)
            } else {
                settings.id = 1
                settingsRepository.updateSetting(settings)
            }

            if (categoriesRepository.getCountAllCategories() > 0){
                for (item in categories){
                    if (categoriesRepository.getCountByName(item.name) == 0){
                        categoriesRepository.addCategory(item)
                    }else{
                        categoriesRepository.updateCategory(item.name, item.isAdd)
                    }
                }
            }else{
                categoriesRepository.addCategories(categories)
            }

            getQuiz(settings, categories)
            openQuizFragment.postValue(true)
        }
    }

    fun getSettings(setSettings:(settings: Settings?, categories:List<QuizCategories>) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            val settings = settingsRepository.getSettings()
            val categories = if (settings != null) {
                categoriesRepository.getCategoriesBySettingsId(settings.id)
            }else{
                arrayListOf()
            }
            setSettings(settings, categories)
        }
    }

    private suspend fun getQuiz(settings: Settings, categoriesList: List<QuizCategories>) {
        var categories:String? = null

        for (item in categoriesList){
            if (item.isAdd){
                if (categories == null){
                    categories = item.name
                }else{
                    categories += ",${item.name}"
                }
            }
        }

        val response =
            quizRepository.getQuiz(settings.limit, settings.difficulty, categories, null, null)
        if (response.isSuccessful) {
            if (response.body()?.size == 0){
                errorMessage?.invoke("No quiz found with the specified parameters.")
            }
            addQuizToDb(response.body())
        }else{
            errorMessage?.invoke(response.message())
        }
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
                        quizId = quizId
                    )
                )
                val incorrectAnswersList = arrayListOf<IncorrectAnswers>()
                for (incorrectAnswer in quiz.incorrectAnswers) {
                    incorrectAnswersList.add(
                        IncorrectAnswers(
                            incorrectAnswer = incorrectAnswer,
                            questionId = questionId
                        )
                    )
                }
                questionsRepository.addIncorrectAnswers(incorrectAnswersList)

                val regionList = arrayListOf<Regions>()
                for (region in quiz.regions) {
                    regionList.add(Regions(region = region, questionId = questionId))
                }
                questionsRepository.addRegions(regionList)

                val tagList = arrayListOf<Tags>()
                for (tag in quiz.tags) {
                    tagList.add(
                        Tags(
                            tag = tag,
                            questionId = questionId,
                            userId = userRepository.getUserId(preferencesRepository.getUserUid())
                        )
                    )
                }
                questionsRepository.addTags(tagList)
            }
        }
    }
}