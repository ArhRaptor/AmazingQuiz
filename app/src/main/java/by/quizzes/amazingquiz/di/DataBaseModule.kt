package by.quizzes.amazingquiz.di

import android.content.Context
import androidx.room.Room
import by.quizzes.amazingquiz.db.AppDataBase
import by.quizzes.amazingquiz.db.CategoriesDao
import by.quizzes.amazingquiz.db.IncorrectAnswersDao
import by.quizzes.amazingquiz.db.QuestionsDao
import by.quizzes.amazingquiz.db.QuizDao
import by.quizzes.amazingquiz.db.RegionsDao
import by.quizzes.amazingquiz.db.SettingsDao
import by.quizzes.amazingquiz.db.TagsDao
import by.quizzes.amazingquiz.db.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDb(context: Context): AppDataBase{
        return Room.databaseBuilder(context, AppDataBase::class.java, "quizDataBase").build()
    }

    @Provides
    @Singleton
    fun providesCategoriesRepository(app: AppDataBase): CategoriesDao{
        return app.categoriesDao()
    }
    @Provides
    @Singleton
    fun incorrectAnswerRepository(app: AppDataBase): IncorrectAnswersDao{
        return app.incorrectAnswersDao()
    }
    @Provides
    @Singleton
    fun questionsRepository(app: AppDataBase): QuestionsDao{
        return app.questionsDao()
    }
    @Provides
    @Singleton
    fun quizRepository(app: AppDataBase): QuizDao{
        return app.quizDao()
    }
    @Provides
    @Singleton
    fun regionsRepository(app: AppDataBase): RegionsDao{
        return app.regionsDao()
    }
    @Provides
    @Singleton
    fun providesSettingRepository(app: AppDataBase): SettingsDao{
        return app.settingsDao()
    }
    @Provides
    @Singleton
    fun tagsRepository(app: AppDataBase): TagsDao{
        return app.tagsDao()
    }
    @Provides
    @Singleton
    fun userRepository(app: AppDataBase): UserDao {
        return app.userDao()
    }
}