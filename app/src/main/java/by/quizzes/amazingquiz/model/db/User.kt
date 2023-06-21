package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("name")
    var name: String? = null,
    @ColumnInfo("surname")
    var surname: String? = null,
    @ColumnInfo("photo")
    var photoUrl: String? = null,
    @ColumnInfo("email")
    val email: String,
    @ColumnInfo("uid")
    val userUid: String
)
