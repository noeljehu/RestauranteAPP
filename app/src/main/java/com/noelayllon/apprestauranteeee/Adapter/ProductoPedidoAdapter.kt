package com.noelayllon.apprestauranteeee.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noelayllon.apprestauranteeee.R
import com.noelayllon.apprestauranteeee.databinding.ItemPedidoBinding
import com.noelayllon.apprestauranteeee.modelo.Producto
import com.squareup.picasso.Picasso

class ProductoPedidoAdapter(
    private var productos: List<Producto>,
    private val onCantidadChange: (productoId: Int, cantidad: Int) -> Unit
) : RecyclerView.Adapter<ProductoPedidoAdapter.ViewHolder>() {

    private val cantidades: MutableMap<Int, Int> = mutableMapOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivComida: ImageView = itemView.findViewById(R.id.ivComida)
        val tvNombreComida: TextView = itemView.findViewById(R.id.tvNombreComida)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvIngredientes: TextView = itemView.findViewById(R.id.tvIngredientes)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        val btnMas: Button = itemView.findViewById(R.id.btnMas)
        val btnMenos: Button = itemView.findViewById(R.id.btnMenos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]

        holder.tvNombreComida.text = producto.nombre
        holder.tvPrecio.text = "S/ ${"%.2f".format(producto.precio)}"
        holder.tvIngredientes.text = producto.descripcion

        Picasso.get()
            .load(producto.imagenUrl)
            .placeholder(R.drawable.btn_round_gray)
            .error(R.drawable.error_image)
            .into(holder.ivComida)

        val cantidadActual = cantidades[producto.id] ?: 0
        holder.tvCantidad.text = cantidadActual.toString()

        holder.btnMas.setOnClickListener {
            val productoId = producto.id
            if (productoId != null) {
                val nuevaCantidad = cantidadActual + 1
                actualizarCantidad(productoId, nuevaCantidad)
                holder.tvCantidad.text = nuevaCantidad.toString()
            }
        }

        holder.btnMenos.setOnClickListener {
            val productoId = producto.id
            if (productoId != null && cantidadActual > 0) {
                val nuevaCantidad = cantidadActual - 1
                actualizarCantidad(productoId, nuevaCantidad)
                holder.tvCantidad.text = nuevaCantidad.toString()
            }
        }
    }

    private fun actualizarCantidad(productoId: Int, cantidad: Int) {
        cantidades[productoId] = cantidad
        onCantidadChange(productoId, cantidad)
    }

    override fun getItemCount(): Int = productos.size

    fun obtenerCantidades(): Map<Int, Int> {
        return cantidades.filterValues { it > 0 }
    }
}