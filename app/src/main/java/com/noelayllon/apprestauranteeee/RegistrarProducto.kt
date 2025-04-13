package com.noelayllon.apprestauranteeee

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityRegistrarProductoBinding
import com.noelayllon.apprestauranteeee.modelo.Producto
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class RegistrarProducto : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrarProductoBinding
    private var imagenUrlSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityRegistrarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSeleccionarImagen.setOnClickListener {
            val url = binding.etImagenUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                imagenUrlSeleccionada = url
                Picasso.get().load(url).into(binding.ivImagenProducto)
            } else {
                Toast.makeText(this, "Ingresa una URL de imagen", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGuardarProducto.setOnClickListener {
            guardarProducto()
        }
    }

    private fun guardarProducto() {
        val nombre = binding.etNombre.text.toString().trim()
        val descripcion = binding.etDescripcion.text.toString().trim()
        val precio = binding.etPrecio.text.toString().toDoubleOrNull()
        val categoria = binding.etCategoria.text.toString().trim()

        if (nombre.isEmpty() || precio == null || imagenUrlSeleccionada.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val producto = Producto(
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            categoria = categoria,
            imagenUrl = imagenUrlSeleccionada
        )

        lifecycleScope.launch {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "mi_basedatos").build()
            db.productoDao().insert(producto)
            Toast.makeText(this@RegistrarProducto, "Producto guardado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
