package com.davidepani.cryptomaterialmarket.data.db.room

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class RoomTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime): Long {
        return date.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun doubleListToString(list: List<Double>): String {
        return list.map { it.toString() }.reduce { s1, s2 ->
            "$s1 $s2"
        }
    }

    @TypeConverter
    fun stringToDoubleList(string: String): List<Double> {
        return string.split(" ").map {
            it.toDouble()
        }
    }

}
