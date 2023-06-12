package by.quizzes.amazingquiz.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import by.quizzes.amazingquiz.repository.QuestionsRepository
import by.quizzes.amazingquiz.repository.QuizPresencesRepository
import by.quizzes.amazingquiz.repository.QuizRepository
import javax.inject.Inject

class ResultViewModelProvider @Inject constructor(
   private val questionsRepository: QuestionsRepository,
   private val quizRepository: QuizRepository,
   private val quizPresencesRepository: QuizPresencesRepository
):ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ResultViewModel(questionsRepository, quizRepository, quizPresencesRepository) as T
    }
}