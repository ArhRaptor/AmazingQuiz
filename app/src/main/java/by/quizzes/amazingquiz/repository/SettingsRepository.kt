package by.quizzes.amazingquiz.repository

import by.quizzes.amazingquiz.db.SettingsDao
import by.quizzes.amazingquiz.model.db.QuizCategories
import by.quizzes.amazingquiz.model.db.Settings
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val settingsDao: SettingsDao) {

    suspend fun addSettings(settings: Settings) = settingsDao.addSettings(settings)
    suspend fun updateSetting(settings: Settings) = settingsDao.updateSetting(settings)
    suspend fun getSettings() = settingsDao.getSettings()

    suspend fun isEmptySettings(): Boolean {
        if (settingsDao.getCount() > 0) {
            return false
        }
        return true
    }
}