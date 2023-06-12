package by.quizzes.amazingquiz.extensions

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorNull(){
    editText?.doAfterTextChanged {
        if (it.toString().trim() != ""){
            error = null
        }
    }
}