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
        binding.tvDifficulty.text = questionResult.difficulty
        binding.tvMyAnswer.run {
            text = questionResult.myAnswer
            if (questionResult.correctAnswer == questionResult.myAnswer){
                setTextColor(resources.getColor(R.color.green, null))
            }else{
                setTextColor(resources.getColor(R.color.red, null))
            }
        }
        binding.tvScore.text = questionResult.score.toString()
    }
}