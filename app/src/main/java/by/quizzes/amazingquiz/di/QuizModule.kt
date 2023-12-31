package by.quizzes.amazingquiz.di

import by.quizzes.amazingquiz.network.QuizApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://the-trivia-api.com/api/"

@Module
class QuizModule {

    @Singleton
    @Provides
    fun provideApi(): QuizApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                      setLevel(HttpLoggingInterceptor.Level.BODY)
                    }).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApi::class.java)
    }

}