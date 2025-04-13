package com.noelayllon.apprestauranteeee.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noelayllon.apprestauranteeee.databinding.ItemUsuarioBinding
import com.noelayllon.apprestauranteeee.modelo.Usuario

class UsuarioAdapter(
    private val listaUsuarios: List<Usuario>,
    private val onItemClick: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    inner class UsuarioViewHolder(private val binding: ItemUsuarioBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(usuario: Usuario) {
            binding.nombreTextView.text = usuario.nombre
            binding.verMasButton.setOnClickListener {
                onItemClick(usuario)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(listaUsuarios[position])
    }

    override fun getItemCount(): Int = listaUsuarios.size
}
