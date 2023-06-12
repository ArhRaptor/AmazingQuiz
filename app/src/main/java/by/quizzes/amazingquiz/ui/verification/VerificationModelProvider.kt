package by.quizzes.amazingquiz.ui.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import by.quizzes.amazingquiz.repository.FirebaseRepository
import javax.inject.Inject

class VerificationModelProvider @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return VerificationViewModel(firebaseRepository) as T
    }
}