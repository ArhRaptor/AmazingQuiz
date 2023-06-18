package by.quizzes.amazingquiz.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tags",
    foreignKeys = [ForeignKey(
        entity = Questions::class,
        parentColumns = ["id"],
        childColumns = ["question_id"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Tags(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo("tag")
    val tag: String,
    @ColumnInfo("is_correct")
    var isCorrect: Boolean? = null,
    @ColumnInfo("question_id")
    val questionId: Long,
    @ColumnInfo("user_id")
    var userId: Long
)
