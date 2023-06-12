package by.quizzes.amazingquiz.network

import by.quizzes.amazingquiz.model.api.Categories
import by.quizzes.amazingquiz.model.api.Quizlet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {

    @GET("categories")
    suspend fun getCategories(): Response<Categories>

    @GET("questions")
    suspend fun getQuiz(
        @Query("limit") limit: Int,
        @Query("difficulty") difficulty: String?,
        @Query("categories") categories: String?,
        @Query("region") region: String?,
        @Query("tags") tags: String?
    ): Response<Quizlet>
}