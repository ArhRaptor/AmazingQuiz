package by.quizzes.amazingquiz.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    fun login(
        login: String,
        password: String,
        onSuccess: (userUid: String) -> Unit,
        onError: (text: String) -> Unit
    ) {

        auth.signInWithEmailAndPassword(login, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(auth.currentUser!!.uid)
            } else {
                onError(task.exception?.localizedMessage ?: "")
            }
        }
    }

    fun registration(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (text: String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.localizedMessage ?: "")
            }
        }
    }
}