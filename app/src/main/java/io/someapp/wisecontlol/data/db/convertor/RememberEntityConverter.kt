package io.someapp.wisecontlol.data.db.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import io.someapp.wisecontlol.data.tasks.RememberEntity


class RememberEntityConverter {

    private val gson = Gson()

    @TypeConverter
    fun dateToTimestamp(value: RememberEntity?): String? {
        if (value == null) {
            return null
        }
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromTimestamp(value: String?): RememberEntity? {
        if (value == null) {
            return null
        }
        return gson.fromJson(value, RememberEntity::class.java)
    }

}