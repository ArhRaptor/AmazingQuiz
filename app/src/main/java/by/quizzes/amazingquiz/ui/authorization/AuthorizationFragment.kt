package by.quizzes.amazingquiz.ui.authorization

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
import by.quizzes.amazingquiz.databinding.FragmentAuthorizationBinding
import by.quizzes.amazingquiz.extensions.setErrorNull
import by.quizzes.amazingquiz.extensions.toTrimText
import javax.inject.Inject


class AuthorizationFragment : Fragment() {

    @Inject
    lateinit var authorizationModelProvider: AuthorizationModelProvider

    private val viewModel: AuthorizationViewModel by viewModels {
        authorizationModelProvider
    }
    private var binding: FragmentAuthorizationBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnLogin?.setOnClickListener {
            val email = binding?.etEmail?.toTrimText()
            val password = binding?.etPassword?.toTrimText()

            binding?.tilEmail?.setErrorNull()
            binding?.tilPassword?.setErrorNull()
            
            if (email == "") {
                binding?.tilEmail?.error = getString(R.string.empty_field)
            }
            if (password == ""){
                binding?.tilPassword?.error = getString(R.string.empty_field)
            }else{
                viewModel.openMainFragment ={
                    findNavController().navigate(AuthorizationFragmentDirections.actionAuthorizationFragmentToMainFragment())
                }
                viewModel.errorText.observe(viewLifecycleOwner){
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
                viewModel.login(email!!, password!!)
            }
        }

        binding?.tvSignUp?.setOnClickListener {
            findNavController().navigate(AuthorizationFragmentDirections.actionAuthorizationFragmentToRegistrationFragment())
        }

    }
}