package by.quizzes.amazingquiz.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.quizzes.amazingquiz.model.db.IncorrectAnswers
import by.quizzes.amazingquiz.model.db.Questions
import by.quizzes.amazingquiz.model.db.DbQuiz
import by.quizzes.amazingquiz.model.db.QuizCategories
import by.quizzes.amazingquiz.model.db.Regions
import by.quizzes.amazingquiz.model.db.Settings
import by.quizzes.amazingquiz.model.db.Tags
import by.quizzes.amazingquiz.model.db.User
import by.quizzes.amazingquiz.util.DateTypeConverter

@Database(
    entities = [IncorrectAnswers::class, Questions::class, DbQuiz::class, QuizCategories::class, Regions::class, Settings::class, Tags::class, User::class],
    version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao
    abstract fun incorrectAnswersDao(): IncorrectAnswersDao
    abstract fun questionsDao(): QuestionsDao
    abstract fun quizDao(): QuizDao
    abstract fun regionsDao(): RegionsDao
    abstract fun settingsDao(): SettingsDao
    abstract fun tagsDao(): TagsDao
    abstract fun userDao(): UserDao
}