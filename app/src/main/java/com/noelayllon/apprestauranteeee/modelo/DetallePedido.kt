package com.noelayllon.apprestaurante.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_pedido",
    foreignKeys = [
        ForeignKey(
            entity = Pedido::class,
            parentColumns = ["id"],
            childColumns = ["pedido_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["producto_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("pedido_id"), Index("producto_id")]
)
data class DetallePedido(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "pedido_id") val pedidoId: Int,
    @ColumnInfo(name = "producto_id") val productoId: Int,
    val cantidad: Int,
    val subtotal: Double
)
