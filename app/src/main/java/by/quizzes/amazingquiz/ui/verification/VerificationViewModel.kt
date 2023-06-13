package by.quizzes.amazingquiz.ui.verification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.quizzes.amazingquiz.repository.FirebaseRepository

class VerificationViewModel (private val firebaseRepository: FirebaseRepository) : ViewModel() {

    val message = MutableLiveData<String>()
    val isNextRegistrationStep = MutableLiveData<Boolean>()

    fun sentVerificationMessage(){
        firebaseRepository.sentVerificationMessage {
            message.value = it
        }
    }

    fun isVerified(){
       isNextRegistrationStep.value = firebaseRepository.isVerified()
    }
}