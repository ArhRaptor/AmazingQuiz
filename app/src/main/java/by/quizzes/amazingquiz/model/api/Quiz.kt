package by.quizzes.amazingquiz.model.api

data class Quiz(
    val correctAnswer: String,
    val difficulty: String,
    val incorrectAnswers: List<String>,
    val question: String,
    val regions: List<String>,
    val tags: List<String>,
)