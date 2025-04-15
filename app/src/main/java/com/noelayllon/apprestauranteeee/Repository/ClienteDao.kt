package com.noelayllon.apprestauranteeee.Repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.noelayllon.apprestauranteeee.modelo.Cliente
import com.noelayllon.apprestauranteeee.modelo.DetallePedido
import com.noelayllon.apprestauranteeee.modelo.Pedido
import com.noelayllon.apprestauranteeee.modelo.Producto
import kotlinx.coroutines.flow.Flow

// ClienteDao.kt
@Dao
interface ClienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cliente: Cliente)

    @Query("SELECT * FROM Clientes")
    fun getAllClientes(): Flow<List<Cliente>>

    @Query("SELECT * FROM clientes WHERE UPPER(dni) = UPPER(:dni) LIMIT 1")
    suspend fun getClienteByDni(dni: String): Cliente?

    @Query("DELETE FROM Clientes WHERE dni = :dni")
    suspend fun deleteClienteByDni(dni: String)

    @Query("""
    UPDATE Clientes 
    SET nombre = :nombre, 
        telefono = :telefono, 
        direccion = :direccion, 
        email_cliente = :email
    WHERE dni = :dni
    """)
    suspend fun updateCliente(
        dni: String,
        nombre: String,
        telefono: String,
        direccion: String,
        email: String
    )

}


// ProductoDao.kt
@Dao
interface ProductoDao {
    @Insert
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM productos")
    suspend fun getAllProductos(): List<Producto>  // Función no reactiva

    @Query("SELECT * FROM productos")
    fun getAllProductosFlow(): Flow<List<Producto>>  // Función reactiva
}




// PedidoDao.kt
@Dao
interface PedidoDao {
    @Query("SELECT * FROM Pedidos WHERE id = :pedidoId")
    suspend fun getPedidoById(pedidoId: Int): Pedido

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pedido: Pedido): Long

    @Query("SELECT * FROM Pedidos")
    suspend fun getAllPedidos(): List<Pedido>

    @Query("SELECT * FROM Pedidos WHERE dni_cliente = :dniCliente")
    fun getPedidosByCliente(dniCliente: String): Flow<List<Pedido>>
}

// DetallePedidoDao.kt
@Dao
interface DetallePedidoDao {

    @Query("SELECT * FROM detalle_pedido WHERE pedido_id = :pedidoId")
    suspend fun getDetallesByPedidoId(pedidoId: Int): List<DetallePedido>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detalle: DetallePedido): Long

    @Query("SELECT * FROM detalle_pedido WHERE pedido_id = :pedidoId")
    fun getDetallesByPedido(pedidoId: Int): Flow<List<DetallePedido>>
}