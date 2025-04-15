package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.noelayllon.apprestauranteeee.Adapter.ProductoAdapter
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityListarProductoBinding
import com.noelayllon.apprestauranteeee.modelo.Producto
import kotlinx.coroutines.launch

class ListarProducto : AppCompatActivity() {

    private lateinit var binding: ActivityListarProductoBinding
    private val productoAdapter = ProductoAdapter(emptyList()) { producto ->
        val intent = Intent(this, DetalleProducto::class.java)
        intent.putExtra("producto", producto)  // Enviamos el objeto completo
        startActivity(intent)
    }

    private var listaProductos: List<Producto> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityListarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el RecyclerView
        binding.rvProductos.layoutManager = GridLayoutManager(this, 2)

        binding.rvProductos.adapter = productoAdapter

        // Cargar los productos desde la base de datos
        cargarProductos()

        // Configurar el SearchView para filtrar los productos
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar los productos en base al texto ingresado
                filtrarProductos(newText)
                return true
            }
        })
    }

    private fun cargarProductos() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)

            // Usamos Flow para observar los cambios automáticamente
            db.productoDao().getAllProductosFlow().collect { productos ->
                // Actualizamos la lista de productos en el adaptador
                listaProductos = productos
                productoAdapter.actualizarProductos(listaProductos)
            }
        }
    }

    private fun filtrarProductos(query: String?) {
        val queryLowerCase = query?.lowercase().orEmpty() // Usamos lowercase() en lugar de toLowerCase()

        if (queryLowerCase.isEmpty()) {
            productoAdapter.actualizarProductos(listaProductos)
        } else {
            // Filtrar la lista de productos en base al nombre
            val resultadoBusqueda = listaProductos.filter {
                it.nombre.lowercase().contains(queryLowerCase) // También usamos lowercase() aquí
            }
            productoAdapter.actualizarProductos(resultadoBusqueda)
        }
    }
}
