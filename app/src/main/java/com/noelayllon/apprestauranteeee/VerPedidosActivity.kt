package com.noelayllon.apprestauranteeee

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.noelayllon.apprestauranteeee.Adapter.ResumenPedidoAdapter
import com.noelayllon.apprestauranteeee.bd.AppDatabase
import com.noelayllon.apprestauranteeee.databinding.ActivityVerPedidosBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VerPedidosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerPedidosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvResumenPedidos.layoutManager = LinearLayoutManager(this)

        val db = AppDatabase.getDatabase(this)
        val pedidoDao = db.pedidoDao()
        val clienteDao = db.clienteDao()



        lifecycleScope.launch {
            val pedidos = pedidoDao.getAllPedidos()
            clienteDao.getAllClientes().collect { clientes ->
                val adapter = ResumenPedidoAdapter(pedidos, clientes) { pedidoSeleccionado ->
                    val intent = Intent(this@VerPedidosActivity, DetallePedidoActivity::class.java)
                    intent.putExtra("pedido_id", pedidoSeleccionado.id)
                    startActivity(intent)
                }
                binding.rvResumenPedidos.adapter = adapter
            }
        }


    }
}
