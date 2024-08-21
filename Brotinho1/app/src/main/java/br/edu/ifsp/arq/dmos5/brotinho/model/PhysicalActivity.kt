package br.edu.ifsp.arq.dmos5.brotinho.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "activity")
data class PhysicalActivity (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var userId: String,
    var type: PhysicalActivityType?,
    var distance: Double,
    var duration: Double,
    var date: String): Serializable {

    @Ignore
    constructor(): this(UUID.randomUUID().toString(), "", PhysicalActivityType.CAMINHADA, 0.0, 0.0, "")

    enum class PhysicalActivityType(val value: String) {
        CAMINHADA("Caminhada"),
        CICLISMO("Ciclismo"),
        CORRIDA("Corrida"),
        NATACAO("Natação")
    }

}