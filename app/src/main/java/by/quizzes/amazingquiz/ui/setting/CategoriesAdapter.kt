package by.quizzes.amazingquiz.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.quizzes.amazingquiz.databinding.ItemCategoriesBinding
import by.quizzes.amazingquiz.model.db.QuizCategories

class CategoriesAdapter(
    private val categories: ArrayList<QuizCategories>,
    val onClick: (categoryName: String, isCheck: Boolean) -> Unit
) :
    RecyclerView.Adapter<CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(categories[position]){ categoryName, isCheck ->
            onClick(categoryName, isCheck)
        }
    }

}