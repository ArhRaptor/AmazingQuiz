package by.quizzes.amazingquiz.ui.main

import androidx.recyclerview.widget.RecyclerView
import by.quizzes.amazingquiz.databinding.ItemAnsweredTagBinding

import by.quizzes.amazingquiz.model.CorrectTags
import java.util.Locale

class TagAnswersViewHolder(val binding: ItemAnsweredTagBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(correctTags: CorrectTags){
        val tag = correctTags.tagName.replace('_', ' ')
        binding.tvTagName.text = tag.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        binding.tvAnswers.text = correctTags.count.toString()
    }
}