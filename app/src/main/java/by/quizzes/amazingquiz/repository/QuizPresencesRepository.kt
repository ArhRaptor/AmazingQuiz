package by.quizzes.amazingquiz.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

private const val QUIZ_PREFERENCES = "quizPreferences"
private const val QUIZ_ID = "id"

@Singleton
class QuizPresencesRepository @Inject constructor (context: Context) {

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(QUIZ_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun addQuizId(id: Long){
        preferences.edit {
            putLong(QUIZ_ID, id)
        }
    }

    fun getQuizId(): Long{
        return preferences.getLong(QUIZ_ID, 0)
    }

    fun clearPreferences(){
        preferences.edit {
            clear()
        }
    }
}