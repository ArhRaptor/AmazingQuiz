package by.quizzes.amazingquiz.ui.result

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.databinding.FragmentResultBinding
import javax.inject.Inject

class ResultFragment:Fragment() {

    @Inject
    lateinit var viewModelProvider: ResultViewModelProvider
    private val viewModel: ResultViewModel by viewModels {
        viewModelProvider
    }
    private var binding: FragmentResultBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setQuizComplete(true)

        viewModel.totalScore.observe(viewLifecycleOwner){result ->
            binding?.tvScore?.text = result.toString()
        }
        viewModel.getTotalScore()

        viewModel.totalQuestions.observe(viewLifecycleOwner){total ->
            binding?.tvQuestion?.text = total.toString()
        }
        viewModel.getCountQuestions()

        viewModel.totalCorrectAnswers.observe(viewLifecycleOwner){total ->
            binding?.tvAnswers?.text = total.toString()
        }
        viewModel.getCountCorrectAnswers()

        viewModel.questionsResult.observe(viewLifecycleOwner){
            binding?.rvResults?.adapter = ResultAdapter(it)
            binding?.rvResults?.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getQuestionsResult()

        binding?.btnBackToMain?.setOnClickListener {
            findNavController().navigate(ResultFragmentDirections.actionResultFragmentToMainFragment())
        }
    }
}