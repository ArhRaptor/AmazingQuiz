package by.quizzes.amazingquiz.ui.verification

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.databinding.FragmentVerificationBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class VerificationFragment : Fragment() {

    @Inject
    lateinit var verificationModelProvider: VerificationModelProvider

    private val viewModel: VerificationViewModel by viewModels {
        verificationModelProvider
    }
    private var binding: FragmentVerificationBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*        viewModel.isNextRegistrationStep.observe(viewLifecycleOwner) {
                    if (it) {
                        findNavController().navigate(VerificationFragmentDirections.actionVerificationFragmentToRegistrationStep2Fragment())
                    }
                }
                viewModel.isVerified()*/

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
        viewModel.sentVerificationMessage()

        binding?.checkMail?.setOnClickListener {

            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            startActivity(intent)

        }

        binding?.verify?.setOnClickListener {
            viewModel.sentVerificationMessage()
        }
    }
}