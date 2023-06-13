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
                if (auth.currentUser?.isEmailVerified == true) {
                    onSuccess(auth.currentUser!!.uid)
                } else {
                    onError("The account has not been verified. Check your mail!")
                }
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

    fun sentVerificationMessage(onSentMessage: (text: String) -> Unit) {
        val user = auth.currentUser

        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSentMessage("Account verification email has been sent!")
                } else {
                    onSentMessage(task.exception?.localizedMessage?: "")
                }
            }
    }

    fun isVerified() = auth.currentUser?.isEmailVerified

    fun updateUser(name: String, surname: String) {
        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = "$name $surname"
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->

            }
    }
}