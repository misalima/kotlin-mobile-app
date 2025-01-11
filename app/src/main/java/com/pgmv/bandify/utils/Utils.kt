package com.pgmv.bandify.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun getCurrentTime(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
}

fun stringToLocalDateTime(dateTimeString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return LocalDateTime.parse(dateTimeString, formatter)
}

fun localDateTimeToString(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    return dateTime.format(formatter)
}

fun Long.millisToLocalDate(millis: Long = this): LocalDate {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("GMT")).toLocalDate()
}