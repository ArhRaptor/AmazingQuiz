package by.quizzes.amazingquiz

import android.app.Application
import by.quizzes.amazingquiz.di.AppModule
import by.quizzes.amazingquiz.di.ApplicationComponent
import by.quizzes.amazingquiz.di.DaggerApplicationComponent
import by.quizzes.amazingquiz.di.DataBaseModule
import by.quizzes.amazingquiz.di.QuizModule


class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .dataBaseModule(DataBaseModule())
            .quizModule(QuizModule())
            .build()

    }

    companion object{
        var applicationComponent :ApplicationComponent? = null
    }
}