package tech.henriquedev.todoapp.data

import androidx.room.TypeConverter
import tech.henriquedev.todoapp.data.model.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}