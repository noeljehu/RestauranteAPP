package com.noelayllon.apprestaurante.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trabajadores")
data class Trabajador(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val telefono: String?,
    val puesto: String?,
    @ColumnInfo(name = "fecha_contratacion") val fechaContratacion: String?,
    val salario: Double?
)
