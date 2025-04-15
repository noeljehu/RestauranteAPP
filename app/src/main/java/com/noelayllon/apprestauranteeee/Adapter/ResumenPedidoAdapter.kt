package com.noelayllon.apprestauranteeee.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noelayllon.apprestauranteeee.databinding.ItemResumenPedidoBinding
import com.noelayllon.apprestauranteeee.modelo.Cliente
import com.noelayllon.apprestauranteeee.modelo.Pedido

class ResumenPedidoAdapter(
    private val pedidos: List<Pedido>,
    private val clientes: List<Cliente>,
    private val onVerDetallesClick: (Pedido) -> Unit // <- nuevo parámetro
) : RecyclerView.Adapter<ResumenPedidoAdapter.ResumenViewHolder>() {

    inner class ResumenViewHolder(val binding: ItemResumenPedidoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumenViewHolder {
        val binding = ItemResumenPedidoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ResumenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResumenViewHolder, position: Int) {
        val pedido = pedidos[position]
        val cliente = clientes.find { it.dni == pedido.dniCliente }
        val nombreCliente = cliente?.nombre ?: "Nombre no encontrado"

        with(holder.binding) {
            tvResumenPedidoId.text = "Pedido ID: ${pedido.id}"
            tvResumenDni.text = "Cliente: $nombreCliente"
            tvResumenMesa.text = "Mesa: ${pedido.mesa}"
            tvResumenFecha.text = "Fecha: ${pedido.fechaPedido}"
            tvResumenEstado.text = "Estado: ${pedido.estado}"
            tvResumenTotal.text = "Total: S/ ${String.format("%.2f", pedido.total)}"

            btnVerDetalles.setOnClickListener {
                onVerDetallesClick(pedido)
            }
        }
    }

    override fun getItemCount(): Int = pedidos.size
}
