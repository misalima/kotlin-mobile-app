package com.pgmv.bandify.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentTime(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
}