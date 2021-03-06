package io.someapp.wisecontlol.data.db.convertor

import androidx.room.TypeConverter
import java.util.*


class DateConverter {

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

}