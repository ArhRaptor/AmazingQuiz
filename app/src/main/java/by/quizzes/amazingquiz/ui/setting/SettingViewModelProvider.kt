package by.quizzes.amazingquiz.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import by.quizzes.amazingquiz.repository.CategoriesRepository
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizPresencesRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import by.quizzes.amazingquiz.repository.SettingsRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import javax.inject.Inject

class SettingViewModelProvider @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: UserPreferencesRepository,
    private val quizPresencesRepository: QuizPresencesRepository,
    private val questionsRepository: QuestionsRepository,
    private val categoriesRepository: CategoriesRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SettingViewModel(
            settingsRepository,
            quizRepository,
            userRepository,
            preferencesRepository,
            quizPresencesRepository,
            questionsRepository,
            categoriesRepository
        ) as T
    }
}