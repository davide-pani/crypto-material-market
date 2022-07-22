package com.davidepani.cryptomaterialmarket.presentation.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.*

fun LocalDateTime.minutesBetween(secondDateTime: LocalDateTime): Long {
    return this.differenceBetween(
        secondDateTime = secondDateTime,
        unit = ChronoUnit.MINUTES
    )
}

fun LocalDateTime.differenceBetween(secondDateTime: LocalDateTime, unit: ChronoUnit): Long {
    return unit.between(this, secondDateTime)
}

fun LocalDateTime.toFormattedString(formatter: DateTimeFormatter): String {
    return this.format(formatter)
}

fun LocalDateTime.toFormattedDateOnlyString(formatter: DateTimeFormatter): String {
    return this.toLocalDate().format(formatter)
}

fun LocalDate.toFormattedString(formatter: DateTimeFormatter): String {
    return this.format(formatter)
}

fun LocalDateTime.toFormattedDateWithTimeString(
    formatStyle: FormatStyle = FormatStyle.SHORT,
    locale: Locale = Locale.getDefault()
): String {
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale)
    return this.toFormattedString(formatter = formatter)
}

fun LocalDateTime.toFormattedDateString(
    formatStyle: FormatStyle = FormatStyle.SHORT,
    locale: Locale = Locale.getDefault()
): String {
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale)
    return this.toLocalDate().toFormattedString(formatter = formatter)
}