package by.quizzes.amazingquiz.extensions

import android.widget.TextView
import by.quizzes.amazingquiz.R

 fun TextView.setAnswerCardText(textArray: ArrayList<TextView?>) {

    for (text in textArray) {
        if (text?.id == this.id) {
            setTextColor(resources.getColor(R.color.green, null))
            textSize = 20f
        } else {
            text?.setTextColor(resources.getColor(R.color.black, null))
            text?.textSize = 14f
        }
    }
}