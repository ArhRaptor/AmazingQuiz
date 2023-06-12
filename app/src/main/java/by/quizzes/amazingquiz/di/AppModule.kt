package by.quizzes.amazingquiz.di

import android.content.Context
import by.quizzes.amazingquiz.MyApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: MyApp) {

    @Singleton
    @Provides
    fun provideContext(): Context{
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth
}