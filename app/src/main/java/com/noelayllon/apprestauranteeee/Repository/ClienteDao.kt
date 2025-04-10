package com.noelayllon.apprestauranteeee.Repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM Clientes WHERE dni = :dni")
    suspend fun getClienteByDni(dni: String): Cliente?
}

// ProductoDao.kt
@Dao
interface ProductoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Query("SELECT * FROM Productos")
    fun getAllProductos(): Flow<List<Producto>>

    @Query("SELECT * FROM Productos WHERE categoria = :categoria")
    fun getProductosByCategoria(categoria: String): Flow<List<Producto>>
}

// PedidoDao.kt
@Dao
interface PedidoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pedido: Pedido)

    @Query("SELECT * FROM Pedidos WHERE dni_cliente = :dniCliente")
    fun getPedidosByCliente(dniCliente: String): Flow<List<Pedido>>
}

// DetallePedidoDao.kt
@Dao
interface DetallePedidoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detalle: DetallePedido)

    @Query("SELECT * FROM detalle_pedido WHERE pedido_id = :pedidoId")
    fun getDetallesByPedido(pedidoId: Int): Flow<List<DetallePedido>>
}