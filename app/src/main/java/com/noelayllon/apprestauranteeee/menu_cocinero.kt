package com.noelayllon.apprestauranteeee

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.noelayllon.apprestauranteeee.databinding.ActivityLoginBinding
import com.noelayllon.apprestauranteeee.databinding.ActivityMainBinding
import com.noelayllon.apprestauranteeee.databinding.ActivityMenuCocineroBinding

class menu_cocinero : AppCompatActivity() {
    private lateinit var binding: ActivityMenuCocineroBinding // Declaramos el objeto binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_menu_cocinero)

        binding = ActivityMenuCocineroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}