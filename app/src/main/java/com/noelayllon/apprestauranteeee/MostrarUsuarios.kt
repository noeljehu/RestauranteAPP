package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.noelayllon.apprestauranteeee.Adapter.UsuarioAdapter
import com.noelayllon.apprestauranteeee.databinding.ActivityMostrarUsuariosBinding
import com.noelayllon.apprestauranteeee.modelo.Usuario

class MostrarUsuarios : AppCompatActivity() {

    private lateinit var binding: ActivityMostrarUsuariosBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: UsuarioAdapter
    private lateinit var detalleUsuarioLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityMostrarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        binding.recyclerViewUsuarios.layoutManager = LinearLayoutManager(this)

        // Inicializa el launcher para recibir resultados de DetalleUsuario
        detalleUsuarioLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                cargarUsuarios()
            }
        }

        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        firestore.collection("usuarios")
            .get()
            .addOnSuccessListener { documents ->
                val usuariosList = mutableListOf<Usuario>()
                for (document in documents) {
                    val usuario = document.toObject(Usuario::class.java)
                    usuariosList.add(usuario)
                }

                adapter = UsuarioAdapter(usuariosList) { usuario ->
                    val intent = Intent(this, DetalleUsuario::class.java).apply {
                        putExtra("usuario_nombre", usuario.nombre)
                        putExtra("usuario_telefono", usuario.telefono)
                        putExtra("usuario_puesto", usuario.puesto)
                        putExtra("usuario_salario", usuario.salario)
                        putExtra("usuario_fechaContratacion", usuario.fechaContratacion)
                    }
                    detalleUsuarioLauncher.launch(intent)
                }

                binding.recyclerViewUsuarios.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar los usuarios: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
