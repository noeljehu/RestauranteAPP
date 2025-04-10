package com.noelayllon.apprestaurante.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val telefono: String?,
    val direccion: String?,
    @ColumnInfo(name = "email_cliente") val emailCliente: String?,
    val dni: String
)
