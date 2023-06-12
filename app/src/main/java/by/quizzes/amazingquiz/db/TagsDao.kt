package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import by.quizzes.amazingquiz.model.db.Tags

@Dao
interface TagsDao {
    @Insert
    suspend fun addTags(tags: List<Tags>)
}