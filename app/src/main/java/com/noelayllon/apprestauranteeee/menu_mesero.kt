package com.noelayllon.apprestauranteeee

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.noelayllon.apprestauranteeee.databinding.ActivityLoginBinding
import com.noelayllon.apprestauranteeee.databinding.ActivityMenuCocineroBinding
import com.noelayllon.apprestauranteeee.databinding.ActivityMenuMeseroBinding

class menu_mesero : AppCompatActivity() {
    private lateinit var binding: ActivityMenuMeseroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_mesero)
        binding = ActivityMenuMeseroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}