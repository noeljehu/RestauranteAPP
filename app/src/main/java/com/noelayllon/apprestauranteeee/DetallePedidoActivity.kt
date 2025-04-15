package com.noelayllon.apprestauranteeee
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityDetallePedidoBinding
import kotlinx.coroutines.launch

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetallePedidoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pedidoId = intent.getIntExtra("pedido_id", -1)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val pedido = db.pedidoDao().getPedidoById(pedidoId)
            val cliente = db.clienteDao().getClienteByDni(pedido.dniCliente)
            val detalle = db.detallePedidoDao().getDetallesByPedidoId(pedidoId)
            val productos = db.productoDao().getAllProductos()

            val nombreCliente = cliente?.nombre ?: "Desconocido"

            binding.tvCliente.text = "Cliente: $nombreCliente"
            binding.tvFecha.text = "Fecha: ${pedido.fechaPedido}"
            binding.tvEstado.text = "Estado: ${pedido.estado}"
            binding.tvTotal.text = "Total: S/ ${String.format("%.2f", pedido.total)}"

            // Mostrar detalles de productos
            val detallesConProducto = detalle.map { detallePedido ->
                val producto = productos.find { it.id == detallePedido.productoId }
                Triple(producto?.nombre ?: "Producto desconocido", detallePedido.cantidad, detallePedido.subtotal)
            }

            val detalleTexto = detallesConProducto.joinToString("\n") {
                "- ${it.first} x${it.second} = S/ ${String.format("%.2f", it.third)}"
            }

            binding.tvProductos.text = detalleTexto
        }
    }
}
