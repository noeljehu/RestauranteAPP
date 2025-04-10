package com.noelayllon.apprestaurante.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pedidos",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["id"],
            childColumns = ["cliente_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cliente_id")]
)
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "cliente_id") val clienteId: Int,
    val trabajador: String?, // texto, no FK
    @ColumnInfo(name = "fecha_pedido") val fechaPedido: String?,
    val estado: String?,
    val total: Double
)
