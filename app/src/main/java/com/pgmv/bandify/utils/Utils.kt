package com.pgmv.bandify.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentTime(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
}

fun stringToLocalDateTime(dateTimeString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return LocalDateTime.parse(dateTimeString, formatter)
}