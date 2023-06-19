package by.quizzes.amazingquiz.ui.quiz

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.FragmentQuizBinding
import by.quizzes.amazingquiz.extensions.setAnswerCardText
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizFragment : Fragment() {

    @Inject
    lateinit var quizViewModelProvider: QuizViewModelProvider
    private var countDownTimer: CountDownTimer? = null

    private var binding: FragmentQuizBinding? = null
    private val viewModel: QuizViewModel by viewModels {
        quizViewModelProvider
    }

    private var currentQuestion: String? = null
    private var currentDifficulty: String? = null
    private var selectedCard: MaterialCardView? = null
    private var correctCard: MaterialCardView? = null
    private var isTimer = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isTimer.observe(viewLifecycleOwner) {
            if (it == true) {
                binding?.timer?.visibility = VISIBLE
                isTimer = true

                binding?.progressBar?.apply {
                    max = 100
                    progress = 100
                }

                val totalTime = 10000 // Общее время в миллисекундах
                val interval = 1000 // Интервал обновления прогресса в миллисекундах

                countDownTimer = object : CountDownTimer(totalTime.toLong(), interval.toLong()) {
                    override fun onTick(millisUntilFinished: Long) {
                        val remainingTime =
                            millisUntilFinished / 1000
                        binding?.remainingTimeTextView?.text =
                            remainingTime.toString()
                        binding?.progressBar?.progress =
                            ((millisUntilFinished.toFloat() / totalTime) * 100).toInt()
                    }

                    override fun onFinish() {
                        setClickableCard(false)
                        binding?.progressBar?.visibility = INVISIBLE
                        binding?.remainingTimeTextView?.text =
                            resources.getString(R.string.times_is_up)
                        viewModel.setUserAnswer(currentQuestion, "", getCardText(correctCard), currentDifficulty, isTimer)
                        showCorrectAnswer()
                        binding?.progressBar?.visibility = VISIBLE
                    }
                }

                countDownTimer?.start()
            }
        }

        viewModel.openResultFragment = {
            findNavController().navigate(QuizFragmentDirections.actionFragmentQuizToResultFragment())
        }

        binding?.btnNextQuiz?.isEnabled = false

        viewModel.quiz.observe(viewLifecycleOwner) { question ->
            val answers = arrayListOf<String>()
            currentQuestion = question.question
            currentDifficulty = question.difficulty

            answers.add(question.correctAnswer)
            answers.addAll(question.incorrectAnswers)
            answers.shuffle()

            binding?.tvQuestion?.text = question.question
            binding?.tvDifficulty?.text = question.difficulty

            binding?.tvAnswer1?.text = answers[0]
            if (answers[0] == question.correctAnswer) {
                correctCard = binding?.cvAnswer1
                binding?.ivAnswer1?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_correct,
                        null
                    )
                )
            } else {
                binding?.ivAnswer1?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_incorrect,
                        null
                    )
                )
            }

            binding?.tvAnswer2?.text = answers[1]
            if (answers[1] == question.correctAnswer) {
                correctCard = binding?.cvAnswer2
                binding?.ivAnswer2?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_correct,
                        null
                    )
                )
            } else {
                binding?.ivAnswer2?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_incorrect,
                        null
                    )
                )
            }

            binding?.tvAnswer3?.text = answers[2]
            if (answers[2] == question.correctAnswer) {
                correctCard = binding?.cvAnswer3
                binding?.ivAnswer3?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_correct,
                        null
                    )
                )
            } else {
                binding?.ivAnswer3?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_incorrect,
                        null
                    )
                )
            }
            binding?.tvAnswer4?.text = answers[3]

            if (answers[3] == question.correctAnswer) {
                correctCard = binding?.cvAnswer4
                binding?.ivAnswer4?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_correct,
                        null
                    )
                )
            } else {
                binding?.ivAnswer4?.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_incorrect,
                        null
                    )
                )
            }
        }
        viewModel.getQuiz()

        val textArray = arrayListOf(
            binding?.tvAnswer1,
            binding?.tvAnswer2,
            binding?.tvAnswer3,
            binding?.tvAnswer4
        )

        binding?.cvAnswer1?.setOnClickListener {
            binding?.tvAnswer1?.setAnswerCardText(textArray)
            selectedCard = binding?.cvAnswer1
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.cvAnswer2?.setOnClickListener {
            binding?.tvAnswer2?.setAnswerCardText(textArray)
            selectedCard = binding?.cvAnswer2
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.cvAnswer3?.setOnClickListener {
            binding?.tvAnswer3?.setAnswerCardText(textArray)
            selectedCard = binding?.cvAnswer3
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.cvAnswer4?.setOnClickListener {
            binding?.tvAnswer4?.setAnswerCardText(textArray)
            selectedCard = binding?.cvAnswer4
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.btnNextQuiz?.setOnClickListener {

            countDownTimer?.cancel()
            setClickableCard(false)

            viewModel.setUserAnswer(currentQuestion, getCardText(selectedCard), getCardText(correctCard), currentDifficulty, isTimer)
            showCorrectAnswer()
        }
    }

    private fun getCardText(card: MaterialCardView?):String{
        val layout = card?.getChildAt(0) as LinearLayout

        for (i in 0 until layout.childCount) {
            val innerChild = layout.getChildAt(i)
            if (innerChild is TextView) {
                return innerChild.text.toString()
            }
        }
        return ""
    }

    private fun showCorrectAnswer() {
        lifecycleScope.launch {
            binding?.btnNextQuiz?.isEnabled = false

            if (selectedCard == null) {
                setAnimation(correctCard, true)
                delay(1100)
                setAnimation(correctCard, false)
            }
            if (selectedCard == correctCard) {
                setAnimation(selectedCard, true)
                delay(1300)
                setAnimation(selectedCard, false)
            }
            if (selectedCard != correctCard && selectedCard != null) {
                setAnimation(selectedCard, true)
                delay(1300)
                setAnimation(correctCard, true)
                delay(2200)
                setAnimation(selectedCard, false)
                setAnimation(correctCard, false)
            }
            delay(1500)

            selectedCard = null
            correctCard = null
            viewModel.getNextQuiz()
            setClickableCard(true)
            countDownTimer?.start()
        }
    }

    private fun setClickableCard(isClickable: Boolean) {
        val arrayCards = arrayListOf(
            binding?.cvAnswer1,
            binding?.cvAnswer2,
            binding?.cvAnswer3,
            binding?.cvAnswer4
        )

        for (card in arrayCards) {
            card?.isClickable = isClickable
        }
    }

    private fun setAnimation(
        cardView: MaterialCardView?,
        frontToBack: Boolean
    ) {
        val layout = cardView?.getChildAt(0) as LinearLayout

        var frontView: TextView? = null
        var backView: ImageView? = null

        for (i in 0 until layout.childCount) {
            val innerChild = layout.getChildAt(i)
            if (innerChild is TextView) {
                frontView = innerChild
            } else if (innerChild is ImageView) {
                backView = innerChild
            }
        }
        val rotationValue1: Float
        val rotationValue2: Float
        if (frontToBack) {
            rotationValue1 = 0f
            rotationValue2 = 180f
        } else {
            rotationValue1 = 180f
            rotationValue2 = 0f
        }

        val scale = requireContext().resources.displayMetrics.density
        val cameraDist = 8000 * scale

        cardView.cameraDistance = cameraDist

        val animator = ObjectAnimator.ofFloat(cardView, "rotationY", rotationValue1, rotationValue2)
        animator.duration = 1000

        animator.addUpdateListener {
            val animatedValue = it.animatedValue as Float

            when (frontToBack) {
                true -> {
                    if (animatedValue >= 90f) {
                        frontView?.visibility = GONE
                        backView?.visibility = VISIBLE
                    }
                }

                false -> {
                    if (animatedValue <= 90f) {
                        frontView?.run {
                            visibility = VISIBLE
                            setTextColor(resources.getColor(R.color.black, null))
                            textSize = 14f
                        }
                        backView?.visibility = GONE
                    }
                }
            }
        }

        animator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}