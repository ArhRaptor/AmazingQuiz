package by.quizzes.amazingquiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.quizzes.amazingquiz.model.db.User

@Dao
interface UserDao {

    @Insert
    suspend fun addUser(user: User)

    @Query("UPDATE user SET photo = :photoUrl WHERE uid = :userUid")
    suspend fun updateUser(photoUrl: String?, userUid: String?)

    @Query("UPDATE user SET name = :name, surname = :surname WHERE uid = :userUid")
    suspend fun updateUser(name: String, surname: String, userUid: String?)

    @Query("SELECT * FROM user WHERE uid = :userUid")
    suspend fun getUser(userUid: String?): User?

    @Query("SELECT id FROM user WHERE uid = :userUid")
    suspend fun getUserId(userUid: String?): Long
}