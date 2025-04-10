package com.noelayllon.apprestaurante.modelo

import androidx.room.Embedded
import androidx.room.Relation

data class PedidoConDetalles(
    @Embedded val pedido: Pedido,
    @Relation(
        parentColumn = "id",
        entityColumn = "pedido_id"
    )
    val detalles: List<DetallePedido>
)
