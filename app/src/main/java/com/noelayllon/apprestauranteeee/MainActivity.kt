package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.noelayllon.apprestauranteeee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // Declaramos el objeto binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializamos el binding con el layout correspondiente
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Establecemos el layout con el binding
        setContentView(binding.root)

        // Configuramos el OnClickListener para el botón
        binding.button.setOnClickListener {
            // Iniciar la actividad de Login
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}
