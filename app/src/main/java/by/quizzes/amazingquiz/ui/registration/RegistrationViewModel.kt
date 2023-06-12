package by.quizzes.amazingquiz.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.quizzes.amazingquiz.repository.FirebaseRepository

class RegistrationViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {

    val errorText = MutableLiveData<String>()
    var openMainFragment: (() -> Unit)? = null

    fun registration(email:String, password: String){
        firebaseRepository.registration(email, password, {
            openMainFragment?.invoke()
        }, {
            errorText.value = it
        })
    }
}