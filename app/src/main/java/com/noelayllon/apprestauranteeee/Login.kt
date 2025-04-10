package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.noelayllon.apprestauranteeee.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {
            // Llamar a la función de inicio de sesión
            login()
        }
        binding.txtRegister .setOnClickListener {
            // Navegar a la actividad de registro
            val intent = Intent(this, RegistrarUsuario::class.java)
            startActivity(intent)


        }

    }
    private fun login() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login exitoso
                        val user = auth.currentUser
                        if (user != null) {
                            // Recuperar el documento del usuario en Firestore
                            val userId = user.uid
                            val userRef = firestore.collection("usuarios").document(userId)

                            userRef.get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        // Obtener el puesto de trabajo del usuario desde Firestore
                                        val puesto = document.getString("puesto")

                                        // Verificar el puesto y redirigir
                                        when (puesto) {
                                            "administrador" -> {

                                                Toast.makeText(this, "Bienvenido Administrador", Toast.LENGTH_SHORT).show()
                                               startActivity(Intent(this, MenuAdmin::class.java))


                                            }
                                            "mesero" -> {
                                                // Redirigir a la pantalla de mesero
                                                Toast.makeText(this, "Bienvenido Mesero", Toast.LENGTH_SHORT).show()
                                              //  startActivity(Intent(this, MeseroActivity::class.java))
                                            }
                                            "cocinero" -> {
                                                Toast.makeText(this, "Bienvenido Cocinero", Toast.LENGTH_SHORT).show()
                                                // Redirigir a la pantalla de cocinero
                                                //startActivity(Intent(this, CocineroActivity::class.java))
                                            }
                                            "limpieza" -> {
                                                Toast.makeText(this, "Bienvenido Limpieza", Toast.LENGTH_SHORT).show()
                                                // Redirigir a la pantalla de limpieza
                                                //startActivity(Intent(this, LimpiezaActivity::class.java))
                                            }
                                            else -> {
                                                Toast.makeText(this, "Puesto desconocido", Toast.LENGTH_SHORT).show()
                                                // En caso de que el puesto no sea reconocido
                                               // Toast.makeText(this, "Puesto desconocido", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        finish() // Cerrar la actividad de login después de redirigir
                                    } else {
                                        Toast.makeText(this, "Error: No se encontró el puesto de trabajo", Toast.LENGTH_LONG).show()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(this, "Error al obtener los datos del usuario: ${exception.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                    } else {
                        // Error en el login
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            // Campos vacíos
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }




}