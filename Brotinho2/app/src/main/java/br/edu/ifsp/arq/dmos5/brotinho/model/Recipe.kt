package br.edu.ifsp.arq.dmos5.brotinho.model

import androidx.room.Entity

@Entity(tableName = "receitas")
data class Recipe(

    var nome: String? = null,
    var ingredientes: List<String>? = null,
    var modoPreparo: String? = null,
    var categoria: String? = null
)
