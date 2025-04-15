package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.noelayllon.apprestauranteeee.databinding.ActivityMenuAdminBinding

class MenuAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityMenuAdminBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        binding = ActivityMenuAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        initGoogleClient()

        // Botones para navegación
        binding.btnNuevoCliente.setOnClickListener {
            startActivity(Intent(this, Registrodecliente::class.java))
        }
        binding.btnClientes.setOnClickListener {
            startActivity(Intent(this, ListarCliente::class.java))
        }
        binding.btnNuevoProducto.setOnClickListener {
            startActivity(Intent(this, RegistrarProducto::class.java))
        }
        binding.btnProductos.setOnClickListener {
            startActivity(Intent(this, ListarProducto::class.java))
        }
        binding.btnNuevoTrabajador.setOnClickListener {
            startActivity(Intent(this, RegistrarUsuario::class.java))
        }
        binding.btnTrabajadores.setOnClickListener {
            startActivity(Intent(this, MostrarUsuarios::class.java))
        }
        binding.btnNuevoPedido.setOnClickListener {
            startActivity(Intent(this, RegistrarPedido::class.java))
        }
        binding.btnPedidos.setOnClickListener {
            startActivity(Intent(this, VerPedidosActivity::class.java))
        }

        // Botón cerrar sesión
        binding.btnCerrarSesion.setOnClickListener {
            signOut()
        }
    }

    private fun initGoogleClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
