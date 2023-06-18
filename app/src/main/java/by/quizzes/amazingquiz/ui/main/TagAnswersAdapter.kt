package by.quizzes.amazingquiz.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.quizzes.amazingquiz.databinding.ItemAnsweredTagBinding
import by.quizzes.amazingquiz.model.CorrectTags

class TagAnswersAdapter(private val tagAnswer: List<CorrectTags>) :
    RecyclerView.Adapter<TagAnswersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagAnswersViewHolder {
        val binding = ItemAnsweredTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagAnswersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tagAnswer.size
    }

    override fun onBindViewHolder(holder: TagAnswersViewHolder, position: Int) {
       holder.bind(tagAnswer[position])
    }

}