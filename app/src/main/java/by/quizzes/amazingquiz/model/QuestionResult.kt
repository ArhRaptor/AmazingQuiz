package by.quizzes.amazingquiz.model

data class QuestionResult(
    val question: String,
    val difficulty: String,
    val correctAnswer: String,
    val myAnswer: String?,
    val score: Int?
)