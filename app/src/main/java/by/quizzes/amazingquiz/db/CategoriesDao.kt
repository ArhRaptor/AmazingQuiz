package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.QuizCategories

@Dao
interface CategoriesDao {

    @Insert
    suspend fun addCategories(categories: List<QuizCategories>)

    @Query("SELECT value FROM categories WHERE settings_id=:settingsId")
    suspend fun getCategoriesBySettingsId(settingsId: Long): List<String>
}