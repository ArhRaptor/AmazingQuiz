package by.quizzes.amazingquiz.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.quizzes.amazingquiz.model.db.User
import by.quizzes.amazingquiz.repository.FirebaseRepository
import by.quizzes.amazingquiz.repository.UserPreferencesRepository
import by.quizzes.amazingquiz.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val errorText = MutableLiveData<String>()
    var openMainFragment: (() -> Unit)? = null

    fun login(login: String, password: String) {
        firebaseRepository.login(login, password, { userUid ->
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.getUser(userUid)
                if (user == null) {
                    userRepository.addUser(
                        User(
                            email = login,
                            userUid = userUid
                        )
                    )
                }
            }
            preferencesRepository.addUid(userUid)
            openMainFragment?.invoke()

        }, {
            errorText.value = it
        })
    }
}