package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.Tags

@Dao
interface TagsDao {
    @Insert
    suspend fun addTags(tags: List<Tags>)

    @Query("UPDATE tags SET is_correct = :isCorrect WHERE question_id = :questionId")
    suspend fun updateTags(questionId: Long, isCorrect: Boolean)

    @Query("SELECT tag FROM tags WHERE question_id = :questionId")
    suspend fun getTagsByQuestionId(questionId: Long?): List<String>

    @Query("SELECT DISTINCT tag FROM tags WHERE user_id = :userId AND is_correct = 1")
    suspend fun selectUniqueTags(userId: Long): List<String>

    @Query("SELECT COUNT(*) FROM tags WHERE tag = :tag")
    suspend fun getCountTag(tag: String): Int
}