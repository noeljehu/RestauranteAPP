package com.noelayllon.apprestauranteeee

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityRegistrarProductoBinding
import com.noelayllon.apprestauranteeee.modelo.Producto
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrarProducto : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarProductoBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la base de datos
        db = AppDatabase.getDatabase(this)

        // Configurar el botón de guardar
        binding.btnGuardarProducto.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val descripcion = binding.etDescripcion.text.toString().trim()
            val precio = binding.etPrecio.text.toString().trim()
            val categoria = binding.etCategoria.text.toString().trim()
            val imagenUrl = binding.etImagenUrl.text.toString().trim()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio.isNotEmpty() && categoria.isNotEmpty() && imagenUrl.isNotEmpty()) {
                // Insertar un nuevo producto en la base de datos
                val producto = Producto(
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio.toDouble(),
                    categoria = categoria,
                    imagenUrl = imagenUrl
                )

                // Realizar la inserción en un hilo secundario
                CoroutineScope(Dispatchers.IO).launch {
                    db.productoDao().insert(producto)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegistrarProducto, "Producto guardado", Toast.LENGTH_SHORT).show()
                    }
                }

                // Agregar ImageView dinámicamente
                val imageView = ImageView(this)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    200 // Puedes ajustar la altura
                )
                imageView.layoutParams = params

                // Agregar ImageView al layout
                binding.root.addView(imageView)

                // Usar Picasso para cargar la imagen en el ImageView
                Picasso.get()
                    .load(imagenUrl)
                    .error(R.drawable.error_image)  // Si la imagen no carga, muestra una imagen de error
                    .into(imageView)  // Cargar la imagen en el ImageView

            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
