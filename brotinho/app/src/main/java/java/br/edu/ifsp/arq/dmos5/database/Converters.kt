package java.br.edu.ifsp.arq.dmos5.database

import androidx.room.TypeConverter
import java.br.edu.ifsp.arq.dmos5.model.User
import java.time.LocalDate
import java.time.ZoneId

class Converters {

    @TypeConverter
    fun fromGender(gender: User.Gender) : String = gender.toString()

    @TypeConverter
    fun toGender(string: String) : User.Gender = User.Gender.valueOf(string)

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}