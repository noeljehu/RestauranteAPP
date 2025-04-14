package com.noelayllon.apprestauranteeee

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.noelayllon.apprestauranteeee.Repository.ProductoDao
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityDetalleProductoBinding
import com.noelayllon.apprestauranteeee.modelo.Producto
import kotlinx.coroutines.launch

class DetalleProducto : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleProductoBinding
    private lateinit var productoDao: ProductoDao
    private var productoId: Int = -1 // ID del producto actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityDetalleProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos el DAO
        productoDao = AppDatabase.getDatabase(applicationContext).productoDao()

        // Recuperamos el objeto producto desde el intent
        val producto = intent.getParcelableExtra<Producto>("producto") ?: return

        productoId = producto.id ?: -1

        // Mostramos los datos del producto en el formulario
        binding.etNombre.setText(producto.nombre)
        binding.etDescripcion.setText(producto.descripcion ?: "")
        binding.etPrecio.setText(producto.precio?.toString() ?: "")
        binding.etCategoria.setText(producto.categoria ?: "")
        binding.etImagenUrl.setText(producto.imagenUrl)

        // Acción: Actualizar producto
        binding.btnActualizar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val descripcion = binding.etDescripcion.text.toString()
            val precio = binding.etPrecio.text.toString().toDoubleOrNull() ?: 0.0
            val categoria = binding.etCategoria.text.toString()
            val imagenUrl = binding.etImagenUrl.text.toString()

            val productoActualizado = Producto(
                id = productoId,
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                categoria = categoria,
                imagenUrl = imagenUrl
            )

            lifecycleScope.launch {
                // Usamos update() en lugar de insert()
                productoDao.update(productoActualizado)
                finish()
            }
        }

        // Acción: Eliminar producto
        binding.btnEliminar.setOnClickListener {
            lifecycleScope.launch {
                // Eliminamos el producto por ID
                productoDao.deleteById(productoId)
                finish()
            }
        }
        binding.toolbarTitleNcomida.setOnClickListener{
            finish()
        }
    }
}
