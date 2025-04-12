package com.noelayllon.apprestauranteeee
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityClienteDetalleBinding
import com.noelayllon.apprestauranteeee.modelo.Cliente
import kotlinx.coroutines.launch

class ClienteDetalle : AppCompatActivity() {

    private lateinit var binding: ActivityClienteDetalleBinding
    private lateinit var database: AppDatabase
    private var cliente: Cliente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityClienteDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        database = AppDatabase.getDatabase(this)

        // Recuperar cliente desde el Intent
        cliente = intent.getParcelableExtra("cliente")

        if (cliente == null) {
            finish() // Si no se recibió el cliente, cierra la pantalla
            return
        }

        // Mostrar datos en el formulario
        binding.nombreEditText.setText(cliente?.nombre)
        binding.telefonoEditText.setText(cliente?.telefono.orEmpty())
        binding.direccionEditText.setText(cliente?.direccion.orEmpty())
        binding.emailEditText.setText(cliente?.email.orEmpty())
        binding.dniEditText.setText(cliente?.dni)

        // Modificar cliente
        binding.modificarButton.setOnClickListener {
            val nombre = binding.nombreEditText.text.toString().trim()
            val telefono = binding.telefonoEditText.text.toString().trim()
            val direccion = binding.direccionEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()

            // Validaciones simples
            if (nombre.isEmpty()) {
                binding.nombreEditText.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (cliente != null) {
                lifecycleScope.launch {
                    (if (telefono.isEmpty()) null else telefono)?.let { it1 ->
                        (if (direccion.isEmpty()) null else direccion)?.let { it2 ->
                            (if (email.isEmpty()) null else email)?.let { it3 ->
                                database.clienteDao().updateCliente(
                                    dni = cliente!!.dni,
                                    nombre = nombre,
                                    telefono = it1,
                                    direccion = it2,
                                    email = it3
                                )
                            }
                        }
                    }
                    // Mostrar mensaje de éxito
                    Toast.makeText(this@ClienteDetalle, "Cliente modificado correctamente", Toast.LENGTH_SHORT).show()
                    finish() // Cerrar la actividad después de la modificación
                }
            }
        }

        // Eliminar cliente
        binding.eliminarButton.setOnClickListener {
            cliente?.let {
                lifecycleScope.launch {
                    database.clienteDao().deleteClienteByDni(it.dni)
                    // Mostrar mensaje de éxito
                    Toast.makeText(this@ClienteDetalle, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show()
                    finish() // Cerrar la actividad después de la eliminación
                }
            }
        }
    }
}
