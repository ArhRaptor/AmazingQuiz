package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.QuizCategories

@Dao
interface CategoriesDao {

    @Insert
    suspend fun addCategory(category: QuizCategories)
    @Insert
    suspend fun addCategories(categories: List<QuizCategories>)
    @Query("UPDATE categories SET is_add = :isAdd WHERE name = :categoryName")
    suspend fun updateCategory(categoryName: String, isAdd: Boolean)
    @Query("SELECT * FROM categories WHERE settings_id=:settingsId")
    suspend fun getCategoriesBySettingsId(settingsId: Long?): List<QuizCategories>
    @Query("SELECT COUNT (*) FROM categories")
    suspend fun getCountAllCategories(): Int
    @Query("SELECT COUNT(*) FROM categories WHERE name = :categoryName")
    suspend fun getCountCategoryByName(categoryName: String): Int
}