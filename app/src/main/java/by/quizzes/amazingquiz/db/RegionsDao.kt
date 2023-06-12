package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import by.quizzes.amazingquiz.model.db.Regions

@Dao
interface RegionsDao {
    @Insert
    suspend fun addRegions(regions: List<Regions>)
}