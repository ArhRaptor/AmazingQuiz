package by.quizzes.amazingquiz.ui.main

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.AlertUserDataBinding
import by.quizzes.amazingquiz.databinding.FragmentMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject


class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: MainViewModelProvider
    private val viewModel: MainViewModel by viewModels {
        viewModelProvider
    }
    private var binding: FragmentMainBinding? = null

    private var imagePickerLauncher: ActivityResultLauncher<Intent>? = null

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

        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null && data.data != null) {
                        val imageUri = data.data
                        viewModel.addPhotoToDb(getImageUrl(imageUri))
                    }
                }
            }

        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.ivUserPhoto?.setOnClickListener {
            openGallery()
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.name == null && user.surname == null) {
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

                        if (name == "") {
                            alertBinding.tilName.error = getString(R.string.empty_field)
                            shouldDismissDialog = false
                        }
                        if (surname == "") {
                            alertBinding.tilSurname.error = getString(R.string.empty_field)
                            shouldDismissDialog = false
                        } else {
                            viewModel.updateUser(name, surname)
                            addPhotoAlert()
                        }

                        if (shouldDismissDialog) {
                            alertDialog.dismiss()
                            viewModel.getUser()
                        }
                    }
                }
                alertDialog.show()

            } else {
                binding?.tvUserName?.text = "${user.name} ${user.surname}"
                binding?.ivUserPhoto?.let {
                    Glide.with(requireActivity())
                        .load(user.photoUrl)
                        .placeholder(R.drawable.ic_user)
                        .into(it)
                }
            }
        }
        viewModel.getUser()

        binding?.start?.setOnClickListener {
            viewModel.incompleteScore.observe(viewLifecycleOwner) { score ->
                if (score != null) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.attention))
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setMessage("You started the test but didn't finish it. Do you really want to start a new test and lose $score points?")
                        .setPositiveButton(getString(R.string.yes2)) { dialog, _ ->
                            dialog.dismiss()
                            viewModel.deleteQuiz()
                            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingFragment())
                        }
                        .setNegativeButton(getString(R.string.no2)) { dialog, _ ->
                            dialog.dismiss()
                            findNavController().navigate(MainFragmentDirections.actionMainFragmentToFragmentQuiz())
                        }
                        .show()
                } else {
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

            binding?.cvFavoriteTags?.visibility = if (it.size > 0) {
                VISIBLE
            } else {
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

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showExitConfirmationDialog()
        }
    }

    private fun addPhotoAlert(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.photo))
            .setIcon(R.drawable.ic_launcher_foreground)
            .setMessage(getString(R.string.add_photo_message))
            .setPositiveButton(getString(R.string.yes1)){_, _ ->
                openGallery()
            }
            .setNegativeButton(getString(R.string.no1)){dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showExitConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.exit))
            .setIcon(R.drawable.ic_launcher_foreground)
            .setMessage(getString(R.string.close_app_message))
            .setPositiveButton(getString(R.string.close_app)) { _, _ ->
                activity?.finish()
            }
            .setNegativeButton(getString(R.string.logout)) { _, _ ->
                Firebase.auth.signOut()
                activity?.finish()
            }
            .show()
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

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        imagePickerLauncher?.launch(Intent.createChooser(intent, "Select photo"))
    }

    private fun getImageUrl(uri: Uri?): String? {
        var imageUrl: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            uri?.let { requireContext().contentResolver.query(it, projection, null, null, null) }
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                imageUrl = it.getString(columnIndex)
            }
        }
        return imageUrl
    }
}