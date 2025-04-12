package com.noelayllon.apprestauranteeee.modelo

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(
    tableName = "Clientes",
    indices = [
        Index(value = ["dni"], unique = true),
        Index(value = ["email_cliente"], unique = true)
    ]
)
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nombre: String,
    val telefono: String?,
    val direccion: String?,
    @ColumnInfo(name = "email_cliente") val email: String?,
    val dni: String
) : Parcelable


// Producto.kt
@Parcelize
@Entity(tableName = "Productos")
data class Producto(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nombre: String,
    val descripcion: String?,
    val precio: Double?,
    val categoria: String?,
    @ColumnInfo(name = "imagenUrl") val imagenUrl: String
): Parcelable

// Pedido.kt

@Entity(
    tableName = "Pedidos",
    foreignKeys = [ForeignKey(
        entity = Cliente::class,
        parentColumns = ["dni"],
        childColumns = ["dni_cliente"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["dni_cliente"])]
)
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "dni_cliente") val dniCliente: String,
    val mesa: String,
    @ColumnInfo(name = "fecha_pedido") val fechaPedido: String,
    val estado: String,
    val total: Double
)

// DetallePedido.kt
@Entity(
    tableName = "detalle_pedido",
    primaryKeys = ["pedido_id", "producto_id"],
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
    ]
)
data class DetallePedido(
    @ColumnInfo(name = "pedido_id") val pedidoId: Int,
    @ColumnInfo(name = "producto_id") val productoId: Int,
    val cantidad: Int,
    val subtotal: Double
)