package by.quizzes.amazingquiz.ui.registrationstep2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.quizzes.amazingquiz.MyApp
import by.quizzes.amazingquiz.databinding.FragmentRegistrationStep2Binding
import by.quizzes.amazingquiz.extensions.setErrorNull
import javax.inject.Inject

class RegistrationStep2Fragment: Fragment() {

    @Inject
    lateinit var registrationStep2ModelProvider: RegistrationStep2ModelProvider

    private val viewModel:RegistrationStep2ViewModel by viewModels {
        registrationStep2ModelProvider
    }

    private var binding: FragmentRegistrationStep2Binding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MyApp.applicationComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationStep2Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tilName?.setErrorNull()
        binding?.tilSurname?.setErrorNull()

        binding?.btnSignUp?.setOnClickListener {
            
        }
    }
}