package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.Regions

@Dao
interface RegionsDao {
    @Insert
    suspend fun addRegions(regions: List<Regions>)

    @Query("SELECT region FROM region WHERE question_id = :questionId")
    suspend fun getRegionsByQuestionId(questionId: Long?): List<String>
}