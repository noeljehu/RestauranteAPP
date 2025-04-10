package com.noelayllon.apprestauranteeee

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.noelayllon.apprestauranteeee.Adapter.ClienteAdapter
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityListarClienteBinding
import com.noelayllon.apprestauranteeee.modelo.Cliente
import kotlinx.coroutines.launch

class ListarCliente : AppCompatActivity() {
    private lateinit var binding: ActivityListarClienteBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListarClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        binding.clientsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Observa los cambios en la lista de clientes desde Room
        lifecycleScope.launch {
            database.clienteDao().getAllClientes().collect { listaClientes ->
                binding.clientsRecyclerView.adapter = ClienteAdapter(listaClientes) { cliente ->
                    // Aquí manejas el clic en "Detalles"
                    Toast.makeText(this@ListarCliente, "Cliente: ${cliente.nombre}", Toast.LENGTH_SHORT).show()
                    // Puedes abrir un Dialog, nueva Activity, etc.
                }
            }
        }
        // Configura el topAppBar2
        binding.topAppBar2.setNavigationOnClickListener {
            intent = intent.setClass(this, MenuAdmin::class.java)
            onBackPressed()
        }


    }
}