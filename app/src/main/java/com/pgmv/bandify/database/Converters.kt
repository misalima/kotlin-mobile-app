package com.pgmv.bandify.database

import androidx.room.TypeConverter

class Converters {

    private val delimiter = ","

    // Converts List<String> to a String
    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return list?.joinToString(delimiter)
    }

    // Converts String back to List<String>
    @TypeConverter
    fun fromStringToList(value: String?): List<String>? {
        return value?.split(delimiter)
    }
}