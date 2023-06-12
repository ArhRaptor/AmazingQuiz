package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.quizzes.amazingquiz.model.db.Settings

@Dao
interface SettingsDao {

    @Insert
    suspend fun addSettings(settings: Settings)

    @Update
    suspend fun updateSetting(settings: Settings)

    @Query("SELECT COUNT(*) FROM setting")
    suspend fun getCount(): Int

    @Query("SELECT * FROM setting WHERE id = '1'")
    suspend fun getSettings(): Settings
}