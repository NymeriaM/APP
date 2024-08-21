package br.edu.ifsp.arq.dmos5.brotinho.database

import androidx.room.TypeConverter
import br.edu.ifsp.arq.dmos5.brotinho.model.User
import java.time.LocalDate

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