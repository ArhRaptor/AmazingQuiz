package by.quizzes.amazingquiz.util

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverter {
    @TypeConverter
    fun dateToLong(date: Date?): Long? = date?.time

    @TypeConverter
    fun longToDate(long: Long?): Date? = long?.let { Date(it) }
}