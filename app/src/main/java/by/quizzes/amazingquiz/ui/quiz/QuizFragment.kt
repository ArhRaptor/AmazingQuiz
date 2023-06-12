package by.quizzes.amazingquiz.ui.quiz

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.FragmentQuizBinding
import com.google.android.material.card.MaterialCardView
import javax.inject.Inject

class QuizFragment : Fragment() {

    @Inject
    lateinit var quizViewModelProvider: QuizViewModelProvider

    private var binding: FragmentQuizBinding? = null
    private val viewModel: QuizViewModel by viewModels {
        quizViewModelProvider
    }

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

        var currentQuestion: String? = null
        var currentDifficulty: String? = null
        var selectedCard: MaterialCardView? = null
        var correctCard: MaterialCardView? = null

        viewModel.isTimer.observe(viewLifecycleOwner) {
            if (it == true) {
                binding?.timer?.visibility = VISIBLE

                object : CountDownTimer(6000, 1) {
                    override fun onTick(milliseconds: Long) {
                        val seconds = milliseconds / 1000
                        val millisec = milliseconds % 100
                        binding?.timer?.text = String.format("%02d:%02d left", seconds, millisec)
                    }

                    override fun onFinish() {
                        binding?.timer?.text = getString(R.string.times_is_up)
                    }
                }.start()
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

        binding?.cvAnswer1?.setOnClickListener {
            setAnswerCardText()
            selectedCard = binding?.cvAnswer1
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.cvAnswer2?.setOnClickListener {
           setAnswerCardText()
            selectedCard = binding?.cvAnswer2
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.cvAnswer3?.setOnClickListener {
            setAnswerCardText()
            selectedCard = binding?.cvAnswer3
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.cvAnswer4?.setOnClickListener {
            setAnswerCardText()
            selectedCard = binding?.cvAnswer4
            binding?.btnNextQuiz?.isEnabled = true
        }

        binding?.btnNextQuiz?.setOnClickListener {

            val layout = selectedCard?.getChildAt(0) as LinearLayout

            for (i in 0 until layout.childCount) {
                val innerChild = layout.getChildAt(i)
                if (innerChild is TextView) {
                    val userAnswer = innerChild.text.toString()
                    var score = 2
                    if (correctCard == selectedCard) {
                        when (currentDifficulty) {
                            "medium" -> {
                                score += 2
                            }

                            "hard" -> {
                                score += 3
                            }
                        }
                    } else {
                        score = 0
                    }
                    viewModel.setUserAnswer(currentQuestion, score, userAnswer)
                }
            }

            binding?.btnNextQuiz?.isEnabled = false
            setAnimation(selectedCard, true)
            if (correctCard != selectedCard) {
                Handler(Looper.getMainLooper()).postDelayed({
                    setAnimation(correctCard, true)
                }, 1800)
            }

            Handler(Looper.getMainLooper()).postDelayed({
                setAnimation(selectedCard, false)
                if (selectedCard != correctCard) {
                    setAnimation(correctCard, false)
                }
            }, 3800)

            Handler(Looper.getMainLooper()).postDelayed({
                selectedCard = null
                correctCard = null
                viewModel.getNextQuiz()
            }, 6200)
        }
    }

    private fun setAnswerCardText() {

        val textArray = arrayListOf(
            binding?.tvAnswer1,
            binding?.tvAnswer2,
            binding?.tvAnswer3,
            binding?.tvAnswer4
        )

        for (text in textArray) {
            if (text?.id == this.id) {
                text.setTextColor(resources.getColor(R.color.green, null))
                text.textSize = 20f
            } else {
                text?.setTextColor(resources.getColor(R.color.black, null))
                text?.textSize = 14f
            }
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
        animator.duration = 1500

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
}