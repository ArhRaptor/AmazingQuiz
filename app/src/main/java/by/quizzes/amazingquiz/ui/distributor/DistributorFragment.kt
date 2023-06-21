package by.quizzes.amazingquiz.ui.distributor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.quizzes.amazingquiz.databinding.FragmentDistributorBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DistributorFragment:Fragment() {

    private var binding: FragmentDistributorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDistributorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Firebase.auth.currentUser != null){
            findNavController().navigate(DistributorFragmentDirections.actionDistributorFragmentToMainFragment())
        }else{
            findNavController().navigate(DistributorFragmentDirections.actionDistributorFragmentToAuthorizationFragment())
        }
    }
}