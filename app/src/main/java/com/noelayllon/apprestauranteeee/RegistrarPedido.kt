package com.noelayllon.apprestauranteeee

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.noelayllon.apprestauranteeee.Adapter.ProductoPedidoAdapter
import com.noelayllon.apprestauranteeee.Repository.ClienteDao
import com.noelayllon.apprestauranteeee.Repository.ProductoDao
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityRegistrarPedidoBinding
import com.noelayllon.apprestauranteeee.modelo.DetallePedido
import com.noelayllon.apprestauranteeee.modelo.Pedido
import com.noelayllon.apprestauranteeee.modelo.Producto
import kotlinx.coroutines.launch

class RegistrarPedido : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarPedidoBinding
    private lateinit var adapter: ProductoPedidoAdapter
    private lateinit var dao: ProductoDao
    private lateinit var clienteDao: ClienteDao


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el RecyclerView
        binding.rvPedidos.layoutManager = LinearLayoutManager(this)

        // Inicializa tu base de datos y DAO
        val db = AppDatabase.getDatabase(this) // Debes tener tu instancia singleton
        dao = db.productoDao()
        clienteDao = db.clienteDao()


        // Carga los productos usando corrutinas
        lifecycleScope.launch {
            val productos = dao.getAllProductos ()

            adapter = ProductoPedidoAdapter(productos) { productoId, cantidad ->


                // Lógica de cantidades aquí
            }
            binding.rvPedidos.adapter = adapter
        }
        // Configuración del evento de búsqueda del cliente
        binding.etDni.setOnTouchListener { v, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // índice del drawableEnd
                val drawable = binding.etDni.compoundDrawables[drawableEnd]
                if (drawable != null && event.rawX >= (binding.etDni.right - drawable.bounds.width())) {
                    val dniIngresado = binding.etDni.text.toString().trim().uppercase()

                    if (dniIngresado.isEmpty()) {
                        // Mostrar un Toast si el DNI está vacío
                        Toast.makeText(this, "Ingrese un DNI válido", Toast.LENGTH_SHORT).show()
                        return@setOnTouchListener true
                    }

                    // Buscar cliente en la base de datos usando la corrutina
                    lifecycleScope.launch {
                        val cliente = clienteDao.getClienteByDni(dniIngresado)
                        if (cliente != null) {
                            // Mostrar Toast si el cliente es encontrado
                            Toast.makeText(this@RegistrarPedido, "Cliente: ${cliente.nombre}", Toast.LENGTH_SHORT).show()
                            binding.tvNombreCliente.text = "Cliente: ${cliente.nombre}"
                        } else {
                            // Mostrar Toast si el cliente no es encontrado
                            Toast.makeText(this@RegistrarPedido, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                            binding.tvNombreCliente.text = "Cliente no encontrado"

                        }
                    }

                    return@setOnTouchListener true
                }
            }
            false
        }
        binding.btnConfirmar .setOnClickListener {
            val dniCliente = binding.etDni.text.toString().trim().uppercase()
            val mesaSeleccionada = binding.spinnerMesa.selectedItem.toString()
            val cantidades = adapter.obtenerCantidades() // productoId -> cantidad

            // Validaciones
            if (dniCliente.isEmpty()) {
                Toast.makeText(this, "Ingrese un DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (mesaSeleccionada == "Selecciona una mesa") {
                Toast.makeText(this, "Seleccione una mesa válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cantidades.isEmpty()) {
                Toast.makeText(this, "Seleccione al menos un producto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val cliente = clienteDao.getClienteByDni(dniCliente)
                if (cliente == null) {
                    Toast.makeText(this@RegistrarPedido, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // Calcular total
                val productos = dao.getAllProductos()
                var total = 0.0

                cantidades.forEach { (productoId, cantidad) ->
                    val producto = productos.find { it.id == productoId }
                    if (producto != null) {
                        total += producto.precio * cantidad
                    }
                }

                val fecha = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())

                val pedido = Pedido(
                    dniCliente = dniCliente,
                    mesa = mesaSeleccionada,
                    fechaPedido = fecha,
                    estado = "Registrado",
                    total = total
                )

                val pedidoId = db.pedidoDao().insert(pedido).toInt()


                cantidades.forEach { (productoId, cantidad) ->
                    val producto = productos.find { it.id == productoId }
                    if (producto != null) {
                        val detalle = DetallePedido(
                            pedidoId = pedidoId,
                            productoId = productoId,
                            cantidad = cantidad,
                            subtotal = producto.precio * cantidad
                        )
                        db.detallePedidoDao().insert (detalle)
                    }
                }

                Toast.makeText(this@RegistrarPedido, "Pedido registrado con éxito", Toast.LENGTH_LONG).show()

                // Opcional: limpiar campos
                binding.etDni.text?.clear()
                binding.tvNombreCliente.text = ""
                binding.spinnerMesa.setSelection(0)
                adapter = ProductoPedidoAdapter(productos) { _, _ -> }
                binding.rvPedidos.adapter = adapter
            }
        }

    }
}







