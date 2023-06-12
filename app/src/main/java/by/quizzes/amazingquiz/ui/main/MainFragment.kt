package by.quizzes.amazingquiz.ui.main

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.databinding.FragmentMainBinding
import javax.inject.Inject


class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: MainViewModelProvider
    private val viewModel: MainViewModel by viewModels {
        viewModelProvider
    }
    private var binding: FragmentMainBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.start?.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingFragment())
        }

        viewModel.totalScore.observe(viewLifecycleOwner){total ->
            if (total != null){
                startAnimation(0, total, binding?.tvTotalScore)
            }
        }
        viewModel.getTotalScore()

        viewModel.rank.observe(viewLifecycleOwner){
            binding?.tvRank?.text = it.first
            binding?.tvScoreLeft?.text = it.second.toString()
        }
        viewModel.setRank{
            binding?.rbRank?.rating = it
        }
    }

    private fun startAnimation(startValue: Int, endValue: Int, textView: TextView?){

        val valueAnimator = ValueAnimator.ofInt(startValue, endValue)
        valueAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            textView?.text = animatedValue.toString()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                val textSizeAnimator = ObjectAnimator.ofFloat(textView, "textSize", 30f, 74f)
                textSizeAnimator.duration = 1000
                textSizeAnimator.start()
            }

            override fun onAnimationEnd(animation: Animator) {
                val textSizeAnimator = ObjectAnimator.ofFloat(textView, "textSize", 74f, 30f)
                textSizeAnimator.duration = 1000
                textSizeAnimator.start()
            }

            override fun onAnimationCancel(animation: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(animation: Animator) {
                TODO("Not yet implemented")
            }

        })
        valueAnimator.duration = 1000
        valueAnimator.start()
    }
}