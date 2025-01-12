package com.pgmv.bandify.utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun getCurrentTime(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
}

fun Long.millisToLocalDate(millis: Long = this): LocalDate {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.of("GMT")).toLocalDate()
}

fun formatTimeDifference(createdAt: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val createdDateTime = LocalDateTime.parse(createdAt, formatter)


    val now = LocalDateTime.now(ZoneId.systemDefault())


    val duration = Duration.between(createdDateTime, now)
    val hours = duration.toHours()
    val days = duration.toDays()

    return when {
        hours < 24 -> "$hours horas atrás"
        hours < 48 -> "Ontem"
        days < 7 -> "${days} dias atrás"
        else -> createdDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}