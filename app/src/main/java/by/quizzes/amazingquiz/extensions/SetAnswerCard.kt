package by.quizzes.amazingquiz.extensions

import android.widget.TextView
import by.quizzes.amazingquiz.R

const val BIG_TEXT = 20f
const val NORMAL_TEXT = 14f

 fun TextView.setAnswerCardText(textArray: ArrayList<TextView?>) {

    for (text in textArray) {
        if (text?.id == this.id) {
            setTextColor(resources.getColor(R.color.green, null))
            textSize = BIG_TEXT
        } else {
            text?.setTextColor(resources.getColor(R.color.black, null))
            text?.textSize = NORMAL_TEXT
        }
    }
}