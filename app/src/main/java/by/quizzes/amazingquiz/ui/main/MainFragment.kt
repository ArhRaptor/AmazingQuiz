package by.quizzes.amazingquiz.ui.main

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.AlertUserDataBinding
import by.quizzes.amazingquiz.databinding.FragmentMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner){user ->
            if (user.name == null && user.surname == null){
                val alertBinding = AlertUserDataBinding.inflate(layoutInflater)
                val alertDialog = MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.hello))
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setView(alertBinding.root)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.nice_to_meet_you), null)
                    .create()

                alertDialog.setOnShowListener {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val name = alertBinding.etName.text.toString().trim()
                        val surname = alertBinding.etSurname.text.toString().trim()

                        var shouldDismissDialog = true

                        if (name == ""){
                            alertBinding.tilName.error = getString(R.string.empty_field)
                            shouldDismissDialog = false
                        }
                        if (surname == ""){
                            alertBinding.tilSurname.error = getString(R.string.empty_field)
                            shouldDismissDialog = false
                        }else{
                            viewModel.updateUser(name, surname)
                        }

                        if (shouldDismissDialog){
                            alertDialog.dismiss()
                            viewModel.getUser()
                        }
                    }
                }
                alertDialog.show()

            }else{
                binding?.tvUserName?.text = "${user.name} ${user.surname}"
            }
        }
        viewModel.getUser()

        binding?.start?.setOnClickListener {
            viewModel.incompleteScore.observe(viewLifecycleOwner) { score ->
                if (score != null){
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Attention!!!")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setMessage("You started the test but didn't finish it. Do you really want to start a new test and lose $score points?")
                        .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                            dialog.dismiss()
                            viewModel.deleteQuiz()
                            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingFragment())
                        }
                        .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                            dialog.dismiss()
                            findNavController().navigate(MainFragmentDirections.actionMainFragmentToFragmentQuiz())
                        }
                        .show()
                }else{
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingFragment())
                }
            }
            viewModel.getIncompleteQuizScore()
        }

        viewModel.totalScore.observe(viewLifecycleOwner) { total ->
            if (total != null) {
                startAnimation(total, binding?.tvTotalScore)
            }
        }
        viewModel.getTotalScore()

        viewModel.rank.observe(viewLifecycleOwner) {
            binding?.tvRank?.text = it.first
            binding?.tvScoreLeft?.text = it.second.toString()
        }
        viewModel.setRank {
            binding?.rbRank?.rating = it
        }

        viewModel.statistics.observe(viewLifecycleOwner) {
            binding?.rvFavoriteTags?.adapter = TagAnswersAdapter(it)
            binding?.rvFavoriteTags?.layoutManager =
                GridLayoutManager(requireContext(), calculateSpanCount(binding?.rvFavoriteTags))

            binding?.cvFavoriteTags?.visibility = if(it.size > 0){
               VISIBLE
            }else{
                GONE
            }
        }
        viewModel.getStatistic()

        viewModel.isContinue.observe(viewLifecycleOwner) {
            binding?.btnContinue?.visibility = if (it) {
                VISIBLE
            } else {
                GONE
            }
        }
        viewModel.getIncompleteQuiz()

        binding?.btnContinue?.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToFragmentQuiz())
        }
    }

    private fun startAnimation(endValue: Int, textView: TextView?) {

        val valueAnimator = ValueAnimator.ofInt(0, endValue)
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

    private fun calculateSpanCount(recyclerView: RecyclerView?): Int {
        val parentWidth = recyclerView?.width
        val desiredItemWidth = resources.getDimensionPixelSize(R.dimen.item_width)
        val spanCount = parentWidth?.div(desiredItemWidth)
        if (spanCount != null) {
            return if (spanCount > 0) spanCount else 1
        }
        return 1
    }
}