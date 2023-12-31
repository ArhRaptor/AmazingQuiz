package by.quizzes.amazingquiz.di

import by.quizzes.amazingquiz.ui.authorization.AuthorizationFragment
import by.quizzes.amazingquiz.ui.main.MainFragment
import by.quizzes.amazingquiz.ui.quiz.QuizFragment
import by.quizzes.amazingquiz.ui.registration.RegistrationFragment
import by.quizzes.amazingquiz.ui.result.ResultFragment
import by.quizzes.amazingquiz.ui.setting.SettingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =  [AppModule::class, QuizModule::class, DataBaseModule::class])
interface ApplicationComponent {

    fun inject(fragment: QuizFragment)
    fun inject(fragment: SettingFragment)
    fun inject(fragment: AuthorizationFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: ResultFragment)
    fun inject(fragment: MainFragment)
}