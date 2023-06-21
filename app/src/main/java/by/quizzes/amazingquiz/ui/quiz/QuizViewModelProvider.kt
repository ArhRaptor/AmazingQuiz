package by.quizzes.amazingquiz.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizPresencesRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import by.quizzes.amazingquiz.repository.SettingsRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import javax.inject.Inject

class QuizViewModelProvider @Inject constructor(
    private val quizRepository: QuizRepository,
    private val settingsRepository: SettingsRepository,
    private val questionsRepository: QuestionsRepository,
    private val quizPresencesRepository: QuizPresencesRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return QuizViewModel(
            quizRepository,
            settingsRepository,
            questionsRepository,
            quizPresencesRepository
        ) as T
    }
}