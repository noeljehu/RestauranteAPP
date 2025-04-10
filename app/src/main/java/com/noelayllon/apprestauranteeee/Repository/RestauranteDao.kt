package com.noelayllon.apprestaurante.Repository


import androidx.room.*
import com.noelayllon.apprestaurante.modelo.Cliente
import com.noelayllon.apprestaurante.modelo.Producto
import com.noelayllon.apprestaurante.modelo.Pedido
import com.noelayllon.apprestaurante.modelo.DetallePedido
import com.noelayllon.apprestaurante.modelo.PedidoConDetalles


@Dao
interface RestauranteDao {

    // CRUD Clientes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(cliente: Cliente)

    @Update
    suspend fun actualizarCliente(cliente: Cliente)

    @Delete
    suspend fun eliminarCliente(cliente: Cliente)

    @Query("SELECT * FROM clientes")
    suspend fun obtenerClientes(): List<Cliente>

    @Query("SELECT * FROM clientes WHERE id = :id")
    suspend fun obtenerClientePorId(id: Int): Cliente?

    // CRUD Productos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: Producto)

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Delete
    suspend fun eliminarProducto(producto: Producto)

    @Query("SELECT * FROM productos")
    suspend fun obtenerProductos(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun obtenerProductoPorId(id: Int): Producto?

    // CRUD Pedidos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPedido(pedido: Pedido)

    @Update
    suspend fun actualizarPedido(pedido: Pedido)

    @Delete
    suspend fun eliminarPedido(pedido: Pedido)

    @Query("SELECT * FROM pedidos")
    suspend fun obtenerPedidos(): List<Pedido>

    @Query("SELECT * FROM pedidos WHERE id = :id")
    suspend fun obtenerPedidoPorId(id: Int): Pedido?

    // CRUD Detalle_Pedido
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDetalle(detalle: DetallePedido)

    @Update
    suspend fun actualizarDetalle(detalle: DetallePedido)

    @Delete
    suspend fun eliminarDetalle(detalle: DetallePedido)

    @Query("SELECT * FROM detalle_pedido WHERE pedido_id = :pedidoId")
    suspend fun obtenerDetallesDePedido(pedidoId: Int): List<DetallePedido>

    // Método para obtener pedidos con detalles (relación)
    @Transaction
    @Query("SELECT * FROM pedidos WHERE id = :pedidoId")
    suspend fun obtenerPedidoConDetalles(pedidoId: Int): PedidoConDetalles
}
