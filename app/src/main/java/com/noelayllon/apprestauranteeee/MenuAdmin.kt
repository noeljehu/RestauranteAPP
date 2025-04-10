package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.noelayllon.apprestauranteeee.databinding.ActivityMenuAdminBinding

class MenuAdmin : AppCompatActivity() {
    // Declaramos el objeto binding
    private lateinit var binding: ActivityMenuAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_admin)

        binding = ActivityMenuAdminBinding.inflate(layoutInflater)

        setContentView(binding.root)
        // Configuramos el OnClickListener para el botón
        binding.btnNuevoCliente .setOnClickListener {
            // Iniciar la actividad de Registro de Cliente
            val intent = Intent(this, Registrodecliente::class.java)
            startActivity(intent)
        }
        binding.btnClientes.setOnClickListener {
            // Iniciar la actividad de Listar Clientes
            val intent = Intent(this, ListarCliente::class.java)
            startActivity(intent)
        }






    }
}