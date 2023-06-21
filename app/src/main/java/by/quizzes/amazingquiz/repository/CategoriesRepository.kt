package by.quizzes.amazingquiz.repository

import by.quizzes.amazingquiz.db.CategoriesDao
import by.quizzes.amazingquiz.model.db.QuizCategories
import javax.inject.Inject

class CategoriesRepository @Inject constructor(private val categoriesDao: CategoriesDao) {

    suspend fun addCategory(category: QuizCategories) = categoriesDao.addCategory(category)
    suspend fun addCategories(categories: List<QuizCategories>) = categoriesDao.addCategories(categories)
    suspend fun getCategoriesBySettingsId(settingsId: Long?) = categoriesDao.getCategoriesBySettingsId(settingsId)
    suspend fun updateCategory(categoryName: String, isAdd: Boolean) = categoriesDao.updateCategory(categoryName, isAdd)
    suspend fun getCountAllCategories() = categoriesDao.getCountAllCategories()
    suspend fun getCountByName(categoryName: String) = categoriesDao.getCountCategoryByName(categoryName)
}