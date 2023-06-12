package by.quizzes.amazingquiz.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES = "userPreferences"
private const val USER_UID = "uid"

@Singleton
class UserPreferencesRepository @Inject constructor(context: Context){

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun addUid(userUid: String){
        preferences.edit {
            putString(USER_UID, userUid)
        }
    }

    fun getUserUid(): String? {
        return preferences.getString(USER_UID, "")
    }

    fun clearSharedPreferences() {
        preferences.edit{
            clear()
        }
    }
}