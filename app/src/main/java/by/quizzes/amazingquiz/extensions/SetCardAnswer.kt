package by.quizzes.amazingquiz.extensions

import android.util.Log
import android.widget.TextView
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.FragmentQuizBinding

fun TextView.setAnswerCard(binding: FragmentQuizBinding){
    Log.e("AnswerCard", findViewById<TextView>(R.id.tv_answer1).id.toString())
    Log.e("AnswerCard", findViewById<TextView>(R.id.tv_answer2).id.toString())
    Log.e("AnswerCard", findViewById<TextView>(R.id.tv_answer3).id.toString())
    Log.e("AnswerCard", findViewById<TextView>(R.id.tv_answer4).id.toString())
}