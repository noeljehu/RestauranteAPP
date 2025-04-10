package com.noelayllon.apprestauranteeee

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.noelayllon.apprestauranteeee.Repository.ClienteDao
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityRegistrodeclienteBinding
import com.noelayllon.apprestauranteeee.modelo.Cliente

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Registrodecliente : AppCompatActivity() {
    // Declaramos el objeto binding
    private lateinit var binding: ActivityRegistrodeclienteBinding
    private lateinit var restauranteDao: ClienteDao

    private lateinit var database: AppDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewBinding
        binding = ActivityRegistrodeclienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instancia de la BD Room
        database = AppDatabase.getDatabase(this)

        // Registrar cliente
        binding.registerButton.setOnClickListener {
            registrarCliente()
        }

        // Botón regresar (toolbar)
        binding.topAppBar1.setNavigationOnClickListener {
            finish()
        }
    }

    private fun registrarCliente() {
        val nombre = binding.nameEditText.text.toString().trim()
        val telefono = binding.phoneEditText.text.toString().trim()
        val direccion = binding.addressEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val dni = binding.dniEditText.text.toString().trim()

        if (nombre.isBlank() || telefono.isBlank() || direccion.isBlank() ||
            email.isBlank() || dni.isBlank()
        ) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val cliente = Cliente(
            nombre = nombre,
            telefono = telefono,
            direccion = direccion,
            email = email,
            dni = dni
        )

        // Guardado en base de datos en hilo de background
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    database.clienteDao().insert(cliente)
                }
                Toast.makeText(this@Registrodecliente, " Registrado corectamente:", Toast.LENGTH_LONG).show()
                limpiarCampos()
            } catch (e: Exception) {
                Toast.makeText(this@Registrodecliente, "Error al registrar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun limpiarCampos() {
        binding.nameEditText.text?.clear()
        binding.phoneEditText.text?.clear()
        binding.addressEditText.text?.clear()
        binding.emailEditText.text?.clear()
        binding.dniEditText.text?.clear()
    }
}
