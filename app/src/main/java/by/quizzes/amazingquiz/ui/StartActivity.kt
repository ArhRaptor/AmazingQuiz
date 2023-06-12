package by.quizzes.amazingquiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import by.quizzes.amazingquiz.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private var binding: ActivityStartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        intent.data
    }
}