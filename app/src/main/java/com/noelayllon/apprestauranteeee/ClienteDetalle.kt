package com.noelayllon.apprestauranteeee

import android.os.Bundle
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
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout
        binding = ActivityClienteDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar navegación atrás
        binding.topAppBar .setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        // Obtener instancia DB
        database = AppDatabase.getDatabase(this)

        // Obtener DNI del cliente desde el Intent
        val clienteDni = intent.getStringExtra("clienteDni")  // <-- Usa DNI, no ID
        if (clienteDni != null) {
            lifecycleScope.launch {
                val clienteBD = database.clienteDao().getClienteByDni(clienteDni)
                if (clienteBD != null) {
                    cliente = clienteBD
                    // Mostrar en pantalla
                    binding.nombreEditText.setText(cliente.nombre)
                    binding.telefonoEditText.setText(cliente.telefono ?: "")
                    binding.direccionEditText.setText(cliente.direccion ?: "")
                    binding.emailEditText.setText(cliente.email ?: "")
                    binding.dniEditText.setText(cliente.dni)
                }
            }
        }

        // Modificar cliente
        binding.modificarButton.setOnClickListener {
            val nombre = binding.nombreEditText.text.toString()
            val telefono = binding.telefonoEditText.text.toString()
            val direccion = binding.direccionEditText.text.toString()
            val email = binding.emailEditText.text.toString()

            lifecycleScope.launch {
                database.clienteDao().updateCliente(
                    dni = cliente.dni,
                    nombre = nombre,
                    telefono = telefono,
                    direccion = direccion,
                    email = email
                )
                onBackPressedDispatcher.onBackPressed()
            }
        }

        // Eliminar cliente
        binding.eliminarButton.setOnClickListener {
            lifecycleScope.launch {
                database.clienteDao().deleteClienteByDni(cliente.dni)
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}
