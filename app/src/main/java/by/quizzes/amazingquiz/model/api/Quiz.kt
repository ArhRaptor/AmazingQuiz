package by.quizzes.amazingquiz.model.api

data class Quiz(
    val category: String,
    val correctAnswer: String,
    val difficulty: String,
    val id: String,
    val incorrectAnswers: List<String>,
    val question: String,
    val regions: List<String>,
    val tags: List<String>,
    val type: String
)