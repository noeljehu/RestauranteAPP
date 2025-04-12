package com.noelayllon.apprestauranteeee.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noelayllon.apprestauranteeee.R
import com.noelayllon.apprestauranteeee.modelo.Cliente

class ClienteAdapter(
    private val clientes: List<Cliente>,
    private val onDetalleClick: (Cliente) -> Unit
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        val verMasButton: ImageView = itemView.findViewById(R.id.verMasButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.nombreTextView.text = cliente.nombre

        holder.verMasButton.setOnClickListener {
            onDetalleClick(cliente)
        }
    }

    override fun getItemCount() = clientes.size
}
