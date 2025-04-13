package com.noelayllon.apprestauranteeee

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.noelayllon.apprestauranteeee.databinding.ActivityDetalleUsuarioBinding
import com.noelayllon.apprestauranteeee.modelo.Usuario
import java.text.SimpleDateFormat
import java.util.*

class DetalleUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleUsuarioBinding
    private lateinit var firestore: FirebaseFirestore
    private var fechaSeleccionada: Timestamp = Timestamp.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityDetalleUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        val nombre = intent.getStringExtra("usuario_nombre")
        val telefono = intent.getStringExtra("usuario_telefono")
        val puesto = intent.getStringExtra("usuario_puesto")
        val salario = intent.getStringExtra("usuario_salario")
        val fechaContratacionTimestamp = intent.getSerializableExtra("usuario_fechaContratacion") as? Timestamp
        fechaSeleccionada = fechaContratacionTimestamp ?: Timestamp.now()

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.etFecha.setText(formatter.format(fechaSeleccionada.toDate()))

        val puestos = resources.getStringArray(R.array.puestos_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, puestos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPuesto.adapter = adapter

        binding.etNombre.setText(nombre)
        binding.etTelefono.setText(telefono)
        binding.etSalario.setText(salario)

        val puestoPosicion = puestos.indexOf(puesto)
        if (puestoPosicion >= 0) {
            binding.spinnerPuesto.setSelection(puestoPosicion)
        }

        binding.etFecha.setOnClickListener {
            mostrarDatePickerDialog()
        }

        binding.btnActualizar.setOnClickListener {
            val usuarioActualizado = Usuario(
                nombre = binding.etNombre.text.toString(),
                telefono = binding.etTelefono.text.toString(),
                puesto = binding.spinnerPuesto.selectedItem.toString(),
                salario = binding.etSalario.text.toString(),
                fechaContratacion = fechaSeleccionada
            )
            actualizarUsuario(usuarioActualizado)
        }

        binding.btnEliminar.setOnClickListener {
            eliminarUsuario(nombre ?: "")
        }
    }

    private fun mostrarDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                fechaSeleccionada = Timestamp(selectedDate.time)
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.etFecha.setText(formatter.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun actualizarUsuario(usuario: Usuario) {
        firestore.collection("usuarios")
            .whereEqualTo("nombre", usuario.nombre)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    firestore.collection("usuarios").document(document.id)
                        .update(
                            "nombre", usuario.nombre,
                            "telefono", usuario.telefono,
                            "puesto", usuario.puesto,
                            "salario", usuario.salario,
                            "fechaContratacion", usuario.fechaContratacion
                        )
                        .addOnSuccessListener {
                            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Error al actualizar: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al buscar usuario: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarUsuario(nombre: String) {
        firestore.collection("usuarios")
            .whereEqualTo("nombre", nombre)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    firestore.collection("usuarios").document(document.id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Error al eliminar: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al buscar usuario: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
