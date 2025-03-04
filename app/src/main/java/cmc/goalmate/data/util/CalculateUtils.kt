package cmc.goalmate.data.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun convertUtcToLocal(utcString: String): LocalDateTime {
    val instant = Instant.parse(utcString)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return localDateTime
}
