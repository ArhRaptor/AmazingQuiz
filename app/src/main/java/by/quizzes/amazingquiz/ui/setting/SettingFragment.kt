package by.quizzes.amazingquiz.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.FragmentSettingBinding
import by.quizzes.amazingquiz.model.api.enumeration.CategoriesEnum.*
import by.quizzes.amazingquiz.model.db.Settings
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

        val difficultyAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, difficultyValues)
        binding?.difficulty?.setAdapter(difficultyAdapter)

        val categoriesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categoriesValues)
        binding?.categories?.setAdapter(categoriesAdapter)
        binding?.categories?.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        binding?.start?.setOnClickListener {

            val limit = binding?.limit?.value?.toInt() ?: 1
            val difficulty = binding?.difficulty?.text.toString().lowercase()

            viewModel.addSettings(Settings(limit = limit, difficulty = difficulty, timer = binding?.useTimer?.isChecked?: false))

            viewModel.openQuizFragment.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToFragmentQuiz())
                }
            }
        }
    }
}