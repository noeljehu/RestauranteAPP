package com.noelayllon.apprestauranteeee.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noelayllon.apprestauranteeee.R
import com.noelayllon.apprestauranteeee.databinding.ItemProductoBinding
import com.noelayllon.apprestauranteeee.modelo.Producto
import com.squareup.picasso.Picasso

class ProductoAdapter(
    private var productos: List<Producto>,
    private val onAgregarClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Método para actualizar la lista de productos
    fun actualizarProductos(nuevosProductos: List<Producto>) {
        productos = nuevosProductos
        notifyDataSetChanged()  // Notificamos al adaptador que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        with(holder.binding) {
            txtNombre.text = producto.nombre
            txtPrecio.text = "S/ %.2f".format(producto.precio ?: 0.0)

            // Cargar imagen con Picasso
            Picasso.get()
                .load(producto.imagenUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(imgComida)

            // Click en el botón agregar
            btnAgregar.setOnClickListener {
                onAgregarClick(producto)
            }
        }
    }

    override fun getItemCount(): Int = productos.size
}
