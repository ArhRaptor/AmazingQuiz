package by.quizzes.amazingquiz.ui.result

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.ItemResultBinding
import by.quizzes.amazingquiz.model.QuestionResult

class ResultViewHolder(val binding: ItemResultBinding): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("ResourceAsColor")
    fun bind(questionResult: QuestionResult){
        binding.tvQuestion.text = questionResult.question
        binding.tvCorrectAnswer.text = questionResult.correctAnswer
        binding.tvMyAnswer.run {
            text = questionResult.myAnswer
            if (questionResult.correctAnswer == questionResult.question){
                setTextColor(R.color.green)
            }else{
                setTextColor(R.color.red)
            }
        }
        binding.tvScore.text = questionResult.score.toString()
    }
}