package by.quizzes.amazingquiz.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.FragmentSettingBinding
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.ARTS_AND_LITERATURE
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.FILM_AND_TV
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.FOOD_AND_DRINK
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.GENERAL_KNOWLEDGE
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.GEOGRAPHY
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.HISTORY
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.MUSIC
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.SCIENCE
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.SOCIETY_AND_CULTURE
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.SPORT_AND_LEISURE
import by.quizzes.amazingquiz.model.db.QuizCategories
import by.quizzes.amazingquiz.model.db.Settings
import java.util.Locale
import javax.inject.Inject


class SettingFragment : Fragment() {

    @Inject
    lateinit var settingViewModelProvider: SettingViewModelProvider

    private var binding: FragmentSettingBinding? = null

    private val viewModel: SettingViewModel by viewModels {
        settingViewModelProvider
    }
    private val difficultyValues = arrayOf("Easy", "Medium", "Hard")
    private val categoriesValues = arrayOf(
        ARTS_AND_LITERATURE.code,
        FILM_AND_TV.code,
        FOOD_AND_DRINK.code,
        GENERAL_KNOWLEDGE.code,
        GEOGRAPHY.code,
        HISTORY.code,
        MUSIC.code,
        SCIENCE.code,
        SOCIETY_AND_CULTURE.code,
        SPORT_AND_LEISURE.code
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quizCategories = arrayListOf<QuizCategories>()

        val difficultyAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, difficultyValues)
        binding?.difficulty?.setAdapter(difficultyAdapter)

        viewModel.getSettings { settings, categories ->
            if (settings != null){
                binding?.limit?.value = settings.limit.toFloat()
                binding?.difficulty?.post {
                    binding?.difficulty?.setText(settings.difficulty?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    })
                }
                binding?.useTimer?.isChecked = settings.timer
                quizCategories.addAll(categories)
            }

            if (quizCategories.size == 0) {
                for (category in categoriesValues) {
                    quizCategories.add(
                        QuizCategories(
                            name = category,
                            settingsId = 1
                        )
                    )
                }
            }

        }

        viewModel.errorMessage = {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding?.rvCategories?.adapter =
            CategoriesAdapter(quizCategories) { categoryName, isCheck ->
                for (item in quizCategories) {
                    if (item.name == categoryName) {
                        item.isAdd = isCheck
                    }
                }
            }

        binding?.rvCategories?.layoutManager = GridLayoutManager(requireContext(), 3)

        binding?.start?.setOnClickListener {

            val limit = binding?.limit?.value?.toInt() ?: 1
            val difficulty = binding?.difficulty?.text.toString().lowercase()

            var isCorrectDifficultyValues = false
            for (i in difficultyValues) {
                if (i.lowercase().trim() == difficulty) {
                    isCorrectDifficultyValues = true
                    break
                }
            }
            if (isCorrectDifficultyValues || difficulty == "") {
                viewModel.addSettings(
                    Settings(
                        limit = limit,
                        difficulty = difficulty,
                        timer = binding?.useTimer?.isChecked ?: false
                    ), quizCategories
                )

                viewModel.openQuizFragment.observe(viewLifecycleOwner) {
                    if (it) {
                        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToFragmentQuiz())
                    }
                }
            }else{
                Toast.makeText(requireContext(), getString(R.string.correct_difficulty), Toast.LENGTH_SHORT).show()
            }
        }
    }
}