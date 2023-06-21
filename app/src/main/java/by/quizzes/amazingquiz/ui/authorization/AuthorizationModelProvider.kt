package by.quizzes.amazingquiz.ui.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import by.quizzes.amazingquiz.repository.FirebaseRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import javax.inject.Inject

class AuthorizationModelProvider @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return AuthorizationViewModel(firebaseRepository, userRepository, preferencesRepository) as T
    }
}