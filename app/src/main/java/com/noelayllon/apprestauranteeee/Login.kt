package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.noelayllon.apprestauranteeee.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        configureGoogleSignIn()

        binding.btnSignIn.setOnClickListener {
            loginWithEmail()
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegistrarUsuario::class.java))
        }

        binding.googleSignInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun loginWithEmail() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    checkUserInFirestore(auth.currentUser!!)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Falló el login con Google", e)
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                checkUserInFirestore(auth.currentUser!!)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al autenticar con Google: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun checkUserInFirestore(user: FirebaseUser) {
        val userRef = firestore.collection("usuarios").document(user.uid)
        userRef.get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val puesto = doc.getString("puesto")
                    navigateToDashboard(puesto)
                } else {
                    // Si no existe el usuario y viene de Google, redirige directamente al menú mesero
                    navigateToDashboard("mesero")
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_LONG).show()
            }
    }

    private fun navigateToDashboard(puesto: String?) {
        if (puesto != null) {
            when (puesto) {
                "administrador" -> startActivity(Intent(this, MenuAdmin::class.java))
                "mesero" -> startActivity(Intent(this, menu_mesero::class.java))
                "cocinero" -> startActivity(Intent(this, menu_cocinero::class.java))
                else -> Toast.makeText(this, "Puesto desconocido", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Por si se logueó por correo y aún no completó su información
            startActivity(Intent(this, RegistrarUsuario::class.java))
        }
        finish()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            checkUserInFirestore(currentUser)
        }
    }
}
