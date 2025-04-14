package com.noelayllon.apprestauranteeee

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.noelayllon.apprestauranteeee.Adapter.ProductoPedidoAdapter
import com.noelayllon.apprestauranteeee.databinding.ActivityRegistrarPedidoBinding
import com.noelayllon.apprestauranteeee.modelo.Producto

class RegistrarPedido : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarPedidoBinding
    private lateinit var adapter: ProductoPedidoAdapter
    private var productos: List<Producto> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el binding
        binding = ActivityRegistrarPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.rvPedidos.layoutManager = LinearLayoutManager(this)
        adapter = ProductoPedidoAdapter(productos) { productoId, cantidad ->
            // Aquí manejas el cambio de cantidad
        }
        binding.rvPedidos.adapter = adapter
    }
}