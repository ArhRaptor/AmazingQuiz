package by.quizzes.amazingquiz.ui.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.R
import by.quizzes.amazingquiz.databinding.FragmentRegistrationBinding
import by.quizzes.amazingquiz.extensions.setErrorNull
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    @Inject
    lateinit var registrationModelProvider: RegistrationModelProvider
    private val viewModel: RegistrationViewModel by viewModels {
        registrationModelProvider
    }
    private var binding: FragmentRegistrationBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tilEmail?.setErrorNull()
        binding?.tilPassword?.setErrorNull()
        binding?.tilPassword2?.setErrorNull()

        viewModel.openMainFragment = {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToVerificationFragment())
        }

        binding?.tvLogin?.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToAuthorizationFragment())
        }

        binding?.btnSignUp?.setOnClickListener {

            if (binding?.etEmail?.text.toString().trim() == "") {
                binding?.tilEmail?.error = getString(R.string.empty_field)
            }
            if (binding?.etPassword?.text.toString().trim() == "") {
                binding?.tilPassword?.error = getString(R.string.empty_field)
            }
            if (binding?.etPassword2?.text.toString().trim() == "") {
                binding?.tilPassword2?.error = getString(R.string.empty_field)
            } else {
                if (binding?.etPassword?.text.toString()
                        .trim() != binding?.etPassword2?.text.toString().trim()
                ) {
                    binding?.tilPassword?.error = getString(R.string.password_mismatch)
                    binding?.tilPassword2?.error = getString(R.string.password_mismatch)
                } else {

                    viewModel.errorText.observe(viewLifecycleOwner){
                        Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                    }
                    viewModel.registration(
                        binding?.etEmail?.text.toString().trim(),
                        binding?.etPassword?.text.toString().trim()
                    )
                }
            }
        }
    }
}