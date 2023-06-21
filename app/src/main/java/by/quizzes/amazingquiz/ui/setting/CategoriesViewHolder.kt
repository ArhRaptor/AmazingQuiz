package by.quizzes.amazingquiz.ui.setting

import androidx.recyclerview.widget.RecyclerView
import by.quizzes.amazingquiz.databinding.ItemCategoriesBinding
import by.quizzes.amazingquiz.model.db.QuizCategories

class CategoriesViewHolder(val binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(category:QuizCategories, onClick: (categoryName:String, isCheck: Boolean) -> Unit){
        binding.tvCategoryName.text = category.name
        binding.cbIsAdd.isChecked = category.isAdd
        binding.cbIsAdd.setOnClickListener {
            onClick(category.name, binding.cbIsAdd.isChecked)
        }
    }
}