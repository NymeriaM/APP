package java.br.edu.ifsp.arq.dmos5.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

@Entity(tableName = "user")
data class User (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var email: String,
    var name: String,
    var surname: String,
    var password: String,
    val image: String,
    var dateOfBirth: LocalDate?,
    var gender: Gender?): Serializable {

    enum class Gender(val value: String) {

        MASCULINO("Masculino"),
        FEMININO("Feminino"),
        OUTRO("Outro"),
        PREFIRO_NAO_DIZER("Prefiro não dizer")
    }

}