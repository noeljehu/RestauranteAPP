package com.noelayllon.apprestauranteeee

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.room.Insert
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import com.noelayllon.apprestauranteeee.databinding.ActivityRegistrarUsuarioBinding
import com.noelayllon.apprestauranteeee.modelo.Usuario
import java.text.SimpleDateFormat
import java.util.*

class RegistrarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarUsuarioBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // CORRECCIÓN: inicializa binding
        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Configura el DatePicker para la fecha de contratación
        binding.etFecha.setOnClickListener {
            mostrarDatePickerDialog()
        }

        // Configura el botón para registrar el usuario
        binding.btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
        binding.toolbarTitle.setOnClickListener{
            finish()
        }
    }

    private fun mostrarDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.etFecha.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun registrarUsuario() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val nombre = binding.etNombre.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()
        val puesto = binding.spinnerPuesto.selectedItem.toString()
        val fechaContratacion = binding.etFecha.text.toString().trim()
        val salario = binding.etSalario.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty() && nombre.isNotEmpty()
            && telefono.isNotEmpty() && puesto.isNotEmpty()
            && fechaContratacion.isNotEmpty() && salario.isNotEmpty()
        ) {
            // Validación de la fecha de contratación
            if (!validarFecha(fechaContratacion)) {
                Toast.makeText(this, "Fecha inválida", Toast.LENGTH_SHORT).show()
                return
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userId = user?.uid

                        val usuario = Usuario(
                            nombre,
                            telefono,
                            puesto,
                            convertirFechaAFirebaseTimestamp(fechaContratacion),
                            salario
                        )

                        userId?.let {
                            val userRef = firestore.collection("usuarios").document(it)
                            userRef.set(usuario)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarFecha(fecha: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(fecha)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun convertirFechaAFirebaseTimestamp(fecha: String): Timestamp {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.parse(fecha)
        return Timestamp(date)
    }
}
